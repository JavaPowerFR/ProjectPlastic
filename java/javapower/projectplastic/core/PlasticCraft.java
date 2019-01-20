package javapower.projectplastic.core;

import javapower.projectplastic.item.PCItems;
import javapower.projectplastic.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/***************************************************
*						 ^
* 						/ \
* 					   /   \
*                     /  |  \
*                    /   .   \
*                   ___________
*                   
*   			[ W A R N I N G ! ]
*
****************************************************
*
*	All class belonging to the mod Project Plastic (Plastic Craft) is developed by Cyril GENIN (Java_Power).
*	All rights reserved to Cyril GENIN.
*	it is strictly forbidden to copy or recopy!
*	These rules apply to all class, scripts, textures, configs and all file types of this project.
*
*		author: Cyril GENIN (Java_Power)
*		website: http://javapower.fr/
*		email: cyril@famille-genin.fr
*		creation date: 27/04/2018 (dd/mm/yyyy)
*		creat at: Montigny Le Bretonneux France
*		last modification: 09/01/2019 (dd/mm/yyyy)
*		comment: RAS
*		
***************************************************/

@Mod(modid = PlasticCraft.MODID, name = PlasticCraft.NAME, version = PlasticCraft.VERSION)
public class PlasticCraft
{
    public static final String MODID = "projectplastic";
    public static final String NAME = "Project Plastic";
    public static final String VERSION = "1.5";
	
    @Instance
    public static PlasticCraft INSTANCE;
    
    @SidedProxy(clientSide = "javapower.projectplastic.proxy.ClientProxy", serverSide = "javapower.projectplastic.proxy.CommonProxy")
    public static CommonProxy proxy;
    	
	public static CreativeTabs creativeTab = new CreativeTabs(MODID)
	{
		
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(PCItems.item_resource);
		}
	};
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
    	proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }
}
