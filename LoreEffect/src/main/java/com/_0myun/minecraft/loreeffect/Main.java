package com._0myun.minecraft.loreeffect;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if (item == null || !item.getItemMeta().hasLore()) return;
        List<String> lores = item.getItemMeta().getLore();
        final JSONObject[] json = new JSONObject[1];
        lores.forEach(lore -> {
            if (!lore.startsWith(Main.this.getConfig().getString("sign"))) return;
            String sign = lore.substring(Main.this.getConfig().getString("sign").length(), lore.length());
            try {
                json[0] = (JSONObject) new JSONParser().parse(ColorSign.asciiToString(sign.replace("§", "")));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        });
        if (json[0] == null) return;
        if (this.getCD(item) > System.currentTimeMillis()) {
            p.sendMessage(String.format(getConfig().getString("lang"), String.valueOf(this.getCD(item) - System.currentTimeMillis())));
            return;
        }
        String effect = String.valueOf(json[0].get("effect"));
        int time = Integer.valueOf(String.valueOf(json[0].get("time")));
        int cd = Integer.valueOf(String.valueOf(json[0].get("cd")));
        int level = Integer.valueOf(String.valueOf(json[0].get("level")));
        p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), time * 20, level));
        this.setCD(item, cd);
        p.sendMessage(getConfig().getString("lang1"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("reload ok");
            return true;
        }
        if (args.length != 4 || !sender.isOp()) {
            sender.sendMessage("指令格式：/0myun_loreeffect 效果 时间 冷却时间 等级");
            return true;
        }
        String effect = args[0];
        int time = Integer.valueOf(args[1]);
        int cd = Integer.valueOf(args[2]);
        int level = Integer.valueOf(args[3]);
        JSONObject json = new JSONObject();
        json.put("effect", effect);
        json.put("time", time);
        json.put("cd", cd);
        json.put("level", level);
        String jsonStr = json.toJSONString();
        System.out.println(ColorSign.asciiToLore(ColorSign.stringToAscii(jsonStr)).replace("§", "&"));
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
        List<String> lore = itemMeta.getLore();
        lore.add(getConfig().getString("sign").replace("&","§") + ColorSign.asciiToLore(ColorSign.stringToAscii(jsonStr)));
        itemMeta.setLore(lore);
        itemInHand.setItemMeta(itemMeta);
        sender.sendMessage("成功为手持物品加效果");
        return true;
    }

    public long getCD(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getLongOrDefault("com._0myun.minecraft.loreeffect.cd");
    }

    public void setCD(ItemStack itemStack, long cd) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt = nbt.put("com._0myun.minecraft.loreeffect.cd", System.currentTimeMillis() + (cd * 1000l));
        NbtFactory.setItemTag(itemStack, nbt);
    }
}
