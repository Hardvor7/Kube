package com.kube.kubeMod;

import com.kube.kubeMod.proxy.CommonProxy;
import com.kube.kubeMod.tabs.KubeTab;
import com.kube.kubeMod.util.Reference;
import com.kube.kubeMod.util.handler.RegisteryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main 
{
	public static final CreativeTabs KUBE_TAB = new KubeTab();
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
    	RegisteryHandler.preInitRegisteries();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
    	RegisteryHandler.initRegisteries();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {
    	RegisteryHandler.postInitRegisteries();
    }
}
