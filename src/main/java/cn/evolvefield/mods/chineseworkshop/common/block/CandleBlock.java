
package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CandleBlock extends TorchBlock {
	public static final VoxelShape CANDLE_SHAPE = Block.box(4.8, 0, 4.8, 11.2, 11.2, 11.2);
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public CandleBlock(Properties builder) {
		super(builder, ParticleTypes.FLAME);
	}

	@Override
	public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
		return CANDLE_SHAPE;
	}

	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		double d0 = pos.getX() + 0.5D;
		double d1 = pos.getY() + 0.55D;
		double d2 = pos.getZ() + 0.5D;
		worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		worldIn.addParticle(flameParticle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

}
