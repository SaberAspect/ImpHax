package mod.imphack.ui.clickgui;

import mod.imphack.module.Category;
import mod.imphack.ui.clickgui.settingeditor.SettingController;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGuiController extends GuiScreen {

	private int scrollOffset;

	public static final ClickGuiController INSTANCE = new ClickGuiController();

	public SettingController settingController;

	static ArrayList<ClickGuiFrame> frames;

	public ClickGuiController() {
		frames = new ArrayList<>();
		int offset = 0;
		for (Category category : Category.values()) {
			frames.add(new ClickGuiFrame(category, 2 + offset, 20));
			offset += 71;
		}
	}

	public static ArrayList<ModuleButton> getButtons() {
		ArrayList<ModuleButton> mb;
		mb = new ArrayList<>();
		for (ClickGuiFrame f : frames) {
			mb.addAll(f.moduleButtons);
		}
		return mb;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		scroller();
		for (ClickGuiFrame frame : frames) {
			frame.render(mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseButton == 0) {
			for (ClickGuiFrame frame : frames) {
				frame.onClick(mouseX, mouseY, mouseButton);
			}
		}

		if (mouseButton == 1) {

			settingController = null;

			for (ModuleButton m : ClickGuiController.getButtons()) {
				if (mouseX >= m.x && mouseX <= m.x + m.width && mouseY >= m.y && mouseY <= m.y + m.height) {
					settingController = new SettingController(mouseX, mouseY);
				}
			}
			if (settingController != null)
				mc.displayGuiScreen(settingController);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		super.doesGuiPauseGame();
		return false;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		if (keyCode == 1) {
			for (ClickGuiFrame c : frames) {
				c.y = c.y - scrollOffset;
				for (ModuleButton m : c.moduleButtons) {
					m.y -= scrollOffset;
				}
			}
			scrollOffset = 0;
		}
	}

	private void scroller() {
		int dWheel = Mouse.getDWheel();
		if (dWheel < 0) {
			for (ClickGuiFrame c : frames) {
				c.y = c.y - 10;
				for (ModuleButton m : c.moduleButtons) {
					m.y = m.y - 10;
				}
			}
			scrollOffset -= 10;
		} else if (dWheel > 0) {
			for (ClickGuiFrame c : frames) {
				c.y = c.y + 10;
				for (ModuleButton m : c.moduleButtons) {
					m.y = m.y + 10;
				}
			}
			scrollOffset += 10;
		}
	}
}
