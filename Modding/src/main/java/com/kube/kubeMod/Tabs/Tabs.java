package com.kube.kubeMod.Tabs;

import com.kube.kubeMod.init.ItemInit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Tabs {

	public static final CreativeTabs tabKubeMod = new CreativeTabs("tabKubeMod") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ItemInit.INGOT_COLDIRON);
		}
		
		@Override
		public boolean hasSearchBar() {
			return false;
		}
	}.setBackgroundImageName("item_search.png");
}
