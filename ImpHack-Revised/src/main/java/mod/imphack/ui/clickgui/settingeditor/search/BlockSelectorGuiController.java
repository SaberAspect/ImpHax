package mod.imphack.ui.clickgui.settingeditor.search;

import mod.imphack.Main;
import mod.imphack.event.ImpHackEventBus;
import mod.imphack.event.events.ImpHackEventSettings;
import mod.imphack.module.modules.render.Search;
import mod.imphack.setting.settings.SearchBlockSelectorSetting;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

public class BlockSelectorGuiController extends GuiScreen {

	public ArrayList<BlockButton> blocks;

	public final boolean colorSettings;
	public final GuiScreen lastScreen;

	public int scrollOffset = 0;

	public BlockSelectorGuiFrame frame;

	public final SearchBlockSelectorSetting setting;

	public GuiTextField searchField;

	public BlockSelectorGuiController(GuiScreen lastScreen, boolean colorSettings, SearchBlockSelectorSetting setting) {
		this.lastScreen = lastScreen;
		this.colorSettings = colorSettings;
		this.setting = setting;

		if (setting != null && setting.parent != null) {
			frame = new BlockSelectorGuiFrame(20, 20, this, "");
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		scroller();

		if (frame == null) {
			this.onGuiClosed();
			return;
		}

		frame.render(mouseX, mouseY);

	}

	@Override
	public boolean doesGuiPauseGame() {
		super.doesGuiPauseGame();
		return false;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {

		if (frame == null)
			return;

		super.keyTyped(typedChar, keyCode);
		if (keyCode == 1) {

			frame.y -= scrollOffset;

			for (BlockButton b : blocks) {
				if (b != null) {
					b.y -= scrollOffset;
					if (b.textFieldRed != null && b.textFieldGreen != null && b.textFieldBlue != null) {
						b.textFieldRed.y -= scrollOffset;
						b.textFieldGreen.y -= scrollOffset;
						b.textFieldBlue.y -= scrollOffset;
						b.textFieldRed.setVisible(false);
						b.textFieldRed.setEnabled(false);
						b.textFieldGreen.setVisible(false);
						b.textFieldGreen.setEnabled(false);
						b.textFieldBlue.setVisible(false);
						b.textFieldBlue.setEnabled(false);
					}
				}
			}

			if (searchField != null) {
				searchField.y -= scrollOffset;
				searchField.setVisible(false);
				searchField.setEnabled(false);
			}

			scrollOffset = 0;

			this.mc.displayGuiScreen(lastScreen);

			if (this.mc.currentScreen == null) {
				this.mc.setIngameFocus();
			}

			ImpHackEventBus.EVENT_BUS.post(new ImpHackEventSettings(
					Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks"),
					Main.settingManager
							.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks").parent));
		}

		ArrayList<BlockButton> onScreen = new ArrayList<>();

		for (BlockButton b : blocks) {
			if (b != null && b.y + b.height >= 0 && b.y <= mc.displayHeight)
				onScreen.add(b);
		}

		for (BlockButton b : onScreen) {
			if (b != null && b.textFieldRed != null && b.textFieldGreen != null && b.textFieldBlue != null) {
				b.textFieldRed.textboxKeyTyped(typedChar, keyCode);
				b.textFieldGreen.textboxKeyTyped(typedChar, keyCode);
				b.textFieldBlue.textboxKeyTyped(typedChar, keyCode);
			}
		}

		if (searchField != null) {
			searchField.textboxKeyTyped(typedChar, keyCode);
			if (searchField.isFocused()) {
				refresh();
			}
		}

		ImpHackEventBus.EVENT_BUS.post(new ImpHackEventSettings(
				Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks"),
				Main.settingManager
						.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks").parent));
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (frame == null)
			return;

		if (mouseButton == 0) {
			frame.onClick(mouseX, mouseY, mouseButton);
		}

		// ArrayList<BlockButton> onScreen = new ArrayList<>();

		/*
		 * for (BlockButton b : blocks) {
		 * 
		 * if (b != null && b.textFieldRed != null && b.textFieldGreen != null &&
		 * b.textFieldBlue != null && (b.textFieldRed.isFocused() ||
		 * b.textFieldGreen.isFocused() || b.textFieldBlue.isFocused())) {
		 * b.textFieldRed.setFocused(false); b.textFieldGreen.setFocused(false);
		 * b.textFieldBlue.setFocused(false); }
		 * 
		 * if (b != null && b.y + b.height >= 0 && b.y <= mc.displayHeight) //
		 * onScreen.add(b); b.onClick(mouseX, mouseY, mouseButton); else if
		 * (b.textFieldRed != null && b.textFieldGreen != null && b.textFieldBlue !=
		 * null) { b.textFieldRed.setFocused(false); b.textFieldGreen.setFocused(false);
		 * b.textFieldBlue.setFocused(false); } }
		 */

		/*
		 * for (BlockButton b : onScreen) { if (b != null) if (b.textFieldRed != null &&
		 * b.textFieldGreen != null && b.textFieldBlue != null) { if (mouseX >=
		 * b.textFieldRed.x && mouseX <= b.textFieldRed.x + b.textFieldRed.width &&
		 * mouseY >= b.textFieldRed.y && mouseY <= b.textFieldRed.y +
		 * b.textFieldRed.height) { b.textFieldRed.setFocused(true); } if (mouseX >=
		 * b.textFieldGreen.x && mouseX <= b.textFieldGreen.x + b.textFieldGreen.width
		 * && mouseY >= b.textFieldGreen.y && mouseY <= b.textFieldGreen.y +
		 * b.textFieldGreen.height) { b.textFieldGreen.setFocused(true); } if (mouseX >=
		 * b.textFieldBlue.x && mouseX <= b.textFieldBlue.x + b.textFieldBlue.width &&
		 * mouseY >= b.textFieldBlue.y && mouseY <= b.textFieldBlue.y +
		 * b.textFieldBlue.height) { b.textFieldBlue.setFocused(true); } } }
		 */

		if (this.setting.blocks != null)
			for (Block b : this.setting.blocks) {
				int color;
				color = this.setting.getColor(b);
				((Search) this.setting.parent).to_search.put(b, color);
			}

		Main.config.Save();
	}

	private void scroller() {
		int dWheel = Mouse.getDWheel();
		if (dWheel < 0) {

			for (BlockButton b : blocks) {
				if (b != null) {
					b.y -= 10;
					if (b.textFieldRed != null && b.textFieldGreen != null && b.textFieldBlue != null) {
						b.textFieldRed.y -= 10;
						b.textFieldGreen.y -= 10;
						b.textFieldBlue.y -= 10;
						if (b.y + b.height < 0) {
							if (b.textFieldRed.isFocused() || b.textFieldGreen.isFocused()
									|| b.textFieldBlue.isFocused()) {
								b.textFieldRed.setFocused(false);
								b.textFieldGreen.setFocused(false);
								b.textFieldBlue.setFocused(false);
							}
						}
					}
				}
			}

			frame.y -= 10;

			scrollOffset -= 10;

		} else if (dWheel > 0) {

			for (BlockButton b : blocks) {
				if (b != null) {
					b.y += 10;
					if (b.textFieldRed != null && b.textFieldGreen != null && b.textFieldBlue != null) {
						b.textFieldRed.y += 10;
						b.textFieldGreen.y += 10;
						b.textFieldBlue.y += 10;
						if (b.y > mc.displayHeight) {
							if (b.textFieldRed.isFocused() || b.textFieldGreen.isFocused()
									|| b.textFieldBlue.isFocused()) {
								b.textFieldRed.setFocused(false);
								b.textFieldGreen.setFocused(false);
								b.textFieldBlue.setFocused(false);
							}
						}
					}
				}
			}

			frame.y += 10;

			scrollOffset += 10;

		}
	}

	public void refresh() {
		Main.config.Save();
		frame = new BlockSelectorGuiFrame(20, 20, this, searchField.getText());
		frame.y += scrollOffset;
	}
}
