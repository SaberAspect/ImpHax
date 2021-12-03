package mod.imphack.module.modules.combat;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.BlockInteractionUtil;
import mod.imphack.util.BlockInteractionUtil.ValidResult;
import mod.imphack.util.BlockUtil;
import mod.imphack.util.MathUtil;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SelfFill extends Module {

	
	  	BooleanSetting toggle = new BooleanSetting("Toggle", this, false);
	  	BooleanSetting rotate = new BooleanSetting("Rotate", this, false);
	    ModeSetting swing = new ModeSetting("Swing", this, "Mainhand", "Mainhand", "Offhand", "Both", "None");

	    private BlockPos trap_pos;

	    
	    public SelfFill() {
	    	super("SelfFill", "Keeps you in holes", Category.COMBAT);
	    	
	    	addSetting(toggle, rotate, swing);
	    }
	    @Override
	    protected void onEnable() {
	        if (find_in_hotbar() == -1) {
	            this.disable();
	            return;
	        }
	    }

	    @Override
	    public void onUpdate() {
	        final Vec3d pos = MathUtil.interpolateEntity(mc.player, mc.getRenderPartialTicks());
	        trap_pos = new BlockPos(pos.x, pos.y + 2, pos.z);
	        if (is_trapped()) {

	            if (!toggle.isEnabled()) {
	                toggle();
	                return;
	            } 

	        }

	        ValidResult result = BlockInteractionUtil.valid(trap_pos);

	        if (result == ValidResult.AlreadyBlockThere && !mc.world.getBlockState(trap_pos).getMaterial().isReplaceable()) {
	            return;
	        } 

	        if (result == ValidResult.NoNeighbors) {

	            BlockPos[] tests = {
	                trap_pos.north(),
	                trap_pos.south(),
	                trap_pos.east(),
	                trap_pos.west(),
	                trap_pos.up(),
	                trap_pos.down().west() // ????? salhack is weird and i dont care enough to remove this. who the fuck uses this shit anyways fr fucking jumpy
	            };

	            for (BlockPos pos_ : tests) {

	                ValidResult result_ = BlockInteractionUtil.valid(pos_);

	                if (result_ == ValidResult.NoNeighbors || result_ == ValidResult.NoEntityCollision) continue;

	                if (BlockUtil.placeBlock(pos_, find_in_hotbar(), rotate.isEnabled(), rotate.isEnabled(), true, swing)) {
	                    return;
	                }

	            }

	            return;

	        }

	        BlockUtil.placeBlock(trap_pos, find_in_hotbar(), rotate.isEnabled(), rotate.isEnabled(), true, swing);

	    }

	    public boolean is_trapped() {

	        if (trap_pos == null) return false;

	        IBlockState state = mc.world.getBlockState(trap_pos);

	        return state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.WATER && state.getBlock() != Blocks.LAVA;

	    }

	    private int find_in_hotbar() {

	        for (int i = 0; i < 9; ++i) {

	            final ItemStack stack = mc.player.inventory.getStackInSlot(i);

	            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {

	                final Block block = ((ItemBlock) stack.getItem()).getBlock();

	                if (block instanceof BlockEnderChest)
	                    return i;
	                
	                else if (block instanceof BlockObsidian)
	                    return i;
	                
	            }
	        }
	        return -1;
	    }
    
}
