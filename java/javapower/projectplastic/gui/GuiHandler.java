package javapower.projectplastic.gui;

import javapower.projectplastic.util.IGuiRegister;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return getContainer(world, new BlockPos(x, y, z), player);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return getGuiContainer(world, new BlockPos(x, y, z), player);
	}
	
	@SideOnly(Side.CLIENT)
	public static GuiScreen getGuiContainer(World world, BlockPos pos, EntityPlayer player)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(block != null && block instanceof IGuiRegister)
				return ((IGuiRegister)block).getGui(world.getTileEntity(pos), player);
		
		return null;
	}
	
	public static Container getContainer(World world, BlockPos pos, EntityPlayer player)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(block != null && block instanceof IGuiRegister)
				return ((IGuiRegister)block).getContainer(world.getTileEntity(pos), player);
		
		return null;
	}
}
