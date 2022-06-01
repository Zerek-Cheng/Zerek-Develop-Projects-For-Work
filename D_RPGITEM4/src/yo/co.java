/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.util.BlockIterator
 */
package yo;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.BlockIterator;
import yo.ao_0;
import yo.bt_1;

public class co
extends bt_1 {
    public int h = 5;

    @Override
    public boolean c() {
        return true;
    }

    @Override
    public int a(Player player) {
        if (this.y(player)) {
            Block block;
            World world = player.getWorld();
            Location start = player.getLocation();
            start.setY(start.getY() + 1.6);
            Block lastSafe = world.getBlockAt(start);
            BlockIterator bi = new BlockIterator((LivingEntity)player, this.h);
            while (bi.hasNext() && (!(block = bi.next()).getType().isSolid() || block.getType() == Material.AIR)) {
                lastSafe = block;
            }
            Location newLoc = lastSafe.getLocation();
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
            return 1;
        }
        return 0;
    }

    public void b(Player player, Projectile p) {
        if (this.z(player)) {
            Location newLoc;
            World world = player.getWorld();
            Location start = player.getLocation();
            if (start.distanceSquared(newLoc = p.getLocation()) >= (double)(this.h * this.h)) {
                player.sendMessage((Object)ChatColor.AQUA + ao_0.a("message.too.far", ao_0.a(player)));
                return;
            }
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
        }
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getInt("distance");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("distance", (Object)this.h);
    }

    @Override
    public String e() {
        return "teleport";
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + String.format(ao_0.a("power.teleport", locale), this.h, (double)this.g / 20.0);
    }
}

