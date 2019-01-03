package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemResource extends ItemBase
{
	String[] names = {
			"plasticball",
			"clearplasticball",
			"plasticstick",
			"woodflour",
			"syntheticstring",
			"integratedcircuit",
			"bowlofgelatin",
			"gelatinpowder",
			"plasticgoo",
			"rubberball",
			"battery",
			"roughsilicon",
			"syntheticfiber"
			};
	
	public ItemResource()
	{
		super("resource");
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(stack != null && names.length > stack.getItemDamage())
		return "item."+names[stack.getItemDamage()];
		
		return super.getUnlocalizedName();
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(tab == PlasticCraft.creativeTab)
			for(int i = 0; i <= names.length-1; ++i)
				items.add(new ItemStack(this, 1, i));
	}
}
