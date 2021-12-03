package mod.imphack.module.modules.movement;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import net.minecraft.client.Minecraft;

public class ElytraFlight extends Module {

	final FloatSetting downSpeed = new FloatSetting("DownSpeed", this, 0.15f);
	final FloatSetting upSpeed = new FloatSetting("UpSpeed", this, 2.0f);
	final FloatSetting baseSpeed = new FloatSetting("BaseSpeed", this, 0.15f);
	final BooleanSetting noVelocity = new BooleanSetting("noVelocity", this, true);

	public ElytraFlight() {
		super("ElytraFlight", "Fly with Elytras", Category.MOVEMENT);

		this.addSetting(downSpeed);
		this.addSetting(upSpeed);
		this.addSetting(baseSpeed);
		this.addSetting(noVelocity);
	}

	@Override
	public void onUpdate() {

		if (!Minecraft.getMinecraft().player.isElytraFlying())
			return;

		float yaw = Minecraft.getMinecraft().player.rotationYaw;
		float pitch = Minecraft.getMinecraft().player.rotationPitch;

		if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()) {
			Minecraft.getMinecraft().player.motionX -= Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
					* baseSpeed.getValue();
			Minecraft.getMinecraft().player.motionZ += Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))
					* baseSpeed.getValue();
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
			Minecraft.getMinecraft().player.motionY += upSpeed.getValue();
		}
		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
			Minecraft.getMinecraft().player.motionY -= downSpeed.getValue();
		}

		if (noVelocity.isEnabled())
			if (!Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()
					&& !Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()
					&& !Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
				mc.player.setVelocity(0, 0, 0);
			}
	}
}
