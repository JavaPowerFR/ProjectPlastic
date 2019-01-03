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
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class MicrowaveFuelCategory implements IRecipeCategory<MicrowaveFuelWrapper>
{
	public static final String UID = PlasticCraft.MODID + ".microwavefuel";
	private final String localizedName;
	
	private final IDrawableStatic background, icon;
	protected final IDrawableAnimated energy;
	
	public MicrowaveFuelCategory(IGuiHelper guiHelper)
	{
		localizedName = I18n.format(PlasticCraft.MODID+".jei.category.microwavefuel");
		background = guiHelper.createDrawable(ResourceLocationRegister.textrue_gui_jei_recipe, 0, 38, 120, 36);
		icon = guiHelper.createDrawable(ResourceLocationRegister.textrue_gui_jei_recipe, 120, 38, 4, 12);
		energy = guiHelper.drawableBuilder(ResourceLocationRegister.textrue_gui_jei_recipe, 120, 38, 4, 12).buildAnimated(50, IDrawableAnimated.StartDirection.BOTTOM, false);
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
		energy.draw(minecraft, 7, 4);
	}
	
	@Override
	public IDrawable getIcon()
	{
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MicrowaveFuelWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		
		guiItemStacks.init(0, true, 0, 18);
		guiItemStacks.set(ingredients);
	}
}
