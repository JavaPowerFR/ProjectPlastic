package javapower.projectplastic.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class LatexExtractorWrapperFactory implements IRecipeWrapperFactory<ILatexExtractorRecipe>
{

	@Override
	public IRecipeWrapper getRecipeWrapper(ILatexExtractorRecipe paramT)
	{
		return new LatexExtractorWrapper(paramT.getInput(), paramT.getOutput());
	}
	
}
