package mod.imphack.mixin;

import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventHorseSaddled;
import mod.imphack.event.events.ImpHackEventSteerEntity;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPig.class)
public class ImpHackMixinEntityPig {
	@Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
	public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
		ImpHackEventSteerEntity event = new ImpHackEventSteerEntity();
		ImpHackEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "getSaddled", at = @At("HEAD"), cancellable = true)
	public void getSaddled(CallbackInfoReturnable<Boolean> cir) {
		ImpHackEventHorseSaddled event = new ImpHackEventHorseSaddled();
		ImpHackEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}
}