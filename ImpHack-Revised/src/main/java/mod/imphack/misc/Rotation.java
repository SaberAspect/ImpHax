package mod.imphack.misc;

import mod.imphack.util.Timer;
import net.minecraft.client.Minecraft;

public class Rotation {

	
	Minecraft mc = Minecraft.getMinecraft();
	  public float yaw;
	    public float pitch;
	    public RotationPriority rotationPriority;
	    public RotationMode mode;
	    public Timer rotationStay = new Timer();

	    public Rotation(float yaw, float pitch, RotationMode mode, RotationPriority rotationPriority) {
	        this.yaw = yaw;
	        this.pitch = pitch;
	        this.mode = mode;
	        this.rotationPriority = rotationPriority;

	        rotationStay.reset();
	    }

	    // updates player rotations here
	    public void updateRotations() {
	        try {
	            switch (this.mode) {
	                case Packet:
	                    mc.player.renderYawOffset = this.yaw;
	                    mc.player.rotationYawHead = this.yaw;
	                    break;
	                case Legit:
	                    mc.player.rotationYaw = this.yaw;
	                    mc.player.rotationPitch = this.pitch;
	                    break;
	                case None:
	                    break;
	            }
	        } catch (Exception ignored) {

	        }
	    }

	    public void restoreRotation() {
	        try {
	            this.yaw = mc.player.rotationYaw;
	            this.pitch = mc.player.rotationPitch;
	            mc.player.rotationYawHead = mc.player.rotationYaw;

	            rotationStay.reset();
	        } catch (Exception ignored) {

	        }
	    }

	    public enum RotationMode {
	        Packet,
	        Legit,
	        None
	    }
	}
