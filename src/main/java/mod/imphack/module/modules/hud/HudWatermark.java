package mod.imphack.module.modules.hud;

import mod.imphack.Main;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.util.Reference;
import mod.imphack.util.font.FontUtils;
import mod.imphack.util.render.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HudWatermark {

	private final Minecraft mc = Minecraft.getMinecraft();



	@SubscribeEvent
	public void renderOverlay(Text event) {
		if (Main.moduleManager.getModule("Hud").toggled) {

			if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
				if (((BooleanSetting) Main.settingManager.getSettingByName(Main.moduleManager.getModule("Hud"),
						"Watermark")).enabled)
                	if(Main.moduleManager.getModule("ClientFont").isToggled()) {

					FontUtils.drawStringWithShadow(true,"ImpHack Revised " + Reference.DEV_VERSION, 2, 1, new ColorUtil(128, 0, 128, 255));
                	}
                	else {
    					FontUtils.drawStringWithShadow(false,"ImpHack Revised " + Reference.DEV_VERSION, 2, 1, new ColorUtil(128, 0, 128, 255));

                	}

			}
		}
	}
}
