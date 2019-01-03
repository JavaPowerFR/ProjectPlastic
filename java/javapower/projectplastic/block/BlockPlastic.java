package javapower.projectplastic.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockPlastic extends BlockBase
{
	public BlockPlastic()
	{
		super(Material.IRON, "plasticblock");
		this.setHardness(2.0F);
		this.setResistance(1000.0F);
	}

	@Override
	public Item getItem()
	{
		if(thisItem == null)
		{
			thisItem = new ItemBlock(this);
			thisItem.setRegistryName(name);
		}
		return thisItem;
	}

}
