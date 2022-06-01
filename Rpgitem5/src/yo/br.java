// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.permissions.PermissionAttachment;
import think.rpgitems.Plugin;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.TreeType;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import java.util.Arrays;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.block.Block;
import java.util.Collection;
import org.bukkit.WeatherType;
import java.io.Serializable;
import org.bukkit.inventory.ItemStack;
import com.google.common.collect.Lists;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.entity.Entity;
import java.util.List;

public class bR extends bI
{
    public String h;
    public String i;
    public String j;
    private static final String k = "Unknown";
    
    public bR() {
        this.h = "";
        this.i = "Runs command";
        this.j = "";
    }
    
    private static List a(final String prefix, final Object object) {
        if (!(object instanceof Entity)) {
            return new ArrayList();
        }
        if (object instanceof Player) {
            return a(prefix, (Player)object);
        }
        if (object instanceof Horse) {
            return a(prefix, (Horse)object);
        }
        return a(prefix, (Entity)object);
    }
    
    private static List<?> a(final String prefix, final Entity entity) {
        return (List<?>)Lists.newArrayList((Object[])new String[] { prefix + "entityType", entity.getType().name(), prefix + "entityName", (entity.getCustomName() != null) ? entity.getCustomName() : "Unknown" });
    }
    
    private static List<?> a(final String prefix, final ItemStack item) {
        return (List<?>)Lists.newArrayList((Object[])new Serializable[] { prefix + "itemType", item.getType().name(), prefix + "itemData", item.getDurability(), prefix + "itemAmount", item.getAmount(), prefix + "itemName", (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName() : "Unknown" });
    }
    
    private static List<?> a(final String prefix, final Player player) {
        final List list = Lists.newArrayList((Object[])new Serializable[] { prefix + "player", player.getName(), prefix + "display", (player.getDisplayName() != null) ? player.getDisplayName() : "Unknown", prefix + "ip", player.getAddress().getAddress().getCanonicalHostName(), prefix + "flyspeed", player.getFlySpeed(), prefix + "walkspeed", player.getWalkSpeed(), prefix + "exptolevel", player.getExpToLevel(), prefix + "food", player.getFoodLevel(), prefix + "hp", player.getHealth(), prefix + "gamemode", player.getGameMode().name(), prefix + "level", player.getLevel(), prefix + "maxhp", player.getMaxHealth(), prefix + "listname", (player.getPlayerListName() != null) ? player.getPlayerListName() : "Unknown", prefix + "weather", (player.getPlayerWeather() != null) ? player.getPlayerWeather() : (player.getWorld().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR).name(), prefix + "saturation", player.getSaturation(), prefix + "exp", player.getTotalExperience(), prefix + "uuid", player.getUniqueId().toString() });
        list.addAll(a(prefix, player.getLocation()));
        return (List<?>)list;
    }
    
    private static List<?> a(final String prefix, final Block block) {
        return (List<?>)Lists.newArrayList((Object[])new Serializable[] { prefix + "biome", block.getBiome().name(), prefix + "lightlevel", block.getLightLevel(), prefix + "blockType", block.getType().name(), prefix + "blockTypeId", block.getTypeId(), prefix + "blockTypeData", block.getData() });
    }
    
    private static List<?> a(final String prefix, final Ageable ageable) {
        return (List<?>)Lists.newArrayList(new Object[] { a(prefix, (Entity)ageable), prefix + "age", ageable.getAge() });
    }
    
    private static List a(final String prefix, final Horse horse) {
        final List list = Lists.newArrayList((Object[])new Serializable[] { prefix + "domestication", horse.getDomestication(), prefix + "color", horse.getColor().name(), prefix + "jumpstrength", horse.getJumpStrength(), prefix + "style", horse.getStyle().name(), prefix + "variant", horse.getVariant(), prefix + "maxdomestication", horse.getMaxDomestication() });
        list.addAll(a(prefix, (Ageable)horse));
        return list;
    }
    
    private static List a(final String prefix, final Location loc) {
        final List list = Lists.newArrayList((Object[])new Serializable[] { prefix + "locx", loc.getBlockX(), prefix + "locy", loc.getBlockY(), prefix + "locz", loc.getBlockZ(), prefix + "locyaw", loc.getYaw(), prefix + "locpitch", loc.getPitch(), prefix + "chunkx", loc.getChunk().getX(), prefix + "chunkz", loc.getChunk().getZ() });
        list.addAll(a(prefix, loc.getWorld()));
        return list;
    }
    
    private static List a(final String prefix, final World world) {
        return new ArrayList(Arrays.asList(prefix + "world", world.getName(), prefix + "difficulty", world.getDifficulty().name(), prefix + "environment", world.getEnvironment().name()));
    }
    
    @Override
    public String d() {
        return this.e() + "." + this.h + ".cooldown";
    }
    
    @Override
    public boolean c() {
        return true;
    }
    
    @Override
    public boolean b() {
        return true;
    }
    
    private String a(String command, final List list) {
        for (int i = 0; i < list.size(); i += 2) {
            command = command.replace("{" + list.get(i).toString().toLowerCase() + "}", list.get(i + 1).toString());
        }
        return command;
    }
    
    private String a(String command, final Object... objects) {
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] instanceof List) {
                command = this.a(command, (List)objects[i]);
            }
            else {
                command = command.replace("{" + objects[i].toString().toLowerCase() + "}", objects[i + 1].toString());
                ++i;
            }
        }
        return command;
    }
    
    @Override
    public int a(final Player player, final EnchantItemEvent event) {
        return this.a(player, "costExp", event.getExpLevelCost(), a("", event.getItem()), a("", player));
    }
    
    @Override
    public int a(final Player player, final EntityTameEvent event) {
        return this.a(player, a("", (Entity)event.getEntity()), a("", player));
    }
    
    @Override
    public int a(final Player player, final EntityCombustEvent event) {
        return this.a(player, "combustDuration", event.getDuration(), a("", player));
    }
    
    @Override
    public int a(final Player player, final PlayerTeleportEvent event) {
        return this.a(player, "cause", event.getCause().name(), a("", event.getPlayer()));
    }
    
    @Override
    public int a(final Player player, final PlayerItemBreakEvent event) {
        return this.a(player, a("", player), a("", event.getBrokenItem()));
    }
    
    @Override
    public int a(final Player player, final PlayerBucketEmptyEvent event) {
        return this.a(player, a("", player), a("", event.getBlockClicked()));
    }
    
    @Override
    public int a(final Player player, final PlayerBucketFillEvent event) {
        return this.a(player, a("", player), a("", event.getBlockClicked()));
    }
    
    @Override
    public int a(final Player player, final HorseJumpEvent event) {
        return this.a(player, a("", player), a("", event.getEntity()), "power", event.getPower());
    }
    
    @Override
    public int a(final Player player, final EntityRegainHealthEvent event) {
        return this.a(player, a("", player), a("", event.getEntity()), "reason", event.getRegainReason(), "regain", event.getAmount());
    }
    
    @Override
    public int a(final Player player, final PlayerEditBookEvent event) {
        return this.a(player, a("", player), "author", event.getNewBookMeta().hasAuthor() ? event.getNewBookMeta().getAuthor() : "Unknown", "title", event.getNewBookMeta().hasTitle() ? event.getNewBookMeta().getTitle() : "Unknown");
    }
    
    @Override
    public int a(final Player player, final PlayerUnleashEntityEvent event) {
        return this.a(player, a("", player), a("", event.getEntity()), "reason", event.getReason());
    }
    
    @Override
    public int a(final Player player, final PlayerLeashEntityEvent event) {
        return this.a(player, a("", player), a("", event.getEntity()));
    }
    
    @Override
    public int e(final Player player, final Block bed) {
        return this.a(player, a("", player), a("", bed));
    }
    
    @Override
    public int d(final Player player, final Block bed) {
        return this.a(player, a("", player), a("", bed));
    }
    
    @Override
    public int c(final Player player, final Block bed) {
        return this.a(player, a("", player), a("", bed));
    }
    
    @Override
    public int x(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int w(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int v(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int u(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int t(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int s(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int r(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int q(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int p(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int o(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int n(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int m(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int l(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int b(final Player player, final Location respawnLocation) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int a(final Player player, final int oldLevel, final int newLevel) {
        return this.a(player, a("", player), "oldLevel", oldLevel, "newLevel", newLevel);
    }
    
    @Override
    public int a(final Player player, final Location location, final TreeType species) {
        return this.a(player, "TreeType", species.name(), a("", player));
    }
    
    @Override
    public int k(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int j(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int a(final Player player, final aU weatherType) {
        return this.a(player, a("", player), "weather", weatherType.name());
    }
    
    @Override
    public int a(final Player player, final PlayerFishEvent event) {
        return this.a(player, a("", player), "expToDrop", event.getExpToDrop());
    }
    
    @Override
    public int a(final Player player, final Entity target, final Projectile arrow) {
        return this.a(player, a("", player), a("target", (Object)target));
    }
    
    @Override
    public int a(final Player player, final Projectile arrow) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int a(final Player player, final Block block) {
        return this.a(player, a("", player), a("", block));
    }
    
    @Override
    public int b(final Player player, final Block block) {
        return this.a(player, a("", player), a("", block));
    }
    
    @Override
    public int a(final Player player, final LivingEntity killer) {
        return this.a(player, a("", player), a("killer", killer));
    }
    
    @Override
    public int b(final Player player, final LivingEntity target) {
        return this.a(player, a("", player), a("target", target));
    }
    
    @Override
    public int a(final Player player, final ItemStack consumeItem) {
        return this.a(player, a("", player), a("", consumeItem));
    }
    
    @Override
    public int a(final Player player, final PlayerInteractEntityEvent event) {
        return this.a(player, a("", player), a("target", event.getRightClicked()));
    }
    
    @Override
    public int g(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player), a("", event.getClickedBlock()));
    }
    
    @Override
    public int f(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player), a("", event.getClickedBlock()));
    }
    
    @Override
    public int e(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int d(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int c(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int b(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int a(final Player player, final PlayerInteractEvent event) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int i(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int h(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int g(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int f(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int e(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int d(final Player player) {
        return this.a(player, a("", player));
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        return this.a(player, a("", player), a("damager", damager));
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        return this.a(player, a("", player), a("target", target));
    }
    
    @Override
    public int c(final Player player) {
        return this.a(player, a("", player));
    }
    
    private int a(final Player player, final Object... objects) {
        return this.a(player, this.a(this.h, objects));
    }
    
    public int a(final Player player, String command) {
        if (!this.y(player)) {
            return 0;
        }
        if (command.startsWith("c:")) {
            command = command.substring(2);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command);
            return 1;
        }
        PermissionAttachment attachment = null;
        if (this.j.length() != 0 && !this.j.equals("*")) {
            attachment = player.addAttachment((org.bukkit.plugin.Plugin)Plugin.c, 1);
            final String[] perms = this.j.split("\\.");
            final StringBuilder p = new StringBuilder();
            for (final String perm : perms) {
                p.append(perm);
                attachment.setPermission(p.toString(), true);
                p.append('.');
            }
        }
        final boolean wasOp = player.isOp();
        if (this.j.equals("*")) {
            player.setOp(true);
        }
        player.chat("/" + command);
        if (this.j.equals("*")) {
            player.setOp(wasOp);
        }
        else if (attachment != null) {
            player.removeAttachment(attachment);
        }
        return 1;
    }
    
    public String b(final String locale) {
        return ChatColor.GREEN + this.i;
    }
    
    @Override
    public String e() {
        return "command";
    }
    
    public void c(final ConfigurationSection s) {
        this.h = s.getString("command", "");
        this.i = s.getString("display", "");
        this.j = s.getString("permission", "");
    }
    
    public void d(final ConfigurationSection s) {
        s.set("command", (Object)this.h);
        s.set("display", (Object)this.i);
        s.set("permission", (Object)this.j);
    }
}
