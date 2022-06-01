package com._0myun.minecraft.mod.event.rpgitem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "_0mrpgitem";
    public static final String NAME = "灵梦云Rpg物品";
    public static final String VERSION = "1.0";
    public static final CreativeTabs rpgitemTab = new RpgitemTab();
    public static final CreativeTabs rpgitemArmorTab = new RpgitemArmotTab();
    private static Logger logger;
    public static final List<Item> items = new LinkedList<>(); //创建一个物品组

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SubscribeEvent
    public synchronized static void registerBlocks(RegistryEvent.Register<Item> event) {
        for (int i = 1; i <= 300; i++) {
            items.add(new Rpgitem("rpgitem" + String.valueOf(i)));
        }
        for (int i = 1; i <= 30; i++) {
            ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial("armor_material_" + String.valueOf(i), MODID + ":armor" + String.valueOf(i), 9999, new int[]{0, 0, 0, 0}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
            items.add(new RpgitemArmor("armor" + String.valueOf(i) + "_layer_1", material, EntityEquipmentSlot.HEAD));
            items.add(new RpgitemArmor("armor" + String.valueOf(i) + "_layer_2", material, EntityEquipmentSlot.CHEST));
            items.add(new RpgitemArmor("armor" + String.valueOf(i) + "_layer_3", material, EntityEquipmentSlot.LEGS));
            items.add(new RpgitemArmor("armor" + String.valueOf(i) + "_layer_4", material, EntityEquipmentSlot.FEET));
        }
        event.getRegistry().registerAll(items.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : items) { //for循环 注册items
            if (item instanceof Rpgitem) {
                ((Rpgitem) item).registerModels();
            }
            if (item instanceof RpgitemArmor) {
                ((RpgitemArmor) item).registerModels();
            }
        }
    }
}
