package javapower.projectplastic.proxy;

import javapower.projectplastic.block.PCBlocks;
import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.entity.PCEntitys;
import javapower.projectplastic.gui.GuiHandler;
import javapower.projectplastic.item.PCItems;
import javapower.projectplastic.message.ClientHandlerTileSync;
import javapower.projectplastic.message.NetworkTileSync;
import javapower.projectplastic.message.ServerHandlerTileSync;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
	public static MinecraftServer minecraftServer = FMLCommonHandler.instance().getMinecraftServerInstance();
	
	public static SimpleNetworkWrapper network_TileSynchroniser = NetworkRegistry.INSTANCE.newSimpleChannel(PlasticCraft.MODID+"1");
	
    public void preInit(FMLPreInitializationEvent e)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    	PCBlocks.registerTE();
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(PlasticCraft.INSTANCE, new GuiHandler());
    	
    	network_TileSynchroniser.registerMessage(ServerHandlerTileSync.class, NetworkTileSync.class, 0, Side.SERVER);
    	network_TileSynchroniser.registerMessage(ClientHandlerTileSync.class, NetworkTileSync.class, 1, Side.CLIENT);
    	PCEntitys.registerEntitys();
    }
	
    public void init(FMLInitializationEvent e)
    {
    	//Recipe.register();
    	GameRegistry.addSmelting(new ItemStack(PCItems.item_mould, 1, 1), new ItemStack(PCItems.item_resource, 1, 0), 0.2f);
    	GameRegistry.addSmelting(new ItemStack(PCItems.item_resource, 1, 6), new ItemStack(PCItems.item_resource, 4, 7), 0.2f);
    	GameRegistry.addSmelting(new ItemStack(PCItems.item_resource, 1, 0), new ItemStack(PCItems.item_resource, 1, 8), 0.2f);
    	GameRegistry.addSmelting(new ItemStack(PCItems.item_resource, 1, 1), new ItemStack(PCItems.item_resource, 1, 8), 0.2f);
    	GameRegistry.addSmelting(new ItemStack(PCItems.item_bucket, 1, 3), new ItemStack(PCItems.item_resource, 2, 9), 0.2f);
    }
    
    public void postInit(FMLPostInitializationEvent e)
    {
    	
    }
    
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {
    	PCBlocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event)
    {
    	PCBlocks.registerItems(event.getRegistry());
    	PCItems.registerItems(event.getRegistry());
    }
    
    @SubscribeEvent
    public void registerEntityEntry(RegistryEvent.Register<EntityEntry> event)
    {
        PCEntitys.registerEntity(event.getRegistry());
    }
}
