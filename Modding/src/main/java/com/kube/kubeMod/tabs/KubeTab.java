package com.kube.kubeMod.tabs;

import com.kube.kubeMod.init.ItemInit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KubeTab extends CreativeTabs
{
	public KubeTab()
	{
		super("kube");
	}

	@Override
    @SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ItemInit.INGOT_COLDIRON);
	}
	
}
