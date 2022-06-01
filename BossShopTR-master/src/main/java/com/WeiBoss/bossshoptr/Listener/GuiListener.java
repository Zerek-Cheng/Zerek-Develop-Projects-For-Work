package com.WeiBoss.bossshoptr.Listener;

import com.WeiBoss.bossshoptr.Constructor.Page;
import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    private static Main plugin = Main.instance;

    @EventHandler
    public void onClickInv(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        Inventory gui = e.getInventory();
        if (gui.getType() == InventoryType.CHEST && gui.getTitle().equals(Config.MainGUI)) {
            ItemStack clickItem = e.getCurrentItem();
            if (clickItem == null) return;
            e.setCancelled(true);
            checkButton(p, clickItem);
        }
    }

    private void checkButton(Player p, ItemStack item) {
        Page page = plugin.pages.get(p);
        ItemStack UpPage = Config.GUI_Button("UpPage");
        ItemStack NextPage = Config.GUI_Button("NextPage");
        Player player = page.getPlayer();
        String shop = page.getShop();
        Integer day = page.getDay();
        int i = page.getPage();
        if (item.equals(UpPage)) {
            page.setPage(i - 1);
            plugin.mainGUI.open(p, player, shop, day, i - 1);
            return;
        }
        if (item.equals(NextPage)) {
            page.setPage(i + 1);
            plugin.mainGUI.open(p, player, shop, day, i + 1);
        }
    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();
        Inventory gui = e.getInventory();
        if (gui.getType() == InventoryType.CHEST && gui.getTitle().equals(Config.MainGUI)) {
            plugin.pages.remove(p);
        }
    }
}