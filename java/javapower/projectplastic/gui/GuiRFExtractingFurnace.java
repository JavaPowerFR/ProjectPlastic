package javapower.projectplastic.gui;

import java.util.ArrayList;
import java.util.List;

import javapower.projectplastic.container.ContainerRFExtractingFurnace;
import javapower.projectplastic.proxy.ResourceLocationRegister;
import javapower.projectplastic.tileentity.TileEntityRFExtractingFurnace;
import javapower.projectplastic.util.IGUITileSync;
import javapower.projectplastic.util.NetworkUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiRFExtractingFurnace extends GuiContainer implements IGUITileSync
{
	TileEntityRFExtractingFurnace tileentity;
	private int progress;
	private int energy;
	
	public GuiRFExtractingFurnace(TileEntityRFExtractingFurnace _tileentity, EntityPlayer player)
	{
		super(new ContainerRFExtractingFurnace(_tileentity, player));
		tileentity = _tileentity;
		xSize = 176;
		ySize = 166;
		NetworkUtils.sendToServerPlayerAsOpenGUI(_tileentity, this);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("u", (byte) 1);
		NetworkUtils.sendToServerTheData(_tileentity, this, nbt );
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ResourceLocationRegister.texture_gui_guiextractor3);
		
		int x = (width - xSize) /2;
        int y = (height - ySize) /2;
		
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        int p = (energy*48)/2000;
        this.drawTexturedModalRect(x+10, y+18+(48-p), 176, (79-p), 12, p);
        
        int p2 = (progress*22)/20;
        this.drawTexturedModalRect(x+80, y+35, 177, 14, p2, 16);
        
        fontRenderer.drawString("Electric Extracting Furnace", x + 18, y + 6, 0x404040, false);
        
        if(mouseX >= x+10 && mouseY >= y+18 && mouseX <= x+21 && mouseY <= y+65)//energy
        {
        	List<String> list = new ArrayList<String>();
        	list.add(energy+" FE");
        	GuiUtils.drawHoveringText(list, mouseX, mouseY, mc.displayWidth, mc.displayHeight, 100, fontRenderer);
        }
        
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	public Class<? extends TileEntity> tileEntityLink()
	{
		return TileEntityRFExtractingFurnace.class;
	}

	@Override
	public void reciveDataFromServer(NBTTagCompound nbt)
	{
		if(nbt.hasKey("p"))
			progress = nbt.getInteger("p");
		if(nbt.hasKey("e"))
			energy = nbt.getInteger("e");
		
	}
	
	public void sendInfo(NBTTagCompound nbt)
	{
		NetworkUtils.sendToServerTheData(tileentity, this, nbt);
	}

}