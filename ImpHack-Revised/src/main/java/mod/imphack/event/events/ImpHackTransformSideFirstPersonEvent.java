package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.util.EnumHandSide;

public class ImpHackTransformSideFirstPersonEvent extends ImpHackEventCancellable {

	private final EnumHandSide enumHandSide;

	public ImpHackTransformSideFirstPersonEvent(EnumHandSide enumHandSide){
		this.enumHandSide = enumHandSide;
	}

	public EnumHandSide getEnumHandSide(){
		return this.enumHandSide;
	}
}