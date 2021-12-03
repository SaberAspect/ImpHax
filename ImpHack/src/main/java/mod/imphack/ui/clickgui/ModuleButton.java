package mod.imphack.ui.clickgui;

import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class ModuleButton {
	public final int x;
	public int y;
	public final int width;
	public final int height;

	public final Module module;

	final ClickGuiFrame parent;

	final Minecraft mc = Minecraft.getMinecraft();

	public ModuleButton(Module module, int x, int y, ClickGuiFrame parent) {
		this.module = module;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = parent.width;
		this.height = 14;
	}

	public void draw(int mouseX, int mouseY) {
		if (module.toggled) {
			FontUtils.drawString(true, module.getName(), x + 2, y + 2, new ColorUtil(255, 150, 50));
		} else {
			FontUtils.drawString(true, module.getName(), x + 2, y + 2, new ColorUtil(180, 240, 255));
		}
			
	}
	

	public void onClick(int x, int y, int button) {
		if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
			module.toggle();
		}
	}
}
