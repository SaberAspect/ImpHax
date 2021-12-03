package mod.imphack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventRenderItem;
import mod.imphack.event.events.ImpHackTransformSideFirstPersonEvent;
import mod.imphack.module.modules.render.NoRender;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumHandSide;

@Mixin(ItemRenderer.class)
public class ImpHackMixinItemRenderer {


	  @Inject(method = "renderSuffocationOverlay", at = @At("HEAD"), cancellable = true)
	    public void renderSuffocationOverlay(TextureAtlasSprite sprite, CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.isEnabled()) {
	            info.cancel();
	        }
	    }

	    @Inject(method = "renderWaterOverlayTexture", at = @At("HEAD"), cancellable = true)
	    public void renderWaterOverlayTexture(float partialTicks, CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.isEnabled()) {
	            info.cancel();
	        }
	    }

	    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
	    public void renderFireInFirstPerson(CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.isEnabled()) {
	            info.cancel();
	        }
	    }
	
	
	


}