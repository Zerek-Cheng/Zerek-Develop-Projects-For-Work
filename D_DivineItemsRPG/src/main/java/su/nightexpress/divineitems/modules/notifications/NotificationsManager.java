/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.notifications;

import java.util.HashMap;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.api.events.DivineItemDamageEvent;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.utils.ActionTitle;

public class NotificationsManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private MyConfig config;
    private boolean e;
    private boolean note_dura_chat;
    private boolean note_dura_bar;
    private boolean note_dura_title;
    private HashMap<Double, String> note_dura_perc;
    private final String n = this.name().toLowerCase().replace(" ", "_");

    public NotificationsManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.config = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        FileConfiguration fileConfiguration = this.config.getConfig();
        this.note_dura_chat = fileConfiguration.getBoolean("Durability.Chat");
        this.note_dura_bar = fileConfiguration.getBoolean("Durability.ActionBar");
        this.note_dura_title = fileConfiguration.getBoolean("Durability.Titles");
        this.note_dura_perc = new HashMap();
        if (fileConfiguration.contains("Durability.Percentage")) {
            for (String string : fileConfiguration.getConfigurationSection("Durability.Percentage").getKeys(false)) {
                double d = Double.parseDouble(string.toString());
                String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Durability.Percentage." + string));
                this.note_dura_perc.put(d, string2);
            }
        }
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isResolvable() {
        return false;
    }

    @Override
    public String name() {
        return "Notifications";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.note_dura_perc.clear();
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public void notifyDurability(Player player, ItemStack itemStack) {
        double d = (double)ItemAPI.getDurability(itemStack, 0) / (double)ItemAPI.getDurability(itemStack, 1) * 100.0;
        if (this.note_dura_perc.containsKey(d)) {
            String string;
            String string2 = string = this.note_dura_perc.get(d).replace("%d1", String.valueOf(ItemAPI.getDurability(itemStack, 0) - 1)).replace("%d2", String.valueOf(ItemAPI.getDurability(itemStack, 1)));
            if (this.note_dura_chat) {
                if (string.contains("/n")) {
                    string2 = string.replace("/n", "");
                }
                player.sendMessage(string2);
            }
            if (this.note_dura_bar) {
                if (string.contains("/n")) {
                    string2 = string.replace("/n", "");
                }
                ActionTitle.sendActionBar(player, string2);
            }
            if (this.note_dura_title) {
                String string3 = "";
                if (string.contains("/n")) {
                    string3 = string.split("/n")[1];
                    string2 = string.split("/n")[0];
                }
                ActionTitle.sendTitles(player, string2, string3, 5, 30, 5);
            }
        }
    }

    @EventHandler
    public void onDmg(DivineItemDamageEvent divineItemDamageEvent) {
        Player player = divineItemDamageEvent.getPlayer();
        ItemStack itemStack = divineItemDamageEvent.getItem();
        this.notifyDurability(player, itemStack);
    }
}

