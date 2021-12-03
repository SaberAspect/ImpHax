package mod.imphack.mixin;

import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventRenderEntityName;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class ImpHackMixinRenderPlayer {
	@Inject(method = "renderEntityName*", at = @At("HEAD"), cancellable = true)
	public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name,
			double distanceSq, CallbackInfo info) {
		ImpHackEventRenderEntityName l_Event = new ImpHackEventRenderEntityName(entityIn, x, y, z, name, distanceSq);
		ImpHackEventBus.EVENT_BUS.post(l_Event);
		if (l_Event.isCancelled())
			info.cancel();

		
	}
	
	
}