package javapower.projectplastic.jei;

import mezz.jei.api.recipe.IIngredientType;

public class IngredientsType
{
	public static final IIngredientType<Integer> INTEGER = new IIngredientType<Integer>()
	{
		@Override
		public Class<? extends Integer> getIngredientClass() {return Integer.class;}
	};
}
