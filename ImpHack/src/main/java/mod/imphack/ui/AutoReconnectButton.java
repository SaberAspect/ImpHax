package mod.imphack.ui;

import mod.imphack.Main;
import mod.imphack.module.modules.utilities.Reconnect;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.GuiConnecting;

public class AutoReconnectButton extends GuiButton {
	public AutoReconnectButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);

		timer = new Timer();
		timer.reset();

		Mod = (Reconnect) Main.moduleManager.getModule("Reconnect");
		reconnectTimer = ((IntSetting) Main.settingManager
				.getSettingByName(Main.moduleManager.getModule("Reconnect"), "Timer")).value;
	}

	private final Reconnect Mod;
	private final Timer timer;
	private long reconnectTimer;

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);

		if (visible && ((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData != null) {
			reconnectTimer = ((IntSetting) Main.settingManager
					.getSettingByName(Main.moduleManager.getModule("Reconnect"), "Timer")).value;
			if (Mod.toggled && timer.getPassedTimeMs() < reconnectTimer)
				this.displayString = "AutoReconnect " + (reconnectTimer - timer.getPassedTimeMs());
			else if (!Mod.toggled)
				this.displayString = "Enable AutoReconnect";
			else {
				mc.displayGuiScreen(new GuiConnecting(null, mc,
						((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData));
			}

			if (timer.getPassedTimeMs() + 100 >= reconnectTimer && Mod.toggled
					&& ((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData != null) {
				mc.displayGuiScreen(new GuiConnecting(null, mc,
						((Reconnect) Main.moduleManager.getModule("Reconnect")).serverData));
			}
		}
	}

	public void Clicked() {
		Mod.toggle();

		timer.reset();
	}
}
