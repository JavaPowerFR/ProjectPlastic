package javapower.projectplastic.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ExtractingFurnaceWrapper implements mezz.jei.api.recipe.IRecipeWrapper
{
	private ItemStack input;
	private List<ItemStack> output;
	
	public ExtractingFurnaceWrapper(ItemStack _input, ItemStack[] _output)
	{
		input = _input;
		output = new ArrayList<ItemStack>();
		output.add(_output[0]);
		output.add(_output[1]);
	}
	
	@Override
	public void getIngredients(mezz.jei.api.ingredients.IIngredients ingredients)
	{
		ingredients.setInput(mezz.jei.api.ingredients.VanillaTypes.ITEM, input);
		ingredients.setOutputs(mezz.jei.api.ingredients.VanillaTypes.ITEM, output);
	}

}
