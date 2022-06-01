package noppes.npcs;

import net.minecraft.entity.passive.*;
import noppes.npcs.entity.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.village.*;
import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import noppes.npcs.blocks.tiles.*;
import net.minecraft.block.*;
import net.minecraftforge.event.entity.living.*;
import noppes.npcs.roles.*;
import net.minecraft.util.*;
import noppes.npcs.items.*;
import net.minecraft.entity.*;
import noppes.npcs.constants.*;
import noppes.npcs.quests.*;
import noppes.npcs.controllers.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.terraingen.*;

public class ServerEventsHandler
{
    public static EntityVillager Merchant;
    public static Entity mounted;
    
    @SubscribeEvent
    public void invoke(final EntityInteractEvent event) {
        final ItemStack item = event.entityPlayer.getCurrentEquippedItem();
        if (item == null) {
            return;
        }
        final boolean isRemote = event.entityPlayer.worldObj.isRemote;
        final boolean npcInteracted = event.target instanceof EntityNPCInterface;
        if (!isRemote && CustomNpcs.OpsOnly && !MinecraftServer.getServer().getConfigurationManager().canSendCommands(event.entityPlayer.getGameProfile())) {
            return;
        }
        if (!isRemote && item.getItem() == CustomItems.soulstoneEmpty && event.target instanceof EntityLivingBase) {
            ((ItemSoulstoneEmpty)item.getItem()).store((EntityLivingBase)event.target, item, event.entityPlayer);
        }
        if (item.getItem() == CustomItems.wand && npcInteracted && !isRemote) {
            final CustomNpcsPermissions instance = CustomNpcsPermissions.Instance;
            if (!CustomNpcsPermissions.hasPermission(event.entityPlayer, CustomNpcsPermissions.NPC_GUI)) {
                return;
            }
            event.setCanceled(true);
            NoppesUtilServer.sendOpenGui(event.entityPlayer, EnumGuiType.MainMenuDisplay, (EntityNPCInterface)event.target);
        }
        else if (item.getItem() == CustomItems.cloner && !isRemote && !(event.target instanceof EntityPlayer)) {
            final NBTTagCompound compound = new NBTTagCompound();
            if (!event.target.writeToNBTOptional(compound)) {
                return;
            }
            final PlayerData data = PlayerDataController.instance.getPlayerData(event.entityPlayer);
            ServerCloneController.Instance.cleanTags(compound);
            if (!Server.sendData((EntityPlayerMP)event.entityPlayer, EnumPacketClient.CLONE, compound)) {
                event.entityPlayer.addChatMessage((IChatComponent)new ChatComponentText("Entity too big to clone"));
            }
            data.cloned = compound;
            event.setCanceled(true);
        }
        else if (item.getItem() == CustomItems.scripter && !isRemote && npcInteracted) {
            final CustomNpcsPermissions instance2 = CustomNpcsPermissions.Instance;
            if (!CustomNpcsPermissions.hasPermission(event.entityPlayer, CustomNpcsPermissions.NPC_GUI)) {
                return;
            }
            NoppesUtilServer.setEditingNpc(event.entityPlayer, (EntityNPCInterface)event.target);
            event.setCanceled(true);
            Server.sendData((EntityPlayerMP)event.entityPlayer, EnumPacketClient.GUI, EnumGuiType.Script.ordinal());
        }
        else if (item.getItem() == CustomItems.mount) {
            final CustomNpcsPermissions instance3 = CustomNpcsPermissions.Instance;
            if (!CustomNpcsPermissions.hasPermission(event.entityPlayer, CustomNpcsPermissions.TOOL_MOUNTER)) {
                return;
            }
            event.setCanceled(true);
            ServerEventsHandler.mounted = event.target;
            if (isRemote) {
                CustomNpcs.proxy.openGui(MathHelper.floor_double(ServerEventsHandler.mounted.posX), MathHelper.floor_double(ServerEventsHandler.mounted.posY), MathHelper.floor_double(ServerEventsHandler.mounted.posZ), EnumGuiType.MobSpawnerMounter, event.entityPlayer);
            }
        }
        else if (item.getItem() == CustomItems.wand && event.target instanceof EntityVillager) {
            final CustomNpcsPermissions instance4 = CustomNpcsPermissions.Instance;
            if (!CustomNpcsPermissions.hasPermission(event.entityPlayer, CustomNpcsPermissions.EDIT_VILLAGER)) {
                return;
            }
            event.setCanceled(true);
            ServerEventsHandler.Merchant = (EntityVillager)event.target;
            if (!isRemote) {
                final EntityPlayerMP player = (EntityPlayerMP)event.entityPlayer;
                player.openGui((Object)CustomNpcs.instance, EnumGuiType.MerchantAdd.ordinal(), player.worldObj, 0, 0, 0);
                final MerchantRecipeList merchantrecipelist = ServerEventsHandler.Merchant.getRecipes((EntityPlayer)player);
                if (merchantrecipelist != null) {
                    Server.sendData(player, EnumPacketClient.VILLAGER_LIST, merchantrecipelist);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void invoke(final LivingHurtEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)event.entityLiving;
        if (event.source.isUnblockable() || event.source.isFireDamage()) {
            return;
        }
        if (!player.isBlocking()) {
            return;
        }
        final ItemStack item = player.getCurrentEquippedItem();
        if (item == null || !(item.getItem() instanceof ItemShield)) {
            return;
        }
        if (((ItemShield)item.getItem()).material.getDamageVsEntity() < player.getRNG().nextInt(9)) {
            return;
        }
        final float damage = item.getMetadata() + event.ammount;
        item.damageItem((int)event.ammount, (EntityLivingBase)player);
        if (damage > item.getMaxDurability()) {
            event.ammount = damage - item.getMaxDurability();
        }
        else {
            event.ammount = 0.0f;
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void invoke(final PlayerInteractEvent event) {
        final EntityPlayer player = event.entityPlayer;
        final Block block = player.worldObj.getBlock(event.x, event.y, event.z);
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && player.getHeldItem() != null && player.getHeldItem().getItem() == CustomItems.teleporter) {
            event.setCanceled(true);
        }
        if (block == Blocks.crafting_table && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !player.worldObj.isRemote) {
            final RecipeController controller = RecipeController.instance;
            NBTTagList list = new NBTTagList();
            int i = 0;
            for (final RecipeCarpentry recipe : controller.globalRecipes.values()) {
                list.appendTag((NBTBase)recipe.writeNBT());
                if (++i % 10 == 0) {
                    final NBTTagCompound compound = new NBTTagCompound();
                    compound.setTag("recipes", (NBTBase)list);
                    Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, compound);
                    list = new NBTTagList();
                }
            }
            if (i % 10 != 0) {
                final NBTTagCompound compound2 = new NBTTagCompound();
                compound2.setTag("recipes", (NBTBase)list);
                Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, compound2);
            }
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_WORKBENCH, new Object[0]);
        }
        if (block == CustomItems.carpentyBench && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && !player.worldObj.isRemote) {
            final RecipeController controller = RecipeController.instance;
            NBTTagList list = new NBTTagList();
            int i = 0;
            for (final RecipeCarpentry recipe : controller.anvilRecipes.values()) {
                list.appendTag((NBTBase)recipe.writeNBT());
                if (++i % 10 == 0) {
                    final NBTTagCompound compound = new NBTTagCompound();
                    compound.setTag("recipes", (NBTBase)list);
                    Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, compound);
                    list = new NBTTagList();
                }
            }
            if (i % 10 != 0) {
                final NBTTagCompound compound2 = new NBTTagCompound();
                compound2.setTag("recipes", (NBTBase)list);
                Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_ADD, compound2);
            }
            Server.sendData((EntityPlayerMP)player, EnumPacketClient.SYNCRECIPES_CARPENTRYBENCH, new Object[0]);
        }
        if ((block == CustomItems.banner || block == CustomItems.wallBanner || block == CustomItems.sign) && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            final ItemStack item = player.inventory.getCurrentItem();
            if (item == null || item.getItem() == null) {
                return;
            }
            int y = event.y;
            final int meta = player.worldObj.getBlockMetadata(event.x, event.y, event.z);
            if (meta >= 7) {
                --y;
            }
            final TileBanner tile = (TileBanner)player.worldObj.getTileEntity(event.x, y, event.z);
            if (!tile.canEdit()) {
                if (item.getItem() == CustomItems.wand && CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.EDIT_BLOCKS)) {
                    tile.time = System.currentTimeMillis();
                    if (player.worldObj.isRemote) {
                        player.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("availability.editIcon", new Object[0]));
                    }
                }
                return;
            }
            if (!player.worldObj.isRemote) {
                tile.icon = item.copy();
                player.worldObj.markBlockForUpdate(event.x, y, event.z);
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void invoke(final LivingDeathEvent event) {
        if (event.entityLiving.worldObj.isRemote) {
            return;
        }
        if (event.source.getEntity() != null) {
            if (event.source.getEntity() instanceof EntityPlayer) {
                this.doExcalibur((EntityPlayer)event.source.getEntity(), event.entityLiving);
            }
            if (event.source.getEntity() instanceof EntityNPCInterface && event.entityLiving != null) {
                final EntityNPCInterface npc = (EntityNPCInterface)event.source.getEntity();
                final Line line = npc.advanced.getKillLine();
                if (line != null) {
                    npc.saySurrounding(line.formatTarget(event.entityLiving));
                }
                npc.script.callScript(EnumScriptType.KILLS, "target", event.entityLiving);
            }
            EntityPlayer player = null;
            if (event.source.getEntity() instanceof EntityPlayer) {
                player = (EntityPlayer)event.source.getEntity();
            }
            else if (event.source.getEntity() instanceof EntityNPCInterface && ((EntityNPCInterface)event.source.getEntity()).advanced.role == EnumRoleType.Follower) {
                player = ((RoleFollower)((EntityNPCInterface)event.source.getEntity()).roleInterface).owner;
            }
            if (player != null) {
                this.doQuest(player, event.entityLiving, true);
                if (event.entityLiving instanceof EntityNPCInterface) {
                    this.doFactionPoints(player, (EntityNPCInterface)event.entityLiving);
                }
            }
        }
        if (event.entityLiving instanceof EntityPlayer) {
            final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)event.entityLiving);
            data.saveNBTData(null);
        }
    }
    
    private void doExcalibur(final EntityPlayer player, final EntityLivingBase entity) {
        final ItemStack item = player.getCurrentEquippedItem();
        if (item == null || item.getItem() != CustomItems.excalibur) {
            return;
        }
        Server.sendData((EntityPlayerMP)player, EnumPacketClient.PLAY_MUSIC, "customnpcs:songs.excalibur");
        player.addChatMessage((IChatComponent)new ChatComponentTranslation("<" + StatCollector.translateToLocal(item.getItem().getUnlocalizedName() + ".name") + "> " + ItemExcalibur.quotes[player.getRNG().nextInt(ItemExcalibur.quotes.length)], new Object[0]));
    }
    
    private void doFactionPoints(final EntityPlayer player, final EntityNPCInterface npc) {
        npc.advanced.factions.addPoints(player);
    }
    
    private void doQuest(final EntityPlayer player, final EntityLivingBase entity, final boolean all) {
        final PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
        boolean change = false;
        final String entityName = EntityList.getEntityString((Entity)entity);
        for (final QuestData data : playerdata.activeQuests.values()) {
            if (data.quest.type != EnumQuestType.Kill && data.quest.type != EnumQuestType.AreaKill) {
                continue;
            }
            if (data.quest.type == EnumQuestType.AreaKill && all) {
                final List<EntityPlayer> list = (List<EntityPlayer>)player.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, entity.boundingBox.expand(10.0, 10.0, 10.0));
                for (final EntityPlayer pl : list) {
                    if (pl != player) {
                        this.doQuest(pl, entity, false);
                    }
                }
            }
            String name = entityName;
            final QuestKill quest = (QuestKill)data.quest.questInterface;
            if (quest.targets.containsKey(entity.getCommandSenderName())) {
                name = entity.getCommandSenderName();
            }
            else if (!quest.targets.containsKey(name)) {
                continue;
            }
            final HashMap<String, Integer> killed = quest.getKilled(data);
            if (killed.containsKey(name) && killed.get(name) >= quest.targets.get(name)) {
                continue;
            }
            int amount = 0;
            if (killed.containsKey(name)) {
                amount = killed.get(name);
            }
            killed.put(name, amount + 1);
            quest.setKilled(data, killed);
            change = true;
        }
        if (!change) {
            return;
        }
        playerdata.checkQuestCompletion(player, EnumQuestType.Kill);
    }
    
    @SubscribeEvent
    public void pickUp(final EntityItemPickupEvent event) {
        if (event.entityPlayer.worldObj.isRemote) {
            return;
        }
        final PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(event.entityPlayer).questData;
        playerdata.checkQuestCompletion(event.entityPlayer, EnumQuestType.Item);
    }
    
    @SubscribeEvent
    public void world(final EntityJoinWorldEvent event) {
        if (event.world.isRemote || !(event.entity instanceof EntityPlayer)) {
            return;
        }
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)event.entity);
        data.updateCompanion(event.world);
    }
    
    @SubscribeEvent
    public void populateChunk(final PopulateChunkEvent.Post event) {
        NPCSpawning.performWorldGenSpawning(event.world, event.chunkX, event.chunkZ, event.rand);
    }
}
