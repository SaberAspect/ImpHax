package mod.imphack.module.modules.hud;

import mod.imphack.Main;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudFPS extends Gui{
	

	private final Minecraft mc = Minecraft.getMinecraft();


	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if (Main.moduleManager.getModule("Hud").toggled) {
			ScaledResolution sr = new ScaledResolution(mc);
			FontRenderer fr = mc.fontRenderer;
			
			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"FPS")).enabled) {
					
					String fps = "FPS: " + Minecraft.getDebugFPS();
					boolean isChatOpen = mc.currentScreen instanceof GuiChat;
					
					int heightFPS = isChatOpen ? sr.getScaledHeight() - 35  : sr.getScaledHeight() - 20;
			        if(Main.moduleManager.getModule("ClientFont").isToggled()) {

					FontUtils.drawStringWithShadow(true,fps,  2, heightFPS, ColorUtil.getRainbow(300, 255));
			        }
			        else {
						FontUtils.drawStringWithShadow(false,fps,  2, heightFPS, ColorUtil.getRainbow(300, 255));

			        }
				
				}
			}
		}
	}
}
