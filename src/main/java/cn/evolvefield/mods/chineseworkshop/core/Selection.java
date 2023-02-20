package cn.evolvefield.mods.chineseworkshop.core;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class Selection {
	private final Item mainItem;
	private final List<Item> subItems;
	private final boolean hide;

	public Selection(Item mainItem, List<Item> subItems, boolean hide) {
		this.mainItem = mainItem;
		this.subItems = subItems;
		this.hide = hide;
	}

	public boolean matches(Item item) {
		return mainItem == item || subItems.contains(item);
	}

	public Item getMainItem() {
		return mainItem;
	}

	public List<Item> getSubItems() {
		return subItems;
	}

	public List<ItemStack> getSubItemStack(){
		List<ItemStack> list  = new ArrayList<>();
		subItems.forEach(item -> list.add(item.getDefaultInstance()));
		return list;
	}

	public boolean show() {
		return !hide;
	}

	public int size() {
		return subItems.size() + 1;
	}

	public Item get(int i) {
		if (i == 0) {
			return mainItem;
		}
		if (i < size()) {
			return subItems.get(i - 1);
		}
		return Items.AIR;
	}
}
