package com.kube.kubeMod.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ColdIronBlock extends BlockBase
{
	public ColdIronBlock(String name, Material material)
	{
		super(name, material);
		
		setSoundType(SoundType.METAL);
		setHardness(5.0f);
		setResistance(10.0f);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(3.0f);
	}

}
