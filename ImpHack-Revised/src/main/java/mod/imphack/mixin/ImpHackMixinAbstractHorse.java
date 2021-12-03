package mod.imphack.mixin;

import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventHorseSaddled;
import mod.imphack.event.events.ImpHackEventSteerEntity;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorse.class)
public class ImpHackMixinAbstractHorse {
	@Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
	public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
		ImpHackEventSteerEntity l_Event = new ImpHackEventSteerEntity();
		ImpHackEventBus.EVENT_BUS.post(l_Event);

		if (l_Event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "isHorseSaddled", at = @At("HEAD"), cancellable = true)
	public void isHorseSaddled(CallbackInfoReturnable<Boolean> cir) {
		ImpHackEventHorseSaddled l_Event = new ImpHackEventHorseSaddled();
		ImpHackEventBus.EVENT_BUS.post(l_Event);

		if (l_Event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}
}
