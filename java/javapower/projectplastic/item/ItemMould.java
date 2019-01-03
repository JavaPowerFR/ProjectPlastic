package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMould extends ItemBase
{

	public ItemMould()
	{
		super("mould");
		this.setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if(stack != null)
		return super.getUnlocalizedName(stack)+stack.getItemDamage();
		
		return super.getUnlocalizedName();
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(tab == PlasticCraft.creativeTab)
			for(int i = 0; i <= 1; ++i)
				items.add(new ItemStack(this, 1, i));
	}

}
