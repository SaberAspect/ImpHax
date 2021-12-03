package mod.imphack.mixin;

import mod.imphack.Main;
import mod.imphack.ui.ImpHackSplashScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = { Minecraft.class })
public abstract class ImpHackMixinMinecraft {

	@Shadow public EntityPlayerSP player;
	@Shadow public PlayerControllerMP playerController;

	private boolean handActive = false;
	private boolean isHittingBlock = false;
	
	@Shadow
	public abstract void displayGuiScreen(@Nullable GuiScreen var1);

	@Inject(method = { "displayGuiScreen" }, at = { @At(value = "HEAD") })
	private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
		if (screen instanceof GuiMainMenu) {
			this.displayGuiScreen(new ImpHackSplashScreen());
		}
	}

	@Inject(method = { "runTick()V" }, at = { @At(value = "RETURN") })
	private void runTick(CallbackInfo callbackInfo) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
			Minecraft.getMinecraft().displayGuiScreen(new ImpHackSplashScreen());
		}
	}
	
	 
}
