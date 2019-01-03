package javapower.projectplastic.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class MicrowaveFuelWrapperFactory implements IRecipeWrapperFactory<IMicrowaveFuel>
{

	@Override
	public IRecipeWrapper getRecipeWrapper(IMicrowaveFuel paramT)
	{
		return new MicrowaveFuelWrapper(paramT.getFuel(), paramT.getFuelSize());
	}

}
