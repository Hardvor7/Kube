package com.kube.kubeMod.init;

import java.util.ArrayList;
import java.util.List;

import com.kube.kubeMod.blocks.ColdIronBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block BLOCK_COLDIRON = new ColdIronBlock("block_coldiron", Material.IRON);
}