package javapower.projectplastic.jei;

import javapower.projectplastic.crafting.IExtractingFurnaceRecipe;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class ExtractingFurnaceWrapperFactory implements IRecipeWrapperFactory<IExtractingFurnaceRecipe>
{

	@Override
	public IRecipeWrapper getRecipeWrapper(IExtractingFurnaceRecipe paramT)
	{
		return new ExtractingFurnaceWrapper(paramT.Input(), paramT.Output());
	}

}
