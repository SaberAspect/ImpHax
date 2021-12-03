package mod.imphack.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static java.lang.Double.isNaN;

import java.util.ArrayList;
import java.util.List;

public class BlockInteractionUtil {
	static final Minecraft mc = Minecraft.getMinecraft();

	
	
	 public enum ValidResult
	    {
	        NoEntityCollision,
	        AlreadyBlockThere,
	        NoNeighbors,
	        Ok,
	    }
	
	public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing) {
		return getRotationsForPosition(x + 0.5 + facing.getDirectionVec().getX() / 2.0,
				y + 0.5 + facing.getDirectionVec().getY() / 2.0, z + 0.5 + facing.getDirectionVec().getZ() / 2.0);
	}

	public static float[] getRotationsForPosition(double x, double y, double z) {
		return getRotationsForPosition(x, y, z, mc.player.posX, mc.player.posY + mc.player.getEyeHeight(),
				mc.player.posZ);
	}
	
	
	public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                do {
                    float f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                } while (true);
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }
	
	
	 public static boolean checkForNeighbours(final BlockPos blockPos) {
	        if (!hasNeighbour(blockPos)) {
	            for (final EnumFacing side : EnumFacing.values()) {
	                final BlockPos neighbour = blockPos.offset(side);
	                if (hasNeighbour(neighbour)) {
	                    return true;
	                }
	            }
	            return false;
	        }
	        return true;
	    }
	 
	  private static boolean hasNeighbour(final BlockPos blockPos) {
	        for (final EnumFacing side : EnumFacing.values()) {
	            final BlockPos neighbour = blockPos.offset(side);
	            if (!mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
	                return true;
	            }
	        }
	        return false;
	    }
	
	  public static ValidResult valid(BlockPos pos)
	    {
	        // There are no entities to block placement,
	        if (!mc.world.checkNoEntityCollision(new AxisAlignedBB(pos)))
	            return ValidResult.NoEntityCollision;

	        if (!checkForNeighbours(pos))
	            return ValidResult.NoNeighbors;

	        IBlockState l_State = mc.world.getBlockState(pos);

	        if (l_State.getBlock() == Blocks.AIR)
	        {
	            final BlockPos[] l_Blocks =
	            { pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down() };

	            for (BlockPos l_Pos : l_Blocks)
	            {
	                IBlockState l_State2 = mc.world.getBlockState(l_Pos);

	                if (l_State2.getBlock() == Blocks.AIR)
	                    continue;

	                for (final EnumFacing side : EnumFacing.values())
	                {
	                    final BlockPos neighbor = pos.offset(side);

	                    if (mc.world.getBlockState(neighbor).getBlock().canCollideCheck(mc.world.getBlockState(neighbor), false))
	                    {
	                        return ValidResult.Ok;
	                    }
	                }
	            }

	            return ValidResult.NoNeighbors;
	        }
	        return ValidResult.AlreadyBlockThere;

	    }


	public static float[] getRotationsForPosition(double x, double y, double z, double sourceX, double sourceY,
			double sourceZ) {
		double deltaX = x - sourceX;
		double deltaY = y - sourceY;
		double deltaZ = z - sourceZ;

		double yawToEntity;

		if (deltaZ < 0 && deltaX < 0) { // quadrant 3
			yawToEntity = 90D + Math.toDegrees(Math.atan(deltaZ / deltaX)); // 90
			// degrees
			// forward
		} else if (deltaZ < 0 && deltaX > 0) { // quadrant 4
			yawToEntity = -90D + Math.toDegrees(Math.atan(deltaZ / deltaX)); // 90
			// degrees
			// back
		} else { // quadrants one or two
			yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
		}

		double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));

		yawToEntity = wrapAngleTo180((float) yawToEntity);
		pitchToEntity = wrapAngleTo180((float) pitchToEntity);

		yawToEntity = isNaN(yawToEntity) ? 0 : yawToEntity;
		pitchToEntity = isNaN(pitchToEntity) ? 0 : pitchToEntity;

		return new float[] { (float) yawToEntity, (float) pitchToEntity };
	}

	public static float wrapAngleTo180(float angle) {
		angle %= 360.0F;

		while (angle >= 180.0F) {
			angle -= 360.0F;
		}
		while (angle < -180.0F) {
			angle += 360.0F;
		}

		return angle;
	}

	public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack) {
		if (WorldUtil.NONSOLID_BLOCKS.contains(mc.world.getBlockState(pos).getBlock())) {
			int old_slot = -1;
			if (slot != mc.player.inventory.currentItem) {
				old_slot = mc.player.inventory.currentItem;
				mc.player.inventory.currentItem = slot;
			}

			EnumFacing[] facings = EnumFacing.values();

			for (EnumFacing f : facings) {
				Block neighborBlock = mc.world.getBlockState(pos.offset(f)).getBlock();
				Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) f.getXOffset() * 0.5D,
						pos.getY() + 0.5D + (double) f.getYOffset() * 0.5D,
						pos.getZ() + 0.5D + (double) f.getZOffset() * 0.5D);

				if (!WorldUtil.NONSOLID_BLOCKS.contains(neighborBlock)
						&& mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25D) {
					float[] rot = new float[] { mc.player.rotationYaw, mc.player.rotationPitch };

					if (rotate) {
						PlayerUtil.lookAtPos(new BlockPos(vec.x, vec.y, vec.z));
					}

					if (WorldUtil.RIGHTCLICKABLE_BLOCKS.contains(neighborBlock)) {
						mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
					}

					mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(f), f.getOpposite(),
							new Vec3d(pos), EnumHand.MAIN_HAND);
					if (WorldUtil.RIGHTCLICKABLE_BLOCKS.contains(neighborBlock)) {
						mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
					}

					if (rotateBack) {
						mc.player.connection.sendPacket(new Rotation(rot[0], rot[1], mc.player.onGround));
					}

					if (old_slot != -1) {
						mc.player.inventory.currentItem = old_slot;
					}

					return true;
				}
			}

		}

		return false;
	}
}
