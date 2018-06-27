package com.kube.kubeMod.util.handler;

import com.kube.kubeMod.Main;
import com.kube.kubeMod.init.BlockInit;
import com.kube.kubeMod.init.EntityInit;
import com.kube.kubeMod.init.ItemInit;
import com.kube.kubeMod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class RegisteryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for (Item item : ItemInit.ITEMS)
		{
			if (item instanceof IHasModel)
				((IHasModel)item).registerModels();
		}

		for (Block block : BlockInit.BLOCKS)
		{
			if (block instanceof IHasModel)
				((IHasModel)block).registerModels();
		}
	}
	
	public static void preInitRegisteries()
	{
		EntityInit.registerEntities();
		RenderHandler.registerEntityRenderers();
	}
	
	public static void initRegisteries()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}

	public static void postInitRegisteries()
	{
	}
}
