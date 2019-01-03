package javapower.projectplastic.util;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGuiRegister
{
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(TileEntity tile, EntityPlayer player);
	public Container getContainer(TileEntity tile, EntityPlayer player);
}
