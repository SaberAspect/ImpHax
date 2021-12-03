package mod.imphack.module.modules.combat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.BlockInteractionUtil;
import mod.imphack.util.BlockInteractionUtil.ValidResult;
import mod.imphack.util.BlockUtil;
import mod.imphack.util.PlayerUtil;

public class HoleFiller extends Module {
    
    public HoleFiller() {
		super("HoleFiller", "Fills Holes", Category.COMBAT);
		
		addSetting(hole_toggle, hole_rotate, hole_range, swing);
    }
    
    
    BooleanSetting hole_toggle = new BooleanSetting("Toggle", this, true);
    BooleanSetting hole_rotate = new BooleanSetting("Rotate", this, true);
    IntSetting hole_range = new IntSetting("Range", this, 4);
    ModeSetting swing = new ModeSetting("Swing", this, "Mainhand", "Mainhand", "Offhand", "Both", "None");


    private final ArrayList<BlockPos> holes = new ArrayList<>();

    @Override
	public void enable() {
		if (find_in_hotbar() == -1) {
		    this.disable();
        }
        find_new_holes();
	}

	@Override
	public void disable() {
        holes.clear();
    }
    
    @Override
	public void onUpdate() {

        if (find_in_hotbar() == -1) {
            this.disable();
            return;
        }

        if (holes.isEmpty()) {
            if (!hole_toggle.isEnabled()) {
                this.disable();
                return;

            } else {
                find_new_holes();
            }
        }

        BlockPos pos_to_fill = null;

        for (BlockPos pos : new ArrayList<>(holes)) {

            if (pos == null) continue;

            BlockInteractionUtil.ValidResult result = BlockInteractionUtil.valid(pos);

            if (result != ValidResult.Ok) {
                holes.remove(pos);
                continue;
            }
            pos_to_fill = pos;
            break;
        }

        if (find_in_hotbar() == -1) {
            this.disable();
            return;
        }

        if (pos_to_fill != null) {
            if (BlockUtil.placeBlock(pos_to_fill, find_in_hotbar(), hole_rotate.isEnabled(), hole_rotate.isEnabled(), true, swing)) {
                holes.remove(pos_to_fill);
            }
        }

    }

    public void find_new_holes() {

        holes.clear();

        for (BlockPos pos : BlockInteractionUtil.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), hole_range.getValue(), (int) hole_range.getValue(), false, true, 0)) {

            if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }

            boolean possible = true;

            for (BlockPos seems_blocks : new BlockPos[] {
            new BlockPos( 0, -1,  0),
            new BlockPos( 0,  0, -1),
            new BlockPos( 1,  0,  0),
            new BlockPos( 0,  0,  1),
            new BlockPos(-1,  0,  0)
            }) {
                Block block = mc.world.getBlockState(pos.add(seems_blocks)).getBlock();

                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                holes.add(pos);
            }
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockEnderChest) {
                    return i;
                }

                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
}