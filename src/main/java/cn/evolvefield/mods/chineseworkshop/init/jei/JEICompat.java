package cn.evolvefield.mods.chineseworkshop.init.jei;

import cn.evolvefield.mods.chineseworkshop.CW;
import cn.evolvefield.mods.chineseworkshop.CWConfig;
import cn.evolvefield.mods.chineseworkshop.core.Selection;
import cn.evolvefield.mods.chineseworkshop.core.Selections;
import cn.evolvefield.mods.chineseworkshop.init.event.RetextureIngredientEvent;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import snownee.kiwi.Kiwi;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEICompat implements IModPlugin {
	public static final ResourceLocation UID = new ResourceLocation(CW.MODID, CW.MODID);

	@Override
	public ResourceLocation getPluginUid() {
		return UID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		if (CWConfig.showConversionJei) {
			registration.addRecipeCategories(new SelectionCategory(registration.getJeiHelpers().getGuiHelper()));
		}
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		if (CWConfig.showConversionJei) {
			List<Selection> recipes = Selections.SELECTIONS.stream().filter(Selection::show).collect(Collectors.toList());
			registration.addRecipes(RecipeType.create(CW.MODID, CW.MODID, Selection.class), recipes);
		}

		if (CWConfig.showRetextureJei && Kiwi.isLoaded(new ResourceLocation(CW.MODID, "retexture"))) {
			NonNullList<ItemStack> ingredients = NonNullList.create();
			MinecraftForge.EVENT_BUS.post(new RetextureIngredientEvent(ingredients));
			if (!ingredients.isEmpty()) {
				registration.addIngredientInfo(ingredients, VanillaTypes.ITEM_STACK, Component.translatable("chineseworkshop.gui.jei.ingredient"));
			}
		}
	}

}
