package mod.imphack.util;

import mod.imphack.setting.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityUtil {

	 private static double x;
	    private static double y;
	    private static double z;
	    private static boolean onground;

	static final Minecraft mc = Minecraft.getMinecraft();

	public static BlockPos getPlayerPosWithEntity() {
		return new BlockPos(
				(mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().posX
						: EntityUtil.mc.player.posX,
				(EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().posY
						: EntityUtil.mc.player.posY,
				(EntityUtil.mc.player.getRidingEntity() != null) ? EntityUtil.mc.player.getRidingEntity().posZ
						: EntityUtil.mc.player.posZ);
	}
	
	

	public static boolean isPassive(Entity e) {
		if (e instanceof EntityWolf && ((EntityWolf) e).isAngry())
			return false;
		if (e instanceof EntityAgeable || e instanceof EntityAmbientCreature || e instanceof EntitySquid)
			return true;
		return e instanceof EntityIronGolem && ((EntityIronGolem) e).getRevengeTarget() == null;
	}

	public static boolean isLiving(Entity e) {
		return e instanceof EntityLivingBase;
	}

	public static boolean isFakeLocalPlayer(Entity entity) {
		return entity != null && entity.getEntityId() == -100 && Minecraft.getMinecraft().player != entity;
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
		return new Vec3d((entity.posX - entity.lastTickPosX) * x, 0 * y, (entity.posZ - entity.lastTickPosZ) * z);
	}
	
	 public static Vec3d interpolateEntity(final Entity entity, final float time) {
	        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
	    }

	public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
		return getInterpolatedAmount(entity, vec.x, vec.y, vec.z);
	}
	
	 public static boolean isInHole(final Entity entity) {
	        return isBlockValid(new BlockPos(entity.posX, entity.posY, entity.posZ));
	    }
	 
	 
	    public static boolean isBlockValid(final BlockPos blockPos) {
	        return isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos);
	    }

	     public static boolean isObbyHole(final BlockPos blockPos) {
	        final BlockPos[] array;
	        final BlockPos[] touchingBlocks = array = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
	        for (final BlockPos pos : array) {
	            final IBlockState touchingState = EntityUtil.mc.world.getBlockState(pos);
	            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.OBSIDIAN) {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    public static boolean isBedrockHole(final BlockPos blockPos) {
	        final BlockPos[] array;
	        final BlockPos[] touchingBlocks = array = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
	        for (final BlockPos pos : array) {
	            final IBlockState touchingState = EntityUtil.mc.world.getBlockState(pos);
	            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.BEDROCK) {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    public static boolean isBothHole(final BlockPos blockPos) {
	        final BlockPos[] array;
	        final BlockPos[] touchingBlocks = array = new BlockPos[] { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
	        for (final BlockPos pos : array) {
	            final IBlockState touchingState = EntityUtil.mc.world.getBlockState(pos);
	            if (touchingState.getBlock() == Blocks.AIR || (touchingState.getBlock() != Blocks.BEDROCK && touchingState.getBlock() != Blocks.OBSIDIAN)) {
	                return false;
	            }
	        }
	        return true;
	    }
	public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
		return getInterpolatedAmount(entity, ticks, ticks, ticks);
	}

	public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
				.add(getInterpolatedAmount(entity, ticks));
	}

    public static boolean isIntercepted(BlockPos pos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox()))
                return true;
        }

        return false;
    }


	public static boolean isMobAggressive(Entity entity) {
		if (entity instanceof EntityPigZombie) {
			if (((EntityPigZombie) entity).isArmsRaised() || ((EntityPigZombie) entity).isAngry()) {
				return true;
			}
		} else if (entity instanceof EntityWolf) {
			return ((EntityWolf) entity).isAngry()
					&& !Minecraft.getMinecraft().player.equals(((EntityWolf) entity).getOwner());
		} else if (entity instanceof EntityEnderman) {
			return ((EntityEnderman) entity).isScreaming();
		}
		return isHostileMob(entity);
	}

	public static boolean isNeutralMob(Entity entity) {
		return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
	}

	public static boolean isFriendlyMob(Entity entity) {
		return (entity.isCreatureType(EnumCreatureType.CREATURE, false) && !EntityUtil.isNeutralMob(entity))
				|| (entity.isCreatureType(EnumCreatureType.AMBIENT, false)) || entity instanceof EntityVillager
				|| entity instanceof EntityIronGolem || (isNeutralMob(entity) && !EntityUtil.isMobAggressive(entity));
	}

	public static boolean isHostileMob(Entity entity) {
		return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EntityUtil.isNeutralMob(entity));
	}
	
	 public static float getArmor(EntityPlayer target) {
	        float armorDurability = 0;
	        for (ItemStack stack : target.getArmorInventoryList()) {
	            if (stack == null || stack.getItem() == Items.AIR)
	                continue;

	            armorDurability += ((float) (stack.getMaxDamage() - stack.getItemDamage()) / (float) stack.getMaxDamage()) * 100.0f;
	        }

	        return armorDurability;
	    }

	    public static boolean getArmor(EntityPlayer target, boolean melt, double durability) {
	        for (ItemStack stack : target.getArmorInventoryList()) {
	            if (stack == null || stack.getItem() == Items.AIR)
	                return true;

	            if (melt && durability >= ((float) (stack.getMaxDamage() - stack.getItemDamage()) / (float) stack.getMaxDamage()) * 100.0f)
	                return true;
	        }

	        return false;
	    }
	    
	    public static void updatePosition() {
	        x = mc.player.posX;
	        y = mc.player.posY;
	        z = mc.player.posZ;
	        onground = mc.player.onGround;
	    }

	    public static void restorePosition() {
	        mc.player.posX = x;
	        mc.player.posY = y;
	        mc.player.posZ = z;
	        mc.player.onGround = onground;
	    }
	    public static void attackEntity(final Entity entity, final boolean packet, final Setting setting) {
	        if (packet) {
	            mc.player.connection.sendPacket(new CPacketUseEntity(entity));
	        }
	        else {
	            mc.playerController.attackEntity(mc.player, entity);
	        }
	        if (setting.is("Mainhand") || setting.is("Both")) {
	            mc.player.swingArm(EnumHand.MAIN_HAND);
	        }
	        if (setting.is("Offhand") || setting.is("Both")) {
	            mc.player.swingArm(EnumHand.OFF_HAND);
	        }
	    }
	}


