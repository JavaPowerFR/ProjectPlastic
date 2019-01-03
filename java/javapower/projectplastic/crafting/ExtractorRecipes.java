package javapower.projectplastic.crafting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;

import javapower.projectplastic.item.PCItems;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

public class ExtractorRecipes
{
	private static final ExtractorRecipes EXTRACTOR_BASE = new ExtractorRecipes();
    private final Map<ItemStack, ItemStack[]> extractorList = Maps.<ItemStack, ItemStack[]>newHashMap();
    
    public static ExtractorRecipes instance()
    {
    	return EXTRACTOR_BASE;
    }
    
    private ExtractorRecipes()
    {
    	addRecipe(new ItemStack(PCItems.item_bucket, 1, 3), new ItemStack(PCItems.item_resource, 3, 9), new ItemStack(PCItems.item_bucket, 1, 0));
    	addRecipe(new ItemStack(PCItems.item_mould, 1, 1), new ItemStack(PCItems.item_resource, 4, 0), new ItemStack(PCItems.item_mould, 1, 0));
    }
    
    private void addRecipe(ItemStack input, ItemStack output1, ItemStack output2)
    {
    	extractorList.put(input, new ItemStack[]{output1, output2});
    }
    
    public ItemStack[] getProcessingResult(ItemStack stack)
    {
        for (Entry<ItemStack, ItemStack[]> entry : this.extractorList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY};
    }
    
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

	public Collection<IExtractingFurnaceRecipe> GetCollection()
	{
		List<IExtractingFurnaceRecipe> recipes = new ArrayList<IExtractingFurnaceRecipe>();
		for(Entry<ItemStack, ItemStack[]> r : extractorList.entrySet())
		{
			recipes.add(new IExtractingFurnaceRecipe()
			{
				@Override
				public ItemStack[] Output()
				{
					return r.getValue();
				}
				
				@Override
				public ItemStack Input()
				{
					return r.getKey();
				}
			});
		}
		return recipes;
	}
}
