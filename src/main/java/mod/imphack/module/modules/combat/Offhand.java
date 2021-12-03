package mod.imphack.module.modules.combat;

import mod.imphack.Main;
import mod.imphack.container.ImpHackInventory;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.InventoryUtil;
import mod.imphack.util.PlayerUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

import org.lwjgl.input.Mouse;

public class Offhand extends Module {
    public Offhand() {
        super("Offhand", "Switches items in the offhand to a totem when low on health", Category.COMBAT);
        addSetting(switch_mode);
        addSetting(totem_switch);
        addSetting(gapple_in_hole);
        addSetting(gapple_hole_hp);
        addSetting(delay);
    }

    ModeSetting switch_mode = new ModeSetting("Offhand", this, "Totem", "Totem", "Crystal", "Gapple");
    IntSetting totem_switch = new IntSetting("Totem HP",this, 16);

    BooleanSetting gapple_in_hole = new BooleanSetting("Gapple In Hole", this, false);
    IntSetting gapple_hole_hp = new IntSetting("Gapple Hole HP", this, 8);

    BooleanSetting delay = new BooleanSetting("Delay", this, false);

    private boolean switching = false;
    private int last_slot;

    @Override
    public void onUpdate() {

        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {

            if (switching) {
                swap_items(last_slot, 2);
                return;
            }

            float hp = mc.player.getHealth() + mc.player.getAbsorptionAmount();

            if (hp > totem_switch.getValue()) {
                if (switch_mode.is("Crystal") && Main.moduleManager.getModule("CrystalAura").isToggled()) {
                    swap_items(get_item_slot(Items.END_CRYSTAL),0);
                    return;
                }
                if (gapple_in_hole.isEnabled() && hp > gapple_hole_hp.getValue() && is_in_hole()) {
                    swap_items(get_item_slot(Items.GOLDEN_APPLE), delay.isEnabled() ? 1 : 0);
                    return;
                }
                if (switch_mode.is("Totem")) {
                    swap_items(get_item_slot(Items.TOTEM_OF_UNDYING), delay.isEnabled() ? 1 : 0);
                    return;
                }
                if (switch_mode.is("Gapple")) {
                    swap_items(get_item_slot(Items.GOLDEN_APPLE), delay.isEnabled() ? 1 : 0);
                    return;
                }
                if (switch_mode.is("Crystal") && !Main.moduleManager.getModule("CrystalAura").isToggled()) {
                    swap_items(get_item_slot(Items.TOTEM_OF_UNDYING),0);
                    return;
                }
            } else {
                swap_items(get_item_slot(Items.TOTEM_OF_UNDYING), delay.isEnabled() ? 1 : 0);
                return;
            }

            if (mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                swap_items(get_item_slot(Items.TOTEM_OF_UNDYING), delay.isEnabled() ? 1 : 0);
            }

        }

    }

    public void swap_items(int slot, int step) {
        if (slot == -1) return;
        if (step == 0) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        }
        if (step == 1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = true;
            last_slot = slot;
        }
        if (step == 2) {
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            switching = false;
        }

        mc.playerController.updateController();
    }

    private boolean is_in_hole() {

        BlockPos player_block = PlayerUtil.GetLocalPlayerPosFloored();

        return mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR;
    }


    private int get_item_slot(Item input) {
        if (input == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == input) {
                if (i < 9) {
                    if (input == Items.GOLDEN_APPLE) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}