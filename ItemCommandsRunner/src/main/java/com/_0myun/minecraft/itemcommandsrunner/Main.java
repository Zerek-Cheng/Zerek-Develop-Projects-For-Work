package com._0myun.minecraft.itemcommandsrunner;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {
    HashMap<UUID, Long> tmp = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (SendGET("http://mcplugin.0myun.com/3163361146_ItemCommandsRunner.php", "key=" + getConfig().getString("license")).equalsIgnoreCase("1")) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().log(Level.WARNING, "ERROR");
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            p.sendMessage("reloadok");
            return true;
        }
        if (args.length < 2) {
            p.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        int time = Integer.valueOf(args[0]);
        String runCommand = "";
        for (int i = 1; i < args.length; i++) {
            runCommand += args[i];
            if (i != args.length - 1) runCommand += " ";
        }
        ItemStack itemInHand = p.getItemInHand();
        itemBindCommand(itemInHand, time, runCommand);
        p.sendMessage(getConfig().getString("lang4"));
        return true;
    }

    public void itemBindCommand(ItemStack itemStack, int time, String command) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.itemcommandsrunner.time", time);
        nbt.put("com._0myun.minecraft.itemcommandsrunner.command", command);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public boolean hasBind(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        try {
            return nbt.getInteger("com._0myun.minecraft.itemcommandsrunner.time") > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTime(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        try {
            return nbt.getInteger("com._0myun.minecraft.itemcommandsrunner.time");
        } catch (Exception e) {
            return 0;
        }
    }

    public String getCommand(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        try {
            return nbt.getString("com._0myun.minecraft.itemcommandsrunner.command");
        } catch (Exception e) {
            return "";
        }
    }

    public boolean runItemStack(Player p, ItemStack itemStack) {
        if (!hasBind(itemStack)) return false;
        int time = getTime(itemStack);
        String command = getCommand(itemStack);
        if (time <= 0) {
            p.sendMessage(String.format(getConfig().getString("lang2"), String.valueOf(time)));
            return false;
        }
        itemBindCommand(itemStack, time - 1, command);
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            p.performCommand(command.replace("<player>", p.getName()));
        } finally {
            if (!op) p.setOp(false);
        }
        if (time - 1 == 0) {
            itemStack.setType(Material.AIR);
            itemStack.setAmount(0);
            p.sendMessage(getConfig().getString("lang3"));
            return true;
        }
        return false;
    }

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
        Player p = e.getPlayer();
        ItemStack itemInHand = p.getItemInHand();
        if (!hasBind(itemInHand)) return;

        Long timeLast = tmp.get(p.getUniqueId());
        if (timeLast == null) timeLast = 0l;
        if ((System.currentTimeMillis() - timeLast) < 500) return;
        boolean has = runItemStack(p, itemInHand);
        try {
            if (has) p.setItemInHand(new ItemStack(Material.AIR));
        } catch (Exception ex) {

        }
        //if (itemStack==null||itemStack.getType().equals(Material.AIR)||itemStack.getAmount()<=0) p.setItemInHand(null);
        tmp.put(p.getUniqueId(), System.currentTimeMillis());
    }


    public static String SendGET(String url, String param) {
        String result = "";//访问返回结果
        BufferedReader read = null;//读取访问结果

        try {
            //创建url
            URL realurl = new URL(url + "?" + param);
            //打开连接
            URLConnection connection = realurl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段，获取到cookies等
            for (String key : map.keySet()) {
            }
            // 定义 BufferedReader输入流来读取URL的响应
            read = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));
            String line;//循环读取
            while ((line = read.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {//关闭流
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
