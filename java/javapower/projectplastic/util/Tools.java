package javapower.projectplastic.util;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class Tools
{
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}
	
	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER;
	}
	
	public static EnumFacing getFaceFromPos(BlockPos center, BlockPos sat)
	{
		return EnumFacing.getFacingFromVector(sat.getX() - center.getX(), sat.getY() - center.getY(), sat.getZ() - center.getZ());
	}
	
	public static boolean PlayerIsOnList(List<EntityPlayerMP> entityPlayers, EntityPlayerMP entityPlayer)
    {
    	if(entityPlayers != null && !entityPlayers.isEmpty())
    		for(EntityPlayer pl : entityPlayers)
    		{
    			if(pl.getUniqueID() == entityPlayer.getPersistentID())
    				return true;
    		}
    	return false;
    }
    
    public static void RemovePlayerOnList(List<EntityPlayerMP> entityPlayers, EntityPlayerMP entityPlayer)
    {
    	if(entityPlayers != null && !entityPlayers.isEmpty())
    	{
    		boolean isPresent = false;
    		int id = 0;
    		for(EntityPlayer pl : entityPlayers)
    		{
    			if(pl.getUniqueID() == entityPlayer.getPersistentID())
    			{
    				isPresent = true;
    				break;
    			}
    			++id;
    		}
    		
    		if(isPresent)
    			entityPlayers.remove(id);
    	}
    }
    
    public static ItemStack TryToPutItemStackInInventory(EntityPlayer playerIn, ItemStack stack)
	{
		for(int id_slot = 0; id_slot <= 35; ++id_slot)
		{
			if(stack.isEmpty())
				 break;
			 ItemStack go = playerIn.inventory.getStackInSlot(id_slot);
			 if(go.isEmpty())
			 {
				 playerIn.inventory.setInventorySlotContents(id_slot, stack.copy());
				 return ItemStack.EMPTY;
			 }
			 else if(go.isItemEqual(stack))
			 {
				 int amt_canput = go.getMaxStackSize() - go.getCount();
				 int amt_from = stack.getCount();
				 int amt_transfert = Math.min(amt_canput, amt_from);
				 go.grow(amt_transfert);
				 stack.shrink(amt_transfert);
				 if(amt_from <= amt_transfert)
					 return ItemStack.EMPTY;
			 }
		}
		return stack;
	}
}
