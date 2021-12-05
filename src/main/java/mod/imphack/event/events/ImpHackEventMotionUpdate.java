package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;

public class ImpHackEventMotionUpdate extends ImpHackEventCancellable {

	
    public int stage;


	public ImpHackEventMotionUpdate(int stage) {
		super();
		this.stage = stage;
	}
	
	 
}