package mod.imphack.event.events;

import mod.imphack.event.ImpHackEventCancellable;
import net.minecraft.util.EnumHand;

public class ImpHackEventSwing extends ImpHackEventCancellable {

	public final EnumHand hand;

	public ImpHackEventSwing(EnumHand hand) {
		super();
		this.hand = hand;
	}

}