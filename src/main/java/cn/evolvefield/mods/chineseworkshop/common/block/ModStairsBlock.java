package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModStairsBlock extends StairBlock {
	public ModStairsBlock(BlockState state) {
		super(state, Block.Properties.copy(state.getBlock()));
	}
}
