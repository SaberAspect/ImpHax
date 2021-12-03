package mod.imphack.module.modules.combat;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.ui.clickgui.settingeditor.KeybindButton;
import mod.imphack.util.EntityUtil;
import mod.imphack.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;

public class SpeedEXP extends Module {

	public SpeedEXP() {
		super("SpeedEXP", "ZOOOOM", Category.COMBAT);
		
		addSetting(mode, delay, stopEXP, footEXP);
	}
	
	
	  ModeSetting mode = new ModeSetting("Mode", this, "Packet", "Packet", "AutoMend", "Throw");
	   FloatSetting delay = new FloatSetting("Throw Delay",this, 0.0f);
	   BooleanSetting stopEXP = new BooleanSetting("Stop EXP", this,false);
	   BooleanSetting footEXP = new BooleanSetting("FootEXP", this,false);




@Override
public void onUpdate() {
    if (mc.player == null || mc.world == null)
        return;

    if (stopEXP.isEnabled() && EntityUtil.getArmor(mc.player) == 100) {
        mc.player.stopActiveHand();
        return;
    }

    if (InventoryUtil.getHeldItem(Items.EXPERIENCE_BOTTLE))
        mc.rightClickDelayTimer = (int) delay.getValue();

    if (!(mode.getMode() == "Thow")) {
        switch (mode.getMode()) {
            case "AutoMend":
                InventoryUtil.switchToSlot(Items.EXPERIENCE_BOTTLE);
                break;
            case "Packet":
                InventoryUtil.switchToSlotGhost(Items.EXPERIENCE_BOTTLE);
                break;
        
        	}	
    	}
	}
}