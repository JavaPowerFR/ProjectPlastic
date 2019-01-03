package javapower.projectplastic.block;

import java.util.Map;
import java.util.Map.Entry;

import javapower.projectplastic.core.PlasticCraft;
import javapower.projectplastic.tileentity.TileEntityExtractingFurnace;
import javapower.projectplastic.tileentity.TileEntityMicrowaveExtractingFurnace;
import javapower.projectplastic.tileentity.TileEntityMicrowaveOven;
import javapower.projectplastic.tileentity.TileEntityRFExtractingFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class PCBlocks
{
	public static final BlockBase block_latexExtractor = new BlockLatexExtractor();
	
	public static final BlockBase block_plastic = new BlockPlastic();
	public static final BlockBase block_plastic_color = new BlockPlasticColor();
	
	public static final BlockBase block_glow_stick = new BlockGlowStick();
	public static final BlockBase block_gooey_plastic = new BlockGooeyPlastic();
	public static final BlockBase block_plexiglass = new BlockPlexiglass();
	public static final BlockBase block_glowing_plexiglass = new BlockGlowingPlexiglass();
	public static final BlockBase block_plexiglass_door = new BlockPlexiglassDoor().p_disableState();
	
	public static final BlockBase block_trampoline = new BlockTrampoline();
	public static final BlockBase block_accelerator = new BlockAccelerator();
	public static final BlockBase block_rope = new BlockRope();
	
	public static final BlockBaseContainer block_microwaveOven = new BlockMicrowaveOven();
	public static final BlockBaseContainer block_extractingFurnace = new BlockExtractingFurnace();
	public static final BlockBaseContainer block_microwaveExtractingFurnace = new BlockMicrowaveExtractingFurnace();
	public static final BlockBaseContainer block_electricExtractingFurnace = new BlockRFExtractingFurnace();
	
	public static void registerBlocks(IForgeRegistry<Block> register)
    {
		register.register(block_latexExtractor);
		register.register(block_plastic);
		register.register(block_plastic_color);
		register.register(block_glow_stick);
		register.register(block_gooey_plastic);
		register.register(block_plexiglass);
		register.register(block_glowing_plexiglass);
		register.register(block_plexiglass_door);
		register.register(block_trampoline);
		register.register(block_accelerator);
		register.register(block_rope);
		register.register(block_microwaveOven);
		register.register(block_extractingFurnace);
		register.register(block_microwaveExtractingFurnace);
		register.register(block_electricExtractingFurnace);
    }
	
	public static void registerItems(IForgeRegistry<Item> register)
    {
		register.register(block_latexExtractor.getItem());
		register.register(block_plastic.getItem());
		register.register(block_plastic_color.getItem());
		register.register(block_glow_stick.getItem());
		register.register(block_gooey_plastic.getItem());
		register.register(block_plexiglass.getItem());
		register.register(block_glowing_plexiglass.getItem());
		register.register(block_plexiglass_door.getItem());
		register.register(block_trampoline.getItem());
		register.register(block_accelerator.getItem());
		register.register(block_rope.getItem());
		register.register(block_microwaveOven.getItem());
		register.register(block_extractingFurnace.getItem());
		register.register(block_microwaveExtractingFurnace.getItem());
		register.register(block_electricExtractingFurnace.getItem());
    }
	
	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModelNormal(block_latexExtractor);
		registerModelNormal(block_plastic);
		
		for(int id = 0; id < 16; ++id)
			ModelLoader.setCustomModelResourceLocation(block_plastic_color.getItem(), id, new ModelResourceLocation(new ResourceLocation(PlasticCraft.MODID, "plasticblockcolor"), "inv"+id));
		
		registerModelNormal(block_glow_stick);
		registerModelNormal(block_gooey_plastic);
		registerModelNormal(block_plexiglass);
		registerModelNormal(block_glowing_plexiglass);
		registerModelNormal(block_plexiglass_door);
		
		StateMapperBase stateMapperBase = new StateMapperBase()
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()), getPropertyString2(state.getProperties()));
			}
			
			public String getPropertyString2(Map < IProperty<?>, Comparable<? >> values)
		    {
		        StringBuilder stringbuilder = new StringBuilder();

		        for (Entry < IProperty<?>, Comparable<? >> entry : values.entrySet())
		        {
		        	if(!entry.getKey().equals(BlockPlexiglassDoor.POWERED))
		        	{
			            if (stringbuilder.length() != 0)
			            {
			                stringbuilder.append(",");
			            }
	
			            IProperty<?> iproperty = (IProperty)entry.getKey();
			            stringbuilder.append(iproperty.getName());
			            stringbuilder.append("=");
			            stringbuilder.append(""+entry.getValue());
		        	}
		        }

		        if (stringbuilder.length() == 0)
		        {
		            stringbuilder.append("normal");
		        }

		        return stringbuilder.toString();
		    }
		};
		ModelLoader.setCustomStateMapper(block_plexiglass_door, stateMapperBase);
		
		registerModelNormal(block_trampoline);
		registerModelNormal(block_accelerator);
		registerModelNormal(block_rope);
		registerModelNormal(block_microwaveOven);
		registerModelNormal(block_extractingFurnace);
		registerModelNormal(block_microwaveExtractingFurnace);
		registerModelNormal(block_electricExtractingFurnace);
	}
	
	public static void registerTE()
	{
		GameRegistry.registerTileEntity(TileEntityMicrowaveOven.class, block_microwaveOven.name);
		GameRegistry.registerTileEntity(TileEntityExtractingFurnace.class, block_extractingFurnace.name);
		GameRegistry.registerTileEntity(TileEntityMicrowaveExtractingFurnace.class, block_microwaveExtractingFurnace.name);
		GameRegistry.registerTileEntity(TileEntityRFExtractingFurnace.class, block_electricExtractingFurnace.name);
	}
	
	//---------- private ----------
	
	private static void registerModelNormal(BlockBaseContainer block)
	{
		ModelLoader.setCustomModelResourceLocation(block.getItem(), 0, new ModelResourceLocation(PlasticCraft.MODID+":"+block.name, "normal"));
	}
	
	private static void registerModelNormal(BlockBase block)
	{
		ModelLoader.setCustomModelResourceLocation(block.getItem(), 0, new ModelResourceLocation(PlasticCraft.MODID+":"+block.name, "normal"));
	}
}
