package mod.imphack.mixin;

import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventSteerEntity;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLlama.class)
public class ImpHackMixinEntityLlama {
	@Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
	public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
		ImpHackEventSteerEntity event = new ImpHackEventSteerEntity();
		ImpHackEventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			cir.cancel();
			cir.setReturnValue(true);
		}
	}
}