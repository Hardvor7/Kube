package com.kube.kubeMod.init;

import java.util.ArrayList;
import java.util.List;

import com.kube.kubeMod.items.ItemBase;

import net.minecraft.item.Item;

public class ItemInit 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();

	// Items
	public static final Item COLDIRON_INGOT = new ItemBase("ingot_coldiron");
}