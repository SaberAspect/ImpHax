package mod.imphack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Utils {

	public static final Minecraft mc = Minecraft.getMinecraft();
	
	
	
	 public static Vec3d getEyesPos() {
	        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
	    }
	 
	 public static  float[] getNeededRotations(Vec3d vec, float yaw, float pitch) {
	        final Vec3d eyesPos = getEyesPos();
	        final double diffX = vec.x - eyesPos.x;
	        final double diffY = vec.y - eyesPos.y;
	        final double diffZ = vec.z - eyesPos.z;

	        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

	        final float rotationYaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
	        final float rotationPitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

	        return new float[]{
	                updateRotation(mc.player.rotationYaw, rotationYaw, yaw / 4),
	                updateRotation(mc.player.rotationPitch, rotationPitch, pitch / 4)
	        };
	    }
	 
	 public static float updateRotation(float PlayerRotation, float Modified, float MaxValueAccepted) {
	        float degrees = MathHelper.wrapDegrees(Modified - PlayerRotation);
	        if (MaxValueAccepted != 0) {
	            if (degrees > MaxValueAccepted) {
	                degrees = MaxValueAccepted;
	            }
	            if (degrees < -MaxValueAccepted) {
	                degrees = -MaxValueAccepted;
	            }
	        }
	        return PlayerRotation + degrees;
	    }
	 
	 public static float getYaw(Entity entity) {
	        double x = entity.posX - PlayerUtil.player().posX;
	        double z = entity.posZ - PlayerUtil.player().posZ;
	        double yaw = Math.atan2(x, z) * 57.29577951308232;
	        yaw = -yaw;
	        return (float) yaw;
	    }
	 
	   public static float getPitch(Entity entity) {
	        double y = entity.posY - PlayerUtil.player().posY;
	        y /= PlayerUtil.player().getDistance(entity);
	        double pitch = Math.asin(y) * 57.29577951308232;
	        pitch = -pitch;
	        return (float) pitch;
	    }
}
