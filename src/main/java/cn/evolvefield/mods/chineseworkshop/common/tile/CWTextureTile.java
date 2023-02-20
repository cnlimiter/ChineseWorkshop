package cn.evolvefield.mods.chineseworkshop.common.tile;

import cn.evolvefield.mods.chineseworkshop.TextureModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import snownee.kiwi.block.entity.RetextureBlockEntity;

public class CWTextureTile extends RetextureBlockEntity {

	public CWTextureTile(BlockPos level, BlockState state) {
		super(TextureModule.RETEXTURE, level, state, "main");
	}


	@Override
	public void load(CompoundTag pTag) {
		readPacketData(pTag);
		super.load(pTag);
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		writePacketData(pTag);
		super.saveAdditional(pTag);
	}


}
