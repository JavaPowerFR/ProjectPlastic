package javapower.projectplastic.jei;

import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.proxy.ResourceLocationRegister;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class LatexExtractorCategory implements IRecipeCategory<LatexExtractorWrapper>
{
	public static final String UID = PlasticCraft.MODID + ".latexextractor";
	private final String localizedName;
	
	private final IDrawableStatic background;
	
	public LatexExtractorCategory(IGuiHelper guiHelper)
	{
		localizedName = I18n.format(PlasticCraft.MODID+".jei.category.latexextractor");
		background = guiHelper.createDrawable(ResourceLocationRegister.textrue_gui_jei_recipe, 0, 74, 82, 44);
	}

	@Override
	public String getUid()
	{
		return UID;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public String getModName()
	{
		return PlasticCraft.NAME;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, LatexExtractorWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 0, 12);
		guiItemStacks.init(1, false, 64, 12);
		guiItemStacks.set(ingredients);

	}
}