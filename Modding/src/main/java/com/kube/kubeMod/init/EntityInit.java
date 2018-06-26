package com.kube.kubeMod.init;

import com.kube.kubeMod.Main;
import com.kube.kubeMod.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
	public static void registerEntities()
	{
	}
	
	public static void registerEntity(String name, Class<? extends Entity> entityClass, int id, int trackingRange, int eggPrimary, int eggSecondary)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entityClass, name, id, Main.instance, trackingRange, 1, true, eggPrimary, eggSecondary);
	}
}
