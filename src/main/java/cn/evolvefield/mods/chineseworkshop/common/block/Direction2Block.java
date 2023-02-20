package cn.evolvefield.mods.chineseworkshop.common.block;


import cn.evolvefield.mods.chineseworkshop.common.tile.CWTextureTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.block.ModBlock;
import snownee.kiwi.util.VoxelUtil;

@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.CUTOUT)
public class Direction2Block extends ModBlock implements SimpleWaterloggedBlock, EntityBlock {
	public final boolean retexture;
	public static final EnumProperty<Direction2> FACING = EnumProperty.create("facing", Direction2.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private final VoxelShape[] shapes = new VoxelShape[2];

	public Direction2Block(BlockBehaviour.Properties builder, boolean retexture) {
		super(builder);
		this.retexture = retexture;
		shapes[0] = shapes[1] = Shapes.block();
	}

	public Direction2Block(BlockBehaviour.Properties builder, VoxelShape shape, boolean retexture) {
		super(builder);
		this.retexture = retexture;
		shapes[0] = shape;
		shapes[1] = VoxelUtil.rotate(shape, Direction.WEST);
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
		return state.getValue(FACING) == Direction2.SOUTH_NORTH ? shapes[0] : shapes[1];
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		var ifluidstate = context.getLevel().getFluidState(blockpos);
		return defaultBlockState().setValue(FACING, Direction2.fromEnumFacing(context.getHorizontalDirection())).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}


	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		Direction2 direction = state.getValue(FACING).rotate(rot);
		return state.setValue(FACING, direction);
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		Direction2 direction = state.getValue(FACING).mirror(mirrorIn);
		return state.setValue(FACING, direction);
	}

	@Override
	public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
		return false;
	}

}
