package noppes.npcs;

import java.io.*;
import noppes.npcs.config.*;
import net.minecraft.server.*;
import net.minecraft.client.*;
import noppes.npcs.enchants.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.*;
import noppes.npcs.entity.old.*;
import noppes.npcs.entity.*;
import net.minecraftforge.common.*;
import net.minecraft.block.*;
import java.util.*;
import noppes.npcs.controllers.*;
import cpw.mods.fml.common.event.*;
import foxz.command.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;
import cpw.mods.fml.common.registry.*;

@Mod(modid = "customnpcs", name = "CustomNpcs", version = "1.7.10d")
public class CustomNpcs
{
    @ConfigProp(info = "Disable Chat Bubbles")
    public static boolean EnableChatBubbles;
    private static int NewEntityStartId;
    @ConfigProp(info = "Navigation search range for NPCs. Not recommended to increase if you have a slow pc or on a server")
    public static int NpcNavRange;
    @ConfigProp(info = "Set to true if you want the dialog command option to be able to use op commands like tp etc")
    public static boolean NpcUseOpCommands;
    @ConfigProp
    public static boolean InventoryGuiEnabled;
    @ConfigProp
    public static boolean DisableExtraItems;
    @ConfigProp
    public static boolean DisableExtraBlock;
    public static long ticks;
    @SidedProxy(clientSide = "noppes.npcs.client.ClientProxy", serverSide = "noppes.npcs.CommonProxy")
    public static CommonProxy proxy;
    @ConfigProp(info = "Enables CustomNpcs startup update message")
    public static boolean EnableUpdateChecker;
    public static CustomNpcs instance;
    public static boolean FreezeNPCs;
    @ConfigProp(info = "Only ops can create and edit npcs")
    public static boolean OpsOnly;
    @ConfigProp(info = "Default interact line. Leave empty to not have one")
    public static String DefaultInteractLine;
    @ConfigProp
    public static boolean DisableEnchants;
    @ConfigProp(info = "Start Id for enchants. IDs can only range from 0-256")
    public static int EnchantStartId;
    @ConfigProp(info = "Number of chunk loading npcs that can be active at the same time")
    public static int ChuckLoaders;
    public static File Dir;
    @ConfigProp(info = "Set to false if you want to disable guns")
    public static boolean GunsEnabled;
    @ConfigProp(info = "Enables leaves decay")
    public static boolean LeavesDecayEnabled;
    @ConfigProp(info = "Enables Vine Growth")
    public static boolean VineGrowthEnabled;
    @ConfigProp(info = "Enables Ice Melting")
    public static boolean IceMeltsEnabled;
    @ConfigProp(info = "Normal players can use soulstone on animals")
    public static boolean SoulStoneAnimals;
    @ConfigProp(info = "When set to Minecraft it will use minecrafts font, when Default it will use OpenSans. Can only use fonts installed on your PC")
    public static String FontType;
    @ConfigProp(info = "Font size for custom fonts (doesn't work with minecrafts font)")
    public static int FontSize;
    public static FMLEventChannel Channel;
    public static FMLEventChannel ChannelPlayer;
    public static ConfigLoader Config;
    
    public CustomNpcs() {
        CustomNpcs.instance = this;
    }
    
    @Mod.EventHandler
    public void load(final FMLPreInitializationEvent ev) {
        CustomNpcs.Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("CustomNPCs");
        CustomNpcs.ChannelPlayer = NetworkRegistry.INSTANCE.newEventDrivenChannel("CustomNPCsPlayer");
        final MinecraftServer server = MinecraftServer.getServer();
        String dir = "";
        if (server != null) {
            dir = new File(".").getAbsolutePath();
        }
        else {
            dir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
        }
        (CustomNpcs.Dir = new File(dir, "customnpcs")).mkdir();
        (CustomNpcs.Config = new ConfigLoader(this.getClass(), new File(dir, "config"), "CustomNpcs")).loadConfig();
        if (CustomNpcs.NpcNavRange < 16) {
            CustomNpcs.NpcNavRange = 16;
        }
        EnchantInterface.load();
        CustomItems.load();
        CustomNpcs.proxy.load();
        NetworkRegistry.INSTANCE.registerGuiHandler((Object)this, (IGuiHandler)CustomNpcs.proxy);
        MinecraftForge.EVENT_BUS.register((Object)new ServerEventsHandler());
        MinecraftForge.EVENT_BUS.register((Object)new ScriptController());
        FMLCommonHandler.instance().bus().register((Object)new ServerTickHandler());
        this.registerNpc((Class<? extends Entity>)EntityNPCHumanMale.class, "npchumanmale");
        this.registerNpc((Class<? extends Entity>)EntityNPCVillager.class, "npcvillager");
        this.registerNpc((Class<? extends Entity>)EntityNpcPony.class, "npcpony");
        this.registerNpc((Class<? extends Entity>)EntityNPCHumanFemale.class, "npchumanfemale");
        this.registerNpc((Class<? extends Entity>)EntityNPCDwarfMale.class, "npcdwarfmale");
        this.registerNpc((Class<? extends Entity>)EntityNPCFurryMale.class, "npcfurrymale");
        this.registerNpc((Class<? extends Entity>)EntityNpcMonsterMale.class, "npczombiemale");
        this.registerNpc((Class<? extends Entity>)EntityNpcMonsterFemale.class, "npczombiefemale");
        this.registerNpc((Class<? extends Entity>)EntityNpcSkeleton.class, "npcskeleton");
        this.registerNpc((Class<? extends Entity>)EntityNPCDwarfFemale.class, "npcdwarffemale");
        this.registerNpc((Class<? extends Entity>)EntityNPCFurryFemale.class, "npcfurryfemale");
        this.registerNpc((Class<? extends Entity>)EntityNPCOrcMale.class, "npcorcfmale");
        this.registerNpc((Class<? extends Entity>)EntityNPCOrcFemale.class, "npcorcfemale");
        this.registerNpc((Class<? extends Entity>)EntityNPCElfMale.class, "npcelfmale");
        this.registerNpc((Class<? extends Entity>)EntityNPCElfFemale.class, "npcelffemale");
        this.registerNpc((Class<? extends Entity>)EntityNpcCrystal.class, "npccrystal");
        this.registerNpc((Class<? extends Entity>)EntityNpcEnderchibi.class, "npcenderchibi");
        this.registerNpc((Class<? extends Entity>)EntityNpcNagaMale.class, "npcnagamale");
        this.registerNpc((Class<? extends Entity>)EntityNpcNagaFemale.class, "npcnagafemale");
        this.registerNpc((Class<? extends Entity>)EntityNpcSlime.class, "NpcSlime");
        this.registerNpc((Class<? extends Entity>)EntityNpcDragon.class, "NpcDragon");
        this.registerNpc((Class<? extends Entity>)EntityNPCEnderman.class, "npcEnderman");
        this.registerNpc((Class<? extends Entity>)EntityNPCGolem.class, "npcGolem");
        this.registerNpc((Class<? extends Entity>)EntityCustomNpc.class, "CustomNpc");
        this.registerNewEntity(EntityChairMount.class, "CustomNpcChairMount", 64, 10, false);
        this.registerNewEntity((Class<? extends Entity>)EntityProjectile.class, "throwableitem", 64, 3, true);
        new RecipeController();
        ForgeChunkManager.setForcedChunkLoadingCallback((Object)this, (ForgeChunkManager.LoadingCallback)new ChunkController());
        new CustomNpcsPermissions();
        PixelmonHelper.load();
    }
    
    @Mod.EventHandler
    public void setAboutToStart(final FMLServerAboutToStartEvent event) {
        ChunkController.instance.clear();
        new QuestController();
        new PlayerDataController();
        new FactionController();
        new TransportController();
        new GlobalDataController();
        new SpawnController();
        new LinkedNpcController();
        ScriptController.Instance.loadStoredData();
        ScriptController.HasStart = false;
        final Set<String> names = (Set<String>)Block.blockRegistry.getKeys();
        for (final String name : names) {
            final Block block = (Block)Block.blockRegistry.getObject(name);
            if (block instanceof BlockLeavesBase) {
                block.setTickRandomly(CustomNpcs.LeavesDecayEnabled);
            }
            if (block instanceof BlockVine) {
                block.setTickRandomly(CustomNpcs.VineGrowthEnabled);
            }
            if (block instanceof BlockIce) {
                block.setTickRandomly(CustomNpcs.IceMeltsEnabled);
            }
        }
    }
    
    @Mod.EventHandler
    public void started(final FMLServerStartedEvent event) {
        RecipeController.instance.load();
        new DialogController();
        new BankController();
        QuestController.instance.load();
        ScriptController.HasStart = true;
        ServerCloneController.Instance = new ServerCloneController();
    }
    
    @Mod.EventHandler
    public void stopped(final FMLServerStoppedEvent event) {
        ServerCloneController.Instance = null;
    }
    
    @Mod.EventHandler
    public void serverstart(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new CommandNoppes());
    }
    
    private void registerNpc(final Class<? extends Entity> cl, final String name) {
        EntityList.stringToClassMapping.put(name, cl);
        EntityRegistry.registerModEntity((Class)cl, name, CustomNpcs.NewEntityStartId++, (Object)this, 64, 3, true);
    }
    
    private void registerNewEntity(final Class<? extends Entity> cl, final String name, final int range, final int update, final boolean velocity) {
        EntityRegistry.registerModEntity((Class)cl, name, CustomNpcs.NewEntityStartId++, (Object)this, range, update, velocity);
    }
    
    public static File getWorldSaveDirectory() {
        final MinecraftServer server = MinecraftServer.getServer();
        File saves = new File(".");
        if (server != null && !server.isDedicatedServer()) {
            saves = new File(Minecraft.getMinecraft().mcDataDir, "saves");
        }
        if (server != null) {
            final File savedir = new File(new File(saves, server.getFolderName()), "customnpcs");
            if (!savedir.exists()) {
                savedir.mkdir();
            }
            return savedir;
        }
        return null;
    }
    
    static {
        CustomNpcs.EnableChatBubbles = true;
        CustomNpcs.NewEntityStartId = 0;
        CustomNpcs.NpcNavRange = 32;
        CustomNpcs.NpcUseOpCommands = false;
        CustomNpcs.InventoryGuiEnabled = true;
        CustomNpcs.DisableExtraItems = false;
        CustomNpcs.DisableExtraBlock = false;
        CustomNpcs.EnableUpdateChecker = true;
        CustomNpcs.FreezeNPCs = false;
        CustomNpcs.OpsOnly = false;
        CustomNpcs.DefaultInteractLine = "Hello @p";
        CustomNpcs.DisableEnchants = false;
        CustomNpcs.EnchantStartId = 100;
        CustomNpcs.ChuckLoaders = 20;
        CustomNpcs.GunsEnabled = true;
        CustomNpcs.LeavesDecayEnabled = true;
        CustomNpcs.VineGrowthEnabled = true;
        CustomNpcs.IceMeltsEnabled = true;
        CustomNpcs.SoulStoneAnimals = true;
        CustomNpcs.FontType = "Default";
        CustomNpcs.FontSize = 18;
    }
}
