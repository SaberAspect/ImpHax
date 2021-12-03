package mod.imphack.ui.clickgui.settingeditor.search;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import mod.imphack.Main;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockSelectorGuiFrame {
	final int x;
	int y;
	final int width;
	final int height;

	final Minecraft mc = Minecraft.getMinecraft();

	final BlockSelectorGuiController controller;

	final String searchText;

	public BlockSelectorGuiFrame(int x, int y, BlockSelectorGuiController controller, String searchText) {

		this.x = x;
		this.y = y;
		this.width = 400;
		this.controller = controller;
		this.searchText = searchText;

		controller.itemRender = mc.getRenderItem();

		int offsetY = 2;

		offsetY += 20;

		controller.blocks = new ArrayList<>();

		if (Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"), "Select Blocks") != null
				&& controller.blocks.isEmpty()) {
			if (controller.searchField == null || controller.searchField.getText().equals("")) {
				for (Block b : ForgeRegistries.BLOCKS.getValuesCollection()) {

					controller.blocks.add(new BlockButton(
							Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"),
									"Select Blocks").parent,
							this.x + 2, this.y + offsetY + controller.scrollOffset, this, b));
					offsetY += 20;
				}
			} else {
				for (Block b : ForgeRegistries.BLOCKS.getValuesCollection()) {
					if (StringUtils.containsIgnoreCase(b.getLocalizedName(), searchText)) {
						controller.blocks.add(new BlockButton(
								Main.settingManager.getSettingByName(Main.moduleManager.getModule("Search"),
										"Select Blocks").parent,
								this.x + 2, this.y + offsetY + controller.scrollOffset, this, b));
						offsetY += 20;
					}
				}
			}
			ArrayList<BlockButton> removeDuplicates = new ArrayList<>();
			for (BlockButton b : controller.blocks) {
				if (!removeDuplicates.contains(b)) {
					removeDuplicates.add(b);
				} else {
					offsetY -= 20;
				}
			}
			controller.blocks = removeDuplicates;
		} else {
			for (int i = 0; i < controller.blocks.size(); i++) {
				offsetY += 20;
			}
		}

		this.height = offsetY;

		if (controller.searchField == null)
			controller.searchField = new GuiTextField(0, mc.fontRenderer, this.x + 4, this.y + 6, 380,
					this.mc.fontRenderer.FONT_HEIGHT + 4);
	}

	public void render(int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glLineWidth(1);

		GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y + height);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x + width, y);
		GL11.glEnd();

		GL11.glColor3f(0.0f, 200.0f, 255.0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x, y + height);
		GL11.glVertex2f(x + width, y + height);
		GL11.glVertex2f(x + width, y);
		GL11.glEnd();

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glColor3f(1, 1, 1);

		controller.searchField.y = this.y + 6;
		controller.searchField.setEnabled(true);
		controller.searchField.drawTextBox();

		for (BlockButton b : controller.blocks) {
			if (b != null && b.y + b.height >= 0 && b.y <= mc.displayHeight)
				b.draw();
		}
	}

	public void onClick(int x, int y, int button) {
		ArrayList<BlockButton> onScreen = new ArrayList<>();

		for (BlockButton b : controller.blocks) {
			if (b != null && b.y + b.height >= 0 && b.y <= mc.displayHeight)
				onScreen.add(b);
			else {
				assert b != null;
				if (b.textFieldRed != null && b.textFieldGreen != null && b.textFieldBlue != null) {
					b.textFieldRed.setFocused(false);
					b.textFieldGreen.setFocused(false);
					b.textFieldBlue.setFocused(false);
				}
			}
		}

		for (BlockButton b : onScreen) {
			if (b != null)
				b.onClick(x, y, button);
		}

		controller.searchField.mouseClicked(x, y, button);
	}
}