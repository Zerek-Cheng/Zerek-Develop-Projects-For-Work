/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Difficulty
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.TreeType
 *  org.bukkit.WeatherType
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Biome
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Ageable
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.Horse$Color
 *  org.bukkit.entity.Horse$Style
 *  org.bukkit.entity.Horse$Variant
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.enchantment.EnchantItemEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.event.entity.EntityTameEvent
 *  org.bukkit.event.entity.EntityUnleashEvent
 *  org.bukkit.event.entity.EntityUnleashEvent$UnleashReason
 *  org.bukkit.event.entity.HorseJumpEvent
 *  org.bukkit.event.entity.PlayerLeashEntityEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerEditBookEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.event.player.PlayerUnleashEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BookMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.permissions.PermissionAttachment
 *  org.bukkit.plugin.Plugin
 */
package yo;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import think.rpgitems.Plugin;
import yo.au_0;
import yo.ay_1;
import yo.bi_1;

public class br_1
extends bi_1 {
    public String h = "";
    public String i = "Runs command";
    public String j = "";
    private static final String k = "Unknown";

    private static List a(String prefix, Object object) {
        if (object instanceof Entity) {
            if (object instanceof Player) {
                return br_1.a(prefix, (Player)object);
            }
            if (object instanceof Horse) {
                return br_1.a(prefix, (Horse)object);
            }
            return br_1.a(prefix, (Entity)object);
        }
        return new ArrayList();
    }

    private static List<?> a(String prefix, Entity entity) {
        Object[] arrobject = new String[4];
        arrobject[0] = prefix + "entityType";
        arrobject[1] = entity.getType().name();
        arrobject[2] = prefix + "entityName";
        arrobject[3] = entity.getCustomName() != null ? entity.getCustomName() : k;
        return Lists.newArrayList((Object[])arrobject);
    }

    private static List<?> a(String prefix, ItemStack item) {
        Object[] arrobject = new Serializable[8];
        arrobject[0] = prefix + "itemType";
        arrobject[1] = item.getType().name();
        arrobject[2] = prefix + "itemData";
        arrobject[3] = Short.valueOf(item.getDurability());
        arrobject[4] = prefix + "itemAmount";
        arrobject[5] = Integer.valueOf(item.getAmount());
        arrobject[6] = prefix + "itemName";
        arrobject[7] = item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : k;
        return Lists.newArrayList((Object[])arrobject);
    }

    private static List<?> a(String prefix, Player player) {
        Object[] arrobject = new Serializable[32];
        arrobject[0] = prefix + "player";
        arrobject[1] = player.getName();
        arrobject[2] = prefix + "display";
        arrobject[3] = player.getDisplayName() != null ? player.getDisplayName() : k;
        arrobject[4] = prefix + "ip";
        arrobject[5] = player.getAddress().getAddress().getCanonicalHostName();
        arrobject[6] = prefix + "flyspeed";
        arrobject[7] = Float.valueOf(player.getFlySpeed());
        arrobject[8] = prefix + "walkspeed";
        arrobject[9] = Float.valueOf(player.getWalkSpeed());
        arrobject[10] = prefix + "exptolevel";
        arrobject[11] = Integer.valueOf(player.getExpToLevel());
        arrobject[12] = prefix + "food";
        arrobject[13] = Integer.valueOf(player.getFoodLevel());
        arrobject[14] = prefix + "hp";
        arrobject[15] = Double.valueOf(player.getHealth());
        arrobject[16] = prefix + "gamemode";
        arrobject[17] = player.getGameMode().name();
        arrobject[18] = prefix + "level";
        arrobject[19] = Integer.valueOf(player.getLevel());
        arrobject[20] = prefix + "maxhp";
        arrobject[21] = Double.valueOf(player.getMaxHealth());
        arrobject[22] = prefix + "listname";
        arrobject[23] = player.getPlayerListName() != null ? player.getPlayerListName() : k;
        arrobject[24] = prefix + "weather";
        arrobject[25] = player.getPlayerWeather() != null ? player.getPlayerWeather() : (player.getWorld().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR).name();
        arrobject[26] = prefix + "saturation";
        arrobject[27] = Float.valueOf(player.getSaturation());
        arrobject[28] = prefix + "exp";
        arrobject[29] = Integer.valueOf(player.getTotalExperience());
        arrobject[30] = prefix + "uuid";
        arrobject[31] = player.getUniqueId().toString();
        ArrayList list = Lists.newArrayList((Object[])arrobject);
        list.addAll(br_1.a(prefix, player.getLocation()));
        return list;
    }

    private static List<?> a(String prefix, Block block) {
        return Lists.newArrayList((Object[])new Serializable[]{prefix + "biome", block.getBiome().name(), prefix + "lightlevel", Byte.valueOf(block.getLightLevel()), prefix + "blockType", block.getType().name(), prefix + "blockTypeId", Integer.valueOf(block.getTypeId()), prefix + "blockTypeData", Byte.valueOf(block.getData())});
    }

    private static List<?> a(String prefix, Ageable ageable) {
        return Lists.newArrayList((Object[])new Object[]{br_1.a(prefix, (Entity)ageable), prefix + "age", ageable.getAge()});
    }

    private static List a(String prefix, Horse horse) {
        ArrayList list = Lists.newArrayList((Object[])new Serializable[]{prefix + "domestication", Integer.valueOf(horse.getDomestication()), prefix + "color", horse.getColor().name(), prefix + "jumpstrength", Double.valueOf(horse.getJumpStrength()), prefix + "style", horse.getStyle().name(), prefix + "variant", horse.getVariant(), prefix + "maxdomestication", Integer.valueOf(horse.getMaxDomestication())});
        list.addAll(br_1.a(prefix, (Ageable)horse));
        return list;
    }

    private static List a(String prefix, Location loc) {
        ArrayList list = Lists.newArrayList((Object[])new Serializable[]{prefix + "locx", Integer.valueOf(loc.getBlockX()), prefix + "locy", Integer.valueOf(loc.getBlockY()), prefix + "locz", Integer.valueOf(loc.getBlockZ()), prefix + "locyaw", Float.valueOf(loc.getYaw()), prefix + "locpitch", Float.valueOf(loc.getPitch()), prefix + "chunkx", Integer.valueOf(loc.getChunk().getX()), prefix + "chunkz", Integer.valueOf(loc.getChunk().getZ())});
        list.addAll(br_1.a(prefix, loc.getWorld()));
        return list;
    }

    private static List a(String prefix, World world) {
        return new ArrayList<String>(Arrays.asList(prefix + "world", world.getName(), prefix + "difficulty", world.getDifficulty().name(), prefix + "environment", world.getEnvironment().name()));
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

    private String a(String command, List list) {
        for (int i = 0; i < list.size(); i += 2) {
            command = command.replace("{" + list.get(i).toString().toLowerCase() + "}", list.get(i + 1).toString());
        }
        return command;
    }

    private /* varargs */ String a(String command, Object ... objects) {
        for (int i = 0; i < objects.length; ++i) {
            if (objects[i] instanceof List) {
                command = this.a(command, (List)objects[i]);
                continue;
            }
            command = command.replace("{" + objects[i].toString().toLowerCase() + "}", objects[i + 1].toString());
            ++i;
        }
        return command;
    }

    @Override
    public int a(Player player, EnchantItemEvent event) {
        return this.a(player, "costExp", event.getExpLevelCost(), br_1.a("", event.getItem()), br_1.a("", player));
    }

    @Override
    public int a(Player player, EntityTameEvent event) {
        return this.a(player, br_1.a("", (Entity)event.getEntity()), br_1.a("", player));
    }

    @Override
    public int a(Player player, EntityCombustEvent event) {
        return this.a(player, "combustDuration", event.getDuration(), br_1.a("", player));
    }

    @Override
    public int a(Player player, PlayerTeleportEvent event) {
        return this.a(player, "cause", event.getCause().name(), br_1.a("", event.getPlayer()));
    }

    @Override
    public int a(Player player, PlayerItemBreakEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getBrokenItem()));
    }

    @Override
    public int a(Player player, PlayerBucketEmptyEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getBlockClicked()));
    }

    @Override
    public int a(Player player, PlayerBucketFillEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getBlockClicked()));
    }

    @Override
    public int a(Player player, HorseJumpEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getEntity()), "power", Float.valueOf(event.getPower()));
    }

    @Override
    public int a(Player player, EntityRegainHealthEvent event) {
        return this.a(player, new Object[]{br_1.a("", player), br_1.a("", event.getEntity()), "reason", event.getRegainReason(), "regain", event.getAmount()});
    }

    @Override
    public int a(Player player, PlayerEditBookEvent event) {
        Object[] arrobject = new Object[5];
        arrobject[0] = br_1.a("", player);
        arrobject[1] = "author";
        arrobject[2] = event.getNewBookMeta().hasAuthor() ? event.getNewBookMeta().getAuthor() : k;
        arrobject[3] = "title";
        arrobject[4] = event.getNewBookMeta().hasTitle() ? event.getNewBookMeta().getTitle() : k;
        return this.a(player, arrobject);
    }

    @Override
    public int a(Player player, PlayerUnleashEntityEvent event) {
        return this.a(player, new Object[]{br_1.a("", player), br_1.a("", event.getEntity()), "reason", event.getReason()});
    }

    @Override
    public int a(Player player, PlayerLeashEntityEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getEntity()));
    }

    @Override
    public int e(Player player, Block bed) {
        return this.a(player, br_1.a("", player), br_1.a("", bed));
    }

    @Override
    public int d(Player player, Block bed) {
        return this.a(player, br_1.a("", player), br_1.a("", bed));
    }

    @Override
    public int c(Player player, Block bed) {
        return this.a(player, br_1.a("", player), br_1.a("", bed));
    }

    @Override
    public int x(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int w(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int v(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int u(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int t(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int s(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int r(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int q(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int p(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int o(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int n(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int m(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int l(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int b(Player player, Location respawnLocation) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int a(Player player, int oldLevel, int newLevel) {
        return this.a(player, br_1.a("", player), "oldLevel", oldLevel, "newLevel", newLevel);
    }

    @Override
    public int a(Player player, Location location, TreeType species) {
        return this.a(player, "TreeType", species.name(), br_1.a("", player));
    }

    @Override
    public int k(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int j(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int a(Player player, au_0 weatherType) {
        return this.a(player, br_1.a("", player), "weather", weatherType.name());
    }

    @Override
    public int a(Player player, PlayerFishEvent event) {
        return this.a(player, br_1.a("", player), "expToDrop", event.getExpToDrop());
    }

    @Override
    public int a(Player player, Entity target, Projectile arrow) {
        return this.a(player, br_1.a("", player), br_1.a("target", (Object)target));
    }

    @Override
    public int a(Player player, Projectile arrow) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int a(Player player, Block block) {
        return this.a(player, br_1.a("", player), br_1.a("", block));
    }

    @Override
    public int b(Player player, Block block) {
        return this.a(player, br_1.a("", player), br_1.a("", block));
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        return this.a(player, br_1.a("", player), br_1.a("killer", (Object)killer));
    }

    @Override
    public int b(Player player, LivingEntity target) {
        return this.a(player, br_1.a("", player), br_1.a("target", (Object)target));
    }

    @Override
    public int a(Player player, ItemStack consumeItem) {
        return this.a(player, br_1.a("", player), br_1.a("", consumeItem));
    }

    @Override
    public int a(Player player, PlayerInteractEntityEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("target", event.getRightClicked()));
    }

    @Override
    public int g(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getClickedBlock()));
    }

    @Override
    public int f(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player), br_1.a("", event.getClickedBlock()));
    }

    @Override
    public int e(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int d(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int c(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int b(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int a(Player player, PlayerInteractEvent event) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int i(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int h(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int g(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int f(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int e(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int d(Player player) {
        return this.a(player, br_1.a("", player));
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        return this.a(player, br_1.a("", player), br_1.a("damager", (Object)damager));
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        return this.a(player, br_1.a("", player), br_1.a("target", (Object)target));
    }

    @Override
    public int c(Player player) {
        return this.a(player, br_1.a("", player));
    }

    private /* varargs */ int a(Player player, Object ... objects) {
        return this.a(player, this.a(this.h, objects));
    }

    public int a(Player player, String command) {
        if (this.y(player)) {
            if (command.startsWith("c:")) {
                command = command.substring(2);
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)command);
                return 1;
            }
            PermissionAttachment attachment = null;
            if (this.j.length() != 0 && !this.j.equals("*")) {
                attachment = player.addAttachment((org.bukkit.plugin.Plugin)Plugin.c, 1);
                String[] perms = this.j.split("\\.");
                StringBuilder p = new StringBuilder();
                for (String perm : perms) {
                    p.append(perm);
                    attachment.setPermission(p.toString(), true);
                    p.append('.');
                }
            }
            boolean wasOp = player.isOp();
            if (this.j.equals("*")) {
                player.setOp(true);
            }
            player.chat("/" + command);
            if (this.j.equals("*")) {
                player.setOp(wasOp);
            } else if (attachment != null) {
                player.removeAttachment(attachment);
            }
            return 1;
        }
        return 0;
    }

    @Override
    public String b(String locale) {
        return (Object)ChatColor.GREEN + this.i;
    }

    @Override
    public String e() {
        return "command";
    }

    @Override
    public void c(ConfigurationSection s) {
        this.h = s.getString("command", "");
        this.i = s.getString("display", "");
        this.j = s.getString("permission", "");
    }

    @Override
    public void d(ConfigurationSection s) {
        s.set("command", (Object)this.h);
        s.set("display", (Object)this.i);
        s.set("permission", (Object)this.j);
    }
}

