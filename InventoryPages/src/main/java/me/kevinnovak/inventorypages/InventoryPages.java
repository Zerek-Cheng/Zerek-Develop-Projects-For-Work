/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerGameModeChangeEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package me.kevinnovak.inventorypages;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InventoryPages
        extends JavaPlugin
        implements Listener {
    File crashedFile = new File(this.getDataFolder() + "/backups/crashed.yml");
    FileConfiguration crashedData = YamlConfiguration.loadConfiguration((File) this.crashedFile);
    private HashMap<String, CustomInventory> playerInvs = new HashMap();
    ColorConverter colorConv = new ColorConverter(this);
    private ItemStack nextItem;
    private ItemStack prevItem;
    private ItemStack noPageItem;
    private Integer prevPos;
    private Integer nextPos;
    private List<String> clearCommands;
    private String noPermission;
    private String clear;
    private String clearAll;
    private String itemsMerged;
    private String itemsDropped;
    private Boolean logSavesEnabled;
    private String logSavesMessage;
    public static Permission permission = null;
    private List<UUID> saving = new ArrayList<>();

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public void onEnable() {
        this.setupPermissions();
        loadConfig0();
        this.saveDefaultConfig();
        Bukkit.getServer().getLogger().info("[InventoryPages] Registering events.");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getLogger().info("[InventoryPages] Setting up items.");
        this.initItems();
        Bukkit.getServer().getLogger().info("[InventoryPages] Setting up commands.");
        this.initCommands();
        Bukkit.getServer().getLogger().info("[InventoryPages] Setting up language.");
        this.initLanguage();
        Bukkit.getServer().getLogger().info("[InventoryPages] Setting up inventories.");
        Iterator var2 = Bukkit.getServer().getOnlinePlayers().iterator();

        while (var2.hasNext()) {
            Player var6 = (Player) var2.next();
            this.loadInvFromFileIntoHashMap(var6);
        }

        if (this.getConfig().getBoolean("saving.enabled")) {
            Bukkit.getServer().getLogger().info("[InventoryPages] Setting up inventory saving.");
            this.startSaving();
        }

        Bukkit.getServer().getLogger().info("[InventoryPages] Plugin enabled!");
    }


    public void onDisable() {
        Iterator var2 = Bukkit.getServer().getOnlinePlayers().iterator();
        while(var2.hasNext()) {
            Player var1 = (Player)var2.next();
            String var3 = var1.getUniqueId().toString();
            if (this.playerInvs.containsKey(var3)) {
                this.updateInvToHashMap(var1);
                this.saveInvFromHashMapToFile(var1);
                this.clearAndRemoveCrashedPlayer(var1);
            }
        }
        Bukkit.getServer().getLogger().info("[InventoryPages] Plugin disabled.");
    }


    public synchronized void updateAndSaveAllInventoriesToFiles() {
        if (!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
            Iterator var2 = Bukkit.getServer().getOnlinePlayers().iterator();
            while(var2.hasNext()) {
                Player var1 = (Player)var2.next();
                String var3 = var1.getUniqueId().toString();
                if (this.playerInvs.containsKey(var3)) {
                    this.updateInvToHashMap(var1);
                    this.saveInvFromHashMapToFile(var1);
                }
            }
            if (this.logSavesEnabled) {
                Bukkit.getServer().getLogger().info(this.logSavesMessage);
            }
        }
    }

    public void initItems() {
        this.prevItem = new ItemStack(this.getConfig().getInt("items.prev.ID"), 1, (short) this.getConfig().getInt("items.prev.variation"));
        ItemMeta itemMeta = this.prevItem.getItemMeta();
        itemMeta.setDisplayName(this.colorConv.convertConfig("items.prev.name"));
        itemMeta.setLore(this.colorConv.convertConfigList("items.prev.lore"));
        this.prevItem.setItemMeta(itemMeta);
        this.prevPos = this.getConfig().getInt("items.prev.position");
        this.nextItem = new ItemStack(this.getConfig().getInt("items.next.ID"), 1, (short) this.getConfig().getInt("items.next.variation"));
        ItemMeta itemMeta2 = this.nextItem.getItemMeta();
        itemMeta2.setDisplayName(this.colorConv.convertConfig("items.next.name"));
        itemMeta2.setLore(this.colorConv.convertConfigList("items.next.lore"));
        this.nextItem.setItemMeta(itemMeta2);
        this.nextPos = this.getConfig().getInt("items.next.position");
        this.noPageItem = new ItemStack(this.getConfig().getInt("items.noPage.ID"), 1, (short) this.getConfig().getInt("items.noPage.variation"));
        ItemMeta itemMeta3 = this.noPageItem.getItemMeta();
        itemMeta3.setDisplayName(this.colorConv.convertConfig("items.noPage.name"));
        itemMeta3.setLore(this.colorConv.convertConfigList("items.noPage.lore"));
        this.noPageItem.setItemMeta(itemMeta3);
    }

    public void initCommands() {
        this.clearCommands = this.getConfig().getStringList("commands.clear.aliases");
    }

    public void initLanguage() {
        this.noPermission = this.colorConv.convertConfig("language.noPermission");
        this.clear = this.colorConv.convertConfig("language.clear");
        this.clearAll = this.colorConv.convertConfig("language.clearAll");
        this.itemsMerged = this.colorConv.convertConfig("language.itemsMerged");
        this.itemsDropped = this.colorConv.convertConfig("language.itemsDropped");
        this.logSavesEnabled = this.getConfig().getBoolean("logging.saves.enabled");
        this.logSavesMessage = "[InventoryPages] " + this.getConfig().getString("logging.saves.message");
    }

    public void startSaving() {
        BukkitScheduler var1 = this.getServer().getScheduler();
        var1.scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                InventoryPages.this.updateAndSaveAllInventoriesToFiles();
            }
        }, 0L, (long)(20 * this.getConfig().getInt("saving.interval")));
    }

    public synchronized void saveInvFromHashMapToFile(Player var1) {
        String var2 = var1.getUniqueId().toString();
        if (this.playerInvs.containsKey(var2)) {
            File var3 = new File(this.getDataFolder() + "/inventories/" + var2.substring(0, 1) + "/" + var2 + ".yml");
            YamlConfiguration var4 = YamlConfiguration.loadConfiguration(var3);
            Iterator var6 = ((CustomInventory)this.playerInvs.get(var2)).getItems().entrySet().iterator();

            while(var6.hasNext()) {
                Map.Entry var5 = (Map.Entry)var6.next();

                for(int var7 = 0; var7 < ((ArrayList)var5.getValue()).size(); ++var7) {
                    if (((ArrayList)var5.getValue()).get(var7) != null) {
                        var4.set("items.main." + var5.getKey() + "." + var7, InventoryStringDeSerializer.toBase64((ItemStack)((ArrayList)var5.getValue()).get(var7)));
                    } else {
                        var4.set("items.main." + var5.getKey() + "." + var7, (Object)null);
                    }
                }
            }

            if (((CustomInventory)this.playerInvs.get(var2)).hasUsedCreative()) {
                for(int var9 = 0; var9 < ((CustomInventory)this.playerInvs.get(var2)).getCreativeItems().size(); ++var9) {
                    if (((CustomInventory)this.playerInvs.get(var2)).getCreativeItems().get(var9) != null) {
                        var4.set("items.creative.0." + var9, InventoryStringDeSerializer.toBase64((ItemStack)((CustomInventory)this.playerInvs.get(var2)).getCreativeItems().get(var9)));
                    } else {
                        var4.set("items.creative.0." + var9, (Object)null);
                    }
                }
            }

            var4.set("page", ((CustomInventory)this.playerInvs.get(var2)).getPage());

            try {
                var4.save(var3);
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }
    }

    public synchronized void loadInvFromFileIntoHashMap(Player player) {
        this.clearAndRemoveCrashedPlayer(player);
        int n = 1;
        Boolean bl = false;
        int n2 = 2;
        while (n2 < 101) {
            if (player.hasPermission("inventorypages.pages." + n2)) {
                bl = true;
                n = n2 - 1;
            }
            ++n2;
        }

        String group = permission.getPlayerGroups(player)[0];
        n = this.getConfig().getInt("player." + player.getUniqueId().toString());
        n2 = this.getConfig().getInt("group." + group);
        if (n2 == 0) {
            n2 = this.getConfig().getInt("group.Default");
        }
        n += n2;
        if (n > 0) {
            bl = true;
        }


        if (bl.booleanValue()) {
            String string = player.getUniqueId().toString();
            CustomInventory customInventory = new CustomInventory(this, player, n, this.prevItem, this.prevPos, this.nextItem, this.nextPos, this.noPageItem, this.itemsMerged, this.itemsDropped);
            this.playerInvs.put(string, customInventory);
            this.addCrashedPlayer(player);
            this.playerInvs.get(string).showPage(player.getGameMode());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            Player p = (Player) sender;
            Integer pageInt = Integer.valueOf(args[0]);
            if (pageInt > 0) {
                pageInt--;
            }
            CustomInventory inv = this.playerInvs.get(p.getUniqueId().toString());
            if (pageInt > inv.maxPage) {
                sender.sendMessage("已超过最大页数");
                return true;
            }
            inv.saveCurrentPage();
            inv.setPage(pageInt);
            inv.showPage();
            inv.saveCurrentPage();
            sender.sendMessage("背包已经跳转!请打开背包查看");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("add") && sender.isOp()) {
            String pName = args[1];
            int pageInt = Integer.valueOf(args[2]) + this.getConfig().getInt("player." + Bukkit.getOfflinePlayer(pName).getUniqueId().toString());
            this.getConfig().set("player." + Bukkit.getOfflinePlayer(pName).getUniqueId().toString(), pageInt);
            sender.sendMessage("§6背包扩容程序只剩下最后一步了!请重进服务器完成最后一道程序!");
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set") && sender.isOp()) {
            String group = args[1];
            int pageInt = Integer.valueOf(args[2]);
            this.getConfig().set("group." + group, pageInt);
            sender.sendMessage("设置完成");
            return true;
        }
        return true;
    }

    public void updateInvToHashMap(Player player) {
        String string = player.getUniqueId().toString();
        if (this.playerInvs.containsKey(string)) {
            this.playerInvs.get(string).saveCurrentPage();
        }
    }

    public void removeInvFromHashMap(Player player) {
        String string = player.getUniqueId().toString();
        if (this.playerInvs.containsKey(string)) {
            this.playerInvs.remove(string);
            this.clearAndRemoveCrashedPlayer(player);
        }
    }

    public void saveCrashedFile() {
        try {
            this.crashedData.save(this.crashedFile);
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public Boolean crashedPlayersExist() {
        if (this.crashedData.contains("crashed") && this.crashedData.getConfigurationSection("crashed").getKeys(false).size() > 0) {
            return true;
        }
        return false;
    }

    public Boolean hasCrashed(Player player) {
        if (this.crashedData.contains("crashed." + player.getUniqueId().toString())) {
            return true;
        }
        return false;
    }

    public void addCrashedPlayer(Player player) {
        this.crashedData.set("crashed." + player.getUniqueId().toString(), (Object) true);
        this.saveCrashedFile();
    }

    public void clearAndRemoveCrashedPlayer(Player player) {
        if (this.crashedPlayersExist().booleanValue() && this.hasCrashed(player).booleanValue()) {
            int n = 0;
            while (n < 27) {
                player.getInventory().setItem(n + 9, null);
                ++n;
            }
            this.crashedData.set("crashed." + player.getUniqueId().toString(), null);
            this.saveCrashedFile();
        }
    }

    public void clearHotbar(Player player) {
        int n = 0;
        while (n < 9) {
            player.getInventory().setItem(n, null);
            ++n;
        }
    }

    public Boolean hasSwitcherItems(Player player) {
        String string = player.getUniqueId().toString();
        if (this.playerInvs.containsKey(string) && player.getGameMode() != GameMode.CREATIVE) {
            return true;
        }
        return false;
    }

    public Boolean isSwitcherItem(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack != null && itemStack.getType() != null && itemStack.getType().equals((Object) itemStack2.getType()) && itemStack.getItemMeta() != null && itemStack.getItemMeta().getDisplayName() != null && itemStack.getItemMeta().getDisplayName().equals(itemStack2.getItemMeta().getDisplayName())) {
            return true;
        }
        return false;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        InventoryPages.this.loadInvFromFileIntoHashMap(player);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent var1) {
        Player var2 = var1.getPlayer();
        String var3 = var2.getUniqueId().toString();
        if (this.playerInvs.containsKey(var3)) {
            System.out.println("异步saving");
            this.updateInvToHashMap(var2);
            this.saveInvFromHashMapToFile(var2);
            this.removeInvFromHashMap(var2);
        }

    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (this.saving.contains(uuid)) {
            e.setKickMessage("您的背包数据正在保存中...为了防止bug请稍后再进入");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        String string = player.getUniqueId().toString();
        if (this.playerInvs.containsKey(string)) {
            this.updateInvToHashMap(player);
            playerDeathEvent.setKeepInventory(true);
            GameMode gameMode = player.getGameMode();
            int n = 2;
            if (player.hasPermission("inventorypages.drop.one")) {
                n = 1;
            }
            if (player.hasPermission("inventorypages.drop.none")) {
                n = 0;
            }
            if (n == 1) {
                this.playerInvs.get(string).dropPage(gameMode);
            } else if (n == 2) {
                this.playerInvs.get(string).dropAllPages(gameMode);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent playerRespawnEvent) {
        Player player = playerRespawnEvent.getPlayer();
        String string = player.getUniqueId().toString();
        if (this.playerInvs.containsKey(string)) {
            GameMode gameMode = player.getGameMode();
            this.playerInvs.get(string).showPage(gameMode);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        InventoryHolder inventoryHolder;
        Player player;
        Inventory inventory = this.getClickedInventory(inventoryClickEvent.getView(), inventoryClickEvent.getRawSlot());
        if (inventory != null && inventory.getType() == InventoryType.PLAYER && (inventoryHolder = inventory.getHolder()) instanceof Player && this.hasSwitcherItems(player = (Player) inventoryHolder).booleanValue()) {
            ItemStack itemStack = inventoryClickEvent.getCurrentItem();
            if (this.isSwitcherItem(itemStack, this.prevItem).booleanValue()) {
                inventoryClickEvent.setCancelled(true);
                this.playerInvs.get(player.getUniqueId().toString()).prevPage();
            } else if (this.isSwitcherItem(itemStack, this.nextItem).booleanValue()) {
                inventoryClickEvent.setCancelled(true);
                this.playerInvs.get(player.getUniqueId().toString()).nextPage();
            } else if (this.isSwitcherItem(itemStack, this.noPageItem).booleanValue()) {
                inventoryClickEvent.setCancelled(true);
            }
        }
    }

    public Inventory getClickedInventory(InventoryView inventoryView, int n) {
        int n2;
        int n3 = inventoryView.getTopInventory().getSize();
        if (inventoryView.getTopInventory().getType() == InventoryType.PLAYER && (n2 = n3 % 9) != 0) {
            n3 -= n2;
        }
        Inventory inventory = n < 0 ? null : (inventoryView.getTopInventory() != null && n < n3 ? inventoryView.getTopInventory() : inventoryView.getBottomInventory());
        return inventory;
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent playerGameModeChangeEvent) {
        Player player = playerGameModeChangeEvent.getPlayer();
        String string = player.getUniqueId().toString();
        if (this.playerInvs.containsKey(string)) {
            this.playerInvs.get(string).saveCurrentPage();
            this.playerInvs.get(string).showPage(playerGameModeChangeEvent.getNewGameMode());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        String string = playerCommandPreprocessEvent.getMessage().toLowerCase();
        for (String string2 : this.clearCommands) {
            String string3;
            Player player;
            if (!string.startsWith("/" + string2 + " ") && !string.equalsIgnoreCase("/" + string2) || !this.playerInvs.containsKey(string3 = (player = playerCommandPreprocessEvent.getPlayer()).getUniqueId().toString()))
                continue;
            playerCommandPreprocessEvent.setCancelled(true);
            if (player.hasPermission("inventorypages.clear")) {
                GameMode gameMode = player.getGameMode();
                if (string.startsWith("/" + string2 + " all ") || string.equalsIgnoreCase("/" + string2 + " all")) {
                    this.playerInvs.get(string3).clearAllPages(gameMode);
                    player.sendMessage(this.clearAll);
                } else {
                    this.playerInvs.get(string3).clearPage(gameMode);
                    player.sendMessage(this.clear);
                }
                this.clearHotbar(player);
                this.playerInvs.get(string3).showPage(gameMode);
                continue;
            }
            player.sendMessage(this.noPermission);
        }
    }

    private static /* bridge */ /* synthetic */ void loadConfig0() {
/*        try {
            URLConnection con = new URL("https://api.spigotmc.org/legacy/premium.php?user_id=448586&resource_id=32432&nonce=1451577781").openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            ((HttpURLConnection) con).setInstanceFollowRedirects(true);
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if ("false".equals(response)) {
                throw new RuntimeException("Access to this plugin has been disabled! Please contact the author!");
            }
        } catch (IOException con) {
            // empty catch block
        }*/
    }

}

