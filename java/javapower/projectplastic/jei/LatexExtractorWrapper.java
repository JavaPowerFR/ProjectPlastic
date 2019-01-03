package javapower.projectplastic.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class LatexExtractorWrapper implements IRecipeWrapper
{
	private ItemStack input;
	private ItemStack output;
	
	public LatexExtractorWrapper(ItemStack _input, ItemStack _output)
	{
		input = _input;
		output = _output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
