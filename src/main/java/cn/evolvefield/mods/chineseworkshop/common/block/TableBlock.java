
package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import snownee.kiwi.block.ModBlock;

public class TableBlock extends ModBlock implements SimpleWaterloggedBlock {
	private static final VoxelShape PLATE_VS = box(0, 14, 0, 16, 16, 16);
	private static final VoxelShape NW_VS = box(3, 0, 3, 5, 14, 5);
	private static final VoxelShape SW_VS = box(3, 0, 11, 5, 14, 13);
	private static final VoxelShape SE_VS = box(11, 0, 11, 13, 14, 13);
	private static final VoxelShape NE_VS = box(11, 0, 3, 13, 14, 5);

	public static final BooleanProperty NW = BooleanProperty.create("nw");
	public static final BooleanProperty NE = BooleanProperty.create("ne");
	public static final BooleanProperty SE = BooleanProperty.create("se");
	public static final BooleanProperty SW = BooleanProperty.create("sw");
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	private final VoxelShape[] shapes;

	private static VoxelShape[] makeShapes() {
		VoxelShape[] shapes = new VoxelShape[16];
		for (int i = 0; i < 16; i++) {
			shapes[i] = PLATE_VS;
			if (i % 2 == 1) {
				shapes[i] = Shapes.or(shapes[i], NW_VS);
			}
			if ((i >> 1) % 2 == 1) {
				shapes[i] = Shapes.or(shapes[i], NE_VS);
			}
			if ((i >> 2) % 2 == 1) {
				shapes[i] = Shapes.or(shapes[i], SW_VS);
			}
			if ((i >> 3) % 2 == 1) {
				shapes[i] = Shapes.or(shapes[i], SE_VS);
			}
		}
		return shapes;
	}

	public TableBlock(BlockBehaviour.Properties builder) {
		super(builder);
		shapes = makeShapes();
		registerDefaultState(stateDefinition.any().setValue(NW, false).setValue(NE, false).setValue(SW, false).setValue(SE, false).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NW, NE, SE, SW, WATERLOGGED);
	}


	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}



	@Override
	public VoxelShape getShape(BlockState state, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
		int i = (state.getValue(NW) ? 1 : 0) + (state.getValue(NE) ? 2 : 0) + (state.getValue(SW) ? 4 : 0) + (state.getValue(SE) ? 8 : 0);
		return shapes[i];
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		var iblockreader = context.getLevel();
		var blockpos = context.getClickedPos();
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return getState(defaultBlockState(), iblockreader, blockpos).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
	}

	public BlockState getState(BlockState state, BlockGetter worldIn, BlockPos pos) {
		Direction facing;
		int n = 0;
		boolean nw = false, ne = false, se = false, sw = false;
		for (int i = 0; i < 4; i++) {
			facing = Direction.from2DDataValue(i);
			if (worldIn.getBlockState(pos.offset(facing.getNormal())).getBlock() == this) {
				n += Math.pow(2, i);
			}
		}
		switch (n) {
			case 0:
				nw = ne = se = sw = true;
				break;
			case 3: // SW
				ne = true;
				break;
			case 6: // WN
				se = true;
				break;
			case 12: // NE
				sw = true;
				break;
			case 9: // ES
				nw = true;
				break;
			case 1: // S
				nw = ne = true;
				break;
			case 2: // W
				ne = se = true;
				break;
			case 4: // N
				se = sw = true;
				break;
			case 8: // E
				nw = sw = true;
				break;
		}
		return state.setValue(NW, nw).setValue(NE, ne).setValue(SE, se).setValue(SW, sw);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
		}
		return getState(stateIn, worldIn, currentPos);
	}
}
