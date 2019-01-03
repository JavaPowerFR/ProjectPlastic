package javapower.projectplastic.util;

import net.minecraft.item.ItemStack;

public class ItemStackFormatException extends Exception
{
	ItemStack item;
	
	public ItemStackFormatException(ItemStack _item, String _msg)
	{
		super(_msg);
	}
	
	public ItemStack getItemStack()
	{
		return item;
	}
}
