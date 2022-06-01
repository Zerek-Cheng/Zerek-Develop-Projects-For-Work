/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.Vector
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import think.rpgitems.item.RPGItem;
import yo.ao_0;
import yo.at_0;
import yo.bg_1;
import yo.bt_1;

public class ck
extends bt_1 {
    public Material h = Material.GLASS;
    public int i = 10;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            at_0 isHooking = at_0.a(player, this.c, "skyhook.isHooking");
            if (isHooking == null) {
                isHooking = new at_0(player, this.c, "skyhook.isHooking", false);
            }
            if (isHooking.b()) {
                player.setVelocity(player.getLocation().getDirection());
                isHooking.a(false);
                return 0;
            }
            Block block = bg_1.a((LivingEntity)player, null, this.i);
            if (block == null || block.getType() == Material.AIR || this.h != Material.AIR && block.getType() != this.h) {
                player.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.skyhook.fail", ao_0.a(player)));
                return 0;
            }
            isHooking.a(true);
            final Location location = player.getLocation();
            player.setAllowFlight(true);
            player.setVelocity(location.getDirection().multiply(block.getLocation().distance(location) / 2.0));
            player.setFlying(true);
            new BukkitRunnable(){
                private int d = 0;

                public void run() {
                    if (!player.getAllowFlight()) {
                        this.cancel();
                        at_0.a(player, ck.this.c, "skyhook.isHooking").a(false);
                        return;
                    }
                    if (!at_0.a(player, ck.this.c, "skyhook.isHooking").b()) {
                        player.setFlying(false);
                        if (player.getGameMode() != GameMode.CREATIVE) {
                            player.setAllowFlight(false);
                        }
                        this.cancel();
                        return;
                    }
                    player.setFlying(true);
                    player.getLocation(location);
                    location.add(0.0, 2.4, 0.0);
                    if (this.d < 20) {
                        ++this.d;
                        if (location.getBlock().getType() == ck.this.h) {
                            this.d = 20;
                        }
                        return;
                    }
                    Vector dir = location.getDirection().setY(0).normalize();
                    location.add(dir);
                    if (location.getBlock().getType() != ck.this.h) {
                        player.setFlying(false);
                        if (player.getGameMode() != GameMode.CREATIVE) {
                            player.setAllowFlight(false);
                        }
                        this.cancel();
                        at_0.a(player, ck.this.c, "skyhook.isHooking").a(false);
                        return;
                    }
                    player.setVelocity(dir.multiply(0.5));
                }
            }.runTaskTimer((Plugin)think.rpgitems.Plugin.c, 0L, 0L);
            return 1;
        }
        return 0;
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = Material.valueOf((String)s.getString("railMaterial", "GLASS"));
        this.i = s.getInt("hookDistance", 10);
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("railMaterial", (Object)this.h.toString());
        s.set("hookDistance", (Object)this.i);
    }

    @Override
    public String e() {
        return "skyhook";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + ao_0.a("power.skyhook", locale);
    }

}

