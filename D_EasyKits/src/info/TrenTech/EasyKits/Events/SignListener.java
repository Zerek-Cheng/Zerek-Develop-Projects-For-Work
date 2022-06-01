/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.Sign
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.SignChangeEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package info.TrenTech.EasyKits.Events;

import info.TrenTech.EasyKits.Kit.Kit;
import info.TrenTech.EasyKits.Kit.KitUser;
import info.TrenTech.EasyKits.Utils.Notifications;
import info.TrenTech.EasyKits.Utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SignListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR)
    public void onSignUse(PlayerInteractEvent event) {
        String kitSign;
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if (!(action == Action.RIGHT_CLICK_BLOCK && block.getType().equals((Object)Material.WALL_SIGN) || action == Action.RIGHT_CLICK_BLOCK && block.getType().equals((Object)Material.SIGN_POST))) {
            return;
        }
        Sign sign = (Sign)event.getClickedBlock().getState();
        String[] line = sign.getLines();
        if (!line[0].equalsIgnoreCase(kitSign = (Object)ChatColor.DARK_BLUE + "[\u793c\u5305\u7cfb\u7edf]")) {
            return;
        }
        if (!player.hasPermission("EasyKits.sign.use")) {
            Notifications notify = new Notifications("Permission-Denied", null, null, 0.0, null, 0);
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
            return;
        }
        String kitName = line[1];
        Kit kit = new Kit(kitName);
        if (!kit.exists()) {
            Notifications notify2 = new Notifications("Kit-Not-Exist", kitName, null, 0.0, null, 0);
            player.sendMessage(notify2.getMessage());
            return;
        }
        if (Utils.getConfig().getString("Config.Sign-Action").equalsIgnoreCase("view")) {
            ItemStack[] inv = kit.getInventory();
            ItemStack[] arm = kit.getArmor();
            Inventory showInv = Utils.getPluginContainer().getServer().createInventory((InventoryHolder)player, 45, "\u00a70\u793c\u5305\u5185\u5bb9: " + kit.getName());
            showInv.setContents(inv);
            int index = 36;
            ItemStack[] array = arm;
            int length = array.length;
            int i = 0;
            while (i < length) {
                ItemStack a = array[i];
                showInv.setItem(index, a);
                ++index;
                ++i;
            }
            ItemStack getKit = new ItemStack(Material.NETHER_STAR);
            ItemMeta getKitMeta = getKit.getItemMeta();
            getKitMeta.setDisplayName((Object)ChatColor.GREEN + "\u00a7d\u70b9\u51fb\u9886\u53d6");
            getKit.setItemMeta(getKitMeta);
            showInv.setItem(44, getKit);
            player.openInventory(showInv);
        } else if (Utils.getConfig().getString("Config.Sign-Action").equalsIgnoreCase("obtain")) {
            KitUser kitUser = new KitUser(player, kit);
            try {
                kitUser.applyKit();
            }
            catch (Exception e) {
                Utils.getLogger().equals(e.getMessage());
            }
        } else {
            player.sendMessage((Object)ChatColor.DARK_RED + "ERROR: Check your config!");
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onSignPlace(SignChangeEvent event) {
        String[] line = event.getLines();
        String kitSign = "[\u793c\u5305\u7cfb\u7edf]";
        if (!line[0].equalsIgnoreCase("[\u793c\u5305\u7cfb\u7edf]")) {
            return;
        }
        Player player = event.getPlayer();
        if (!player.hasPermission("EasyKits.sign.create")) {
            Notifications notify = new Notifications("Permission-Denied", null, null, 0.0, null, 0);
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
            return;
        }
        String kitName = line[1];
        Kit kit = new Kit(kitName);
        if (!kit.exists()) {
            Notifications notify2 = new Notifications("Kit-Not-Exist", kitName, null, 0.0, null, 0);
            player.sendMessage(notify2.getMessage());
            event.setCancelled(true);
            return;
        }
        String newLine = (Object)ChatColor.DARK_BLUE + "[\u793c\u5305\u7cfb\u7edf]";
        event.setLine(0, newLine);
        event.setLine(3, null);
        double price = kit.getPrice();
        if (price > 0.0) {
            event.setLine(2, (Object)ChatColor.GREEN + Double.toString(price));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onSignBreak(BlockBreakEvent event) {
        String kitSign;
        Material block = event.getBlock().getType();
        if (!block.equals((Object)Material.SIGN_POST) || block.equals((Object)Material.WALL_SIGN)) {
            return;
        }
        Sign sign = (Sign)event.getBlock().getState();
        String[] line = sign.getLines();
        if (!line[0].equalsIgnoreCase(kitSign = (Object)ChatColor.DARK_BLUE + "[\u793c\u5305\u7cfb\u7edf]")) {
            return;
        }
        Player player = event.getPlayer();
        if (!player.hasPermission("EasyKits.sign.remove")) {
            Notifications notify = new Notifications("Permission-Denied", null, null, 0.0, null, 0);
            player.sendMessage(notify.getMessage());
            event.setCancelled(true);
        }
    }
}

