package com.kube.kubeMod.objects.blocks.machines.coldironFurnace;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.kube.kubeMod.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ColdironFurnaceRecipes
{
	private static final ColdironFurnaceRecipes INSTANCE = new ColdironFurnaceRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static ColdironFurnaceRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private ColdironFurnaceRecipes() 
	{
		addSmeltingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(ItemInit.INGOT_COLDIRON), 1.0F);
	}


    /**
     * Adds a smelting recipe, where the input item is an instance of Block.
     */
    public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
    {
        this.addSmelting(Item.getItemFromBlock(input), stack, experience);
    }

    /**
     * Adds a smelting recipe using an Item as the input item.
     */
    public void addSmelting(Item input, ItemStack stack, float experience)
    {
        this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    /**
     * Adds a smelting recipe using an ItemStack as the input for the recipe.
     */
	public void addSmeltingRecipe(ItemStack input, ItemStack result, float experience) 
	{
		if(getColdironSmeltingResult(input) != ItemStack.EMPTY) return;
		this.smeltingList.put(input, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getColdironSmeltingResult(ItemStack input) 
	{
        for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
        {
            if (this.compareItemStacks(input, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

    public Map<ItemStack, ItemStack> getSmeltingList()
    {
        return this.smeltingList;
    }
	
	public float getColdironSmeltingExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}
