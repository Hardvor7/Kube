package com.kube.kubeMod.init;

import java.util.ArrayList;
import java.util.List;

import com.kube.kubeMod.objects.armor.ArmorBase;
import com.kube.kubeMod.objects.items.ItemBase;
import com.kube.kubeMod.objects.tools.ToolAxe;
import com.kube.kubeMod.objects.tools.ToolHoe;
import com.kube.kubeMod.objects.tools.ToolPickaxe;
import com.kube.kubeMod.objects.tools.ToolShovel;
import com.kube.kubeMod.objects.tools.ToolSword;
import com.kube.kubeMod.util.Reference;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ItemInit
{
	public static final List<Item> ITEMS = new ArrayList<Item>();

	// Materials
	public static final ToolMaterial TOOL_COLDIRON = EnumHelper.addToolMaterial("tool_coldiron", 2, 250, 6.0F, 2.0F,
			14);
	public static final ArmorMaterial ARMOR_COLDIRON = EnumHelper.addArmorMaterial("armor_coldiron",
			Reference.MOD_ID + ":coldiron", 20, new int[] { 2, 6, 7, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);

	// Items
	public static final Item INGOT_COLDIRON = new ItemBase("ingot_coldiron");
	public static final Item INGOT_DIRTY = new ItemBase("ingot_dirty");

	// Tools
	public static final Item AXE_COLDIRON = new ToolAxe("axe_coldiron", TOOL_COLDIRON, 8.0F, -3.1F);
	public static final Item HOE_COLDIRON = new ToolHoe("hoe_coldiron", TOOL_COLDIRON);
	public static final Item PICKAXE_COLDIRON = new ToolPickaxe("pickaxe_coldiron", TOOL_COLDIRON);
	public static final Item SHOVEL_COLDIRON = new ToolShovel("shovel_coldiron", TOOL_COLDIRON);
	public static final Item SWORD_COLDIRON = new ToolSword("sword_coldiron", TOOL_COLDIRON);

	// Armors
	public static final Item HELMET_COLDIRON = new ArmorBase("helmet_coldiron", ARMOR_COLDIRON, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_COLDIRON = new ArmorBase("chestplate_coldiron", ARMOR_COLDIRON, 1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_COLDIRON = new ArmorBase("leggings_coldiron", ARMOR_COLDIRON, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_COLDIRON = new ArmorBase("boots_coldiron", ARMOR_COLDIRON, 1, EntityEquipmentSlot.FEET);

}
