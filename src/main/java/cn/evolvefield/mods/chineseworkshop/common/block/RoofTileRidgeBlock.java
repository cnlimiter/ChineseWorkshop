package cn.evolvefield.mods.chineseworkshop.common.block;

import cn.evolvefield.mods.chineseworkshop.TextureModule;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import snownee.kiwi.KiwiModule;

import java.util.List;
import java.util.Locale;

@KiwiModule.RenderLayer(KiwiModule.RenderLayer.Layer.CUTOUT)
public class RoofTileRidgeBlock extends Direction2Block {
	public final VoxelShape SHAPE;
	public static final EnumProperty<Variant> VARIANT = EnumProperty.create("variant", Variant.class);

	public RoofTileRidgeBlock(BlockBehaviour.Properties builder, VoxelShape shape, boolean retexture) {
		super(builder, retexture);
		SHAPE = shape;
		registerDefaultState(this.getStateDefinition().any().setValue(VARIANT, Variant.I).setValue(FACING, Direction2.SOUTH_NORTH));
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
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos blockpos = context.getClickedPos();
		FluidState ifluidstate = context.getLevel().getFluidState(blockpos);
		BlockState state = super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.getType() == Fluids.WATER));
		return state.setValue(VARIANT, getVariantProperty(state, context.getLevel(), blockpos));
	}


	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			worldIn.getFluidTicks().willTickThisTick(currentPos, Fluids.WATER);
		}
		return state.setValue(VARIANT, getVariantProperty(state, worldIn, currentPos));
	}

	@Override
	public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
		return false;
	}

	private Variant getVariantProperty(BlockState state, LevelAccessor worldIn, BlockPos pos) {
		Direction facing;
		int connection = 0;
		boolean connect[] = new boolean[4];
		for (int i = 0; i < 4; i++) {
			facing = Direction.from2DDataValue(i);
			if (worldIn.getBlockState(pos.offset(facing.getNormal())).getBlock() == this) {
				connection++;
				connect[i] = true;
			}
		}
		if (connection == 4) {
			return Variant.X;
		} else if (connection == 3) {
			for (int i = 0; i < 4; i++) {
				if (!connect[i]) {
					return Variant.values()[Variant.T.ordinal() + i];
				}
			}
		} else if (connection == 2) {
			if (connect[0] && connect[2] || connect[1] && connect[3]) {
				return connect[0] ? Variant.I : Variant.I_90;
			}
			Variant variant = Variant.L_270;
			for (int i = 0; i < 3; i++) {
				if (connect[i] && connect[i + 1]) {
					variant = Variant.values()[Variant.L.ordinal() + i];
					break;
				}
			}
			return variant;
		} else if (connection == 1) {
			return (connect[0] || connect[2]) ? Variant.I : Variant.I_90;
		}
		return state.getValue(FACING) == Direction2.EAST_WEST ? Variant.I : Variant.I_90;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, VARIANT, WATERLOGGED);
	}


	public static enum Variant implements StringRepresentable {
		I, I_90, L, L_90, L_180, L_270, T, T_90, T_180, T_270, X;


		@Override
		public String getSerializedName() {
			return toString().toLowerCase(Locale.ENGLISH);
		}
	}
}
