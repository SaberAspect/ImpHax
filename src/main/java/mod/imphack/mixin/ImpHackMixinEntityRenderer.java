package mod.imphack.mixin;

import mod.imphack.Main;
import mod.imphack.module.modules.render.NoRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class ImpHackMixinEntityRenderer {



	 @Shadow
	    @Final
	    private Minecraft mc;

	    @Shadow
	    private boolean debugView;

	    @Shadow
	    private ItemStack itemActivationItem;


	    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
	    public void hurtCameraEffect(float partialTicks, CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.hurtCamera.isEnabled()) {
	            info.cancel();
	        }
	    }

	    @Inject(method = "renderRainSnow", at = @At("HEAD"), cancellable = true)
	    public void renderRainSnow(float partialTicks, CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.weather.isEnabled()) {
	            info.cancel();
	        }
	    }

	    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
	    public void setupFog(int startCoords, float partialTicks, CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.fog.isEnabled()) {
	            info.cancel();
	        }
	    }

	    @Inject(method = "renderItemActivation", at = @At("HEAD"), cancellable = true)
	    public void renderItemActivation(int p_190563_1_, int p_190563_2_, float p_190563_3_, CallbackInfo info) {
	        if (NoRender.INSTANCE.isEnabled() && NoRender.INSTANCE.totems.isEnabled() && this.itemActivationItem != null && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
	            info.cancel();
	        }
	    }

	
}