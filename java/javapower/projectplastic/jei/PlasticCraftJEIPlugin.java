package javapower.projectplastic.jei;

import java.util.ArrayList;
import java.util.List;

import javapower.projectplastic.block.BlockMicrowaveOven;
import javapower.projectplastic.block.PCBlocks;
import javapower.projectplastic.crafting.ExtractorRecipes;
import javapower.projectplastic.crafting.IExtractingFurnaceRecipe;
import javapower.projectplastic.item.PCItems;
import javapower.projectplastic.util.ItemStackValue;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class PlasticCraftJEIPlugin implements IModPlugin
{
	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_microwaveOven), VanillaRecipeCategoryUid.SMELTING);
		
		//Extracting Furnace
		registry.handleRecipes(IExtractingFurnaceRecipe.class, new ExtractingFurnaceWrapperFactory(), ExtractingFurnaceCategory.UID);
		registry.addRecipes(ExtractorRecipes.instance().GetCollection(), ExtractingFurnaceCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_extractingFurnace), ExtractingFurnaceCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_microwaveExtractingFurnace), ExtractingFurnaceCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_electricExtractingFurnace), ExtractingFurnaceCategory.UID);
		
		//Microwave Fuel
		List<IMicrowaveFuel> microwaveFuels = new ArrayList<IMicrowaveFuel>();
		for(ItemStackValue isv : BlockMicrowaveOven.FUELS_USEABLE)
		microwaveFuels.add(new IMicrowaveFuel()
		{
			@Override
			public int getFuelSize(){return isv.value;}
			@Override
			public ItemStack getFuel(){return isv.itemstack;}
		});
		
		registry.handleRecipes(IMicrowaveFuel.class, new MicrowaveFuelWrapperFactory(), MicrowaveFuelCategory.UID);
		registry.addRecipes(microwaveFuels, MicrowaveFuelCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_microwaveOven), MicrowaveFuelCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_microwaveExtractingFurnace), MicrowaveFuelCategory.UID);
		
		//Latex Extractor
		List<ILatexExtractorRecipe> latexExtractorRecipe = new ArrayList<ILatexExtractorRecipe>();
		
		latexExtractorRecipe.add(new ILatexExtractorRecipe() {@Override public ItemStack getOutput() {return new ItemStack(PCItems.item_bucket,1,3);} @Override public ItemStack getInput() {return new ItemStack(PCItems.item_bucket,1,0);} });
		
		registry.handleRecipes(ILatexExtractorRecipe.class, new LatexExtractorWrapperFactory(), LatexExtractorCategory.UID);
		registry.addRecipes(latexExtractorRecipe, LatexExtractorCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(PCBlocks.block_latexExtractor), LatexExtractorCategory.UID);
		
	}
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{	
		registry.addRecipeCategories(new ExtractingFurnaceCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new MicrowaveFuelCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new LatexExtractorCategory(registry.getJeiHelpers().getGuiHelper()));
	}
}
