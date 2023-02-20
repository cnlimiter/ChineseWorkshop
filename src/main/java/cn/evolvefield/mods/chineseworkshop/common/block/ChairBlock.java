package cn.evolvefield.mods.chineseworkshop.common.block;

import cn.evolvefield.mods.chineseworkshop.core.ISeat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ChairBlock extends RoofTileBlock implements ISeat {
	public ChairBlock(Block.Properties builder, boolean retexture) {
		super(builder, retexture);
	}

	@Override
	public Vec3 getSeat(BlockState state, LevelAccessor Level, BlockPos pos) {
		return new Vec3(0.5, 0.4, 0.5);
	}

}
