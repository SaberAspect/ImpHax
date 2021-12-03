package mod.imphack.ui.clickgui;

import mod.imphack.Main;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class ClickGuiFrame {
	final int x;
	int y;
	final int width;
	int height;

	final Category category;
	final Minecraft mc = Minecraft.getMinecraft();

	final ArrayList<ModuleButton> moduleButtons;

	public ClickGuiFrame(Category category, int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 68;
		this.height = 0;
		this.category = category;

		moduleButtons = new ArrayList<>();
		int offsetY = 14;
		for (Module module : Main.moduleManager.getModulesByCategory(category)) {
			moduleButtons.add(new ModuleButton(module, x, y + offsetY, this));
			offsetY += 14;
		}

		this.height = offsetY;
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

		ColorUtil categoryColor = new ColorUtil(255, 255, 255);
		if(Main.moduleManager.getModule("ClientFont").toggled) {
			FontUtils.drawString(true,category.toString(), x + 2, y + 2, categoryColor);

		}
		else {
			FontUtils.drawString(false,category.toString(), x + 2, y + 2, categoryColor);

		}
		for (ModuleButton moduleButton : moduleButtons) {
			moduleButton.draw(mouseX, mouseY);
		}
	}

	public void onClick(int x, int y, int button) {
		for (ModuleButton moduleButton : moduleButtons) {
			moduleButton.onClick(x, y, button);
		}
	}

}
