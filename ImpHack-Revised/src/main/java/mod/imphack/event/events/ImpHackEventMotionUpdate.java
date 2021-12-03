package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;

public class ImpHackEventMotionUpdate extends ImpHackEventCancellable {

	public final int stage;

	public ImpHackEventMotionUpdate(int stage) {
		super();
		this.stage = stage;
	}
	
	 public int getStage() {
	        return stage;
	    }


}