package noppes.npcs.scripted;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import noppes.npcs.util.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import noppes.npcs.*;
import noppes.npcs.controllers.*;

public class ScriptPlayer extends ScriptLivingBase
{
    protected EntityPlayerMP player;
    
    public ScriptPlayer(final EntityPlayerMP player) {
        super((EntityLivingBase)player);
        this.player = player;
    }
    
    public String getDisplayName() {
        return this.player.getDisplayName();
    }
    
    public String getName() {
        return this.player.getCommandSenderName();
    }
    
    @Override
    public void setPosition(final double x, final double y, final double z) {
        NoppesUtilPlayer.teleportPlayer(this.player, x, y, z, this.player.dimension);
    }
    
    public boolean hasFinishedQuest(final int id) {
        final PlayerQuestData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player).questData;
        return data.finishedQuests.containsKey(id);
    }
    
    public boolean hasActiveQuest(final int id) {
        final PlayerQuestData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player).questData;
        return data.activeQuests.containsKey(id);
    }
    
    public boolean hasReadDialog(final int id) {
        final PlayerDialogData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player).dialogData;
        return data.dialogsRead.contains(id);
    }
    
    public void startQuest(final int id) {
        final Quest quest = QuestController.instance.quests.get(id);
        if (quest == null) {
            return;
        }
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player);
        if (data.questData.activeQuests.containsKey(id)) {
            return;
        }
        final QuestData questdata = new QuestData(quest);
        data.questData.activeQuests.put(id, questdata);
        Server.sendData(this.player, EnumPacketClient.MESSAGE, "quest.newquest", quest.title);
        Server.sendData(this.player, EnumPacketClient.CHAT, "quest.newquest", ": ", quest.title);
    }
    
    public void finishQuest(final int id) {
        final Quest quest = QuestController.instance.quests.get(id);
        if (quest == null) {
            return;
        }
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player);
        data.questData.finishedQuests.put(id, System.currentTimeMillis());
    }
    
    public void stopQuest(final int id) {
        final Quest quest = QuestController.instance.quests.get(id);
        if (quest == null) {
            return;
        }
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player);
        data.questData.activeQuests.remove(id);
    }
    
    public void removeQuest(final int id) {
        final Quest quest = QuestController.instance.quests.get(id);
        if (quest == null) {
            return;
        }
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player);
        data.questData.activeQuests.remove(id);
        data.questData.finishedQuests.remove(id);
    }
    
    @Override
    public int getType() {
        return 1;
    }
    
    @Override
    public boolean typeOf(final int type) {
        return type == 1 || super.typeOf(type);
    }
    
    public void addFactionPoints(final int faction, final int points) {
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player);
        data.factionData.increasePoints(faction, points);
    }
    
    public int getFactionPoints(final int faction) {
        final PlayerData data = PlayerDataController.instance.getPlayerData((EntityPlayer)this.player);
        return data.factionData.getFactionPoints(faction);
    }
    
    public void sendMessage(final String message) {
        this.player.addChatMessage((IChatComponent)new ChatComponentTranslation(NoppesStringUtils.formatText(message, this.player), new Object[0]));
    }
    
    public int getMode() {
        return this.player.theItemInWorldManager.getGameType().getID();
    }
    
    public void setMode(final int type) {
        this.player.setGameType(WorldSettings.getGameTypeById(type));
    }
    
    public int inventoryItemCount(final ScriptItemStack item) {
        int i = 0;
        for (final ItemStack is : this.player.inventory.mainInventory) {
            if (is != null && is.isItemEqual(item.item)) {
                i += is.stackSize;
            }
        }
        return i;
    }
    
    public ScriptItemStack[] getInventory() {
        final ScriptItemStack[] items = new ScriptItemStack[36];
        for (int i = 0; i < this.player.inventory.mainInventory.length; ++i) {
            final ItemStack item = this.player.inventory.mainInventory[i];
            if (item != null) {
                items[i] = new ScriptItemStack(item);
            }
        }
        return items;
    }
    
    public boolean removeItem(final ScriptItemStack item, int amount) {
        final int count = this.inventoryItemCount(item);
        if (amount > count) {
            return false;
        }
        if (count == amount) {
            this.removeAllItems(item);
        }
        else {
            for (int i = 0; i < this.player.inventory.mainInventory.length; ++i) {
                final ItemStack is = this.player.inventory.mainInventory[i];
                if (is != null && is.isItemEqual(item.item)) {
                    if (amount <= is.stackSize) {
                        is.splitStack(amount);
                        break;
                    }
                    this.player.inventory.mainInventory[i] = null;
                    amount -= is.stackSize;
                }
            }
        }
        return true;
    }
    
    public boolean removeItem(final String id, final int damage, final int amount) {
        final Item item = (Item)Item.itemRegistry.getObject(id);
        return item != null && this.removeItem(new ScriptItemStack(new ItemStack(item, 1, damage)), amount);
    }
    
    public boolean giveItem(final ScriptItemStack item, final int amount) {
        final String itemname = Item.itemRegistry.getNameForObject((Object)item.getMCItemStack().getItem());
        return this.giveItem(itemname, item.getItemDamage(), amount);
    }
    
    public boolean giveItem(final String id, final int damage, final int amount) {
        final Item item = (Item)Item.itemRegistry.getObject(id);
        return item != null && this.player.inventory.addItemStackToInventory(new ItemStack(item, amount, damage));
    }
    
    public void setSpawnpoint(int x, int y, int z) {
        x = ValueUtil.CorrectInt(x, -30000000, 30000000);
        z = ValueUtil.CorrectInt(z, -30000000, 30000000);
        y = ValueUtil.CorrectInt(y, 0, 256);
        this.player.setSpawnChunk(new ChunkCoordinates(x, y, z), true);
    }
    
    public void resetSpawnpoint() {
        this.player.setSpawnChunk((ChunkCoordinates)null, false);
    }
    
    public void removeAllItems(final ScriptItemStack item) {
        for (int i = 0; i < this.player.inventory.mainInventory.length; ++i) {
            final ItemStack is = this.player.inventory.mainInventory[i];
            if (is != null && is.isItemEqual(item.item)) {
                this.player.inventory.mainInventory[i] = null;
            }
        }
    }
    
    public boolean hasAchievement(final String achievement) {
        final StatBase statbase = StatList.getOneShotStat(achievement);
        return statbase != null && statbase instanceof Achievement && this.player.getStatFile().hasAchievementUnlocked((Achievement)statbase);
    }
    
    public boolean hasBukkitPermission(final String permission) {
        return CustomNpcsPermissions.hasPermissionString(this.player, permission);
    }
    
    public int getExpLevel() {
        return this.player.experienceLevel;
    }
    
    public void setExpLevel(final int level) {
        this.player.experienceLevel = level;
        this.player.addExperienceLevel(0);
    }
    
    public ScriptPixelmonPlayerData getPixelmonData() {
        if (!PixelmonHelper.Enabled) {
            return null;
        }
        return new ScriptPixelmonPlayerData(this.player);
    }
}
