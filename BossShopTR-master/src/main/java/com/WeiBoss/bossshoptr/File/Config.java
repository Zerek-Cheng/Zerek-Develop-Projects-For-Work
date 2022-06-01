package com.WeiBoss.bossshoptr.File;

import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.WeiUtil;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static Main plugin = Main.instance;
    public final static String Prefix = WeiUtil.onReplace(plugin.config.getString("Prefix"));
    public final static boolean SQLEnable = plugin.config.getBoolean("MYSQL.Enable");
    public final static String Host = plugin.config.getString("MYSQL.Host");
    public final static String Port = plugin.config.getString("MYSQL.Port");
    public final static String Database = plugin.config.getString("MYSQL.Database");
    public final static String Users = plugin.config.getString("MYSQL.Users");
    public final static String Password = plugin.config.getString("MYSQL.Password");
    public final static Integer LogTime = plugin.config.getInt("Time");
    public final static String MainGUI = WeiUtil.onReplace(plugin.config.getString("MainGUI"));
    public static List<String> getItemLore(){
        List<String> lore = new ArrayList<>();
        plugin.config.getStringList("ItemLore").forEach(s -> lore.add(WeiUtil.onReplace(s)));
        return lore;
    }
    public static ItemStack GUI_Button(String type){
        int id = plugin.config.getInt("Item."+ type +".ID");
        int data = plugin.config.getInt("Item."+ type +".Data");
        String name = WeiUtil.onReplace(plugin.config.getString("Item."+ type +".Name"));
        List<String> lore = new ArrayList<>();
        plugin.config.getStringList("Item."+ type +".Lore").forEach(s -> lore.add(WeiUtil.onReplace(s)));
        return WeiUtil.createItem(id,data,1,name,lore);
    }
}
