package cn.evolvefield.mods.chineseworkshop.common.block;

import cn.evolvefield.mods.chineseworkshop.core.ISeat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


public class BenchBlock extends Direction2Block implements ISeat {

	public BenchBlock(Properties builder, boolean retexture) {
		super(builder, box(1, 0, 3, 15, 10, 13), retexture);
	}

	@Override
	public Vec3 getSeat(BlockState state, LevelAccessor Level, BlockPos pos) {
		return new Vec3(0.5, 0.4, 0.5);
	}


}
