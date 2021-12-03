package mod.imphack.module.modules.combat;


import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import mod.imphack.Client;
import mod.imphack.event.events.ImpHackEventRender;
import mod.imphack.module.Category;
import mod.imphack.module.Module;
import mod.imphack.setting.settings.BooleanSetting;
import mod.imphack.setting.settings.ColorSetting;
import mod.imphack.setting.settings.FloatSetting;
import mod.imphack.setting.settings.IntSetting;
import mod.imphack.setting.settings.ModeSetting;
import mod.imphack.util.BlockUtil;
import mod.imphack.util.InventoryUtil;
import mod.imphack.util.PlayerUtil;
import mod.imphack.util.Reference;
import mod.imphack.util.render.ColorUtil;
import mod.imphack.util.render.RenderUtil;
import mod.imphack.misc.RenderBuilder;



public class Surround extends Module {
	public Surround() {
		super("Surround", "Places Obsidian Around You", Category.COMBAT);

		addSetting(rotate);
		addSetting(hybrid);
		addSetting(toggleable);
		addSetting(centerPlyer);
		addSetting(blockFace);
		addSetting(tickForPlace);
		addSetting(timeoutTicks);
		addSetting(swingMode);




	}

	BooleanSetting rotate = new BooleanSetting("Rotate", this, true);
	BooleanSetting hybrid = new BooleanSetting("Hybrid", this, true);
	BooleanSetting toggleable = new BooleanSetting("Toggle",this , true);
	BooleanSetting centerPlyer = new BooleanSetting("Center", this,  false);
	BooleanSetting blockFace = new BooleanSetting("Block Face", this, false);

	IntSetting tickForPlace = new IntSetting("Blocks per tick", this, 2);
	IntSetting timeoutTicks = new IntSetting("Ticks til timeout", this, 20);
	ModeSetting swingMode = new ModeSetting("Swing", this, "Mainhand", "Mainhand", "Offhand", "Both", "None");


	private int playerYlevel = 0;
	private int tick = 0;
	private int playerStepOffsets = 0;

	private Vec3d centerOfBlock = Vec3d.ZERO;
	
	


	@Override
	public void onEnable() {
		if (findItemInHotbar() == -1) {
			this.disable();
			return;
		}

		if (mc.player != null) {

			playerYlevel = (int) Math.round(mc.player.posY);

			centerOfBlock = getCenterPlayer(mc.player.posX, mc.player.posY, mc.player.posZ);

			if (centerPlyer.isEnabled()) {
				mc.player.motionX = 0;
				mc.player.motionZ = 0;
			}
		}
	}
	
	

	@Override
	public void onUpdate() {

		if (mc.player != null) {

			if (centerOfBlock != Vec3d.ZERO && centerPlyer.isEnabled()) {

				double xDifferential = Math.abs(centerOfBlock.x - mc.player.posX);
				double zDifferential = Math.abs(centerOfBlock.z - mc.player.posZ);

				if (xDifferential <= 0.1 && zDifferential <= 0.1) {
					centerOfBlock = Vec3d.ZERO;
				} else {
					double playerMotionX = centerOfBlock.x - mc.player.posX;
					double playerMotionZ = centerOfBlock.z - mc.player.posZ;

					mc.player.motionX = playerMotionX / 2;
					mc.player.motionZ = playerMotionZ / 2;
				}
			}

			if ((int) Math.round(mc.player.posY) != playerYlevel && this.hybrid.isEnabled()) {
				this.disable();
				return;
			}

			if (!this.toggleable.isEnabled() && this.tick >= this.timeoutTicks.getValue()) { 
				this.tick = 0;
				this.disable();
				return;
			}

			int blocksPlaced = 0;

			while (blocksPlaced < this.tickForPlace.getValue()) {

				if (this.playerStepOffsets >= (blockFace.isEnabled() ? this.surroundPlayerFace.length : this.surroundPlayer.length)) {
					this.playerStepOffsets = 0;
					break;
				}

				BlockPos offsetPos = new BlockPos(blockFace.isEnabled() ? this.surroundPlayerFace[playerStepOffsets] : this.surroundPlayer[playerStepOffsets]);
				BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

				boolean tryPlace = true;

				if (!mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
					tryPlace = false;
				}

				for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
					if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
					tryPlace = false;
					break;
				}

				if (tryPlace && BlockUtil.placeBlock(targetPos, findItemInHotbar(), rotate.isEnabled(), rotate.isEnabled(), true, swingMode)) {
					blocksPlaced++;
				}

				playerStepOffsets++;
			}

			this.tick++;
		}
	}
	
	public boolean blocksSurround() {
		if (!this.isEnabled()) {
			return false;
		}

		boolean areAllPlaced = true;

		for (Vec3d target : surroundPlayer) {
			BlockPos offsetPos = new BlockPos(target);
			BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
			areAllPlaced = areAllPlaced && !mc.world.getBlockState(targetPos).getMaterial().isReplaceable();
		}

		return !areAllPlaced;
	}

	

	public Vec3d getCenterPlayer(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5D;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5D ;

        return new Vec3d(x, y, z);
    }

	
	private int findItemInHotbar() {

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
	
	
	Vec3d[] surroundPlayer = {
			new Vec3d(  1,   0,   0),
			new Vec3d(  0,   0,   1),
			new Vec3d(- 1,   0,   0),
			new Vec3d(  0,   0, - 1),
			new Vec3d(  1, - 1,   0),
			new Vec3d(  0, - 1,   1),
			new Vec3d(- 1, - 1,   0),
			new Vec3d(  0, - 1, - 1),
			new Vec3d(  0, - 1,   0)
		};

		Vec3d[] surroundPlayerFace = {
				new Vec3d(  1,   1,   0),
				new Vec3d(  0,   1,   1),
				new Vec3d(- 1,   1,   0),
				new Vec3d(  0,   1, - 1),
				new Vec3d(  1,   0,   0),
				new Vec3d(  0,   0,   1),
				new Vec3d(- 1,   0,   0),
				new Vec3d(  0,   0, - 1),
				new Vec3d(  1, - 1,   0),
				new Vec3d(  0, - 1,   1),
				new Vec3d(- 1, - 1,   0),
				new Vec3d(  0, - 1, - 1),
				new Vec3d(  0, - 1,   0)
		};
	
}