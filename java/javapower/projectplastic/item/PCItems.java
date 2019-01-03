package javapower.projectplastic.item;

import javapower.projectplastic.core.PlasticCraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class PCItems
{
	public static final ItemResource item_resource = new ItemResource();
	public static final ItemBase item_mould = new ItemMould();
	public static final ItemPlasticBucket item_bucket = new ItemPlasticBucket();
	public static final ItemPlasticBottle item_bottle = new ItemPlasticBottle();
	
	public static final ItemPlasticSword item_plasticSword = new ItemPlasticSword();
	public static final ItemPlasticShovel item_plasticShovel = new ItemPlasticShovel();
	public static final ItemPlasticPickaxe item_plasticPickaxe = new ItemPlasticPickaxe();
	public static final ItemPlasticAxe item_plasticAxe = new ItemPlasticAxe();
	
	public static final ItemNightvisionGoggles item_nightvisionGoggles = new ItemNightvisionGoggles();
	public static final ItemKevlarPants item_kevlarPants = new ItemKevlarPants();
	public static final ItemKevlarVest item_kevlarVest = new ItemKevlarVest();
	public static final ItemShockAbsorbingBoots item_shockAbsorbingBoots = new ItemShockAbsorbingBoots();
	
	public static final ItemPlasticBoat item_plasticBoat = new ItemPlasticBoat();
	
	public static void registerItems(IForgeRegistry<Item> register)
    {
		register.register(item_resource);
		register.register(item_mould);
		register.register(item_bucket);
		register.register(item_bottle);
		
		register.register(item_plasticSword);
		register.register(item_plasticShovel);
		register.register(item_plasticPickaxe);
		register.register(item_plasticAxe);
		register.register(item_nightvisionGoggles);
		register.register(item_kevlarPants);
		register.register(item_kevlarVest);
		register.register(item_shockAbsorbingBoots);
		//register.register(item_plasticBoat);
    }
	
	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		//ModelBakery
		
		for(String subname : item_resource.names)
			ModelBakery.registerItemVariants(item_resource, new ResourceLocation(PlasticCraft.MODID, subname));
		
		for(String subname : item_bucket.names)
			ModelBakery.registerItemVariants(item_bucket, new ResourceLocation(PlasticCraft.MODID, subname));
		
		for(String subname : item_bottle.names)
			ModelBakery.registerItemVariants(item_bottle, new ResourceLocation(PlasticCraft.MODID, subname));
		
		
		ModelBakery.registerItemVariants(item_mould, new ResourceLocation(PlasticCraft.MODID, item_mould.name+0));
		ModelBakery.registerItemVariants(item_mould, new ResourceLocation(PlasticCraft.MODID, item_mould.name+1));
		
		//ModelLoader
		
		int id = 0;
		for(String subname : item_resource.names)
		{
			ModelLoader.setCustomModelResourceLocation(item_resource, id, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID, subname), "inventory"));
			++id;
		}
		
		id = 0;
		for(String subname : item_bucket.names)
		{
			ModelLoader.setCustomModelResourceLocation(item_bucket, id, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID, subname), "inventory"));
			++id;
		}
		
		id = 0;
		for(String subname : item_bottle.names)
		{
			ModelLoader.setCustomModelResourceLocation(item_bottle, id, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID, subname), "inventory"));
			++id;
		}
		
		ModelLoader.setCustomModelResourceLocation(item_mould, 0, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID, item_mould.name+0), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item_mould, 1, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID, item_mould.name+1), "inventory"));
		
		registerModel(item_plasticSword);
		registerModel(item_plasticShovel);
		registerModel(item_plasticPickaxe);
		registerModel(item_plasticAxe);
		
		ModelLoader.setCustomModelResourceLocation(item_nightvisionGoggles, 0, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID+":nightvisiongoggles"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item_kevlarPants, 0, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID+":kevlarpants"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item_kevlarVest, 0, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID+":kevlarvest"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(item_shockAbsorbingBoots, 0, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID+":shockabsorbingboots"), "inventory"));
		
		//registerModel(item_plasticBoat);
	}
	
	//---------- private ----------
	
	private static void registerModel(ItemBase item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(PlasticCraft.MODID+":"+item.name));
	}
}
