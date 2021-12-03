package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;

public class ImpHackEventSpawnEffect extends ImpHackEventCancellable {
	
	   private int particleID;

	    public ImpHackEventSpawnEffect(int particleID) {
	        this.particleID = particleID;
	    }

	    public int getParticleID() {
	        return particleID;
	    }

	    public void setParticleID(int particleID) {
	        this.particleID = particleID;
	    }
	}