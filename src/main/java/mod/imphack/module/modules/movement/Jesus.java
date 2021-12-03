package mod.imphack.module.modules.movement;

import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.ModeSetting;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

public class Jesus extends Module {

	final ModeSetting mode = new ModeSetting("Mode", this, "bounce", "bounce", "packet", "spacebar");
	final FloatSetting speed = new FloatSetting("Float Speed", this, 1.0f);
	final BooleanSetting dmg = new BooleanSetting("Packet Anti-FallDamage", this, false);

	public Jesus() {
		super("Jesus", "makes you walk on water", Category.MOVEMENT);

		this.addSetting(mode);
		this.addSetting(speed);
		this.addSetting(dmg);
	}

	@Override
	protected void onEnable() {

		MinecraftForge.EVENT_BUS.register(this);

		ImpHackEventBus.EVENT_BUS.subscribe(this);

		Main.config.Save();
	}

	@Override
	public void onUpdate() {
		if (mc.player.isRiding())
			return;
		if (mode.getMode().equalsIgnoreCase("bounce")) {
			if (mc.player.isInWater()) {
				mc.player.motionY = 0.03999999910593033D * speed.value;
			}
		}
		if (mode.getMode().equalsIgnoreCase("packet")) {
			if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.2f, mc.player.posZ))
					.getBlock() == Block.getBlockFromName("water") && mc.player.motionY < 0.0D) {
				mc.player.posY -= mc.player.motionY;
				mc.player.motionY = 0;
				if (dmg.enabled)
					mc.player.fallDistance = 0;
			}
			if (mc.player.isInWater()) {
				mc.player.motionY = 0.03999999910593033D * speed.value;
			}
		}
		if (mode.getMode().equalsIgnoreCase("spacebar")) {
			if (mc.player.isInWater()) {
				mc.player.motionY += 0.03999999910593033D;
			}
		}
	}
}
