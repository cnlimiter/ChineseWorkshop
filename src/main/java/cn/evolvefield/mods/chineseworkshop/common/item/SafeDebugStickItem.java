package cn.evolvefield.mods.chineseworkshop.common.item;

import cn.evolvefield.mods.chineseworkshop.CWConfig;
import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DebugStickItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import snownee.kiwi.item.ModItem;

import javax.annotation.Nullable;
import java.util.List;

public class SafeDebugStickItem extends DebugStickItem {
	public SafeDebugStickItem(Properties builder) {
		super(builder);
	}

	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
		return false;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		var playerentity = context.getPlayer();
		var world = context.getLevel();
		if (!world.isClientSide && playerentity != null) {
			BlockPos blockpos = context.getClickedPos();
			this.handleClick(playerentity, world.getBlockState(blockpos), world, blockpos, true, context.getItemInHand());
		}

		return InteractionResult.SUCCESS;
	}



	public void handleClick(Player player, BlockState state, LevelAccessor worldIn, BlockPos pos, boolean rightClick, ItemStack stack) {
		if (canApplyOn(state)) {
			Block block = state.getBlock();
			var statecontainer = block.getStateDefinition();
			var collection = Lists.newArrayList(statecontainer.getProperties());
			collection.remove(BlockStateProperties.WATERLOGGED);
			@SuppressWarnings("deprecation")
			String s = ForgeRegistries.BLOCKS.getKey(block).toString();
			if (collection.isEmpty()) {
				sendMessage(player, Component.translatable(Items.DEBUG_STICK.getDescriptionId() + ".empty", s));
			} else {
				var compoundnbt = stack.getOrCreateTagElement("DebugProperty");
				String s1 = compoundnbt.getString(s);
				var iproperty = statecontainer.getProperty(s1);
				if (rightClick) {
					if (iproperty == null) {
						iproperty = collection.iterator().next();
					}

					BlockState blockstate = cycleProperty(state, iproperty, player.isSecondaryUseActive());
					worldIn.setBlock(pos, blockstate, 18);
					sendMessage(player, Component.translatable(Items.DEBUG_STICK.getDescriptionId() + ".update", iproperty.getName(), func_195957_a(blockstate, iproperty)));
				} else {
					iproperty = getAdjacentValue(collection, iproperty, player.isSecondaryUseActive());
					String s2 = iproperty.getName();
					compoundnbt.putString(s, s2);
					sendMessage(player, Component.translatable(Items.DEBUG_STICK.getDescriptionId() + ".select", s2, func_195957_a(state, iproperty)));
				}
			}
		} else {
			sendMessage(player, Component.translatable(this.getDescriptionId() + ".unsupported"));
		}
	}

	public static boolean canApplyOn(BlockState state) {
		Block block = state.getBlock();
		if (CWConfig.allowedMods.contains(ForgeRegistries.BLOCKS.getKey(block).getNamespace())) {
			return true;
		}
		String className = block.getClass().getName();
		if (CWConfig.allowedClasses.contains(className)) {
			return true;
		}
		return false;
	}


	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
		ModItem.addTip(pStack, pTooltipComponents, pIsAdvanced);
	}

	private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn, boolean backwards) {
		return state.setValue(propertyIn, (getAdjacentValue(propertyIn.getPossibleValues(), state.getValue(propertyIn), backwards)));
	}

	private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_, boolean p_195959_2_) {
		return p_195959_2_ ? Util.findPreviousInIterable(p_195959_0_, p_195959_1_) : Util.findNextInIterable(p_195959_0_, p_195959_1_);
	}

	private static void sendMessage(Player player, Component text) {
		((ServerPlayer) player)./*sendMessage*/displayClientMessage(text, false);
	}

	private static <T extends Comparable<T>> String func_195957_a(BlockState state, Property<T> propertyIn) {
		return propertyIn.getName(state.getValue(propertyIn));
	}
}
