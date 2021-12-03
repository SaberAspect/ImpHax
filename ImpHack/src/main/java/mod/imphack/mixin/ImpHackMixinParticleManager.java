package mod.imphack.mixin;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.imphack.Main;
import mod.imphack.module.modules.render.NoRender;

@Mixin(ParticleManager.class)
public class ImpHackMixinParticleManager {
	

    @Inject(method = "renderParticles", at = @At("HEAD"), cancellable = true)
    public void renderParticles(Entity entityIn, float partialTicks, CallbackInfo info) {
        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.particles.isEnabled()) {
            info.cancel();
        }
    }
}