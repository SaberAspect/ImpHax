package mod.imphack.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class TileEntityFakeWorld extends World {

	private IBlockState state;
	private Chunk chunk;

	public TileEntityFakeWorld(IBlockState state, WorldProvider provider) {
		super(null, null, provider, null, false);
		this.state = state;
	}

	public TileEntityFakeWorld(ISaveHandler saveHandler, WorldInfo info, WorldProvider provider) {
		super(saveHandler, info, provider, null, false);
		this.state = Blocks.AIR.getDefaultState();
	}

	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}

	public Chunk getChunk(int chunkX, int chunkZ) {
		return this.chunk;
	}

	public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ) { // Runtime name of getChunk (why is this different ?)
		return this.chunk;
	}

	public IBlockState getBlockState(BlockPos position) {
		if (position.equals(BlockPos.ORIGIN))
			return this.state;
		return Blocks.AIR.getDefaultState();
	}

	public boolean setBlockState(BlockPos position, IBlockState state, int updt) {
		this.state = state;
		return true;
	}

	public void notifyBlockUpdate(BlockPos pos, IBlockState oldState, IBlockState newState, int flags) { // Don't do
																											// anything

	}

	public long getTotalWorldTime() {
		if (this.worldInfo == null)
			return 0L;
		return super.getTotalWorldTime();
	}

	protected IChunkProvider createChunkProvider() {
		return null;
	}

	protected boolean isChunkLoaded(int paramInt1, int paramInt2, boolean paramBoolean) {
		return false;
	}

	public void neighborChanged(BlockPos pos, final Block blockIn, BlockPos fromPos) {
	}

	public void observedNeighborChanged(BlockPos pos, final Block block, BlockPos target) {
	}

}