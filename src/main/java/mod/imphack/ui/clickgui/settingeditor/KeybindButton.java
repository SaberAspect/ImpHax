package mod.imphack.ui.clickgui.settingeditor;

import mod.imphack.Client;
import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.ui.clickgui.ClickGuiController;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeybindButton {
	final int x;
	int y;
	final int width;
	final int height;

	final Module module;

	final SettingFrame parent;

	final Color color;

	final Minecraft mc = Minecraft.getMinecraft();

	public KeybindButton(Module module, int key, int x, int y, SettingFrame parent, Color c) {
		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = parent.width;
		this.height = 14;
		this.color = c;
	}

	public void draw(int mouseX, int mouseY) {
		mc.fontRenderer.drawString("Keybind: " + Keyboard.getKeyName(module.getBind()), x + 2, y + 2, color.getRGB());

		
	}

	public void onClick(int x, int y, int button) {
		if (x >= this.x && x <= this.x + mc.fontRenderer.getStringWidth("Keybind: ") + 50 && y >= this.y - 5
				&& y <= this.y + this.height - 5) {
			if (!Client.getNextKeyPressForKeybinding) {
				Client.waitForKeybindKeypress(module);
				ClickGuiController.INSTANCE.settingController.refresh(true);
			} else {
				Client.stopWaitForKeybindPress();
				ClickGuiController.INSTANCE.settingController.refresh(false);
			}
		}
	}
}
