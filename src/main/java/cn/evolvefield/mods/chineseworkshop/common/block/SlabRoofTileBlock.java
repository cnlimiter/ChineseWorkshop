
package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlabRoofTileBlock extends RoofTileBlock {
	private final VoxelShape shape;

	public SlabRoofTileBlock(BlockBehaviour.Properties builder, VoxelShape shape, boolean retexture) {
		super(builder, retexture);
		this.shape = shape;
	}


	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
		return shape;
	}
}
