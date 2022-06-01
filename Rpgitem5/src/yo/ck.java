// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.block.Block;
import think.rpgitems.Plugin;
import org.bukkit.util.Vector;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;
import java.util.Set;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.Material;

public class ck extends bt
{
    public Material h;
    public int i;
    
    public ck() {
        this.h = Material.GLASS;
        this.i = 10;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (!this.y(player)) {
            return 0;
        }
        aT isHooking = aT.a(player, this.c, "skyhook.isHooking");
        if (isHooking == null) {
            isHooking = new aT(player, this.c, "skyhook.isHooking", false);
        }
        if (isHooking.b()) {
            player.setVelocity(player.getLocation().getDirection());
            isHooking.a(false);
            return 0;
        }
        final Block block = bg.a((LivingEntity)player, null, this.i);
        if (block == null || block.getType() == Material.AIR || (this.h != Material.AIR && block.getType() != this.h)) {
            player.sendMessage(ChatColor.AQUA + aO.a("message.skyhook.fail", aO.a(player)));
            return 0;
        }
        isHooking.a(true);
        final Location location = player.getLocation();
        player.setAllowFlight(true);
        player.setVelocity(location.getDirection().multiply(block.getLocation().distance(location) / 2.0));
        player.setFlying(true);
        new BukkitRunnable() {
            private int d = 0;
            
            public void run() {
                if (!player.getAllowFlight()) {
                    this.cancel();
                    aT.a(player, ck.this.c, "skyhook.isHooking").a(false);
                    return;
                }
                if (!aT.a(player, ck.this.c, "skyhook.isHooking").b()) {
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
                final Vector dir = location.getDirection().setY(0).normalize();
                location.add(dir);
                if (location.getBlock().getType() != ck.this.h) {
                    player.setFlying(false);
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        player.setAllowFlight(false);
                    }
                    this.cancel();
                    aT.a(player, ck.this.c, "skyhook.isHooking").a(false);
                    return;
                }
                player.setVelocity(dir.multiply(0.5));
            }
        }.runTaskTimer((org.bukkit.plugin.Plugin)Plugin.c, 0L, 0L);
        return 1;
    }
    
    public void c(final ConfigurationSection s) {
        this.h = Material.valueOf(s.getString("railMaterial", "GLASS"));
        this.i = s.getInt("hookDistance", 10);
    }
    
    public void d(final ConfigurationSection s) {
        s.set("railMaterial", (Object)this.h.toString());
        s.set("hookDistance", (Object)this.i);
    }
    
    @Override
    public String e() {
        return "skyhook";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + aO.a("power.skyhook", locale);
    }
}
