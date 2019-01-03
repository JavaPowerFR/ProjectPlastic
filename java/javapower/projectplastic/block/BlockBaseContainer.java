package javapower.projectplastic.block;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BlockBaseContainer extends BlockContainer
{
	protected Item thisItem;
	protected String name;
	
	public BlockBaseContainer(Material materialIn, String _name)
	{
		super(materialIn);
		name = _name;
		setCreativeTab(PlasticCraft.creativeTab);
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
	public abstract Item getItem();
	
	public ItemStack getNewItemStack()
	{
		return new ItemStack(this);
	}
	
	public ItemStack getNewItemStack(int amt)
	{
		return new ItemStack(this, amt);
	}
	
	public ItemStack getNewItemStack(int amt, int met)
	{
		return new ItemStack(this, amt, met);
	}

}
