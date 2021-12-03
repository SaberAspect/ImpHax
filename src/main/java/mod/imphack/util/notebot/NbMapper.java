package mod.imphack.util.notebot;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNote;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.util.ArrayList;

public class NbMapper {
	private static NbInstrument inst1, inst2, inst3, inst4, inst5, inst6, inst7, inst8, inst9, inst10, inst11, inst12,
			inst13, inst14, inst15, inst16;

	public static void Unmap() {
		inst1 = null;
		inst2 = null;
		inst3 = null;
		inst4 = null;
		inst5 = null;
		inst6 = null;
		inst7 = null;
		inst8 = null;
		inst9 = null;
		inst10 = null;
		inst11 = null;
		inst12 = null;
		inst13 = null;
		inst14 = null;
		inst15 = null;
		inst16 = null;
	}

	public static NbInstrument GetInstrument(int inst) {
		switch (inst) {
		case 1:
			return inst1;
		case 2:
			return inst2;
		case 3:
			return inst3;
		case 4:
			return inst4;
		case 5:
			return inst5;
		case 6:
			return inst6;
		case 7:
			return inst7;
		case 8:
			return inst8;
		case 9:
			return inst9;
		case 10:
			return inst10;
		case 11:
			return inst11;
		case 12:
			return inst12;
		case 13:
			return inst13;
		case 14:
			return inst14;
		case 15:
			return inst15;
		case 16:
			return inst16;
		}

		return null;
	}

	public static Color GetInstrumentColor(int inst) {
		switch (inst) {
		case 1:
			return new Color(188, 152, 98);
		case 2:
			return new Color(127, 127, 127);
		case 3:
			return new Color(255, 230, 150);
		case 4:
			return new Color(255, 255, 255);
		case 5:
			return new Color(108, 164, 93);
		case 6:
			return new Color(150, 164, 93);
		case 7:
			return new Color(28, 164, 93);
		case 8:
			return new Color(108, 55, 93);
		case 9:
			return new Color(108, 55, 255);
		case 10:
			return new Color(108, 255, 0);
		case 11:
			return new Color(108, 164, 100);
		case 12:
			return new Color(108, 64, 150);
		case 13:
			return new Color(8, 64, 93);
		case 14:
			return new Color(208, 164, 93);
		case 15:
			return new Color(208, 264, 93);
		case 16:
			return new Color(208, 264, 193);
		}

		return null;
	}

	private static BlockPos GetPosition(BlockPos center, int forward, int left) {
		double cx = center.getX();
		double cy = center.getY();
		double cz = center.getZ();

		EnumFacing facing = Minecraft.getMinecraft().player.getHorizontalFacing();

		Vector3d forwardv = new Vector3d(0, 0, 0);
		Vector3d leftv = new Vector3d(0, 0, 0);

		switch (facing) {
		case NORTH:
			forwardv.z = -1;
			leftv.x = -1;
			break;
		case EAST:
			forwardv.x = 1;
			leftv.z = -1;
			break;
		case SOUTH:
			forwardv.z = 1;
			leftv.x = 1;
			break;
		case WEST:
			forwardv.x = -1;
			leftv.z = 1;
			break;
		default:
			break;
		}

		double forwardx = forwardv.x * forward;
		double forwardz = forwardv.z * forward;
		double leftx = leftv.x * left;
		double leftz = leftv.z * left;

		return new BlockPos(cx + forwardx + leftx, cy, cz + forwardz + leftz);
	}

	public static void MapInstruments() {

		inst1 = new NbInstrument(1);

		ArrayList<Block> blockList = new ArrayList <>();

		blockList.add(Blocks.PLANKS);

		addNoteLoop(inst1, blockList);

		inst2 = new NbInstrument(2);

		blockList = new ArrayList <>();

		blockList.add(Blocks.SAND);
		blockList.add(Blocks.GRAVEL);
		blockList.add(Blocks.CONCRETE_POWDER);

		addNoteLoop(inst2, blockList);

		inst3 = new NbInstrument(3);

		blockList = new ArrayList <>();

		blockList.add(Blocks.GLASS);
		blockList.add(Blocks.SEA_LANTERN);
		blockList.add(Blocks.BEACON);

		addNoteLoop(inst3, blockList);

		inst4 = new NbInstrument(4);

		blockList = new ArrayList <>();

		blockList.add(Blocks.STONE);
		blockList.add(Blocks.NETHERRACK);
		blockList.add(Blocks.OBSIDIAN);
		blockList.add(Blocks.QUARTZ_BLOCK);
		blockList.add(Blocks.SANDSTONE);
		blockList.add(Blocks.COAL_ORE);
		blockList.add(Blocks.DIAMOND_ORE);
		blockList.add(Blocks.EMERALD_ORE);
		blockList.add(Blocks.GOLD_ORE);
		blockList.add(Blocks.IRON_ORE);
		blockList.add(Blocks.LAPIS_ORE);
		blockList.add(Blocks.LIT_REDSTONE_ORE);
		blockList.add(Blocks.QUARTZ_ORE);
		blockList.add(Blocks.REDSTONE_ORE);
		blockList.add(Blocks.BRICK_BLOCK);
		blockList.add(Blocks.BEDROCK);
		blockList.add(Blocks.CONCRETE);

		addNoteLoop(inst4, blockList);

		inst5 = new NbInstrument(5);

		blockList = new ArrayList <>();

		blockList.add(Blocks.GOLD_BLOCK);

		addNoteLoop(inst5, blockList);

		inst6 = new NbInstrument(6);

		blockList = new ArrayList <>();

		blockList.add(Blocks.CLAY);

		addNoteLoop(inst6, blockList);

		inst7 = new NbInstrument(7);

		blockList = new ArrayList <>();

		blockList.add(Blocks.PACKED_ICE);

		addNoteLoop(inst7, blockList);

		inst8 = new NbInstrument(8);

		blockList = new ArrayList <>();

		blockList.add(Blocks.WOOL);

		addNoteLoop(inst8, blockList);

		inst9 = new NbInstrument(9);

		blockList = new ArrayList <>();

		blockList.add(Blocks.BONE_BLOCK);

		addNoteLoop(inst9, blockList);

		inst10 = new NbInstrument(10);

		blockList = new ArrayList <>();

		blockList.add(Blocks.IRON_BLOCK);

		addNoteLoop(inst10, blockList);

		inst11 = new NbInstrument(11);

		blockList = new ArrayList <>();

		blockList.add(Blocks.SOUL_SAND);

		addNoteLoop(inst11, blockList);

		inst12 = new NbInstrument(12);

		blockList = new ArrayList <>();

		blockList.add(Blocks.PUMPKIN);

		addNoteLoop(inst12, blockList);

		inst13 = new NbInstrument(13);

		blockList = new ArrayList <>();

		blockList.add(Blocks.EMERALD_BLOCK);

		addNoteLoop(inst13, blockList);

		inst14 = new NbInstrument(14);

		blockList = new ArrayList <>();

		blockList.add(Blocks.HAY_BLOCK);

		addNoteLoop(inst14, blockList);

		inst15 = new NbInstrument(15);

		blockList = new ArrayList <>();

		blockList.add(Blocks.GLOWSTONE);

		addNoteLoop(inst15, blockList);

		inst16 = new NbInstrument(16);

		blockList = new ArrayList <>();

		blockList.add(Blocks.GLOWSTONE);
		blockList.add(Blocks.HAY_BLOCK);
		blockList.add(Blocks.EMERALD_BLOCK);
		blockList.add(Blocks.PUMPKIN);
		blockList.add(Blocks.SOUL_SAND);
		blockList.add(Blocks.IRON_BLOCK);
		blockList.add(Blocks.BONE_BLOCK);
		blockList.add(Blocks.WOOL);
		blockList.add(Blocks.PACKED_ICE);
		blockList.add(Blocks.CLAY);
		blockList.add(Blocks.GOLD_BLOCK);
		blockList.add(Blocks.STONE);
		blockList.add(Blocks.NETHERRACK);
		blockList.add(Blocks.OBSIDIAN);
		blockList.add(Blocks.QUARTZ_BLOCK);
		blockList.add(Blocks.SANDSTONE);
		blockList.add(Blocks.COAL_ORE);
		blockList.add(Blocks.DIAMOND_ORE);
		blockList.add(Blocks.EMERALD_ORE);
		blockList.add(Blocks.GOLD_ORE);
		blockList.add(Blocks.IRON_ORE);
		blockList.add(Blocks.LAPIS_ORE);
		blockList.add(Blocks.LIT_REDSTONE_ORE);
		blockList.add(Blocks.QUARTZ_ORE);
		blockList.add(Blocks.REDSTONE_ORE);
		blockList.add(Blocks.BRICK_BLOCK);
		blockList.add(Blocks.BEDROCK);
		blockList.add(Blocks.CONCRETE);
		blockList.add(Blocks.GLASS);
		blockList.add(Blocks.SEA_LANTERN);
		blockList.add(Blocks.BEACON);
		blockList.add(Blocks.SAND);
		blockList.add(Blocks.GRAVEL);
		blockList.add(Blocks.CONCRETE_POWDER);
		blockList.add(Blocks.PLANKS);

		EntityPlayerSP ply = Minecraft.getMinecraft().player;
		BlockPos center = new BlockPos(ply.posX, (int) ply.posY, ply.posZ);

		int curpitch = 0;

		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				for (int k = -5; k <= 5; k++) {
					if (Minecraft.getMinecraft().world.getBlockState(GetPosition(center, i, j).add(0, k, 0))
							.getBlock() instanceof BlockNote
							&& !blockList.contains(Minecraft.getMinecraft().world
									.getBlockState(GetPosition(center, i, j).add(0, k - 1, 0)).getBlock())) {
						if (curpitch == 25)
							return;
						inst16.AddNote(curpitch++, GetPosition(center, i, j).add(0, k, 0));
					}
				}
			}
		}
	}

	public static void addNoteLoop(NbInstrument n, ArrayList<Block> b) {

		EntityPlayerSP ply = Minecraft.getMinecraft().player;
		BlockPos center = new BlockPos(ply.posX, (int) ply.posY, ply.posZ);

		int curpitch = 0;

		for (int i = -5; i <= 5; i++) {
			for (int j = -5; j <= 5; j++) {
				for (int k = -5; k <= 5; k++) {
					if (Minecraft.getMinecraft().world.getBlockState(GetPosition(center, i, j).add(0, k, 0))
							.getBlock() instanceof BlockNote
							&& b.contains(Minecraft.getMinecraft().world
									.getBlockState(GetPosition(center, i, j).add(0, k - 1, 0)).getBlock())) {
						if (curpitch == 25)
							return;
						n.AddNote(curpitch++, GetPosition(center, i, j).add(0, k, 0));
					}
				}
			}
		}
	}
}