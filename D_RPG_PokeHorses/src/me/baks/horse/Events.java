package me.baks.horse;

import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_11_R1.AttributeInstance;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

public class Events
        implements Listener {
    static Main plugin = Main.plugin;

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final String playerName = player.getName();
        final Item item = event.getItemDrop();
        final ItemStack stack = item.getItemStack();
        if ((stack != null) && (stack.getTypeId() == ConfigManager.ITEM_ID)) {
            if (Utils.getPetName(stack) == null) {
                return;
            }
            item.setCustomName(Utils.getPetName(stack));
            if (PetsManager.checkPetPlayer(playerName)) {
                if (ConfigManager.COOLDOWN_ENABLE) {
                    if (CooldownManager.cooldown.containsKey(playerName)) {
                        if (!CooldownManager.checkTime(playerName)) {
                            player.sendMessage(ConfigManager.COOLDOWN_MSG.replace("%time", String.valueOf(ConfigManager.COOLDOWN_TIME - CooldownManager.getTime(playerName))));
                            event.setCancelled(true);
                        }
                    } else {
                        CooldownManager.addCooldown(playerName);
                    }
                }
                item.setPickupDelay(999999);
                item.setGlowing(true);
                item.setCustomNameVisible(true);

                new BukkitRunnable() {
                    public void run() {
                        Location loc = item.getLocation();
                        item.remove();
                        ParticleEffect_V1_11_R1.playEffect(player.getWorld().getPlayers(), "LAVA", (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0.0F, 0.0F, 0.0F, 1, 20);

                        Horse horse = (Horse) player.getWorld().spawn(loc, Horse.class);
                        String petName = Utils.getPetName(stack);

                        horse.setOwner(player);
                        horse.setTamed(true);
                        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                        horse.setPassenger(player);
                        horse.setAgeLock(true);
                        horse.setAge(0);
                        HorseInventory inv = horse.getInventory();
                        inv.setArmor(Utils.getPetArmor(stack));
                        if (Utils.getPetStyle(stack) != null) {
                            horse.setStyle(Utils.getPetStyle(stack));
                        }
                        if (Utils.getPetColor(stack) != null) {
                            horse.setColor(Utils.getPetColor(stack));
                        }
                        horse.setCustomName(petName);
                        horse.setCustomNameVisible(true);
                        if (Utils.getPetInventory(stack).booleanValue()) {
                            horse.setCarryingChest(true);
                        }
                        ((EntityLiving) ((CraftEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Utils.getPetSpeed(stack));
                        horse.setJumpStrength(Utils.getPetJump(stack));
                        if (Utils.getPetInvulnerable(stack).booleanValue()) {
                            horse.setInvulnerable(true);
                        } else {
                            horse.setMaxHealth(Utils.getPetHealth(stack));
                            horse.setHealth(Utils.getPetHealth(stack));
                        }
                        PetsManager.addPet(playerName, horse, stack);
                        player.sendMessage(ConfigManager.MSG_SPAWNED.replace("%horse", petName));
                        if (!Events.plugin.effects.containsKey(playerName)) {
                            player.sendMessage(ConfigManager.MSG_NEED_EFFECT);
                        }
                    }
                }.runTaskLater(plugin, 50L);
            }
        }
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        ItemStack item = event.getItem();
        if (!ConfigManager.RIGHT_CLICK) {
            return;
        }
        if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) &&
                (item != null) && (item.getTypeId() == ConfigManager.ITEM_ID)) {
            if (Utils.getPetName(item) == null) {
                return;
            }
            if (PetsManager.checkPetPlayer(playerName)) {
                if (ConfigManager.COOLDOWN_ENABLE) {
                    if (CooldownManager.cooldown.containsKey(playerName)) {
                        if (!CooldownManager.checkTime(playerName)) {
                            player.sendMessage(ConfigManager.COOLDOWN_MSG.replace("%time", String.valueOf(ConfigManager.COOLDOWN_TIME - CooldownManager.getTime(playerName))));
                        }
                    } else {
                        CooldownManager.addCooldown(playerName);
                    }
                }
                Horse horse = (Horse) player.getWorld().spawn(player.getLocation(), Horse.class);
                String petName = Utils.getPetName(item);

                horse.setOwner(player);
                horse.setTamed(true);
                horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                horse.setPassenger(player);
                horse.setAgeLock(true);
                horse.setAge(0);
                HorseInventory inv = horse.getInventory();
                inv.setArmor(Utils.getPetArmor(item));
                if (Utils.getPetStyle(item) != null) {
                    horse.setStyle(Utils.getPetStyle(item));
                }
                if (Utils.getPetColor(item) != null) {
                    horse.setColor(Utils.getPetColor(item));
                }
                horse.setCustomName(petName);
                horse.setCustomNameVisible(true);
                if (Utils.getPetInventory(item).booleanValue()) {
                    horse.setCarryingChest(true);
                }
                ((EntityLiving) ((CraftEntity) horse).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Utils.getPetSpeed(item));
                horse.setJumpStrength(Utils.getPetJump(item));
                if (Utils.getPetInvulnerable(item).booleanValue()) {
                    horse.setInvulnerable(true);
                } else {
                    horse.setMaxHealth(Utils.getPetHealth(item));
                    horse.setHealth(Utils.getPetHealth(item));
                }
                PetsManager.addPet(playerName, horse, item);
                player.getInventory().remove(item);
                player.sendMessage(ConfigManager.MSG_SPAWNED.replace("%horse", petName));
                if (!plugin.effects.containsKey(playerName)) {
                    player.sendMessage(ConfigManager.MSG_NEED_EFFECT);
                }
            }
        }
    }

    @EventHandler
    public void onDeathPet(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() == EntityType.HORSE) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                String playerName = player.getName();
                if ((!PetsManager.checkPetPlayer(playerName)) &&
                        (((LivingEntity) PetsManager.pets.get(playerName)).getType() == entity.getType())) {
                    player.sendMessage(ConfigManager.MSG_KILLED);
                    Manager.addItemPlayer(PetsManager.getItem(playerName), player);
                    PetsManager.removePet(playerName);
                    event.getDrops().clear();
                    if (ConfigManager.COOLDOWN_ENABLE) {
                        CooldownManager.addCooldown(playerName);
                    }
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPetDismount(EntityDismountEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        Entity entity = event.getDismounted();
        if ((entity instanceof LivingEntity)) {
            String playerName = player.getName();
            if (!PetsManager.checkPetPlayer(playerName)) {
                Manager.addItemPlayer(PetsManager.getItem(playerName), player);
                ((LivingEntity) PetsManager.pets.get(playerName)).remove();
                PetsManager.removePet(playerName);
                if (ConfigManager.COOLDOWN_ENABLE) {
                    CooldownManager.addCooldown(playerName);
                }
                player.sendMessage(ConfigManager.MSG_DISMOUNT);
            }
        }
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!PetsManager.checkPetPlayer(playerName)) {
            Manager.addItemPlayer(PetsManager.getItem(playerName), player);
            ((LivingEntity) PetsManager.pets.get(playerName)).remove();
            PetsManager.removePet(playerName);
            if (ConfigManager.COOLDOWN_ENABLE) {
                CooldownManager.addCooldown(playerName);
            }
            if (plugin.effects.containsKey(playerName)) {
                plugin.effects.remove(playerName);
            }
            player.sendMessage(ConfigManager.MSG_DISMOUNT);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!ConfigManager.EFFECTS) {
            return;
        }
        Player player = event.getPlayer();
        String playerName = player.getName();
        if ((!PetsManager.checkPetPlayer(playerName)) &&
                (plugin.effects.containsKey(playerName))) {
            String effect = (String) plugin.effects.get(playerName);
            if (effect.equalsIgnoreCase("DISABLED")) {
                return;
            }
            Location loc = player.getLocation();
            ParticleEffect_V1_11_R1.playEffect(player.getWorld().getPlayers(), effect, (float) loc.getX() + Utils.getRandom(0.2D), (float) (loc.getY() - 0.4D) + Utils.getRandom(0.2D), (float) loc.getZ() + Utils.getRandom(0.2D), 0.0F, 0.0F, 0.0F, 1, 3);
        }
    }
}
