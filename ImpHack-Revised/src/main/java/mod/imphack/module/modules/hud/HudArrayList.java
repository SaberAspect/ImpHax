package mod.imphack.module.modules.hud;

import mod.imphack.Main;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class HudArrayList extends Gui{
	

	private final Minecraft mc = Minecraft.getMinecraft();

	public static class ModuleComparator implements Comparator<Module> {

		@Override
		public int compare(Module arg0, Module arg1) {
			if (Minecraft.getMinecraft().fontRenderer.getStringWidth(
					arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
				return -1;
			}
			if (Minecraft.getMinecraft().fontRenderer.getStringWidth(
					arg0.getName()) > Minecraft.getMinecraft().fontRenderer.getStringWidth(arg1.getName())) {
				return 1;
			}
			return 0;
		}
	}

	final Comparator<Module> comparator = (a, b) -> {
		final String firstName = a.getName();
		final String secondName = b.getName();
		final float dif = Minecraft.getMinecraft().fontRenderer.getStringWidth(secondName)
				- Minecraft.getMinecraft().fontRenderer.getStringWidth(firstName);
		return dif != 0 ? (int) dif : secondName.compareTo(firstName);
	};

	@SubscribeEvent
	public void renderOverlay(Text event) {
		if (Main.moduleManager.getModule("Hud").toggled) {
			Main.moduleManager.modules.sort(new ModuleComparator());
			ScaledResolution sr = new ScaledResolution(mc);
			FontRenderer fr = mc.fontRenderer;

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {

				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"ArrayList")).enabled) {
					int y = 2;
					ArrayList<Module> modules = new ArrayList<>();
					for (Module mod : Main.moduleManager.getModuleList()) {
							if (!mod.getName().equalsIgnoreCase("Hud") && mod.isToggled()) {
								if (!mod.getName().equalsIgnoreCase("ClientFont") && mod.isToggled()) {


							modules.add(mod);
								}
						}
					}

					modules.sort(comparator);

					for (Module m : modules) {
	                	if(Main.moduleManager.getModule("ClientFont").isToggled()) {

						FontUtils.drawStringWithShadow(true, m.getName(), sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 2,
								y, ColorUtil.getRainbow(300, 255));
	                	}
	                	else {
	                		FontUtils.drawStringWithShadow(false, m.getName(), sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 2,
									y, ColorUtil.getRainbow(300, 255));
	                	}
						y += fr.FONT_HEIGHT;
					}
				}
				
			}
		}
	}

	public static String round(double num) {
		return (Integer.valueOf((int) num)).toString() + "."
				+ (Integer.valueOf(Math.abs((int) ((num % 1) * 10)))).toString();
	}

	
}


