package mod.imphack.util.font;

import mod.imphack.Main;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;

public class FontUtils {

	public int fontSize = 16;

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static float drawStringWithShadow(boolean customFont, String text, int x, int y, ColorUtil color) {
		if (customFont) {
			return Main.customFontRenderer.drawStringWithShadow(text, x, y, color);
		} else {
			return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
		}
	}

	public static int getStringWidth(boolean customFont, String string) {
		if (customFont) {
			return Main.customFontRenderer.getStringWidth(string);
		} else {
			return mc.fontRenderer.getStringWidth(string);
		}
	}

	public static int getFontHeight(boolean customFont) {
		if (customFont) {
			return Main.customFontRenderer.getHeight();
		} else {
			return mc.fontRenderer.FONT_HEIGHT;
		}
	}

	public static void drawStringWithShadow(String text, double x, double y, int color) {

	}

	public static float drawString(boolean customFont, String text, int x, int y, ColorUtil color) {
		if (customFont && Main.customFontRenderer != null) {
			return Main.customFontRenderer.drawString(text, x, y, color);
		} else {
			return mc.fontRenderer.drawString(text, x, y, color.getRGB());
		}
	}

}