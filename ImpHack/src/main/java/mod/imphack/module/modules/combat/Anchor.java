package mod.imphack.module.modules.combat;

import mod.imphack.module.Category;
import mod.imphack.module.Module;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Anchor extends Module {
	
	public Anchor() {
		super ("Anchor", "sucks you in holes.", Category.COMBAT);
	}

	public static Minecraft mc = Minecraft.getMinecraft();
	
	private int packets;
	private boolean jumped;
	private final double[] oneblockPositions = new double[]{ 0.42, 0.75};

	@Override
	public void onUpdate(){
		if (Anchor.mc.world == null || Anchor.mc.player == null){
			return;
		}
			if (!Anchor.mc.player.onGround){
				if (Anchor.mc.gameSettings.keyBindJump.isKeyDown()){
					this.jumped = true;
				}
			}
			else{
				this.jumped = false;
			}
			if (!this.jumped && Anchor.mc.player.fallDistance < 0.5 && this.isInHole() && Anchor.mc.player.posY - this.getNearestBlockBelow() <= 1.125 && Anchor.mc.player.posY - this.getNearestBlockBelow() <= 0.95 && !this.isOnLiquid() && !this.isInLiquid()){
				if (!Anchor.mc.player.onGround){
					this.packets++;
				}
				if (!Anchor.mc.player.onGround && !Anchor.mc.player.isInsideOfMaterial(Material.WATER) && !Anchor.mc.player.isInsideOfMaterial(Material.LAVA) && !Anchor.mc.gameSettings.keyBindJump.isKeyDown() && !Anchor.mc.player.isOnLadder() && this.packets > 0){
					final BlockPos blockPos = new BlockPos(Anchor.mc.player.posX, Anchor.mc.player.posY, Anchor.mc.player.posZ);
					for (final double position : this.oneblockPositions){
						Anchor.mc.player.connection.sendPacket(new CPacketPlayer.Position(blockPos.getX() + 0.5f, Anchor.mc.player.posY - position, blockPos.getZ() + 0.5f, true));
					}
					Anchor.mc.player.setPosition(blockPos.getX() + 0.5f, this.getNearestBlockBelow() + 0.1, blockPos.getZ() + 0.5f);
					this.packets = 0;
				}
			}
		}

	private boolean isInHole(){
		final BlockPos blockPos = new BlockPos(Anchor.mc.player.posX, Anchor.mc.player.posY, Anchor.mc.player.posZ);
		final IBlockState blockState = Anchor.mc.world.getBlockState(blockPos);
		return this.isBlockValid(blockState, blockPos);
	}

	private double getNearestBlockBelow(){
		for (double y = Anchor.mc.player.posY; y > 0.0; y -= 0.001){
			if (!(Anchor.mc.world.getBlockState(new BlockPos(Anchor.mc.player.posX, y, Anchor.mc.player.posZ)).getBlock() instanceof BlockSlab) && Anchor.mc.world.getBlockState(new BlockPos(Anchor.mc.player.posX, y, Anchor.mc.player.posZ)).getBlock().getDefaultState().getCollisionBoundingBox(Anchor.mc.world, new BlockPos(0, 0, 0)) != null){
				return y;
			}
		}
		return -1.0;
	}

	private boolean isBlockValid(final IBlockState blockState, final BlockPos blockPos){
		return blockState.getBlock() == Blocks.AIR && Anchor.mc.player.getDistanceSq(blockPos) >= 1.0 && Anchor.mc.world.getBlockState(blockPos.up()).getBlock() == Blocks.AIR && Anchor.mc.world.getBlockState(blockPos.up(2)).getBlock() == Blocks.AIR && (this.isBedrockHole(blockPos) || this.isObbyHole(blockPos) || this.isBothHole(blockPos) || this.isElseHole(blockPos));
	}

	private boolean isObbyHole(final BlockPos blockPos){
		final BlockPos[] array;
		array = new BlockPos[]{ blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
		for (final BlockPos touching : array){
			final IBlockState touchingState = Anchor.mc.world.getBlockState(touching);
			if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.OBSIDIAN){
				return false;
			}
		}
		return true;
	}

	private boolean isBedrockHole(final BlockPos blockPos){
		final BlockPos[] array;
		array = new BlockPos[]{ blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
		for (final BlockPos touching : array){
			final IBlockState touchingState = Anchor.mc.world.getBlockState(touching);
			if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.BEDROCK){
				return false;
			}
		}
		return true;
	}

	private boolean isBothHole(final BlockPos blockPos){
		final BlockPos[] array;
		array = new BlockPos[]{ blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
		for (final BlockPos touching : array){
			final IBlockState touchingState = Anchor.mc.world.getBlockState(touching);
			if (touchingState.getBlock() == Blocks.AIR || (touchingState.getBlock() != Blocks.BEDROCK && touchingState.getBlock() != Blocks.OBSIDIAN)){
				return false;
			}
		}
		return true;
	}

	private boolean isElseHole(final BlockPos blockPos){
		final BlockPos[] array;
		array = new BlockPos[]{ blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
		for (final BlockPos touching : array){
			final IBlockState touchingState = Anchor.mc.world.getBlockState(touching);
			if (touchingState.getBlock() == Blocks.AIR || !touchingState.isFullBlock()){
				return false;
			}
		}
		return true;
	}

	private boolean isOnLiquid(){
		final double y = Anchor.mc.player.posY - 0.03;
		for (int x = MathHelper.floor(Anchor.mc.player.posX); x < MathHelper.ceil(Anchor.mc.player.posX); x++){
			for (int z = MathHelper.floor(Anchor.mc.player.posZ); z < MathHelper.ceil(Anchor.mc.player.posZ); z++){
				final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
				if (Anchor.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid){
					return true;
				}
			}
		}
		return false;
	}

	private boolean isInLiquid(){
		final double y = Anchor.mc.player.posY + 0.01;
		for (int x = MathHelper.floor(Anchor.mc.player.posX); x < MathHelper.ceil(Anchor.mc.player.posX); x++){
			for (int z = MathHelper.floor(Anchor.mc.player.posZ); z < MathHelper.ceil(Anchor.mc.player.posZ); z++){
				final BlockPos pos = new BlockPos(x, (int)y, z);
				if (Anchor.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid){
					return true;
				}
			}
		}
		return false;
	}
}