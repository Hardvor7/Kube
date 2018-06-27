package com.kube.kubeMod.util.handler;

import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.ContainerColdironFurnace;
import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.GuiColdironFurnace;
import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.TileEntityColdironFurnace;
import com.kube.kubeMod.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == Reference.GUI_COLDIRON_FURNACE)
		{
			return new ContainerColdironFurnace(player.inventory,
					(TileEntityColdironFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == Reference.GUI_COLDIRON_FURNACE)
		{
			return new GuiColdironFurnace(player.inventory,
					(TileEntityColdironFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
