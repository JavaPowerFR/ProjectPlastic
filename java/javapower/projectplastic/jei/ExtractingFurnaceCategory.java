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
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ExtractingFurnaceCategory implements mezz.jei.api.recipe.IRecipeCategory<ExtractingFurnaceWrapper>
{
	public static final String UID = PlasticCraft.MODID + ".extracting";
	private final String localizedName;
	
	private final IDrawableStatic background;
	protected final IDrawableAnimated arrow;
	
	public ExtractingFurnaceCategory(IGuiHelper guiHelper)
	{
		localizedName = I18n.format(PlasticCraft.MODID+".jei.category.extracting");
		background = guiHelper.createDrawable(ResourceLocationRegister.textrue_gui_jei_recipe, 0, 0, 78, 38);
		arrow = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
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
	
	public void drawExtras(Minecraft minecraft)
	{
		arrow.draw(minecraft, 24, 11);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, ExtractingFurnaceWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 0, 10);
		guiItemStacks.init(2, false, 60, 0);
		guiItemStacks.init(3, false, 60, 20);
		guiItemStacks.set(ingredients);

	}

}
