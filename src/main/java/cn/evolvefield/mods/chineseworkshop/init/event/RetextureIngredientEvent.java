package cn.evolvefield.mods.chineseworkshop.init.event;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.Event;

public class RetextureIngredientEvent extends Event {
	public NonNullList<ItemStack> stacks;

	public RetextureIngredientEvent(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	public NonNullList<ItemStack> getStacks() {
		return stacks;
	}

	public void add(ItemLike item) {
		stacks.add(new ItemStack(item));
	}
}
