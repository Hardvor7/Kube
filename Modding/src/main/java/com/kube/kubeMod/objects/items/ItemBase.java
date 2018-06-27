package com.kube.kubeMod.objects.items;

import com.kube.kubeMod.Main;
import com.kube.kubeMod.Tabs.Tabs;
import com.kube.kubeMod.init.ItemInit;
import com.kube.kubeMod.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel
{
	
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Tabs.tabKubeMod);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
