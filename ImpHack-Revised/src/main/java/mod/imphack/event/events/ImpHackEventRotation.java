package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;

public class ImpHackEventRotation extends ImpHackEventCancellable {
	
	  float yaw;
	    float pitch;
	    
	    public ImpHackEventRotation() {}

	    public float getYaw() {
	        return yaw;
	    }

	    public float getPitch() {
	        return pitch;
	    }

	    public void setYaw(float yaw) {
	        this.yaw = yaw;
	    }

	    public void setPitch(float pitch) {
	        this.pitch = pitch;
	    }

	}
