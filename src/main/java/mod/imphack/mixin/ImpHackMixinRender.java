package mod.imphack.mixin;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Render.class)
abstract class ImpHackMixinRender<T extends Entity> {

    @Shadow
    protected abstract boolean bindEntityTexture(T entity);
}