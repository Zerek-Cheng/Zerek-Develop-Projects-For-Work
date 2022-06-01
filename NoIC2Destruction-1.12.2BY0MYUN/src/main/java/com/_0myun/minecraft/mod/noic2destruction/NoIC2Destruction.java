package com._0myun.minecraft.mod.noic2destruction;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = NoIC2Destruction.MODID, name = NoIC2Destruction.NAME, version = NoIC2Destruction.VERSION,
        dependencies = "required-after:ic2@2.8.109")
public class NoIC2Destruction {
    public static final String MODID = "noic2destruction";
    public static final String NAME = "NoIC2Destruction";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        System.out.println("【灵梦云】你灵梦云爸爸的工业放炸开始生效了!");
        //MinecraftForge.EVENT_BUS.register(new IEventHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
