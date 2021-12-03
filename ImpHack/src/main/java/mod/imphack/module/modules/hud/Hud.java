package mod.imphack.module.modules.hud;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;

public class Hud extends Module {

	public BooleanSetting watermark = new BooleanSetting("Watermark", this, true);
	public BooleanSetting arraylist = new BooleanSetting("ArrayList", this, false);
	public BooleanSetting coords = new BooleanSetting("Coordinates", this, false);
	public BooleanSetting fps = new BooleanSetting("FPS", this, false);
	public BooleanSetting armor = new BooleanSetting("Armor", this, false);
	public BooleanSetting welcome = new BooleanSetting("Welcome", this, false);



	public Hud() {
		super("Hud", "In-Game Overlay", Category.HUD);

		addSetting(watermark);
		addSetting(arraylist);
		addSetting(coords);
		addSetting(fps);
		addSetting(armor);
		addSetting(welcome);

		
	}
}
