package javapower.projectplastic.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class MicrowaveFuelWrapper implements IRecipeWrapper
{
	private ItemStack fuel;
	public int fuel_size;
	
	public MicrowaveFuelWrapper(ItemStack _fuel, int _fuel_size)
	{
		fuel = _fuel;
		fuel_size = _fuel_size;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(VanillaTypes.ITEM, fuel);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		IRecipeWrapper.super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		minecraft.fontRenderer.drawString("Fuel Energy:", 22, 10, 0x404040);
		minecraft.fontRenderer.drawString(fuel_size+" Item"+(fuel_size > 1 ? "s":""), 22, 20, 0x404040);
	}

}
