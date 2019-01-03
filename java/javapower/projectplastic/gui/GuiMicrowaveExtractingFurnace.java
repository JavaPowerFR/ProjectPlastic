package javapower.projectplastic.gui;

import javapower.projectplastic.container.ContainerMicrowaveExtractingFurnace;
import javapower.projectplastic.proxy.ResourceLocationRegister;
import javapower.projectplastic.tileentity.TileEntityMicrowaveExtractingFurnace;
import javapower.projectplastic.util.IGUITileSync;
import javapower.projectplastic.util.NetworkUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class GuiMicrowaveExtractingFurnace extends GuiContainer implements IGUITileSync
{
	TileEntityMicrowaveExtractingFurnace tileentity;
	private int progress;
	private int fuel;
	
	public GuiMicrowaveExtractingFurnace(TileEntityMicrowaveExtractingFurnace _tileentity, EntityPlayer player)
	{
		super(new ContainerMicrowaveExtractingFurnace(_tileentity, player));
		tileentity = _tileentity;
		xSize = 176;
		ySize = 166;
		NetworkUtils.sendToServerPlayerAsOpenGUI(_tileentity, this);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("u", (byte) 1);
		NetworkUtils.sendToServerTheData(tileentity, this, nbt );
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ResourceLocationRegister.texture_gui_guiextractor2);
		
		int x = (width - xSize) /2;
        int y = (height - ySize) /2;
		
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        int p = 1+(fuel*12)/256;
        this.drawTexturedModalRect(x+57, y+37+(14-p), 176, (14-p), 14, p);
        
        int p2 = (progress*22)/20;
        this.drawTexturedModalRect(x+80, y+35, 177, 14, p2, 16);
        
        fontRenderer.drawString("Microwave Extracting Furnace", x + 12, y + 6, 0x404040, false);
        
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
		return TileEntityMicrowaveExtractingFurnace.class;
	}

	@Override
	public void reciveDataFromServer(NBTTagCompound nbt)
	{
		if(nbt.hasKey("p"))
			progress = nbt.getInteger("p");
		if(nbt.hasKey("f"))
			fuel = nbt.getInteger("f");
		
	}
	
	public void sendInfo(NBTTagCompound nbt)
	{
		NetworkUtils.sendToServerTheData(tileentity, this, nbt);
	}

}