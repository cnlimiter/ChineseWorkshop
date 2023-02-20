
package cn.evolvefield.mods.chineseworkshop.common.block;

import cn.evolvefield.mods.chineseworkshop.TextureModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class RoofTileJBlock extends ModHorizontalBlock{
	public RoofTileJBlock(Block.Properties builder, boolean retexture) {
		this(builder, Shapes.block(), retexture);
	}

	public RoofTileJBlock(Block.Properties builder, VoxelShape shape, boolean retexture) {
		super(builder, shape, retexture);
	}



	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (isTextureable()) {
			TextureModule.addTooltip(stack, tooltip, "frame");
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}



	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		var blockpos = context.getClickedPos();
		var ifluidstate = context.getLevel().getFluidState(blockpos);
		var direction = Direction.fromYRot(context.getRotation() + 45);
		BlockState blockstate = this.defaultBlockState().setValue(HORIZONTAL_FACING, direction.getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.getType() == Fluids.WATER));
		return blockstate;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING, WATERLOGGED);
	}


	@Override
	public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
		return false;
	}
}
