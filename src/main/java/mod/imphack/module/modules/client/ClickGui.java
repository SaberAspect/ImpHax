package mod.imphack.module.modules.client;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.ui.clickgui.ClickGuiController;

public class ClickGui extends Module {

	public ClickGui() {
		super("ClickGUI", "GUI interface to interact with modules", Category.CLIENT);
	}

	@Override
	protected void onEnable() {
		mc.displayGuiScreen(ClickGuiController.INSTANCE);
		this.toggled = false;
	}

}
