package cn.evolvefield.mods.chineseworkshop.client;

import cn.evolvefield.mods.chineseworkshop.common.network.ConvertItemPacket;
import cn.evolvefield.mods.chineseworkshop.core.Selection;
import cn.evolvefield.mods.chineseworkshop.core.Selections;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ConvertScreen extends Screen {
	private static final Component TITLE = Component.translatable("chineseworkshop.gui.convert");

	private final ItemStack stack;
	private StackButton selectedButton;
	private float alpha;
	private boolean closing;

	protected ConvertScreen(ItemStack stack) {
		super(TITLE);
		this.stack = stack;
	}

	@Override
	protected void init() {
		if (stack.isEmpty()) {
			return;
		}
		Selection selection = Selections.find(stack);
		if (selection == null) {
			return;
		}

		int xStart = minecraft.getWindow().getGuiScaledWidth() / 2 - 30;
		int yStart = minecraft.getWindow().getGuiScaledHeight() / 2 - 90;
		int size = selection.size();
		for (int i = 0; i < size; i++) {
			int j = i;
			ItemStack substack = new ItemStack(selection.get(i));
			substack.setTag(stack.getTag());
			StackButton button = new StackButton(xStart + i % 5 * 45, yStart + i / 5 * 45, substack, btn -> {
				ConvertItemPacket.send(j);
				onClose();
			});
			if (selectedButton == null) {
				button.selected = substack.getItem() == stack.getItem();
				if (button.selected) {
					selectedButton = button;
				}
			}
			addRenderableWidget(button);
		}
		if (alpha == 0 && selectedButton != null) {
			double d0 = (selectedButton.getX() + 35) * (double) minecraft.getWindow().getWidth() / minecraft.getWindow().getGuiScaledWidth();
			double d1 = (selectedButton.getY() + 30) * (double) minecraft.getWindow().getHeight() / minecraft.getWindow().getGuiScaledHeight();
			GLFW.glfwSetCursorPos(minecraft.getWindow().getWindow(), d0, d1);
		}
	}

	@Override
	public void tick() {
		ItemStack held = minecraft.player.getMainHandItem();
		if (stack.isEmpty() || held.isEmpty() || !ItemStack.isSame(stack, held)) {
			onClose();
		}
	}

	@Override
	public void render(PoseStack matrix, int mouseX, int mouseY, float pTicks) {
		alpha += closing ? -pTicks * .4f : pTicks * .2f;
		if (closing && alpha <= 0) {
			Minecraft.getInstance().setScreen(null);
			return;
		}
		alpha = Mth.clamp(alpha, 0, 1);

		this.renderBackground(matrix);
		if (alpha < 0.5f) {
			return;
		}
		for (net.minecraft.client.gui.components.Renderable widget : this.renderables) {
			widget.render(matrix, mouseX, mouseY, pTicks);
			if (((StackButton)widget).isHoveredOrFocused() && widget.getClass() == StackButton.class) {
				selectedButton = (StackButton) widget;
			}
		}
		int x = minecraft.getWindow().getScreenWidth() / 2 - 150;
		int y = minecraft.getWindow().getScreenHeight() / 2 - 75;
		ItemStack stackMain = selectedButton == null ? stack : selectedButton.stack;
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		matrix.pushPose();
		matrix.translate(x, y, 0);
		matrix.scale(5F, 5F, 5F);
		RenderSystem.enableBlend();
		RenderSystem.clearColor(1, 1, 1, 0.1f);
		itemRenderer.renderAndDecorateItem(stackMain, 0, 0);
		matrix.popPose();
		x += 42;
		y += 42 + 50;
		drawCenteredString(matrix, font, stackMain.getDisplayName().getString(), x, y, 0xFFFFFFFF);
	}

	@Override
	public void renderBackground(PoseStack matrix, int p_renderBackground_1_) {
		int textColor1 = (int) (alpha * 0xA0) << 24;
		int textColor2 = (int) (alpha * 0x70) << 24;
		this.fillGradient(matrix, 0, 0, width, (int) (height * 0.125), textColor1, textColor2);
		this.fillGradient(matrix, 0, (int) (height * 0.125), width, (int) (height * 0.875), textColor2, textColor2);
		this.fillGradient(matrix, 0, (int) (height * 0.875), width, height, textColor2, textColor1);
	}

	@Override
	public void onClose() {
		closing = true;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (ClientHandler.kbSelect.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
			onClose();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

}

