package mod.imphack.module.modules.render;

import mod.imphack.module.Category;
import mod.imphack.module.Module;

public class FullBright extends Module {

	private float lastGamma;

	public FullBright() {
		super("FullBright", "Makes Everything Brighter", Category.RENDER);
	}

	@Override
	public void onEnable() {
		super.onEnable();

		this.lastGamma = mc.gameSettings.gammaSetting;
	}

	@Override
	public void onDisable() {
		super.onDisable();

		if (lastGamma != 1000) {
			mc.gameSettings.gammaSetting = this.lastGamma;
		} else {
			mc.gameSettings.gammaSetting = 100;
		}
	}

	@Override
	public void onUpdate() {
		mc.gameSettings.gammaSetting = 1000;
	}
}
