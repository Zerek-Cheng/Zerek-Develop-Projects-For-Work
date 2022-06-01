//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pokelucky;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
        modid = "pokelucky",
        name = "Pokelucky",
        version = "3.2.8.1",
        acceptedMinecraftVersions = "[1.12.2]"
)
public class pokelucky {
    @Instance("pokelucky")
    public static pokelucky instance;
    @SidedProxy(
            serverSide = "pokelucky.CommonProxy",
            clientSide = "pokelucky.ClientProxy"
    )
    public static CommonProxy proxy;
    public static final CreativeTabs luckytab = new CreativeTabs("luckytab") {
        public ItemStack func_78016_d() {
            return new ItemStack(Register.blockrandmon);
        }
    };
    public static final ToolMaterial copperToolMaterial = EnumHelper.addToolMaterial("COPPER", 2, 500, 6.0F, 2.0F, 14);
    public static final ArmorMaterial copperArmorMaterial;

    public pokelucky() {
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerNetworkStuff();
        proxy.preLoad(event);
        Register.entity();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.load();
        Register.crafting();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    static {
        copperArmorMaterial = EnumHelper.addArmorMaterial("COPPER", "pokelucky:copper", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.field_187725_r, 0.0F);
    }

    @EventBusSubscriber
    public static class RegsitrationHandler {
        public RegsitrationHandler() {
        }

        @SubscribeEvent
        public static void registerItems(net.minecraftforge.event.RegistryEvent.Register<Item> event) {
            Register.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(net.minecraftforge.event.RegistryEvent.Register<Block> event) {
            Register.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            pokelucky.proxy.registerRenders();
        }
    }
}
