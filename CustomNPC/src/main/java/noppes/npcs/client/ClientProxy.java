package noppes.npcs.client;

import net.minecraft.client.settings.*;
import net.minecraft.client.renderer.entity.*;
import noppes.npcs.client.model.*;
import net.minecraft.client.renderer.tileentity.*;
import cpw.mods.fml.client.registry.*;
import noppes.npcs.blocks.tiles.*;
import noppes.npcs.client.renderer.blocks.*;
import net.minecraft.client.*;
import noppes.npcs.client.controllers.*;
import net.minecraftforge.common.*;
import tconstruct.client.tabs.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import noppes.npcs.constants.*;
import noppes.npcs.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.client.gui.*;
import noppes.npcs.client.gui.questtypes.*;
import noppes.npcs.client.gui.global.*;
import noppes.npcs.client.gui.mainmenu.*;
import noppes.npcs.client.gui.roles.*;
import noppes.npcs.client.gui.player.*;
import noppes.npcs.client.gui.*;
import noppes.npcs.containers.*;
import noppes.npcs.client.gui.player.companion.*;
import net.minecraft.entity.*;
import noppes.npcs.client.fx.*;
import java.util.*;
import net.minecraft.client.model.*;
import net.minecraft.item.*;
import noppes.npcs.client.renderer.*;
import net.minecraftforge.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import cpw.mods.fml.common.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import noppes.npcs.config.*;
import noppes.npcs.*;

public class ClientProxy extends CommonProxy
{
    public static KeyBinding QuestLog;
    public static FontContainer Font;
    private ModelSkirtArmor model;
    
    public ClientProxy() {
        this.model = new ModelSkirtArmor();
    }
    
    @Override
    public void load() {
        ClientProxy.Font = new FontContainer(CustomNpcs.FontType, CustomNpcs.FontSize);
        this.createFolders();
        CustomNpcs.Channel.register((Object)new PacketHandlerClient());
        CustomNpcs.ChannelPlayer.register((Object)new PacketHandlerPlayer());
        new MusicController();
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityNpcPony.class, (Render)new RenderNPCPony());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityNpcCrystal.class, (Render)new RenderNpcCrystal(new ModelNpcCrystal(0.5f)));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityNpcDragon.class, (Render)new RenderNpcDragon(new ModelNpcDragon(0.0f), 0.5f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityNpcSlime.class, (Render)new RenderNpcSlime(new ModelNpcSlime(16), new ModelNpcSlime(0), 0.25f));
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityProjectile.class, (Render)new RenderProjectile());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityCustomNpc.class, (Render)new RenderCustomNpc());
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityNPCGolem.class, (Render)new RenderNPCHumanMale(new ModelNPCGolem(0.0f), new ModelNPCGolem(1.0f), new ModelNPCGolem(0.5f)));
        FMLCommonHandler.instance().bus().register((Object)new ClientTickHandler());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileBlockAnvil.class, (TileEntitySpecialRenderer)new BlockCarpentryBenchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileMailbox.class, (TileEntitySpecialRenderer)new BlockMailboxRenderer());
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new BlockBorderRenderer());
        if (!CustomNpcs.DisableExtraBlock) {
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileBanner.class, (TileEntitySpecialRenderer)new BlockBannerRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileWallBanner.class, (TileEntitySpecialRenderer)new BlockWallBannerRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileTallLamp.class, (TileEntitySpecialRenderer)new BlockTallLampRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileChair.class, (TileEntitySpecialRenderer)new BlockChairRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileWeaponRack.class, (TileEntitySpecialRenderer)new BlockWeaponRackRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCrate.class, (TileEntitySpecialRenderer)new BlockCrateRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCouchWool.class, (TileEntitySpecialRenderer)new BlockCouchWoolRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCouchWood.class, (TileEntitySpecialRenderer)new BlockCouchWoodRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileTable.class, (TileEntitySpecialRenderer)new BlockTableRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCandle.class, (TileEntitySpecialRenderer)new BlockCandleRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileLamp.class, (TileEntitySpecialRenderer)new BlockLampRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileStool.class, (TileEntitySpecialRenderer)new BlockStoolRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileBigSign.class, (TileEntitySpecialRenderer)new BlockBigSignRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileBarrel.class, (TileEntitySpecialRenderer)new BlockBarrelRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCampfire.class, (TileEntitySpecialRenderer)new BlockCampfireRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileTombstone.class, (TileEntitySpecialRenderer)new BlockTombstoneRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileShelf.class, (TileEntitySpecialRenderer)new BlockShelfRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileSign.class, (TileEntitySpecialRenderer)new BlockSignRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileBeam.class, (TileEntitySpecialRenderer)new BlockBeamRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TileBook.class, (TileEntitySpecialRenderer)new BlockBookRenderer());
            ClientRegistry.bindTileEntitySpecialRenderer((Class)TilePedestal.class, (TileEntitySpecialRenderer)new BlockPedestalRenderer());
            RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new BlockBloodRenderer());
        }
        final Minecraft mc = Minecraft.getMinecraft();
        ClientRegistry.registerKeyBinding(ClientProxy.QuestLog = new KeyBinding("Quest Log", 38, "key.categories.gameplay"));
        mc.gameSettings.loadOptions();
        new PresetController(CustomNpcs.Dir);
        if (CustomNpcs.EnableUpdateChecker) {
            final VersionChecker checker = new VersionChecker();
            checker.start();
        }
        ClientCloneController.Instance = new ClientCloneController();
        MinecraftForge.EVENT_BUS.register((Object)new ClientEventHandler());
        if (CustomNpcs.InventoryGuiEnabled) {
            MinecraftForge.EVENT_BUS.register((Object)new TabRegistry());
            if (TabRegistry.getTabList().size() < 2) {
                TabRegistry.registerTab(new InventoryTabVanilla());
            }
            TabRegistry.registerTab(new InventoryTabFactions());
            TabRegistry.registerTab(new InventoryTabQuests());
        }
    }
    
    private void createFolders() {
        final File file = new File(CustomNpcs.Dir, "assets/customnpcs");
        if (!file.exists()) {
            file.mkdirs();
        }
        File check = new File(file, "sounds");
        if (!check.exists()) {
            check.mkdir();
        }
        final File json = new File(file, "sounds.json");
        if (!json.exists()) {
            try {
                json.createNewFile();
                final BufferedWriter writer = new BufferedWriter(new FileWriter(json));
                writer.write("{\n\n}");
                writer.close();
            }
            catch (IOException ex) {}
        }
        check = new File(file, "textures");
        if (!check.exists()) {
            check.mkdir();
        }
        final File cache = new File(check, "cache");
        if (!cache.exists()) {
            cache.mkdir();
        }
        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener((IResourceManagerReloadListener)new CustomNpcResourceListener());
    }
    
    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        if (ID > EnumGuiType.values().length) {
            return null;
        }
        final EnumGuiType gui = EnumGuiType.values()[ID];
        final EntityNPCInterface npc = NoppesUtil.getLastNpc();
        final Container container = this.getContainer(gui, player, x, y, z, npc);
        return this.getGui(npc, gui, container, x, y, z);
    }
    
    private GuiScreen getGui(final EntityNPCInterface npc, final EnumGuiType gui, final Container container, final int x, final int y, final int z) {
        if (gui == EnumGuiType.MainMenuDisplay) {
            if (npc != null) {
                return new GuiNpcDisplay(npc);
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Unable to find npc"));
        }
        else {
            if (gui == EnumGuiType.MainMenuStats) {
                return new GuiNpcStats(npc);
            }
            if (gui == EnumGuiType.MainMenuInv) {
                return (GuiScreen)new GuiNPCInv(npc, (ContainerNPCInv)container);
            }
            if (gui == EnumGuiType.MainMenuAdvanced) {
                return new GuiNpcAdvanced(npc);
            }
            if (gui == EnumGuiType.QuestReward) {
                return (GuiScreen)new GuiNpcQuestReward(npc, (ContainerNpcQuestReward)container);
            }
            if (gui == EnumGuiType.QuestItem) {
                return (GuiScreen)new GuiNpcQuestTypeItem(npc, (ContainerNpcQuestTypeItem)container);
            }
            if (gui == EnumGuiType.MovingPath) {
                return new GuiNpcPather(npc);
            }
            if (gui == EnumGuiType.ManageFactions) {
                return new GuiNPCManageFactions(npc);
            }
            if (gui == EnumGuiType.ManageLinked) {
                return new GuiNPCManageLinkedNpc(npc);
            }
            if (gui == EnumGuiType.ManageTransport) {
                return new GuiNPCManageTransporters(npc);
            }
            if (gui == EnumGuiType.ManageRecipes) {
                return (GuiScreen)new GuiNpcManageRecipes(npc, (ContainerManageRecipes)container);
            }
            if (gui == EnumGuiType.ManageDialogs) {
                return new GuiNPCManageDialogs(npc);
            }
            if (gui == EnumGuiType.ManageQuests) {
                return new GuiNPCManageQuest(npc);
            }
            if (gui == EnumGuiType.ManageBanks) {
                return (GuiScreen)new GuiNPCManageBanks(npc, (ContainerManageBanks)container);
            }
            if (gui == EnumGuiType.MainMenuGlobal) {
                return new GuiNPCGlobalMainMenu(npc);
            }
            if (gui == EnumGuiType.MainMenuAI) {
                return new GuiNpcAI(npc);
            }
            if (gui == EnumGuiType.PlayerFollowerHire) {
                return (GuiScreen)new GuiNpcFollowerHire(npc, (ContainerNPCFollowerHire)container);
            }
            if (gui == EnumGuiType.PlayerFollower) {
                return (GuiScreen)new GuiNpcFollower(npc, (ContainerNPCFollower)container);
            }
            if (gui == EnumGuiType.PlayerTrader) {
                return (GuiScreen)new GuiNPCTrader(npc, (ContainerNPCTrader)container);
            }
            if (gui == EnumGuiType.PlayerBankSmall || gui == EnumGuiType.PlayerBankUnlock || gui == EnumGuiType.PlayerBankUprade || gui == EnumGuiType.PlayerBankLarge) {
                return (GuiScreen)new GuiNPCBankChest(npc, (ContainerNPCBankInterface)container);
            }
            if (gui == EnumGuiType.PlayerTransporter) {
                return new GuiTransportSelection(npc);
            }
            if (gui == EnumGuiType.Script) {
                return new GuiScript(npc);
            }
            if (gui == EnumGuiType.PlayerAnvil) {
                return (GuiScreen)new GuiNpcCarpentryBench((ContainerCarpentryBench)container);
            }
            if (gui == EnumGuiType.SetupFollower) {
                return (GuiScreen)new GuiNpcFollowerSetup(npc, (ContainerNPCFollowerSetup)container);
            }
            if (gui == EnumGuiType.SetupItemGiver) {
                return (GuiScreen)new GuiNpcItemGiver(npc, (ContainerNpcItemGiver)container);
            }
            if (gui == EnumGuiType.SetupTrader) {
                return (GuiScreen)new GuiNpcTraderSetup(npc, (ContainerNPCTraderSetup)container);
            }
            if (gui == EnumGuiType.SetupTransporter) {
                return new GuiNpcTransporter(npc);
            }
            if (gui == EnumGuiType.SetupBank) {
                return new GuiNpcBankSetup(npc);
            }
            if (gui == EnumGuiType.NpcRemote && Minecraft.getMinecraft().currentScreen == null) {
                return new GuiNpcRemoteEditor();
            }
            if (gui == EnumGuiType.PlayerMailman) {
                return (GuiScreen)new GuiMailmanWrite((ContainerMail)container, x == 1, y == 1);
            }
            if (gui == EnumGuiType.PlayerMailbox) {
                return new GuiMailbox();
            }
            if (gui == EnumGuiType.MerchantAdd) {
                return (GuiScreen)new GuiMerchantAdd();
            }
            if (gui == EnumGuiType.Crate) {
                return (GuiScreen)new GuiCrate((ContainerCrate)container);
            }
            if (gui == EnumGuiType.NpcDimensions) {
                return new GuiNpcDimension();
            }
            if (gui == EnumGuiType.Border) {
                return new GuiBorderBlock(x, y, z);
            }
            if (gui == EnumGuiType.BigSign) {
                return new GuiBigSign(x, y, z);
            }
            if (gui == EnumGuiType.RedstoneBlock) {
                return new GuiNpcRedstoneBlock(x, y, z);
            }
            if (gui == EnumGuiType.MobSpawner) {
                return new GuiNpcMobSpawner(x, y, z);
            }
            if (gui == EnumGuiType.MobSpawnerMounter) {
                return new GuiNpcMobSpawnerMounter(x, y, z);
            }
            if (gui == EnumGuiType.Waypoint) {
                return new GuiNpcWaypoint(x, y, z);
            }
            if (gui == EnumGuiType.Companion) {
                return new GuiNpcCompanionStats(npc);
            }
            if (gui == EnumGuiType.CompanionTalent) {
                return new GuiNpcCompanionTalents(npc);
            }
            if (gui == EnumGuiType.CompanionInv) {
                return (GuiScreen)new GuiNpcCompanionInv(npc, (ContainerNPCCompanion)container);
            }
        }
        return null;
    }
    
    @Override
    public void openGui(final int i, final int j, final int k, final EnumGuiType gui, final EntityPlayer player) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft.thePlayer != player) {
            return;
        }
        final GuiScreen guiscreen = this.getGui(null, gui, null, i, j, k);
        if (guiscreen != null) {
            minecraft.displayGuiScreen(guiscreen);
        }
    }
    
    @Override
    public void openGui(final EntityNPCInterface npc, final EnumGuiType gui) {
        this.openGui(npc, gui, 0, 0, 0);
    }
    
    @Override
    public void openGui(final EntityNPCInterface npc, final EnumGuiType gui, final int x, final int y, final int z) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final Container container = this.getContainer(gui, (EntityPlayer)minecraft.thePlayer, x, y, z, npc);
        final GuiScreen guiscreen = this.getGui(npc, gui, container, x, y, z);
        if (guiscreen != null) {
            minecraft.displayGuiScreen(guiscreen);
        }
    }
    
    @Override
    public void openGui(final EntityPlayer player, final Object guiscreen) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        if (!player.worldObj.isRemote || !(guiscreen instanceof GuiScreen)) {
            return;
        }
        if (guiscreen != null) {
            minecraft.displayGuiScreen((GuiScreen)guiscreen);
        }
    }
    
    @Override
    public void spawnParticle(final EntityLivingBase player, final String string, final Object... ob) {
        if (string.equals("Spell")) {
            final int color = (int)ob[0];
            for (int number = (int)ob[1], i = 0; i < number; ++i) {
                final Random rand = player.worldObj.rand;
                final double x = (rand.nextDouble() - 0.5) * player.width;
                final double y = player.getEyeHeight();
                final double z = (rand.nextDouble() - 0.5) * player.width;
                final double f = (rand.nextDouble() - 0.5) * 2.0;
                final double f2 = -rand.nextDouble();
                final double f3 = (rand.nextDouble() - 0.5) * 2.0;
                Minecraft.getMinecraft().effectRenderer.addEffect((EntityFX)new EntityElementalStaffFX(player, x, y, z, f, f2, f3, color));
            }
        }
        else if (string.equals("ModelData")) {
            final ModelData data = (ModelData)ob[0];
            final ModelPartData particles = (ModelPartData)ob[1];
            final EntityCustomNpc npc = (EntityCustomNpc)player;
            final Minecraft minecraft = Minecraft.getMinecraft();
            final double height = npc.getYOffset() + data.getBodyY();
            final Random rand2 = npc.getRNG();
            if (particles.type == 0) {
                for (int j = 0; j < 2; ++j) {
                    final EntityEnderFX fx = new EntityEnderFX(npc, (rand2.nextDouble() - 0.5) * player.width, rand2.nextDouble() * player.height - height - 0.25, (rand2.nextDouble() - 0.5) * player.width, (rand2.nextDouble() - 0.5) * 2.0, -rand2.nextDouble(), (rand2.nextDouble() - 0.5) * 2.0, particles);
                    minecraft.effectRenderer.addEffect((EntityFX)fx);
                }
            }
            else if (particles.type == 1) {
                for (int j = 0; j < 2; ++j) {
                    final double x2 = player.posX + (rand2.nextDouble() - 0.5) * 0.9;
                    final double y2 = player.posY + rand2.nextDouble() * 1.9 - 0.25 - height;
                    final double z2 = player.posZ + (rand2.nextDouble() - 0.5) * 0.9;
                    final double f4 = (rand2.nextDouble() - 0.5) * 2.0;
                    final double f5 = -rand2.nextDouble();
                    final double f6 = (rand2.nextDouble() - 0.5) * 2.0;
                    minecraft.effectRenderer.addEffect((EntityFX)new EntityRainbowFX(player.worldObj, x2, y2, z2, f4, f5, f6));
                }
            }
        }
    }
    
    @Override
    public ModelBiped getSkirtModel() {
        return this.model;
    }
    
    @Override
    public boolean hasClient() {
        return true;
    }
    
    @Override
    public EntityPlayer getPlayer() {
        return (EntityPlayer)Minecraft.getMinecraft().thePlayer;
    }
    
    @Override
    public void registerItem(final Item item) {
        MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)new NpcItemRenderer());
    }
    
    public static void bindTexture(final ResourceLocation location) {
        try {
            if (location == null) {
                return;
            }
            final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            if (location != null) {
                texturemanager.bindTexture(location);
            }
        }
        catch (NullPointerException ex) {}
        catch (ReportedException ex2) {}
    }
    
    @Override
    public void spawnParticle(final String particle, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final float scale) {
        final RenderGlobal render = Minecraft.getMinecraft().renderGlobal;
        final EntityFX fx = render.doSpawnParticle(particle, x, y, z, motionX, motionY, motionZ);
        if (fx == null) {
            return;
        }
        if (particle.equals("flame")) {
            ObfuscationReflectionHelper.setPrivateValue((Class)EntityFlameFX.class, (Object)fx, (Object)scale, 0);
        }
        else if (particle.equals("smoke")) {
            ObfuscationReflectionHelper.setPrivateValue((Class)EntitySmokeFX.class, (Object)fx, (Object)scale, 0);
        }
    }
    
    public static class FontContainer
    {
        private StringCache textFont;
        public boolean useCustomFont;
        
        private FontContainer() {
            this.textFont = null;
            this.useCustomFont = true;
        }
        
        public FontContainer(final String fontType, final int fontSize) {
            this.textFont = null;
            this.useCustomFont = true;
            (this.textFont = new StringCache()).setDefaultFont("Arial", fontSize, true);
            this.useCustomFont = !fontType.equalsIgnoreCase("minecraft");
            try {
                if (!this.useCustomFont || fontType.isEmpty() || fontType.equalsIgnoreCase("default")) {
                    this.textFont.setCustomFont(new ResourceLocation("customnpcs", "OpenSans.ttf"), fontSize, true);
                }
                else {
                    this.textFont.setDefaultFont(fontType, fontSize, true);
                }
            }
            catch (Exception e) {
                LogWriter.info("Failed loading font so using Arial");
            }
        }
        
        public int height() {
            if (this.useCustomFont) {
                return this.textFont.fontHeight;
            }
            return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        }
        
        public int width(final String text) {
            if (this.useCustomFont) {
                return this.textFont.getStringWidth(text);
            }
            return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
        }
        
        public FontContainer copy() {
            final FontContainer font = new FontContainer();
            font.textFont = this.textFont;
            font.useCustomFont = this.useCustomFont;
            return font;
        }
        
        public void drawString(final String text, final int x, final int y, final int color) {
            if (this.useCustomFont) {
                this.textFont.renderString(text, x, y, color, true);
                this.textFont.renderString(text, x, y, color, false);
            }
            else {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, y, color);
            }
        }
        
        public String getName() {
            if (!this.useCustomFont) {
                return "Minecraft";
            }
            return this.textFont.usedFont().getFontName();
        }
    }
}
