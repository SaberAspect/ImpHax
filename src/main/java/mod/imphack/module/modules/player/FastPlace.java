package mod.imphack.module.modules.player;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", "Allows you to place blocks & crystals faster", Category.PLAYER);
        
        addSetting(fast_place);
        addSetting(fast_break);
        addSetting(crystal);
        addSetting(exp);

    }

    BooleanSetting fast_place = new BooleanSetting("Place",this, false);
    BooleanSetting fast_break = new BooleanSetting("Break",this,false);
    BooleanSetting crystal = new BooleanSetting("Crystals", this, true);
    BooleanSetting exp = new BooleanSetting("Exp", this, false);

     
    

    @Override
	public void onUpdate() {
		Item main = mc.player.getHeldItemMainhand().getItem();
		Item off  = mc.player.getHeldItemOffhand().getItem();

		boolean main_exp = main instanceof ItemExpBottle;
		boolean off_exp  = off instanceof ItemExpBottle;
		boolean main_cry = main instanceof ItemEndCrystal;
		boolean off_cry  = off instanceof ItemEndCrystal;

		if (main_exp | off_exp && exp.isEnabled()) {
			mc.rightClickDelayTimer = 0;
		}

		if (main_cry | off_cry && crystal.isEnabled()) {
			mc.rightClickDelayTimer = 0;
		}

		if (!(main_exp | off_exp | main_cry | off_cry) && fast_place.isEnabled()) {
			mc.rightClickDelayTimer = 0;
		}

		if (fast_break.isEnabled()) {
			mc.playerController.blockHitDelay = 0;
		}
	}
}