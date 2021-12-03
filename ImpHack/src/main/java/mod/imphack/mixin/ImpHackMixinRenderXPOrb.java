package mod.imphack.mixin;

import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.imphack.Main;
import mod.imphack.module.modules.render.NoRender;

@Mixin(RenderXPOrb.class)
public class ImpHackMixinRenderXPOrb {
	
	

    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.isEnabled()) {
            info.cancel();
        }
    }
}