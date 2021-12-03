package mod.imphack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtil {
	public static double[] directionSpeed(double speed) {
		final Minecraft mc = Minecraft.getMinecraft();
		float forward = mc.player.movementInput.moveForward;
		float side = mc.player.movementInput.moveStrafe;
		float yaw = mc.player.prevRotationYaw
				+ (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

		if (forward != 0) {
			if (side > 0) {
				yaw += (forward > 0 ? -45 : 45);
			} else if (side < 0) {
				yaw += (forward > 0 ? 45 : -45);
			}
			side = 0;

			if (forward > 0) {
				forward = 1;
			} else if (forward < 0) {
				forward = -1;
			}
		}

		final double sin = Math.sin(Math.toRadians(yaw + 90));
		final double cos = Math.cos(Math.toRadians(yaw + 90));
		final double posX = (forward * speed * cos + side * speed * sin);
		final double posZ = (forward * speed * sin - side * speed * cos);
		return new double[] { posX, posZ };
	}

	public static double degToRad(double deg) {
		return deg * 0.01745329238474369;
	}

	public static double radToDeg(final double rad) {
		return rad * 57.295780181884766;
	}

	public static float[] calcAngle(final Vec3d from, final Vec3d to) {
		final double difX = to.x - from.x;
		final double difY = (to.y - from.y) * -1.0;
		final double difZ = to.z - from.z;
		final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
		return new float[] { (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0),
				(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
	}

	public static float interpolate(float val, float fmin, float fmax, float tmin, float tmax) {
		float fdist = fmax - fmin;
		float tdist = tmax - tmin;

		float scaled = (val - fmin) / fdist;

		return tmin + (scaled * tdist);
	}

	public static Vec3d interpolateEntity(Entity entity, float time) {
		return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time,
				entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time,
				entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
	}

	public static float clamp(float val, float min, float max) {
		if (val <= min) {
			val = min;
		}
		if (val >= max) {
			val = max;
		}
		return val;
	}
	
	 public static int getMiddle(int i, int j) {

	        return (i + j) / 2;
	    }
	 
	 public static double getDistance(Vec3d pos, double x, double y, double z) {
	        final double deltaX = pos.x - x;
	        final double deltaY = pos.y - y;
	        final double deltaZ = pos.z - z;
	        return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
	    }


}
