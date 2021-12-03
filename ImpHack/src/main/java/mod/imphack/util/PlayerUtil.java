package mod.imphack.util;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemFood;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil {
	static final Minecraft mc = Minecraft.getMinecraft();

	 public static Minecraft mc() {
	        return Minecraft.getMinecraft();
	    }
	 public static EntityPlayerSP player() {
	        return mc().player;
	    }
	 
	public static PlayerControllerMP controller() {
        return mc().playerController;
    }

    public void swingArm() {
        player().swingArm(EnumHand.MAIN_HAND);
    }

    public static void attack(Entity entity) {
        controller().attackEntity(player(), entity);
    }
    
    public static void sendPacket(Packet<?> packet) {
        player().connection.sendPacket(packet);
    }

    
	public static double getFallDistance(EntityPlayerSP player, WorldClient world) {
		for (double i = player.serverPosY; i > 0; i--) {
			if (world.getBlockState(new BlockPos(player.serverPosX, i, player.serverPosZ))
					.getBlock() instanceof BlockAir) {
				return i;
			}
		}
		return 0;
	}

	public static void PacketFacePitchAndYaw(float p_Pitch, float p_Yaw) {

		boolean l_IsSprinting = mc.player.isSprinting();

		if (l_IsSprinting != mc.player.serverSprintState) {
			if (l_IsSprinting) {
				mc.player.connection
						.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
			} else {
				mc.player.connection
						.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
			}

			mc.player.serverSprintState = l_IsSprinting;
		}

		boolean l_IsSneaking = mc.player.isSneaking();

		if (l_IsSneaking != mc.player.serverSneakState) {
			if (l_IsSneaking) {
				mc.player.connection
						.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
			} else {
				mc.player.connection
						.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
			}

			mc.player.serverSneakState = l_IsSneaking;
		}

		if (PlayerUtil.isCurrentViewEntity()) {

			AxisAlignedBB axisalignedbb = mc.player.getEntityBoundingBox();
			double l_PosXDifference = mc.player.posX - mc.player.lastReportedPosX;
			double l_PosYDifference = axisalignedbb.minY - mc.player.lastReportedPosY;
			double l_PosZDifference = mc.player.posZ - mc.player.lastReportedPosZ;
			double l_YawDifference = p_Yaw - mc.player.lastReportedYaw;
			double l_RotationDifference = p_Pitch - mc.player.lastReportedPitch;
			mc.player.positionUpdateTicks = mc.player.positionUpdateTicks + 1;
			boolean l_MovedXYZ = l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference
					+ l_PosZDifference * l_PosZDifference > 9.0E-4D || mc.player.positionUpdateTicks >= 20;
			boolean l_MovedRotation = l_YawDifference != 0.0D || l_RotationDifference != 0.0D;

			if (mc.player.isRiding()) {
				mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.motionX, -999.0D,
						mc.player.motionZ, p_Yaw, p_Pitch, mc.player.onGround));
				l_MovedXYZ = false;
			} else if (l_MovedXYZ && l_MovedRotation) {
				mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, axisalignedbb.minY,
						mc.player.posZ, p_Yaw, p_Pitch, mc.player.onGround));
			} else if (l_MovedXYZ) {
				mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, axisalignedbb.minY,
						mc.player.posZ, mc.player.onGround));
			} else if (l_MovedRotation) {
				mc.player.connection.sendPacket(new CPacketPlayer.Rotation(p_Yaw, p_Pitch, mc.player.onGround));
			} else if (mc.player.prevOnGround != mc.player.onGround) {
				mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
			}

			if (l_MovedXYZ) {
				mc.player.lastReportedPosX = mc.player.posX;
				mc.player.lastReportedPosY = axisalignedbb.minY;
				mc.player.lastReportedPosZ = mc.player.posZ;
				mc.player.positionUpdateTicks = 0;
			}

			if (l_MovedRotation) {
				mc.player.lastReportedYaw = p_Yaw;
				mc.player.lastReportedPitch = p_Pitch;
			}

			mc.player.prevOnGround = mc.player.onGround;
			mc.player.autoJumpEnabled = mc.gameSettings.autoJump;
		}
	}

	public static boolean isCurrentViewEntity() {
		return mc.getRenderViewEntity() == mc.player;
	}

	public static void lookAtPos(BlockPos pos) {
		float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()),
				new Vec3d((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f));
		setPlayerRotations(angle[0], angle[1]);
	}

	public static void setPlayerRotations(float yaw, float pitch) {
		mc.player.rotationYaw = yaw;
		mc.player.rotationYawHead = yaw;
		mc.player.rotationPitch = pitch;
	}

	public static boolean IsEating() {
		return mc.player != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemFood
				&& mc.player.isHandActive();
	}
	 public static void centerPlayer(Vec3d centeredBlock) {

	        double xDeviation = Math.abs(centeredBlock.x - mc.player.posX);
	        double zDeviation = Math.abs(centeredBlock.z - mc.player.posZ);

	        if (xDeviation <= 0.1 && zDeviation <= 0.1) {
	            centeredBlock = Vec3d.ZERO;
	        } else {
	            double newX = -2;
	            double newZ = -2;
	            int xRel = (mc.player.posX < 0 ? -1 : 1);
	            int zRel = (mc.player.posZ < 0 ? -1 : 1);
	            if (BlockUtil.getBlock(mc.player.posX, mc.player.posY - 1, mc.player.posZ) instanceof BlockAir) {
	                if (Math.abs((mc.player.posX % 1)) * 1E2 <= 30) {
	                    newX = Math.round(mc.player.posX - (0.3 * xRel)) + 0.5 * -xRel;
	                } else if (Math.abs((mc.player.posX % 1)) * 1E2 >= 70) {
	                    newX = Math.round(mc.player.posX + (0.3 * xRel)) - 0.5 * -xRel;
	                }
	                if (Math.abs((mc.player.posZ % 1)) * 1E2 <= 30) {
	                    newZ = Math.round(mc.player.posZ - (0.3 * zRel)) + 0.5 * -zRel;
	                } else if (Math.abs((mc.player.posZ % 1)) * 1E2 >= 70) {
	                    newZ = Math.round(mc.player.posZ + (0.3 * zRel)) - 0.5 * -zRel;
	                }
	            }

	            if (newX == -2)
	                if (mc.player.posX > Math.round(mc.player.posX)) {
	                    newX = Math.round(mc.player.posX) + 0.5;
	                }
	                // (mc.player.posX % 1)*1E2 < 30
	                else if (mc.player.posX < Math.round(mc.player.posX)) {
	                    newX = Math.round(mc.player.posX) - 0.5;
	                } else {
	                    newX = mc.player.posX;
	                }

	            if (newZ == -2)
	                if (mc.player.posZ > Math.round(mc.player.posZ)) {
	                    newZ = Math.round(mc.player.posZ) + 0.5;
	                } else if (mc.player.posZ < Math.round(mc.player.posZ)) {
	                    newZ = Math.round(mc.player.posZ) - 0.5;
	                } else {
	                    newZ = mc.player.posZ;
	                }

	            mc.player.connection.sendPacket(new CPacketPlayer.Position(newX, mc.player.posY, newZ, true));
	            mc.player.setPosition(newX, mc.player.posY, newZ);
	        }
	        
	        
	    }
	 
	 public static Vec3d getCenter(double posX, double posY, double posZ) {
	        return new Vec3d(Math.floor(posX) + 0.5D, Math.floor(posY), Math.floor(posZ) + 0.5D);
	    }
	 
	 public static double getHealth() {
	        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
	    }
	 public static BlockPos GetLocalPlayerPosFloored() {
	        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
	    }
	 
}