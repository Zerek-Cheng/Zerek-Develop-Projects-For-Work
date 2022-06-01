// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Projectile;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Sound;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.entity.Player;

public class co extends bt
{
    public int h;
    
    public co() {
        this.h = 5;
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public int a(final Player player) {
        if (this.y(player)) {
            final World world = player.getWorld();
            final Location start = player.getLocation();
            start.setY(start.getY() + 1.6);
            Block lastSafe = world.getBlockAt(start);
            final BlockIterator bi = new BlockIterator((LivingEntity)player, this.h);
            while (bi.hasNext()) {
                final Block block = bi.next();
                if (block.getType().isSolid() && block.getType() != Material.AIR) {
                    break;
                }
                lastSafe = block;
            }
            final Location newLoc = lastSafe.getLocation();
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
            return 1;
        }
        return 0;
    }
    
    public void b(final Player player, final Projectile p) {
        if (this.z(player)) {
            final World world = player.getWorld();
            final Location start = player.getLocation();
            final Location newLoc = p.getLocation();
            if (start.distanceSquared(newLoc) >= this.h * this.h) {
                player.sendMessage(ChatColor.AQUA + aO.a("message.too.far", aO.a(player)));
                return;
            }
            newLoc.setPitch(start.getPitch());
            newLoc.setYaw(start.getYaw());
            player.teleport(newLoc);
            world.playEffect(newLoc, Effect.ENDER_SIGNAL, 0);
            world.playSound(newLoc, Sound.ENDERMAN_TELEPORT, 1.0f, 0.3f);
        }
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getInt("distance");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("distance", (Object)this.h);
    }
    
    @Override
    public String e() {
        return "teleport";
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + String.format(aO.a("power.teleport", locale), this.h, this.g / 20.0);
    }
}
