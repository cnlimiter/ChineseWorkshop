
package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class WallCandleBlock extends CandleBlock{
	protected static final VoxelShape NORTH_SHAPE = box(3.2, 3.2, 4.8, 12.8, 12.8, 16);
	protected static final VoxelShape SOUTH_SHAPE = box(3.2, 1.6, 0, 12.8, 12.8, 11.2);
	protected static final VoxelShape WEST_SHAPE = box(4.8, 3.2, 3.2, 16, 12.8, 12.8);
	protected static final VoxelShape EAST_SHAPE = box(0, 3.2, 3.2, 11.2, 12.8, 12.8);

	public WallCandleBlock(Block.Properties builder) {
		super(builder);
	}

	@Override
	public boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return Blocks.WALL_TORCH.isOcclusionShapeFullBlock(state, worldIn, pos);
	}


	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		return Blocks.WALL_TORCH.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = Blocks.WALL_TORCH.getStateForPlacement(context);
		return blockstate == null ? null : this.defaultBlockState().setValue(FACING, blockstate.getValue(FACING));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return Blocks.WALL_TORCH.rotate(state, rot);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return Blocks.WALL_TORCH.mirror(state, mirrorIn);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}


	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		double d0 = pos.getX() + 0.5D;
		double d1 = pos.getY() + 0.85D;
		double d2 = pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
		switch (state.getValue(FACING)) {
			default:
				return EAST_SHAPE;
			case WEST:
				return WEST_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case NORTH:
				return NORTH_SHAPE;
		}
	}
}
