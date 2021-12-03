package mod.imphack.module.modules.client;

import java.awt.Font;

import mod.imphack.Main;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.font.ImpHackFontRenderer;

public class ClientFont extends Module {
	public ModeSetting font = new ModeSetting("font", this, "Comic Sans Ms", "Comic Sans Ms", "Arial", "Verdana");
	
	public ClientFont() {
		super ("ClientFont", "changes the font the client uses.", Category.CLIENT);
		this.addSetting(font);
	}
	
	@Override
	public void onEnable() {
		if(font.is("Comic Sans Ms")) {
			Main.customFontRenderer = new ImpHackFontRenderer(new Font("Comic Sans MS", Font.PLAIN, 18), true, true);
		}
		
		if(font.is("Arial")) {
			Main.customFontRenderer = new ImpHackFontRenderer(new Font("Arial", Font.PLAIN, 18), true, true);
		}
		
		if(font.is("Verdana")) {
			Main.customFontRenderer = new ImpHackFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
		}
	}
}