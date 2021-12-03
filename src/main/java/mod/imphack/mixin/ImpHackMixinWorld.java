package mod.imphack.mixin;

import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventPush;
import mod.imphack.event.events.ImpHackEventRain;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class ImpHackMixinWorld {
	@Redirect(method = {
			"handleMaterialAcceleration" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
	public boolean isPushedbyWaterHook(Entity entity) {
		ImpHackEventPush event = new ImpHackEventPush(2, entity);
		ImpHackEventBus.EVENT_BUS.post(event);
		return entity.isPushedByWater() && !event.isCancelled();
	}
	
	@Inject(method = "getRainStrength", at = @At("HEAD"), cancellable = true)
    public void getRainStrength(float delta, CallbackInfoReturnable<Float> callback) {
		ImpHackEventRain event = new ImpHackEventRain();
	    ImpHackEventBus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue(0.0f);
        }
    }
        
}
	

	    
