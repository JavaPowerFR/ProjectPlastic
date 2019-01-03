package javapower.projectplastic.proxy;

import javapower.projectplastic.block.PCBlocks;
import javapower.projectplastic.entity.PCEntitys;
import javapower.projectplastic.item.PCItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy
{
	public static Minecraft minecraft = Minecraft.getMinecraft();
	
	@Override
	public void preInit(FMLPreInitializationEvent e)
    {
		super.preInit(e);
		PCEntitys.registerEntitysRender();
    }
	
	@Override
    public void init(FMLInitializationEvent e)
    {
		ResourceLocationRegister.register();
		
    	super.init(e);
    }
    
	@Override
    public void postInit(FMLPostInitializationEvent e)
    {
    	super.postInit(e);
    }
	
	@SubscribeEvent
    public void registerModels(ModelRegistryEvent e)
	{
		PCBlocks.registerModels();
		PCItems.registerModels();
	}
}
