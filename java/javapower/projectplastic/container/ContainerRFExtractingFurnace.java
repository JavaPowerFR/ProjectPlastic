package javapower.projectplastic.container;

import javapower.projectplastic.slot.SlotOutput;
import javapower.projectplastic.tileentity.TileEntityRFExtractingFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRFExtractingFurnace extends Container
{
	IInventory block_inv;
	InventoryPlayer playerInventory;
	
	public ContainerRFExtractingFurnace(TileEntityRFExtractingFurnace tile, EntityPlayer player)
	{
		block_inv = tile;
		playerInventory = player.inventory;
		
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
		
		for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
		
		this.addSlotToContainer(new Slot(block_inv, 0, 56, 35));
		this.addSlotToContainer(new SlotOutput(block_inv, 1, 116, 24));
		this.addSlotToContainer(new SlotOutput(block_inv, 2, 116, 44));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
	
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
	    listener.sendAllWindowProperties(this, this.block_inv);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		 ItemStack from = getSlot(index).getStack();
		 if(index >= 36 && index <= 38)
		 {
			 TryToPutItemStackInInventory(playerIn, index);
		 }
		 else
		 {
			 Slot go = getSlot(36);
			 if(go.getStack().isEmpty() || from.isItemEqual(go.getStack()))
			 {
				 int total = go.getStack().getCount() + from.getCount();
				 if(total <= go.getStack().getMaxStackSize())
				 {
					 if(go.getStack().isEmpty())
					 {
						 go.putStack(from.copy());
						 from.setCount(0);
					 }
					 else
					 {
						 go.getStack().setCount(go.getStack().getCount() + from.getCount());
						 from.setCount(0);
					 }
				 }
				 else
				 {
					 int putitemscount = go.getStack().getMaxStackSize() - go.getStack().getCount();
					 go.getStack().setCount(go.getStack().getCount() + putitemscount);
					 from.setCount(from.getCount() - putitemscount);
				 }
			 }
		 }
		 
		 return ItemStack.EMPTY;
	}
	
	private boolean TryToPutItemStackInInventory(EntityPlayer playerIn, int index)
	{
		ItemStack from = getSlot(index).getStack();
		for(int id_slot = 0; id_slot <= 35; ++id_slot)
		{
			if(from.isEmpty())
				 break;
			 Slot go = getSlot(id_slot);
			 if(go.getStack().isItemEqual(from))
			 {
				 int amt_canput = go.getStack().getMaxStackSize() - go.getStack().getCount();
				 int amt_from = from.getCount();
				 int amt_transfert = Math.min(amt_canput, amt_from);
				 go.getStack().grow(amt_transfert);
				 from.shrink(amt_transfert);
				 if(amt_from <= amt_transfert)
					 return true;
			 }
		}
		for(int id_slot = 0; id_slot <= 35; ++id_slot)
		{
			if(from.isEmpty())
				 break;
			 Slot go = getSlot(id_slot);
			 if(go.getStack().isEmpty())
			 {
				 go.putStack(from.copy());
				 getSlot(index).putStack(ItemStack.EMPTY);
				 return true;
			 }
		}
		
		return false;
	}
	
	private boolean canPut(ItemStack from, ItemStack to)
	{
		if(to.isEmpty())
			return true;
		else if(to.isItemEqual(from) && to.getCount() + from.getCount() <= to.getMaxStackSize())
			return true;
		
		return false;
	}

}
