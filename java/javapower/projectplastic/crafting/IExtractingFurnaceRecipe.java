package javapower.projectplastic.crafting;

import net.minecraft.item.ItemStack;

public interface IExtractingFurnaceRecipe
{
	public ItemStack Input();
	public ItemStack[] Output();
}
