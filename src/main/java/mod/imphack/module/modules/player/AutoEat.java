package mod.imphack.module.modules.player;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.util.PlayerUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class AutoEat extends Module {

	final IntSetting hunger = new IntSetting("Hunger", this, 17);

	public AutoEat() {
		super("AutoEat", "Eats Automatically", Category.PLAYER);

		addSetting(hunger);
	}

	private boolean m_WasEating = false;

	@Override
	public void onDisable() {
		super.onDisable();

		if (m_WasEating) {
			m_WasEating = false;
			mc.gameSettings.keyBindUseItem.pressed = false;
		}
	}

	@Override
	public void onUpdate() {
		if (hunger.value > 20)
			hunger.value = 0;

		if (!PlayerUtil.IsEating() && hunger.value >= mc.player.getFoodStats().getFoodLevel()) {
			boolean l_CanEat = false;

			ItemStack l_Stack = null;

			for (int l_I = 0; l_I < 9; ++l_I) {
				l_Stack = mc.player.inventory.getStackInSlot(l_I);

				if (mc.player.inventory.getStackInSlot(l_I).isEmpty())
					continue;

				if (l_Stack.getItem() instanceof ItemFood) {
					l_CanEat = true;
					mc.player.inventory.currentItem = l_I;
					mc.playerController.updateController();
					break;
				}
			}

			if (l_CanEat && l_Stack.getItem() instanceof ItemFood) {
				mc.gameSettings.keyBindUseItem.pressed = true;
				mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
				mc.playerController.updateController();

				m_WasEating = true;
			}
		}

		if (m_WasEating) {
			m_WasEating = false;
		}
	}
}
