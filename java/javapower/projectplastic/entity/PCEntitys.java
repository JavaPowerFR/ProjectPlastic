package javapower.projectplastic.entity;

import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class PCEntitys
{
	
	public static void registerEntity(IForgeRegistry<EntityEntry> registry)
	{
		
	}
	
	public static void registerEntitys()
	{
		//EntityRegistry.registerModEntity(new ResourceLocation(PlasticCraft.MODID+":plasticboat"), EntityPlasticBoat.class, "plasticboat", 0, PlasticCraft.INSTANCE, 80, 1, true);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerEntitysRender()
	{
		//RenderingRegistry.registerEntityRenderingHandler(EntityPlasticBoat.class, RenderPlasticBoat.FACTORY);
	}

}
