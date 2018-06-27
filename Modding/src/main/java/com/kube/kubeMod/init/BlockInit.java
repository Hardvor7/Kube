package com.kube.kubeMod.init;

import java.util.ArrayList;
import java.util.List;

import com.kube.kubeMod.objects.blocks.ColdIronBlock;
import com.kube.kubeMod.objects.blocks.machines.coldironFurnace.BlockColdironFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block BLOCK_COLDIRON = new ColdIronBlock("block_coldiron", Material.IRON);
	

	public static final Block COLDIRON_FURNACE = new BlockColdironFurnace("coldiron_furnace", Material.IRON, false);
	public static final Block LIT_COLDIRON_FURNACE = new BlockColdironFurnace("lit_coldiron_furnace", Material.IRON, true).setCreativeTab(null);
}