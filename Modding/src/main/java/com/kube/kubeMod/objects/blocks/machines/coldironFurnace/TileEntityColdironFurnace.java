package com.kube.kubeMod.objects.blocks.machines.coldironFurnace;

import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.slots.SlotColdironFurnaceFuel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityColdironFurnace extends TileEntity implements IInventory, ITickable
{
	private static final int COLDIRON_FURNACE_COOK_TIME = 50;
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };

	/*
	 * Slot 0: Smelting Item Slot 1: Need to Smelt Item Slot 2: Output of the smelt
	 */
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	private String customName;

	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;
	private int temperature;
	private int temperatureIncreaseAmount;

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.coldiron_furnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}

	public void setCustomInventoryName(String customName)
	{
		this.customName = customName;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory()
	{
		return this.furnaceItemStacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.furnaceItemStacks)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return (ItemStack) this.furnaceItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = (ItemStack) this.furnaceItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.furnaceItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag)
		{
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.furnaceItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = (getItemBurnTime((ItemStack) this.furnaceItemStacks.get(1)))[0];
		this.temperature = compound.getInteger("Temperature");
		this.temperatureIncreaseAmount = (getItemBurnTime((ItemStack) this.furnaceItemStacks.get(1)))[1];

		if (compound.hasKey("CustomName", 8))
		{
			this.setCustomInventoryName(compound.getString("CustomName"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.burnTime);
		compound.setInteger("CookTime", (short) this.cookTime);
		compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
		compound.setFloat("Temperature", this.temperature);
		ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);

		if (this.hasCustomName())
			compound.setString("CustomName", this.customName);
		return compound;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isBurning()
	{
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory)
	{
		return inventory.getField(0) > 0;
	}

	public void update()
	{
		boolean flag = this.isBurning();
		boolean flag1 = false;

		if (this.isBurning())
		{
			this.burnTime--;
			this.temperature += (int) Math.round((double) BlockColdironFurnace.REAL_TEMPERATURE_RATIO * (double) temperatureIncreaseAmount / (double) this.currentBurnTime);
		}
		else
		{
			temperatureIncreaseAmount = 0;
		}

		if (!this.world.isRemote)
		{
			ItemStack stack = (ItemStack) this.furnaceItemStacks.get(1);

			if (this.isBurning() || !stack.isEmpty())
			{
				if (!this.isBurning())
				{
					int[] burnTimeAndAmount = getItemBurnTime(stack);
					this.burnTime = (int) burnTimeAndAmount[0];
					this.temperatureIncreaseAmount = burnTimeAndAmount[1];
					this.currentBurnTime = this.burnTime;

					flag1 = true;

					if (!stack.isEmpty())
					{
						Item item = stack.getItem();
						stack.shrink(1);

						if (stack.isEmpty())
						{
							ItemStack item1 = item.getContainerItem(stack);
							this.furnaceItemStacks.set(1, item1);
						}
					}
				}
			}
			if (flag != this.isBurning())
			{
				flag1 = true;
				BlockColdironFurnace.setState(this.isBurning(), this.world, this.pos);
			}

			if (this.canSmelt())
			{
				++this.cookTime;
				Math.max(0, this.temperature - BlockColdironFurnace.REAL_TEMPERATURE_RATIO);

				if (this.cookTime == this.totalCookTime)
				{
					this.cookTime = 0;
					this.totalCookTime = this.getCookTime((ItemStack) this.furnaceItemStacks.get(0));
					this.smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.cookTime = 0;
			}

		}

		if (flag1)
			this.markDirty();
	}

	public int getCookTime(ItemStack input)
	{
		return COLDIRON_FURNACE_COOK_TIME;
	}

	private boolean canSmelt()
	{
		if (((ItemStack) this.furnaceItemStacks.get(0)).isEmpty() || this.temperature <= 0)
		{
			return false;
		}
		else
		{
			ItemStack result = ColdironFurnaceRecipes.getInstance().getColdironSmeltingResult((ItemStack) this.furnaceItemStacks.get(0),
					BlockColdironFurnace.getRealTemperature(temperature));

			if (result.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack output = (ItemStack) this.furnaceItemStacks.get(2);
				if (output.isEmpty())
				{
					return true;
				}

				if (!output.isItemEqual(result))
				{
					return false;
				}

				int res = output.getCount() + result.getCount();
				return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
			}
		}
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack input = (ItemStack) this.furnaceItemStacks.get(0);
			ItemStack result = ColdironFurnaceRecipes.getInstance().getColdironSmeltingResult(input, BlockColdironFurnace.getRealTemperature(temperature));
			ItemStack output = (ItemStack) this.furnaceItemStacks.get(2);

			if (output.isEmpty())
			{
				this.furnaceItemStacks.set(2, result.copy());
			}
			else if (output.getItem() == result.getItem())
			{
				output.grow(result.getCount());
			}

			input.shrink(1);
		}
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the furnace
	 * burning, or 0 if the item isn't fuel
	 */
	public static int[] getItemBurnTime(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return new int[] { 0, 0 };
		}
		else
		{
			int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
			if (burnTime >= 0)
				return new int[] { burnTime, 10 };
			Item item = stack.getItem();

			if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
			{
				return new int[] { 1, 1 };
			}
			else if (item == Item.getItemFromBlock(Blocks.WOOL))
			{
				return new int[] { 1, 1 };
			}
			else if (item == Item.getItemFromBlock(Blocks.CARPET))
			{
				return new int[] { 1, 1 };
			}
			else if (item == Item.getItemFromBlock(Blocks.LADDER))
			{
				return new int[] { 1, 1 };
			}
			else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
			{
				return new int[] { 1, 1 };
			}
			else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
			{
				return new int[] { 1, 1 };
			}
			else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
			{
				return new int[] { 1200, 270 };
			}
			else if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName()))
			{
				return new int[] { 1, 1 };
			}
			else if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName()))
			{
				return new int[] { 1, 1 };
			}
			else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName()))
			{
				return new int[] { 1, 1 };
			}
			else if (item == Items.STICK)
			{
				return new int[] { 1, 1 };
			}
			else if (item != Items.BOW && item != Items.FISHING_ROD)
			{
				if (item == Items.SIGN)
				{
					return new int[] { 1, 1 };
				}
				else if (item == Items.COAL)
				{
					return new int[] { 160, 90 };
				}
				else if (item == Items.LAVA_BUCKET)
				{
					return new int[] { 3000, 1000 };
				}
				else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL)
				{
					if (item == Items.BLAZE_ROD)
					{
						return new int[] { 300, 200 };
					}
					else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
					{
						return new int[] { 1, 1 };
					}
					else
					{
						return item instanceof ItemBoat ? new int[] { 1, 1 } : new int[] { 0, 0 };
					}
				}
				else
				{
					return new int[] { 1, 1 };
				}
			}
			else
			{
				return new int[] { 1, 1 };
			}
		}
	}

	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemBurnTime(fuel)[0] > 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false
				: player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{

		if (index == 2)
		{
			return false;
		}
		else if (index != 1)
		{
			return true;
		}
		else
		{
			ItemStack itemstack = this.furnaceItemStacks.get(1);
			return isItemFuel(stack) || SlotColdironFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
		}
	}

	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.DOWN)
		{
			return SLOTS_BOTTOM;
		}
		else
		{
			return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
		}
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from
	 * the given side.
	 */
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from
	 * the given side.
	 */
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (direction == EnumFacing.DOWN && index == 1)
		{
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET)
			{
				return false;
			}
		}

		return true;
	}

	public String getGuiID()
	{
		return "km:cold_iron_furnace";
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		case 4:
			return this.temperature;
		case 5:
			return this.temperatureIncreaseAmount;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
			break;
		case 4:
			this.temperature = value;
			break;
		case 5:
			this.temperatureIncreaseAmount = value;
			break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 6;
	}

	@Override
	public void clear()
	{
		this.furnaceItemStacks.clear();
	}
}