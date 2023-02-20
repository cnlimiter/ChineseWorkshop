
package cn.evolvefield.mods.chineseworkshop.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface ISeat {
	default boolean hasSeat(LevelAccessor Level, BlockPos pos) {
		return Level.isEmptyBlock(pos.above());
	}

	Vec3 getSeat(BlockState state, LevelAccessor Level, BlockPos pos);

}
