package mod.imphack.util.notebot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import javax.sound.midi.*;
import java.awt.*;

public class NbUtil {

	public static float Interpolate(float val, float fmin, float fmax, float tmin, float tmax) {
		float fdist = fmax - fmin;
		float tdist = tmax - tmin;

		float scaled = (val - fmin) / fdist;

		return tmin + (scaled * tdist);
	}

	public static float LongInterpolate(long val, long fmin, long fmax, float tmin, float tmax) {
		long fdist = fmax - fmin;
		float tdist = tmax - tmin;

		float scaled = (val - fmin) / (float) fdist;

		return tmin + (scaled * tdist);
	}

	public static Color InterpolateColor(float val, float fmin, float fmax, Color fcolor, Color tcolor) {
		float r = Interpolate(val, fmin, fmax, fcolor.getRed(), tcolor.getRed());
		float g = Interpolate(val, fmin, fmax, fcolor.getGreen(), tcolor.getGreen());
		float b = Interpolate(val, fmin, fmax, fcolor.getBlue(), tcolor.getBlue());
		float a = Interpolate(val, fmin, fmax, fcolor.getAlpha(), tcolor.getAlpha());

		return new Color((int) r, (int) g, (int) b, (int) a);
	}

	public static void LookAt(double x, double y, double z) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP ply = mc.player;

		double px = ply.posX;
		double py = ply.posY;
		double pz = ply.posZ;

		double dirx = x - px;
		double diry = y - py;
		double dirz = z - pz;

		double dirlen = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

		dirx /= dirlen;
		diry /= dirlen;
		dirz /= dirlen;

		double pitch = Math.asin(diry);
		double yaw = Math.atan2(dirz, dirx);

		pitch = pitch * 180.0 / Math.PI;
		yaw = yaw * 180.0 / Math.PI;

		yaw -= 90f;

		ply.rotationPitch = -(float) pitch;
		ply.rotationYaw = (float) yaw;
	}

	public static void RightClick(BlockPos pos) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP ply = mc.player;
		// ItemStack item = ply.getHeldItemMainhand(); removed, item does NOT get used
		// && causing errors

		// Block block = mc.theWorld.getBlockState(pos).getBlock();

		TileEntity tile = mc.world.getTileEntity(pos);

		if (tile == null || tile.getBlockType().getLocalizedName().equals("tile.air"))
			return;

		LookAt(pos.getX() + 0.5f, pos.getY() - 1f, pos.getZ() + 0.5f);

		EnumFacing side = ply.getHorizontalFacing();

		if (mc.objectMouseOver != null)
			side = mc.objectMouseOver.sideHit;

		// onplayerrightclick
		ply.swingArm(EnumHand.MAIN_HAND);
		mc.playerController.processRightClickBlock(ply, mc.world, pos, side, mc.objectMouseOver.hitVec,
				EnumHand.MAIN_HAND);
	}

	public static void LeftClick(BlockPos pos) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP ply = mc.player;

		// Block block = mc.theWorld.getBlockState(pos).getBlock(); /this was previously
		// commented out
		TileEntity tile = mc.world.getTileEntity(pos);

		if (tile == null || tile.getBlockType().getLocalizedName().equals("tile.air"))
			return;

		LookAt(pos.getX() + 0.5f, pos.getY() - 1f, pos.getZ() + 0.5f);

		EnumFacing side = ply.getHorizontalFacing();

		if (mc.objectMouseOver != null)
			side = mc.objectMouseOver.sideHit;

		// clickblock
		ply.swingArm(EnumHand.MAIN_HAND);
		mc.playerController.clickBlock(pos, side);
	}

	public static String InstrumentIntToString(int inst) {
		switch (inst) {
		case 1:
			return "Bass Guitar";
		case 2:
			return "Bass Drum";
		case 3:
			return "Snare Drum";
		case 4:
			return "Sticks";
		case 5:
			return "Piano";
		}

		return "None";
	}

	public static int InstrumentStringToInt(String inst) {
		if (inst.equals("Wood") || inst.equals("Bass Guitar"))
			return 1;
		if (inst.equals("Stone") || inst.equals("Bass Drum"))
			return 2;
		if (inst.equals("Sand") || inst.equals("Snare Drum"))
			return 3;
		if (inst.equals("Glass") || inst.equals("Sticks"))
			return 4;
		if (inst.equals("Air") || inst.equals("Piano"))
			return 5;

		return 0;
	}

	public static String InstrumentIntToResource(int inst) {
		switch (inst) {
		case 1:
			return "note.bassattack";
		case 2:
			return "note.bd";
		case 3:
			return "note.snare";
		case 4:
			return "note.hat";
		case 5:
			return "note.harp";
		}

		return "None";
	}

	public static String GetRealNote(int note) {
		int noter = note % 12;

		switch (noter) {
		case 0:
			return "F#";
		case 1:
			return "G";
		case 2:
			return "G#";
		case 3:
			return "A";
		case 4:
			return "A#";
		case 5:
			return "B";
		case 6:
			return "C";
		case 7:
			return "C#";
		case 8:
			return "D";
		case 9:
			return "D#";
		case 10:
			return "E";
		case 11:
			return "F";
		case 12:
			return "Gb";
		}

		return "null";
	}

	public static String GetInstrumentName(int instrument) throws MidiUnavailableException {
		Synthesizer synth = MidiSystem.getSynthesizer();

		Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();

		int inst = instrument - 1;

		if (inst >= instruments.length || inst < 0)
			return "Unknown";

		return instruments[inst].getName();

	}

	public static void PlayNote(int note, int instrument) throws MidiUnavailableException, InterruptedException {
		Synthesizer synth = MidiSystem.getSynthesizer();

		synth.open();

		MidiChannel[] channels = synth.getChannels();

		Soundbank defaultbank = synth.getDefaultSoundbank();
		synth.loadAllInstruments(defaultbank);

		Instrument[] instruments = defaultbank.getInstruments();

		Patch patch = instruments[instrument].getPatch();
		channels[0].programChange(patch.getBank(), patch.getProgram());

		channels[0].noteOn(note, 600);
		Thread.sleep(1000);
		channels[0].noteOff(note, 600);

		synth.close();
	}

}