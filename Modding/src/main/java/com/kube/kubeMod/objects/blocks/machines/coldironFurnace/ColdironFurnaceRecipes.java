package com.kube.kubeMod.objects.blocks.machines.coldironFurnace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.kube.kubeMod.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ColdironFurnaceRecipes
{
	private static final ColdironFurnaceRecipes INSTANCE = new ColdironFurnaceRecipes();
	private final Table<ItemStack, Integer[], ItemStack> smeltingList = HashBasedTable.<ItemStack, Integer[], ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	public static ColdironFurnaceRecipes getInstance()
	{
		return INSTANCE;
	}

	private ColdironFurnaceRecipes()
	{
		addSmeltingRecipeForBlock(Blocks.IRON_ORE, 800, 1200, new ItemStack(ItemInit.INGOT_COLDIRON), 0.7F);
		addSmeltingRecipeForBlock(Blocks.IRON_ORE, 1300, 1700, new ItemStack(Items.IRON_INGOT), 0.7F);
		addSmeltingRecipeWithoutTemperature(Blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND, 2), 1.0F);

		generateDirtyIngotSmeltingRecipe();
	}

	/**
	 * Adds a smelting recipe, where the input item is an instance of Block.
	 */
	public void addSmeltingRecipeWithoutTemperature(Block input, ItemStack stack, float experience)
	{
		this.addSmelting(Item.getItemFromBlock(input), 0, BlockColdironFurnace.MAX_TEMPERATURE, stack, experience);
	}

	/**
	 * Adds a smelting recipe, where the input item is an instance of Block.
	 */
	public void addSmeltingRecipeForBlock(Block input, int tempMin, int tempMax, ItemStack stack, float experience)
	{
		this.addSmelting(Item.getItemFromBlock(input), tempMin, tempMax, stack, experience);
	}

	/**
	 * Adds a smelting recipe using an Item as the input item.
	 */
	public void addSmelting(Item input, int tempMin, int tempMax, ItemStack stack, float experience)
	{
		this.addSmeltingRecipe(new ItemStack(input, 1, 32767), tempMin, tempMax, stack, experience);
	}

	/**
	 * Adds a smelting recipe using an ItemStack as the input for the recipe.
	 */
	public void addSmeltingRecipe(ItemStack input, int tempMin, int tempMax, ItemStack result, float experience)
	{
		if (this.isInSmeltingList(input, tempMin, tempMax) || tempMax < tempMin)
		{
			return;
		}

		this.experienceList.put(result, Float.valueOf(experience));

		for (Entry<ItemStack, Map<Integer[], ItemStack>> entry : this.smeltingList.rowMap().entrySet())
		{
			if (this.compareItemStacks(input, entry.getKey()))
			{
				entry.getValue().put(new Integer[] { tempMin, tempMax }, result);
				return;
			}
		}
		this.smeltingList.put(input, new Integer[] { tempMin, tempMax }, result);

	}

	public ItemStack getColdironSmeltingResult(ItemStack input, int temperature)
	{
		for (Entry<ItemStack, Map<Integer[], ItemStack>> entry1 : this.smeltingList.rowMap().entrySet())
		{
			if (this.compareItemStacks(input, entry1.getKey()))
			{
				for (Entry<Integer[], ItemStack> entry2 : entry1.getValue().entrySet())
				{
					if (temperature >= entry2.getKey()[0] && temperature <= entry2.getKey()[1])
					{
						return entry2.getValue();
					}
				}

				return ItemStack.EMPTY;
			}
		}

		return ItemStack.EMPTY;
	}

	public boolean isInSmeltingList(ItemStack input, int tempMin, int tempMax)
	{
		for (Entry<ItemStack, Map<Integer[], ItemStack>> entry1 : this.smeltingList.rowMap().entrySet())
		{
			if (this.compareItemStacks(input, entry1.getKey()))
			{
				for (Entry<Integer[], ItemStack> entry2 : entry1.getValue().entrySet())
				{
					if (Math.min(entry2.getKey()[1], tempMax) - Math.max(entry2.getKey()[0], tempMin) > 0)
					{
						return true;
					}
				}

				return false;
			}
		}

		return false;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Table<ItemStack, Integer[], ItemStack> getSmeltingList()
	{
		return this.smeltingList;
	}

	public float getColdironSmeltingExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
		{
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey()))
			{
				return ((Float) entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}

	private void generateDirtyIngotSmeltingRecipe()
	{
		for (Entry<ItemStack, Map<Integer[], ItemStack>> entry1 : this.smeltingList.rowMap().entrySet())
		{
			List<Integer> temperatures = new ArrayList<>();
			for (Entry<Integer[], ItemStack> entry2 : entry1.getValue().entrySet())
			{
				temperatures.add(entry2.getKey()[0]);
				temperatures.add(entry2.getKey()[1]);
			}
			Collections.sort(temperatures);

			if (temperatures.get(0) > 0)
			{
				this.addSmeltingRecipe(entry1.getKey(), 0, temperatures.get(0) - 1, new ItemStack(ItemInit.INGOT_DIRTY), 0.0F);
			}

			for (int i = 1; i < temperatures.size() - 2; i += 2)
			{
				this.addSmeltingRecipe(entry1.getKey(), temperatures.get(i) + 1, temperatures.get(i + 1) - 1, new ItemStack(ItemInit.INGOT_DIRTY), 0.0F);
			}

			int lastTemperature = temperatures.get(temperatures.size() - 1);
			if (lastTemperature < BlockColdironFurnace.MAX_TEMPERATURE)
			{
				this.addSmeltingRecipe(entry1.getKey(), lastTemperature + 1, BlockColdironFurnace.MAX_TEMPERATURE,
						new ItemStack(ItemInit.INGOT_DIRTY), 0.0F);
			}
		}

		/*		Debug only
		System.out.println("--------------------------------------------------------------------------------------------");
		System.out.println("---------------------------------------SECOND-----------------------------------------------");
		System.out.println("--------------------------------------------------------------------------------------------");
		for (Entry<ItemStack, Map<Integer[], ItemStack>> entry1 : this.smeltingList.rowMap().entrySet())
		{
			System.out.println("New Entry");
			List<Integer> temperatures = new ArrayList<>();
			for (Entry<Integer[], ItemStack> entry2 : entry1.getValue().entrySet())
			{
				System.out.println(entry1.getKey().getItem().getUnlocalizedName() + " T° [" + entry2.getKey()[0] + "," + entry2.getKey()[1] + "] --> "
						+ entry2.getValue().getItem().getUnlocalizedName());

				temperatures.add(entry2.getKey()[0]);
				temperatures.add(entry2.getKey()[1]);
			}
			Collections.sort(temperatures);
		}
		*/
	}
}
