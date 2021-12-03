package mod.imphack.mixin;

import mod.imphack.Main;
import mod.imphack.module.modules.render.ExtraTab;
import mod.imphack.setting.settings.IntSetting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = { GuiPlayerTabOverlay.class })
public class ImpHackMixinGuiPlayerTabOverlay extends Gui {
	@Redirect(method = {
			"renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;", remap = false))
	public List<NetworkPlayerInfo> subListHook(List<NetworkPlayerInfo> list, int fromIndex, int toIndex) {
		return list
				.subList(fromIndex,
						Main.moduleManager.getModule("ExtraTab").isToggled() ? Math.min(
								(((IntSetting) Main.settingManager
										.getSettingByName(Main.moduleManager.getModule("ExtraTab"), "Size")).value),
								list.size()) : toIndex);
	}

	@Inject(method = "getPlayerName", at = @At(value = "HEAD"), cancellable = true)
	public void getPlayerNameHook(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable<String> info) {
		if (Main.moduleManager.getModule("ExtraTab").isToggled()) {
			info.setReturnValue(ExtraTab.getPlayerName(networkPlayerInfoIn));
		}
	}
}