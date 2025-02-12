package cn.evolvefield.mods.chineseworkshop.common.block;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

import java.util.Locale;

public enum Direction2 implements StringRepresentable {
	EAST_WEST, SOUTH_NORTH;


	@Override
	public String getSerializedName() {
		return toString().toLowerCase(Locale.ENGLISH);
	}
	public static Direction2 fromEnumFacing(Direction facing) {
		if (facing == Direction.NORTH || facing == Direction.SOUTH) {
			return Direction2.SOUTH_NORTH;
		}
		return Direction2.EAST_WEST;
	}

	public Direction2 rotate(Rotation rot) {
		return rot.ordinal() % 2 == 0 ? this : this == EAST_WEST ? SOUTH_NORTH : EAST_WEST;
	}

	public Direction2 mirror(Mirror mirror) {
		return this;
	}

}
