package javapower.projectplastic.tileentity;

import javapower.projectplastic.crafting.ExtractorRecipes;
import javapower.projectplastic.util.EnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityRFExtractingFurnace extends TileEntitySynchronized implements IInventory
{
	public NonNullList<ItemStack> block_inv_content = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	//public int fuel = 0;
	public EnergyStorage energy = new EnergyStorage(2000,100,0);
	public int progress = 0;
	public boolean old_progress = false;
	
	public boolean update = true;
	
	@Override
	public void reciveDataFromClient(NBTTagCompound nbt, EntityPlayer player)
	{
		if(nbt.hasKey("p"))
			progress = nbt.getInteger("p");
		
		update = true;
		markDirty();
	}

	@Override
	public void onPlayerOpenGUISendData(NBTTagCompound nbt, EntityPlayer player)
	{
		nbt.setInteger("p", progress);
		energy.writeOnNBT(nbt, "e");
	}

	@Override
	public NBTTagCompound updateData()
	{
		if(update)
		{
			update = false;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("p", progress);
			energy.writeOnNBT(nbt, "e");
			return nbt;
		}
		return null;
	}
	
	@Override
	public void update()
	{
		if(energy.energy >= 10)
		{
			ItemStack[] itemstack = ExtractorRecipes.instance().getProcessingResult(block_inv_content.get(0));
			if(itemstack[0].isEmpty())
			{
				if(progress != 0)
				{
					progress = 0;
					update = true;
					markDirty();
				}
			}
			else
			{
				smeltItem();
			}
		}
		
		if(old_progress != (progress != 0))
		{
			old_progress = (progress != 0);
			update = true;
			markDirty();
		}
		super.update();
	}
	
	public boolean smeltItem()
    {
        if (this.canSmelt())
        {
        	if(progress < 20)
			{
				++progress;
				update = true;
				markDirty();
			}
        	else
			{
				//end smelt
				energy.energy -= 10;
				progress = 0;
				
	            ItemStack itemstack_input = this.block_inv_content.get(0);
	            
	            ItemStack[] itemstack_process = ExtractorRecipes.instance().getProcessingResult(itemstack_input);
	            
	            ItemStack itemstack_output_1 = this.block_inv_content.get(1);
	            ItemStack itemstack_output_2 = this.block_inv_content.get(2);
	            
	            if(itemstack_output_1.isEmpty() && !itemstack_process[0].isEmpty())
	            {
	            	block_inv_content.set(1, itemstack_process[0].copy());
	            	
	            	if(itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty())
	            	{
	            		block_inv_content.set(2, itemstack_process[1].copy());
	            	}
	            	else if(!itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty() && canPut(itemstack_process[1], itemstack_output_2))
	            	{
	            		itemstack_output_2.grow(itemstack_process[1].getCount());
	            	}
	            	
	            	itemstack_input.shrink(1);
	            	return true;
	            }
	            else if(!itemstack_output_1.isEmpty() && !itemstack_process[0].isEmpty() && canPut(itemstack_process[0], itemstack_output_1))
	            {
	            	itemstack_output_1.grow(itemstack_process[0].getCount());
	            	
	            	if(itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty())
	            	{
	            		block_inv_content.set(2, itemstack_process[1].copy());
	            	}
	            	else if(!itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty() && canPut(itemstack_process[1], itemstack_output_2))
	            	{
	            		itemstack_output_2.grow(itemstack_process[1].getCount());
	            	}
	            	
	            	itemstack_input.shrink(1);
	            	return true;
	            }
	            
	            update = true;
				markDirty();
			}
        }
		return false;
    }
	
	private boolean canSmelt()
    {
        if (block_inv_content.get(0).isEmpty())
        {
            return false;
        }
        else
        {
            ItemStack[] itemstack_output = ExtractorRecipes.instance().getProcessingResult(block_inv_content.get(0));

            if (itemstack_output[0].isEmpty())
            {
                return false;
            }
            else
            {
            	ItemStack itemstack_output_0 = this.block_inv_content.get(1);
            	ItemStack itemstack_output_1 = this.block_inv_content.get(2);
            	
            	if(canPut(itemstack_output[0], itemstack_output_0) && (itemstack_output[1].isEmpty() || canPut(itemstack_output[1], itemstack_output_1)))
            		return true;
            	else
            		return false;
            }
        }
    }
	
	private boolean canPut(ItemStack from, ItemStack to)
	{
		if(to.isEmpty())
			return true;
		else if(to.isItemEqual(from) && to.getCount() + from.getCount() <= to.getMaxStackSize())
			return true;
		
		return false;
	}
	
	@Override
	public void read(NBTTagCompound tag)
	{
		energy.readFromNBT(tag);
		
		if(tag.hasKey("progress"))
			progress = tag.getInteger("progress");
		
		
		if(tag.hasKey("inv"))
			ItemStackHelper.loadAllItems(tag.getCompoundTag("inv"), block_inv_content);
		
		markDirty();
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound tag)
	{
		energy.writeOnNBT(tag);
		tag.setInteger("progress", progress);
		
		NBTTagCompound nbt_inv = new NBTTagCompound();
		ItemStackHelper.saveAllItems(nbt_inv, block_inv_content);
		tag.setTag("inv", nbt_inv);
		
		return tag;
	}

	@Override
	public String getName()
	{
		return "rfextractingfurnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public int getSizeInventory()
	{
		return block_inv_content.size();
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack is: block_inv_content)
			if(!is.isEmpty())
				return false;
		
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if(index < block_inv_content.size())
			return block_inv_content.get(index);
		
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		 return ItemStackHelper.getAndSplit(block_inv_content, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(block_inv_content, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		block_inv_content.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if(index == 1)
			return false;
		return block_inv_content.get(index).isEmpty() || stack.isItemEqual(block_inv_content.get(index));
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		block_inv_content.clear();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityEnergy.ENERGY)
			return (T) energy;
		return super.getCapability(capability, facing);
	}

}