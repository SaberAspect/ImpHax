package mod.imphack.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


import mod.imphack.misc.Rotation.RotationMode;
import mod.imphack.misc.RotationPriority;
import mod.imphack.setting.Setting;

public class BlockUtil {
	static final Minecraft mc;
	public static final List blackList;
	public static final List shulkerList;

	
	 public static List<Block> emptyBlocks;
	    public static List<Block> rightclickableBlocks;

	    static
	    {
	        emptyBlocks = Arrays.asList(Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, Blocks.TALLGRASS, Blocks.FIRE);
	        rightclickableBlocks = Arrays.asList(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, Blocks.UNPOWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
	    }

	public static boolean isScaffoldPos(final BlockPos pos) {
		return BlockUtil.mc.world.isAirBlock(pos)
				|| BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.SNOW_LAYER
				|| BlockUtil.mc.world.getBlockState(pos).getBlock() == Blocks.TALLGRASS
				|| BlockUtil.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid;
	}

	@SuppressWarnings("deprecation")
	public static boolean isValidBlock(final BlockPos pos) {
		final Block block = BlockUtil.mc.world.getBlockState(pos).getBlock();
		return !(block instanceof BlockLiquid) && block.getMaterial(null) != Material.AIR;
	}

	public static EnumFacing getFacing(BlockPos pos) {
		for (EnumFacing facing : EnumFacing.values()) {
			RayTraceResult rayTraceResult = BlockUtil.mc.world.rayTraceBlocks(new Vec3d(BlockUtil.mc.player.posX,
					BlockUtil.mc.player.posY + (double) BlockUtil.mc.player.getEyeHeight(), BlockUtil.mc.player.posZ),
					new Vec3d((double) pos.getX() + 0.5 + (double) facing.getDirectionVec().getX() / 2.0,
							(double) pos.getY() + 0.5 + (double) facing.getDirectionVec().getY() / 2.0,
							(double) pos.getZ() + 0.5 + (double) facing.getDirectionVec().getZ() / 2.0),
					false, true, false);
			if (rayTraceResult != null && (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK
					|| !rayTraceResult.getBlockPos().equals(pos)))
				continue;
			return facing;
		}
		if ((double) pos.getY() > BlockUtil.mc.player.posY + (double) BlockUtil.mc.player.getEyeHeight()) {
			return EnumFacing.DOWN;
		}
		return EnumFacing.UP;
	}
	
	//BlockOverlay
	
	public static IBlockState getState(BlockPos pos)
	{
		return mc.world.getBlockState(pos);
	}
	
	public static Block getBlock(BlockPos pos)
	{
		return getState(pos).getBlock();
	}
	
	static{
		blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
		shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
				Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
		mc = Minecraft.getMinecraft();
	}
	
	 public static Block getBlock(double x, double y, double z) {
	        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
	    }
	
	public static EnumFacing getPlaceableSide(BlockPos pos){

		for (EnumFacing side : EnumFacing.values()){

			BlockPos neighbour = pos.offset(side);

			if (!mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)){
				continue;
			}

			IBlockState blockState = mc.world.getBlockState(neighbour);
			if (!blockState.getMaterial().isReplaceable()){
				return side;
			}
		}

		return null;
	}
	
	public static boolean canBeClicked(BlockPos pos)
	{
		return !getBlock(pos).canCollideCheck(getState(pos), false);
	}
	public static void faceVectorPacketInstant(Vec3d vec){
		float[] rotations = getNeededRotations(vec);

		mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0],
				rotations[1], mc.player.onGround));
	}
	private static float[] getNeededRotations(Vec3d vec){
		Vec3d eyesPos = getEyesPos();

		double diffX = vec.x - eyesPos.x;
		double diffY = vec.y - eyesPos.y;
		double diffZ = vec.z - eyesPos.z;

		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

		return new float[]{
				mc.player.rotationYaw
						+ MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
				mc.player.rotationPitch + MathHelper
						.wrapDegrees(pitch - mc.player.rotationPitch)};
	}

	public static Vec3d getEyesPos(){
		return new Vec3d(mc.player.posX,
				mc.player.posY + mc.player.getEyeHeight(),
				mc.player.posZ);
	}

	 @SuppressWarnings("deprecation")
	    public static BlockResistance getBlockResistance(BlockPos block) {
	        if (mc.world.isAirBlock(block))
	            return BlockResistance.Blank;

	        else if (mc.world.getBlockState(block).getBlock().getBlockHardness(mc.world.getBlockState(block), mc.world, block) != -1 && !(mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)))
	            return BlockResistance.Breakable;

	        else if (mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) || mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST))
	            return BlockResistance.Resistant;

	        else if (mc.world.getBlockState(block).getBlock().equals(Blocks.BEDROCK))
	            return BlockResistance.Unbreakable;

	        return null;
	 }
	 
	 public static void placeBlock(BlockPos pos, boolean rotate, boolean strict, boolean raytrace, boolean packet, boolean swingArm, boolean antiGlitch) {
	        for (EnumFacing enumFacing : EnumFacing.values()) {
	            if (!(getBlockResistance(pos.offset(enumFacing)) == BlockResistance.Blank) && !EntityUtil.isIntercepted(pos)) {
	                Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

	                float[] old = new float[] {
	                        mc.player.rotationYaw, mc.player.rotationPitch
	                };

	                if (strict)
	                    RotationUtil.rotationQueue.add(new mod.imphack.misc.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), RotationMode.Packet, RotationPriority.High));

	                if (rotate)
	                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float) Math.toDegrees(Math.atan2((vec.z - mc.player.posZ), (vec.x - mc.player.posX))) - 90.0F, (float) (-Math.toDegrees(Math.atan2((vec.y - (mc.player.posY + (double) mc.player.getEyeHeight())), (Math.sqrt((vec.x - mc.player.posX) * (vec.x - mc.player.posX) + (vec.z - mc.player.posZ) * (vec.z - mc.player.posZ)))))), mc.player.onGround));

	                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));

	                if (packet)
	                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, raytrace ? enumFacing : EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
	                else
	                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), raytrace ? enumFacing.getOpposite() : EnumFacing.UP, new Vec3d(pos), EnumHand.MAIN_HAND);

	                if (swingArm)
	                    mc.player.swingArm(EnumHand.MAIN_HAND);

	                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));

	                if (rotate)
	                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(old[0], old[1], mc.player.onGround));

	                if (antiGlitch)
	                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos.offset(enumFacing), enumFacing.getOpposite()));

	                return;
	            }
	        }
	    }
	 
	 public static void swingArm(Setting setting) {
	        if (setting.is("Mainhand") || setting.is("Both")) {
	            mc.player.swingArm(EnumHand.MAIN_HAND);
	        }
	        if (setting.is("Offhand") || setting.is("Both")) {
	            mc.player.swingArm(EnumHand.OFF_HAND);
	        }
	    }
	 
	 
	 public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack, boolean sneak, Setting setting) {
	        if (isBlockEmpty(pos)) {
	            int old_slot = -1;
	            if (slot != mc.player.inventory.currentItem) {
	                old_slot = mc.player.inventory.currentItem;
	                mc.player.inventory.currentItem = slot;
	            }

	            EnumFacing[] facings = EnumFacing.values();

	            for (EnumFacing f : facings) {
	                Block neighborBlock = mc.world.getBlockState(pos.offset(f)).getBlock();
	                Vec3d vec = new Vec3d(pos.getX() + 0.5D + f.getXOffset() * 0.5D, pos.getY() + 0.5D + f.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) f.getZOffset() * 0.5D);

	                if (!emptyBlocks.contains(neighborBlock) && mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25D) {
	                    float[] rot = new float[]{mc.player.rotationYaw, mc.player.rotationPitch};

	                    if (rotate) {
	                        rotatePacket(vec.x, vec.y, vec.z);
	                    }

	                    if (rightclickableBlocks.contains(neighborBlock) && sneak) {
	                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
	                    }

	                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(f), f.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);
	                    if (rightclickableBlocks.contains(neighborBlock) && sneak) {
	                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
	                    }

	                    if (rotateBack) {
	                        mc.player.connection.sendPacket(new Rotation(rot[0], rot[1], mc.player.onGround));
	                    }

	                    swingArm(setting);

	                    if (old_slot != -1) {
	                        mc.player.inventory.currentItem = old_slot;
	                    }

	                    return true;
	                }
	            }
	        }

	        return false;
	    }
	 
	 public static void rotatePacket(double x, double y, double z) {
	        double diffX = x - mc.player.posX;
	        double diffY = y - (mc.player.posY + (double) mc.player.getEyeHeight());
	        double diffZ = z - mc.player.posZ;
	        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

	        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
	        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

	        mc.player.connection.sendPacket(new Rotation(yaw, pitch, mc.player.onGround));
	    }
	 
	 public static boolean isBlockEmpty(BlockPos pos) {
	        try {
	            if (emptyBlocks.contains(mc.world.getBlockState(pos).getBlock())) {
	                AxisAlignedBB box = new AxisAlignedBB(pos);
	                Iterator<Entity> entityIter = mc.world.loadedEntityList.iterator();

	                Entity e;

	                do {
	                    if (!entityIter.hasNext()) {
	                        return true;
	                    }

	                    e = (Entity) entityIter.next();
	                } while (!(e instanceof EntityLivingBase) || !box.intersects(e.getEntityBoundingBox()));

	            }
	        } catch (Exception ignored) { }
	        return false;
	    }
	 
	 public static boolean canSeeBlock(final BlockPos p_Pos) {
	        return mc.player != null && mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(p_Pos.getX(), p_Pos.getY(), p_Pos.getZ()), false, true, false) == null;
	    }
	 
	 public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
	        return !shouldCheck || mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX(), pos.getY() + height, pos.getZ()), false, true, false) == null;
	    }
	  public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
	        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
	    }
	  
	   public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand) {
	        final RayTraceResult result = mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
	        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
	        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
	   }



	
	 public enum BlockResistance {
	        Blank,
	        Breakable,
	        Resistant,
	        Unbreakable
	    }
	
}
