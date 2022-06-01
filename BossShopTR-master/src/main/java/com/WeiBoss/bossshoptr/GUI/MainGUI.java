package com.WeiBoss.bossshoptr.GUI;

import com.WeiBoss.bossshoptr.Constructor.Log;
import com.WeiBoss.bossshoptr.Constructor.Page;
import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.WeiUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainGUI {
    private Main plugin;
    private Inventory gui;
    private ItemStack AIR = new ItemStack(Material.AIR);

    public MainGUI(Main plugin) {
        this.plugin = plugin;
    }

    public void open(Player admin, Player p, String shop, Integer day, Integer page) {
        gui = Bukkit.createInventory(null, 54, Config.MainGUI);
        addButton();
        addContent(p, shop, day, page);
        admin.openInventory(gui);
        plugin.pages.put(admin, new Page(p, shop, day, page));
    }

    private void addButton() {
        gui.setItem(45, Config.GUI_Button("UpPage"));
        gui.setItem(53, Config.GUI_Button("NextPage"));
    }

    private void addContent(Player p, String shop, Integer day, Integer page) {
        List<Log> log = new ArrayList<>(WeiUtil.getPlayerLog(p));
        if (shop != null) {
            log = WeiUtil.getShopLog(log, shop);
        }
        if (day != null) {
            log = WeiUtil.getRecentLog(log, day);
        }
        log = WeiUtil.getDateSort(log);
        HashMap<Integer, List<Log>> map = WeiUtil.getPagination(log);
        if (map.size() >= page)
            log = map.get(page);
        else
            log = map.get(map.size());
        log = log != null ? log:new ArrayList<>();
        int size = 0;
        for (Log wei : log) {
            gui.setItem(size, WeiUtil.getLogItem(wei));
            size++;
        }
        if (page == 1)
            gui.setItem(45, AIR);
        if (page == map.size() || map.size() == 0)
            gui.setItem(53, AIR);
    }
}