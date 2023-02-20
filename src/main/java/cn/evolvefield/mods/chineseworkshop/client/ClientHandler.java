package cn.evolvefield.mods.chineseworkshop.client;


import cn.evolvefield.mods.chineseworkshop.CW;
import cn.evolvefield.mods.chineseworkshop.core.Selections;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientHandler {
	public static final KeyMapping kbSelect = new KeyMapping(CW.MODID + ".keybind.select", 342, CW.MODID + ".gui.keygroup");

	@SubscribeEvent
	public static void onKeyInput(InputEvent.Key event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.screen == null && mc.player != null && mc.isWindowActive() && event.getAction() == 1 && kbSelect.isDown()) {
			var stack = mc.player.getMainHandItem();
			if (!stack.isEmpty() && Selections.contains(stack.getItem())) {
				ConvertScreen screen = new ConvertScreen(stack);
				mc.setScreen(screen);
			}
		}
	}

	@SubscribeEvent
	public static void drawHudPre(RenderGuiOverlayEvent.Pre event) {
		if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type() && Minecraft.getInstance().screen instanceof ConvertScreen) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().isEmpty()) {
			return;
		}
		var item = event.getItemStack().getItem();
		if (Selections.contains(item)) {
			event.getToolTip().add(Component.literal(ChatFormatting.GRAY + I18n.get(CW.MODID + ".tip.selectable", I18n.get(kbSelect.getName()))));
		}
	}



}
