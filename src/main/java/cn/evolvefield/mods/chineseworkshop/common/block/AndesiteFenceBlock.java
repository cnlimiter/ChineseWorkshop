package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AndesiteFenceBlock extends CrossCollisionBlock { //TODO: new WallBlock behavior
	public static final BooleanProperty UP = BlockStateProperties.UP;
	private final VoxelShape[] wallShapes;
	private final VoxelShape[] wallCollisionShapes;

	public AndesiteFenceBlock(Properties properties) {
		super(0, 1.5F, 16, 16, 24, properties);
		this.wallShapes = this.makeShapes(2F, 1.5F, 18.0F, 0.0F, 16.0F);
		this.wallCollisionShapes = this.makeShapes(2F, 1.5F, 24.0F, 0.0F, 24.0F);
	}

	@Override
	public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
		return super.getShape($$0, $$1, $$2, $$3);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
		return super.getCollisionShape($$0, $$1, $$2, $$3);
	}

	@Override
	public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
		return false;
	}

	private boolean parseConnection(BlockState state, boolean connect, Direction direction) {
		Block block = state.getBlock();
		return !isExceptionForConnection(state) && connect || block == this;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		var iworldreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		var ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iworldreader.getBlockState(blockpos1);
		BlockState blockstate1 = iworldreader.getBlockState(blockpos2);
		BlockState blockstate2 = iworldreader.getBlockState(blockpos3);
		BlockState blockstate3 = iworldreader.getBlockState(blockpos4);
		boolean flag = this.parseConnection(blockstate, blockstate.isFaceSturdy(iworldreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
		boolean flag1 = this.parseConnection(blockstate1, blockstate1.isFaceSturdy(iworldreader, blockpos2, Direction.WEST), Direction.WEST);
		boolean flag2 = this.parseConnection(blockstate2, blockstate2.isFaceSturdy(iworldreader, blockpos3, Direction.NORTH), Direction.NORTH);
		boolean flag3 = this.parseConnection(blockstate3, blockstate3.isFaceSturdy(iworldreader, blockpos4, Direction.EAST), Direction.EAST);
		boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
		return this.defaultBlockState().setValue(UP, flag4 || !iworldreader.isEmptyBlock(blockpos.above())).setValue(NORTH, flag).setValue(EAST, Boolean.valueOf(flag1)).setValue(SOUTH, flag2).setValue(WEST, flag3).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
	}



	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
		}

		if (facing == Direction.DOWN) {
			return stateIn;
		} else {
			Direction direction = facing.getOpposite();
			boolean flag = facing == Direction.NORTH ? this.parseConnection(facingState, facingState.isFaceSturdy(worldIn, facingPos, direction), direction) : stateIn.getValue(NORTH);
			boolean flag1 = facing == Direction.EAST ? this.parseConnection(facingState, facingState.isFaceSturdy(worldIn, facingPos, direction), direction) : stateIn.getValue(EAST);
			boolean flag2 = facing == Direction.SOUTH ? this.parseConnection(facingState, facingState.isFaceSturdy(worldIn, facingPos, direction), direction) : stateIn.getValue(SOUTH);
			boolean flag3 = facing == Direction.WEST ? this.parseConnection(facingState, facingState.isFaceSturdy(worldIn, facingPos, direction), direction) : stateIn.getValue(WEST);
			boolean flag4 = (!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3);
			return stateIn.setValue(UP, flag4 || !worldIn.isEmptyBlock(currentPos.above())).setValue(NORTH, flag).setValue(EAST, flag1).setValue(SOUTH, flag2).setValue(WEST, flag3);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(UP, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
	}
}
