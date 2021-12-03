package mod.imphack.module.modules.movement;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import net.minecraft.client.Minecraft;

public class Sprint extends Module {
	public Sprint() {
		super("Sprint", "Automatically sprints", Category.MOVEMENT);
		
		this.addSetting(strict);
		this.addSetting(hungerSafe);
	}
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	final BooleanSetting strict = new BooleanSetting("Strict", this, true);
	final BooleanSetting hungerSafe = new BooleanSetting("HungerSafe", this, true);
	
	public void onUpdate() {
		if(mc.world != null) {
			if(mc.gameSettings.keyBindForward.isKeyDown()) {
                mc.player.setSprinting((!mc.player.collidedHorizontally || !strict.isEnabled()) && (mc.player.getFoodStats().getFoodLevel() > 6 || !hungerSafe.isEnabled()) && !mc.player.isSneaking() && !mc.player.isHandActive());
			} else {
          mc.player.setSprinting(false);
			}
			
		}
				
	}

}
