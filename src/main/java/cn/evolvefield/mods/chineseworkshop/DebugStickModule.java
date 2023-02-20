package cn.evolvefield.mods.chineseworkshop;

import cn.evolvefield.mods.chineseworkshop.common.item.SafeDebugStickItem;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiModule;

@KiwiModule("debug_stick")
@KiwiModule.Category("tools")
@KiwiModule.Subscriber
@KiwiModule.Optional
public class DebugStickModule extends AbstractModule {
	public static final SafeDebugStickItem SAFE_DEBUG_STICK = new SafeDebugStickItem(itemProp());

	@SubscribeEvent
	public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		if (event.getItemStack().getItem() == SAFE_DEBUG_STICK) {
			event.setCanceled(true);
			if (event.getEntity().getCooldowns().getCooldownPercent(SAFE_DEBUG_STICK, 1) > 0) {
				return;
			}
			if (!event.getLevel().isClientSide()) {
				var worldIn = event.getLevel();
				BlockPos pos = event.getPos();
				var state = event.getLevel().getBlockState(pos);
				SAFE_DEBUG_STICK.handleClick(event.getEntity(), state, worldIn, pos, false, event.getItemStack());
			}
			event.getEntity().getCooldowns().addCooldown(SAFE_DEBUG_STICK, 5);
		}
	}
}
