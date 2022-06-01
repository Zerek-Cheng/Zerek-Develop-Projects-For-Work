
package com._0myun.minecraft.attackrangeedit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
        extends JavaPlugin
        implements Listener {
    private String RangeLore;
    private List<String> loreList;
    private List<String> cd = new ArrayList<String>();

    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();
        Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) this);
        this.RangeLore = this.getConfig().getString("range");
        this.RangeLore = this.RangeLore.replace('&', '\u00a7');
        //this.getLogger().info("\u93bb\u638d\u6b22\u935a\ue21a\u59e9-Jimy_Spirits");
    }

    @EventHandler
    public void onPlayerDamage(final EntityDamageByEntityEvent e) {
        try {
            if (e.getDamager() instanceof Player) {
                final Player p = (Player) e.getDamager();
                for (String d : this.cd) {
                    if (!d.equals(p.getName())) continue;
                    return;
                }
                ItemStack itemStack = p.getItemInHand();
                ItemMeta itemMeta = itemStack.getItemMeta();
                this.loreList = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
                for (String lore : this.loreList) {
                    String tmplore = delColor(lore);
                    if (!tmplore.contains(this.RangeLore)) continue;
                    Pattern pattern = Pattern.compile("[0-9]{1,4}");
                    Matcher matcher = pattern.matcher(tmplore);
                    matcher.find();
                    String group = matcher.group();
                    double r = Double.valueOf(group);
                    List<Entity> ent = e.getEntity().getNearbyEntities(r, r, r);
                    ent.remove(e.getEntity());
                    ent.remove(p);
                    for (Entity entity : ent) {
                        if (!(entity instanceof Damageable)) continue;
                        Bukkit.getScheduler().runTask(this, () -> {
                            Main.this.cd.add(p.getName());
                            ((Damageable) entity).damage(e.getDamage(), (Entity) p);
                            Bukkit.getScheduler().runTask(Main.this, () -> Main.this.cd.remove(p.getName()));
                        });
                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    public static String delColor(String color) {
        try {
            StringBuffer sb = new StringBuffer(color);
            for (int i = sb.length() - 1; i >= 0; i--) {
                String subStr = sb.substring(i, i + 1);
                if (!subStr.equalsIgnoreCase("&") && !subStr.equalsIgnoreCase("§")) continue;
                sb.deleteCharAt(i);
                sb.deleteCharAt(i);
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}

