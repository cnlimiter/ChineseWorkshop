package cn.evolvefield.mods.chineseworkshop.common.block;

import cn.evolvefield.mods.chineseworkshop.DecorationModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("hiding")
public class HighDoorBlock extends DoorBlock {
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 2.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 24.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 24.0D, 16.0D);

	public HighDoorBlock(Properties builder) {
		super(builder, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN);
	}


	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		var direction = state.getValue(FACING);
		boolean flag = !state.getValue(OPEN);
		boolean flag1 = state.getValue(HINGE) == DoorHingeSide.RIGHT;
		boolean down = state.getValue(HALF) == DoubleBlockHalf.LOWER;
		VoxelShape shape = null;
		switch (direction) {
			case EAST:
			default:
				shape = flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
				break;
			case SOUTH:
				shape = flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
				break;
			case WEST:
				shape = flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
				break;
			case NORTH:
				shape = flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
				break;
		}
		return shape.move(direction.getStepX() * 3D / 16, down ? -.5 : 0, direction.getStepZ() * 3D / 16);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
			return state.getBlock().defaultBlockState().getTags().toList().contains(DecorationModule.THRESHOLD) || blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP);
		} else {
			return blockstate.getBlock() == this;
		}
	}

}
