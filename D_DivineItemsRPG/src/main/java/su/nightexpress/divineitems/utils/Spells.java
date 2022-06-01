/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Fireball
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 *  org.bukkit.util.EulerAngle
 *  org.bukkit.util.Vector
 */
package su.nightexpress.divineitems.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import su.nightexpress.divineitems.DivineItems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Spells {
    private static Random r = new Random();
    private static DivineItems plugin = DivineItems.instance;
    private static HashMap<Player, HashSet<LivingEntity>> ice = new HashMap();
    private static HashMap<Projectile, String> pjef = new HashMap();

    public static void skillIceSnake(final Player player) {
/*        int n = 20;
        final double d = ItemAPI.getItemTotalDamage(player.getInventory().getItemInHand());
        final ArrayList arrayList = new ArrayList();
        Location location = player.getEyeLocation().add(0.0, -2.2, 0.0);
        final Vector vector = location.getDirection();
        new BukkitRunnable(){
            int i = 0;

            public void run() {
                if (this.i == 20) {
                    this.cancel();
                }
                Location location = Location.this.add(vector);
                if (this.i % 5 == 0) {
                    location = Utils.getDirection(Float.valueOf(player.getLocation().getYaw())) == "EAST" || Utils.getDirection(Float.valueOf(player.getLocation().getYaw())) == "WEST" ? location.add(0.0, 0.0, 0.33) : location.add(0.33, 0.0, 0.0);
                }
                ArmorStand armorStand = (ArmorStand)player.getWorld().spawn(location, ArmorStand.class);
                armorStand.setVisible(false);
                armorStand.setHelmet(new ItemStack(Material.ICE));
                armorStand.setGravity(false);
                armorStand.setInvulnerable(true);
                armorStand.setSmall(false);
                Spells.setHeadPos(armorStand);
                arrayList.add(armorStand);
                EntityUtils.add((Entity)armorStand);
                Utils.playEffect("BLOCK_CRACK:ICE", 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, location.clone().add(0.0, 2.0, 0.0));
                Location location2 = location.clone().add(Utils.getRandDoubleNega(-0.25, 0.25), Utils.getRandDouble(0.2, 0.5), Utils.getRandDoubleNega(-0.25, 0.25));
                ArmorStand armorStand2 = (ArmorStand)player.getWorld().spawn(location2, ArmorStand.class);
                armorStand2.setVisible(false);
                armorStand2.setHelmet(new ItemStack(Material.SNOW_BLOCK));
                armorStand2.setGravity(false);
                armorStand2.setInvulnerable(true);
                armorStand2.setSmall(true);
                Spells.setHeadPos(armorStand2);
                arrayList.add(armorStand2);
                EntityUtils.add((Entity)armorStand2);
                Utils.playEffect("BLOCK_CRACK:SNOW", 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, location2.clone().add(0.0, 0.75, 0.0));
                for (LivingEntity livingEntity : EntityUtils.getEnemies((Entity)armorStand, 1.15, player)) {
                    if (ice.get((Object)player) != null && ((HashSet)ice.get((Object)player)).contains((Object)livingEntity)) continue;
                    livingEntity.damage(d, (Entity)player);
                    if (livingEntity.hasPotionEffect(PotionEffectType.SLOW)) {
                        livingEntity.removePotionEffect(PotionEffectType.SLOW);
                    }
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
                    if (ice.get((Object)player) == null) {
                        HashSet<LivingEntity> hashSet = new HashSet<LivingEntity>();
                        hashSet.add(livingEntity);
                        ice.put(player, hashSet);
                        continue;
                    }
                    ((HashSet)ice.get((Object)player)).add(livingEntity);
                }
                if (new Location(location.getWorld(), location.getX(), location.getY() + 1.5, location.getZ()).getBlock().getType() != Material.AIR) {
                    this.cancel();
                }
                ++this.i;
            }
        }.runTaskTimer((Plugin)plugin, 0L, 1L);
        new BukkitRunnable(){

            public void run() {
                for (ArmorStand armorStand : List.this) {
                    Utils.playEffect("FIREWORKS_SPARK", 0.0, 0.0, 0.0, 0.20000000298023224, 5, armorStand.getLocation().add(0.0, 2.0, 0.0));
                    armorStand.remove();
                    EntityUtils.remove((Entity)armorStand);
                }
            }
        }.runTaskLater((Plugin)plugin, 50L);
        if (ice.get((Object)player) != null) {
            ice.get((Object)player).clear();
            ice.remove((Object)player);
        }
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.7f, 0.7f);*/
    }

    private static void setHeadPos(Object armorStand) {

    }

    public static void skillMeteor(final Player player) {
/*        int n = 50;
        Block block = player.getTargetBlock(null, n);
        final Location location = block.getLocation();
        Location location2 = new Location(location.getWorld(), location.getX(), location.getY() + 20.0, location.getZ());
        Location location3 = block.getLocation();
        Vector vector = new Location(location.getWorld(), location.getX(), location.getY() + 20.0, location.getZ()).toVector();
        location3.setDirection(vector.subtract(location3.toVector()));
        Vector vector2 = location3.getDirection();
        int n2 = 0;
        while ((double)n2 < location2.distance(location)) {
            Location location4 = location3.add(vector2);
            Utils.playEffect("FLAME", 0.0, 0.0, 0.0, 0.0, 25, location4);
            ++n2;
        }
        new BukkitRunnable(){

            public void run() {
                Location location2 = new Location(Location.this.getWorld(), Location.this.getX() + (double)r.nextInt(10), Location.this.getY(), Location.this.getZ() + (double)r.nextInt(10));
                Vector vector = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()).toVector();
                location2.setDirection(vector.subtract(location2.toVector()));
                Vector vector2 = location2.getDirection();
                Fireball fireball = (Fireball)location2.getWorld().spawn(location2, Fireball.class);
                fireball.setShooter((ProjectileSource)player);
                fireball.setDirection(vector2);
                fireball.setVelocity(vector2.normalize());
                pjef.put(fireball, "SMOKE_LARGE");
            }
        }.runTaskLater((Plugin)plugin, 30L);
        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.7f, 0.7f);*/
    }

    public static void startPjEfTask() {
/*        plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, new Runnable(){

            @Override
            public void run() {
                HashMap hashMap = new HashMap(pjef);
                for (Projectile projectile : hashMap.keySet()) {
                    if (projectile.isOnGround() || !projectile.isValid()) {
                        pjef.remove((Object)projectile);
                        continue;
                    }
                    String string = (String)hashMap.get((Object)projectile);
                    Utils.playEffect(string, 0.0, 0.0, 0.0, 0.0, 25, projectile.getLocation());
                }
            }
        }, 0L, 1L);*/
    }

    public static void skillIceFireStorm(Player player, final int n) {
/*        String string;
        String string2;
        ItemStack itemStack;
        int n2 = 50;
        Block block = player.getTargetBlock(null, n2);
        final Location location = block.getLocation();
        final Location location2 = location.clone().add(0.0, 20.0, 0.0);
        if (n == 0) {
            itemStack = new ItemStack(Material.ICE);
            string2 = "CLOUD";
            string = "FIREWORKS_SPARK";
        } else {
            itemStack = new ItemStack(Material.NETHERRACK);
            string2 = "LAVA";
            string = "FLAME";
        }
        new BukkitRunnable(){

            public void run() {
                new BukkitRunnable(Player.this, location2, location, itemStack, string2, n, string){
                    int i = 0;
                    private final *//* synthetic *//* Player val$p;
                    private final *//* synthetic *//* Location val$sky;
                    private final *//* synthetic *//* Location val$ground;
                    private final *//* synthetic *//* ItemStack val$block;
                    private final *//* synthetic *//* String val$p1;
                    private final *//* synthetic *//* int val$j;
                    private final *//* synthetic *//* String val$p2;
                    {
                        this.val$p = player;
                        this.val$sky = location;
                        this.val$ground = location2;
                        this.val$block = itemStack;
                        this.val$p1 = string;
                        this.val$j = n;
                        this.val$p2 = string2;
                    }

                    public void run() {
                        if (this.i == 8) {
                            this.cancel();
                            return;
                        }
                        double d = ItemAPI.getItemTotalDamage(this.val$p.getInventory().getItemInMainHand());
                        final Location location = this.val$sky.clone().add((double)r.nextInt(10), 0.0, (double)r.nextInt(10));
                        Vector vector = this.val$ground.clone().add((double)r.nextInt(5), 0.0, (double)r.nextInt(5)).toVector();
                        location.setDirection(vector.subtract(location.toVector()));
                        final Vector vector2 = location.getDirection();
                        final ArmorStand armorStand = (ArmorStand)this.val$p.getWorld().spawn(location, ArmorStand.class);
                        armorStand.setVisible(false);
                        armorStand.setHelmet(this.val$block);
                        armorStand.setGravity(false);
                        armorStand.setInvulnerable(true);
                        armorStand.setSmall(true);
                        Spells.setHeadPos(armorStand);
                        new BukkitRunnable(this.val$p1, this.val$p, d, this.val$j, this.val$p2, this.val$block){
                            private final *//* synthetic *//* String val$p1;
                            private final *//* synthetic *//* Player val$p;
                            private final *//* synthetic *//* double val$dmg;
                            private final *//* synthetic *//* int val$j;
                            private final *//* synthetic *//* String val$p2;
                            private final *//* synthetic *//* ItemStack val$block;
                            {
                                this.val$p1 = string;
                                this.val$p = player;
                                this.val$dmg = d;
                                this.val$j = n;
                                this.val$p2 = string2;
                                this.val$block = itemStack;
                            }

                            public void run() {
                                Location location3 = location.add(vector2);
                                armorStand.teleport(location3);
                                Location location2 = armorStand.getEyeLocation().clone().add(0.0, 1.3, 0.0);
                                if (location2.getBlock() != null && location2.getBlock().getType() != Material.AIR) {
                                    Utils.playEffect(this.val$p1, 1.25, 0.20000000298023224, 1.25, 0.10000000149011612, 100, location2);
                                    for (LivingEntity livingEntity : EntityUtils.getEnemies((Entity)armorStand, 2.0, this.val$p)) {
                                        livingEntity.damage(this.val$dmg, (Entity)this.val$p);
                                        if (this.val$j == 0) {
                                            if (livingEntity.hasPotionEffect(PotionEffectType.SLOW)) {
                                                livingEntity.removePotionEffect(PotionEffectType.SLOW);
                                            }
                                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
                                            continue;
                                        }
                                        livingEntity.setFireTicks(100);
                                    }
                                    location2.getWorld().playSound(location2, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                                    armorStand.remove();
                                    this.cancel();
                                    return;
                                }
                                location2.getWorld().playSound(location2, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                                Utils.playEffect(this.val$p2, 0.0, 0.0, 0.0, 0.25, 15, armorStand.getEyeLocation().clone().add(0.2, 1.0, 0.2));
                                Utils.playEffect("BLOCK_CRACK:" + this.val$block.getType().name(), 0.0, 0.0, 0.0, 0.30000001192092896, 15, armorStand.getEyeLocation().clone().add(0.2, 1.0, 0.2));
                            }
                        }.runTaskTimer((Plugin)plugin, 0L, 1L);
                        ++this.i;
                    }

                }.runTaskTimer((Plugin)plugin, 0L, 15L);
            }

        }.runTaskLater((Plugin)plugin, 30L);*/
    }

}

