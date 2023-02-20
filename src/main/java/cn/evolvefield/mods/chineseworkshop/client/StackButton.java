package cn.evolvefield.mods.chineseworkshop.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StackButton extends Button {
	public final ItemStack stack;
	public boolean selected;
	private float hoverProgress;

	public StackButton(int x, int y, ItemStack stack, OnPress onPress) {
		super(x, y, 40, 40, stack.getDisplayName(), onPress, DEFAULT_NARRATION);
		this.stack = stack;
		alpha = 0;
	}

	@Override
	public void renderButton(PoseStack matrix, int mouseX, int mouseY, float pTicks) {
		alpha = Math.min(alpha + pTicks * 0.2F, 1);
		int y = (int) (this.getY() + 15 - 15 * Mth.sin(alpha));
		GuiComponent.fill(matrix, getX(), y, getX() + width, y + height, 0xAA222222);
		hoverProgress += isHovered ? pTicks * .2f : -pTicks * .2f;
		hoverProgress = Mth.clamp(hoverProgress, .4f, 1);
		int linecolor = 0xFFFFFF | (int) (hoverProgress * 0xFF) << 24;
		GuiComponent.fill(matrix, getX(), y, getX() + 1, y + height, linecolor);
		GuiComponent.fill(matrix, getX() + width - 1, y, getX() + width, y + height, linecolor);
		GuiComponent.fill(matrix, getX() + 1, y, getX() + width - 1, y + 1, linecolor);
		GuiComponent.fill(matrix, getX() + 1, y + height - 1, getX() + width - 1, y + height, linecolor);
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		matrix.pushPose();
		matrix.translate(getX() + 8, y + 8, 0);
		matrix.scale(1.5F, 1.5F, 1.5F);
		itemRenderer.renderAndDecorateItem(stack, 0, 0);
		matrix.popPose();
	}
}
