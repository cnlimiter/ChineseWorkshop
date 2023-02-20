package cn.evolvefield.mods.chineseworkshop.init.jei;


import cn.evolvefield.mods.chineseworkshop.BlockModule;
import cn.evolvefield.mods.chineseworkshop.CW;
import cn.evolvefield.mods.chineseworkshop.core.Selection;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SelectionCategory implements IRecipeCategory<Selection> {
	public static final ResourceLocation UID = new ResourceLocation(CW.MODID, "selection");
	public static final int recipeWidth = 160;
	public static final int recipeHeight = 125;
	private final IDrawable icon;
	private final IDrawable background;
	private final IDrawable slotBackground;
	private final String localizedName;

	public SelectionCategory(IGuiHelper guiHelper) {
		icon = guiHelper.createDrawableItemStack(new ItemStack(BlockModule.LOGO));
		background = guiHelper.createBlankDrawable(recipeWidth, recipeHeight);
		slotBackground = guiHelper.getSlotDrawable();
		localizedName = "chineseworkshop.gui.convert";
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public RecipeType<Selection> getRecipeType() {
		return new RecipeType<>(UID, Selection.class);
	}

	@Override
	public Component getTitle() {
		return Component.translatable(localizedName);
	}



	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, Selection recipe, IFocusGroup focuses) {
		int count = recipe.size();
		int h = 36 + 18 * (count / 6 + 1);

		int x = (recipeWidth - 18) / 2;
		int y = (recipeHeight - h) / 2;

		builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y).setBackground(slotBackground, 0, 0).addItemStacks(recipe.getSubItemStack());

		int j = 0;
		y += 36;
		while (count > 0) {
			x = (recipeWidth - 6 * 18) / 2;
			for (int i = 0; i < 6; i++) {
				++j;
				builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x, y).setBackground(slotBackground, j, j).addItemStacks(recipe.getSubItemStack());

				x += 18;
				--count;
			}
			y += 18;
		}

	}



}
