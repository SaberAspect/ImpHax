package mod.imphack.module.modules.render;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.FloatSetting;
import net.minecraft.client.renderer.ItemRenderer;

public class LowOffHand extends Module {

	FloatSetting height = new FloatSetting("Height", this, 0.7f);
	
	public LowOffHand() {
		super("LowOffHand", "Lowers your offhand", Category.RENDER);
		
		addSetting(height);
		
		
	}
	
	
	ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;
	
	@Override 
	public void onUpdate() {
		itemRenderer.equippedProgressOffHand = (float) height.getValue();
	}
}
