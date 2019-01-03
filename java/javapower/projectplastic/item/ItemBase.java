package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
	protected String name;
	
	public ItemBase(String _name)
	{
		name = _name;
		setCreativeTab(PlasticCraft.creativeTab);
		setRegistryName(name);
		setUnlocalizedName(name);
	}
}
