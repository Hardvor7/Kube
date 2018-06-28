package com.kube.kubeMod.objects.blocks.machines.coldironFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerColdironFurnace extends Container
{
	private final TileEntityColdironFurnace tileEntity;
	private int cookTime;
	private int totalCookTime;
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int temperature;
	private int temperatureIncreaseAmount;

	public ContainerColdironFurnace(InventoryPlayer playerInventory, TileEntityColdironFurnace tileEntity)
	{
		this.tileEntity = tileEntity;
		this.addSlotToContainer(new Slot(tileEntity, 0, 58, 35));
		this.addSlotToContainer(new SlotFurnaceFuel(tileEntity, 1, 15, 35));
		this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, tileEntity, 2, 116, 35));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileEntity);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.cookTime != this.tileEntity.getField(2))
			{
				icontainerlistener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
			}

			if (this.furnaceBurnTime != this.tileEntity.getField(0))
			{
				icontainerlistener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
			}

			if (this.currentItemBurnTime != this.tileEntity.getField(1))
			{
				icontainerlistener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
			}

			if (this.totalCookTime != this.tileEntity.getField(3))
			{
				icontainerlistener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
			}

			if (this.temperature != this.tileEntity.getField(4))
			{
				icontainerlistener.sendWindowProperty(this, 4, this.tileEntity.getField(4));
			}

			if (this.temperatureIncreaseAmount != this.tileEntity.getField(5))
			{
				icontainerlistener.sendWindowProperty(this, 5, this.tileEntity.getField(5));
			}
		}

		this.cookTime = this.tileEntity.getField(2);
		this.furnaceBurnTime = this.tileEntity.getField(0);
		this.currentItemBurnTime = this.tileEntity.getField(1);
		this.totalCookTime = this.tileEntity.getField(3);
		this.temperature = this.tileEntity.getField(4);
		this.temperatureIncreaseAmount = this.tileEntity.getField(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tileEntity.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tileEntity.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();

			if (index == 2)
			{
				if (!this.mergeItemStack(stack1, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(stack1, stack);
			}
			else if (index != 1 && index != 0)
			{
				if (ColdironFurnaceRecipes.getInstance().isInSmeltingList(stack1, 0, TileEntityColdironFurnace.MAX_TEMPERATURE))
				{
					if (!this.mergeItemStack(stack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityColdironFurnace.isItemFuel(stack1))
				{
					if (!this.mergeItemStack(stack1, 1, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 3 && index < 30)
				{
					if (!this.mergeItemStack(stack1, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 30 && index < 39 && !this.mergeItemStack(stack1, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(stack1, 3, 39, false))
			{
				return ItemStack.EMPTY;
			}

			if (stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (stack1.getCount() == stack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}
