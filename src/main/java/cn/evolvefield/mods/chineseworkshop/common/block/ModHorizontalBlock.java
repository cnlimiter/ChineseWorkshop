package cn.evolvefield.mods.chineseworkshop.common.block;

import cn.evolvefield.mods.chineseworkshop.common.tile.CWTextureTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.util.VoxelUtil;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.CUTOUT)
public class ModHorizontalBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
	final boolean retexture;
	private VoxelShape[] shapes = new VoxelShape[4];
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public ModHorizontalBlock(BlockBehaviour.Properties builder, VoxelShape shape, boolean retexture) {
		super(builder);
		this.retexture = retexture;
		for (int i = 0; i < shapes.length; i++) {
			Direction direction = Direction.from2DDataValue(i);
			if (direction == Direction.SOUTH) {
				shapes[i] = shape;
			} else {
				shapes[i] = VoxelUtil.rotate(shape, direction);
			}
		}
	}

	public boolean isTextureable() {
		return retexture;
	}



	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return retexture ? new CWTextureTile(blockPos, blockState) : null;
	}


	@Override
	public VoxelShape getShape(BlockState state, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
		return shapes[state.getValue(HORIZONTAL_FACING).get2DDataValue()];
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED);
	}


	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
		}
		return stateIn;
	}


	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		FluidState ifluidstate = context.getLevel().getFluidState(blockpos);
		BlockState blockstate = this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
		return blockstate;
	}

	@Override
	public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
		return false;
	}

}
