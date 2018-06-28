package com.kube.kubeMod.util.handler;

import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.TileEntityColdironFurnace;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
	@SuppressWarnings("deprecation")
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityColdironFurnace.class, "coldiron_furnace");
	}
}
