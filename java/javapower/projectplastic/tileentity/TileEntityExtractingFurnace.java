package javapower.projectplastic.tileentity;

import javapower.projectplastic.crafting.ExtractorRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

public class TileEntityExtractingFurnace extends TileEntitySynchronized implements IInventory
{
	public NonNullList<ItemStack> block_inv_content = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	public int fuel = 0, fuelMax = 0;
	public int progress = 0;
	
	public boolean update = true;
	
	@Override
	public void reciveDataFromClient(NBTTagCompound nbt, EntityPlayer player)
	{
		if(nbt.hasKey("p"))
			progress = nbt.getInteger("p");
		
		if(nbt.hasKey("f"))
			fuel = nbt.getInteger("f");
		
		update = true;
		markDirty();
	}

	@Override
	public void onPlayerOpenGUISendData(NBTTagCompound nbt, EntityPlayer player)
	{
		nbt.setInteger("p", progress);
		nbt.setInteger("f", fuel);
		nbt.setInteger("g", fuelMax);
	}

	@Override
	public NBTTagCompound updateData()
	{
		if(update)
		{
			update = false;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("p", progress);
			nbt.setInteger("f", fuel);
			nbt.setInteger("g", fuelMax);
			return nbt;
		}
		return null;
	}
	
	@Override
	public void update()
	{
		boolean isburning_flag = this.isBurning();
        boolean update_flag = false;

        if (this.isBurning())
        {
            --fuel;
        }

        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.block_inv_content.get(1);

            if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)this.block_inv_content.get(0)).isEmpty())
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    fuel = TileEntityFurnace.getItemBurnTime(itemstack);
                    fuelMax = fuel;

                    if (this.isBurning())
                    {
                        update_flag = true;

                        if (!itemstack.isEmpty())
                        {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);

                            if (itemstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.block_inv_content.set(1, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++progress;

                    if (progress >= 200)
                    {
                        progress = 0;
                        smeltItem();
                    }
                }
                else
                {
                	progress = 0;
                }
                
                update_flag = true;
                
            }
            else if (!this.isBurning() && progress > 0)
            {
                progress = MathHelper.clamp(progress - 2, 0, 200);
            }

            if (isburning_flag != this.isBurning())
            {
                update_flag = true;
            }
        }

        if (update_flag)
        {
        	update = true;
            this.markDirty();
        }
		/*ItemStack fuel_slot = block_inv_content.get(1);
		if(!fuel_slot.isEmpty())
		{
			int add_fuel = TileEntityFurnace.getItemBurnTime(fuel_slot);
			if(fuel <= 0)
			{
				fuel_slot.setCount(fuel_slot.getCount()-1);
				fuel += add_fuel;
				
				update = true;
				markDirty();
			}
		}*/
		
		super.update();
	}
	
	public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack_input = this.block_inv_content.get(0);
            
            ItemStack[] itemstack_process = ExtractorRecipes.instance().getProcessingResult(itemstack_input);
            
            ItemStack itemstack_output_1 = this.block_inv_content.get(2);
            ItemStack itemstack_output_2 = this.block_inv_content.get(3);
            
            if(itemstack_output_1.isEmpty() && !itemstack_process[0].isEmpty())
            {
            	block_inv_content.set(2, itemstack_process[0].copy());
            	
            	if(itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty())
            	{
            		block_inv_content.set(3, itemstack_process[1].copy());
            	}
            	else if(!itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty() && canPut(itemstack_process[1], itemstack_output_2))
            	{
            		itemstack_output_2.grow(itemstack_process[1].getCount());
            	}
            	
            	itemstack_input.shrink(1);
            }
            else if(!itemstack_output_1.isEmpty() && !itemstack_process[0].isEmpty() && canPut(itemstack_process[0], itemstack_output_1))
            {
            	itemstack_output_1.grow(itemstack_process[0].getCount());
            	
            	if(itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty())
            	{
            		block_inv_content.set(3, itemstack_process[1].copy());
            	}
            	else if(!itemstack_output_2.isEmpty() && !itemstack_process[1].isEmpty() && canPut(itemstack_process[1], itemstack_output_2))
            	{
            		itemstack_output_2.grow(itemstack_process[1].getCount());
            	}
            	
            	itemstack_input.shrink(1);
            }

            /*if (itemstack_output_1.isEmpty())
            {
                this.furnaceItemStacks.set(2, itemstack_process.copy());
            }
            else if (itemstack2.getItem() == itemstack_process.getItem())
            {
                itemstack2.grow(itemstack_process.getCount());
            }

            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !((ItemStack)this.furnaceItemStacks.get(1)).isEmpty() && ((ItemStack)this.furnaceItemStacks.get(1)).getItem() == Items.BUCKET)
            {
                this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);*/
        }
    }
	
	public boolean isBurning()
    {
        return this.fuel > 0;
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
            	ItemStack itemstack_output_0 = this.block_inv_content.get(2);
            	ItemStack itemstack_output_1 = this.block_inv_content.get(3);
            	
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
		if(tag.hasKey("fuel"))
			fuel = tag.getInteger("fuel");
		
		if(tag.hasKey("progress"))
			progress = tag.getInteger("progress");
		
		if(tag.hasKey("fuelm"))
			fuelMax = tag.getInteger("fuelm");
		
		
		if(tag.hasKey("inv"))
			ItemStackHelper.loadAllItems(tag.getCompoundTag("inv"), block_inv_content);
		
		markDirty();
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound tag)
	{
		tag.setInteger("progress", progress);
		
		tag.setInteger("fuel", fuel);
		tag.setInteger("fuelm", fuelMax);
		
		NBTTagCompound nbt_inv = new NBTTagCompound();
		ItemStackHelper.saveAllItems(nbt_inv, block_inv_content);
		tag.setTag("inv", nbt_inv);
		
		return tag;
	}

	@Override
	public String getName()
	{
		return "extractingfurnace";
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
		if(index == 2 || index == 3)
			return false;
		if(index == 1)
		{
			return TileEntityFurnace.getItemBurnTime(stack) > 0;
		}
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

}
