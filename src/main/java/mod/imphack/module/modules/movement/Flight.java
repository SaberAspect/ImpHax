package mod.imphack.module.modules.movement;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class Flight extends Module {

	final FloatSetting hSpeed = new FloatSetting("Horizontal Speed", this, 1.0f);
	final FloatSetting vSpeed = new FloatSetting("Vertical Speed", this, 1.0f);
	final FloatSetting glide = new FloatSetting("Downward Glide Speed", this, 0.0f);
	final BooleanSetting dmg = new BooleanSetting("Packet Anti-FallDamage", this, false);

	public Flight() {
		super("Flight", "Allows Flight", Category.MOVEMENT);

		this.addSetting(hSpeed);
		this.addSetting(vSpeed);
		this.addSetting(glide);
		addSetting(dmg);
	}

	@Override
	public void onUpdate() {
		double[] pos = { mc.player.prevPosX, mc.player.prevPosY, mc.player.prevPosZ };

		float player_speed = 0.2873f;
		float rotation_yaw = mc.player.rotationYaw;
		float move_forward = mc.player.movementInput.moveForward;
		float move_strafe = mc.player.movementInput.moveStrafe;
		pos[0] += ((move_forward * player_speed) * Math.cos(Math.toRadians((rotation_yaw + 90.0f)))
				+ (move_strafe * player_speed) * Math.sin(Math.toRadians((rotation_yaw + 90.0f)))) * hSpeed.value;
		pos[2] += ((move_forward * player_speed) * Math.sin(Math.toRadians((rotation_yaw + 90.0f)))
				- (move_strafe * player_speed) * Math.cos(Math.toRadians((rotation_yaw + 90.0f)))) * hSpeed.value;

		double vert = 0;
		if (mc.player.movementInput.jump) {
			vert += 1d * vSpeed.value;
		}
		if (mc.player.movementInput.sneak) {
			vert -= 1d * vSpeed.value;
		}
		vert -= glide.value;
		pos[1] += vert;

		mc.player.setPosition(pos[0], pos[1], pos[2]);
		final float yaw = YawRotationUtility();
		if (mc.player.movementInput.backKeyDown || mc.player.movementInput.forwardKeyDown
				|| mc.player.movementInput.leftKeyDown || mc.player.movementInput.rightKeyDown) {
			mc.player.motionX -= MathHelper.sin(yaw) * 0.017453292f * hSpeed.value;
			mc.player.motionZ += MathHelper.cos(yaw) * 0.017453292f * hSpeed.value;

			mc.player.connection
					.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
		}
		if (dmg.enabled)
			mc.player.fallDistance = 0;
	}

	private float YawRotationUtility() {
		float rotationYaw = mc.player.rotationYaw;
		if (mc.player.moveForward < 0.0f) {
			rotationYaw += 180.0f;
		}
		float n = 1.0f;
		if (mc.player.moveForward < 0.0f) {
			n = -0.5f;
		} else if (mc.player.moveForward > 0.0f) {
			n = 0.5f;
		}
		if (mc.player.moveStrafing > 0.0f) {
			rotationYaw -= 90.0f * n;
		}
		if (mc.player.moveStrafing < 0.0f) {
			rotationYaw += 90.0f * n;
		}
		return rotationYaw * 0.017453292f;
	}
}