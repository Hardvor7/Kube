package com.kube.kubeMod.objects.blocks.machines.coldironFurnace.slots;

import com.kube.kubeMod.init.BlockInit;
import com.kube.kubeMod.init.ItemInit;
import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.TileEntityColdironFurnace;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotColdironFurnaceFuel extends Slot
{

	public SlotColdironFurnaceFuel(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return TileEntityColdironFurnace.isItemFuel(stack) || isBucket(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	}

    public static boolean isBucket(ItemStack stack)
    {
        return stack.getItem() == Items.BUCKET;
    }
}
