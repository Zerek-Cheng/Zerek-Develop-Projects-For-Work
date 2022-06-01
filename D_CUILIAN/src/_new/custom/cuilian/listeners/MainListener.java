package _new.custom.cuilian.listeners;

import _new.custom.cuilian.NewCustomCuiLian;
import _new.custom.cuilian.newapi.NewAPI;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class MainListener implements Listener {
    HashMap ShEntityIdMap = new HashMap();
    HashMap XxEntityIdMap = new HashMap();

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void EntityDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getCause() != DamageCause.ENTITY_ATTACK) {
            double damage = e.getDamage();
            LivingEntity le = (LivingEntity) e.getEntity();
            damage -= NewAPI.getDefense(NewAPI.addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots())).doubleValue();
            if (damage < 0.0D) {
                damage = 0.0D;
            }

            e.setDamage(damage);
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Entity Damager = e.getDamager();
        double damage = e.getDamage();
        if ((Damager instanceof Damageable || Damager instanceof Projectile) && e.getEntity() instanceof Damageable) {
            if (Damager instanceof Damageable) {
                if (e.getEntity() instanceof LivingEntity && Damager instanceof LivingEntity) {
                    LivingEntity psw = (LivingEntity) Damager;
                    LivingEntity ps = (LivingEntity) e.getEntity();
                    List p = NewAPI.addAll(NewAPI.getItemInHand(psw), NewAPI.getItemInOffHand(psw), psw.getEquipment().getHelmet(), psw.getEquipment().getChestplate(), psw.getEquipment().getLeggings(), psw.getEquipment().getBoots());
                    List entity = NewAPI.addAll(NewAPI.getItemInHand(ps), NewAPI.getItemInOffHand(ps), ps.getEquipment().getHelmet(), ps.getEquipment().getChestplate(), ps.getEquipment().getLeggings(), ps.getEquipment().getBoots());
                    damage += NewAPI.getBloodSuck(p).doubleValue() * damage / 100.0D;
                    damage += NewAPI.getDamage(p).doubleValue();
                    if (!psw.isDead()) {
                        if (psw.getHealth() + NewAPI.getBloodSuck(p).doubleValue() * damage / 100.0D < NewAPI.getMaxHealth(psw)) {
                            psw.setHealth(psw.getHealth() + NewAPI.getBloodSuck(p).doubleValue() * damage / 100.0D);
                        } else {
                            psw.setHealth(NewAPI.getMaxHealth(psw));
                        }
                    }
                    //修改

                    if (NewAPI.getReboundDamage(entity).doubleValue() * damage / 100.0D > 0.0D && this.canPVP(psw, ps)) {
                        psw.damage(NewAPI.getReboundDamage(entity).doubleValue() * damage / 100.0D);
                    }

                    damage -= NewAPI.getDefense(entity).doubleValue();

                    try {
                        NewAPI.removeDurability(entity);
                    } catch (NoSuchMethodError var12) {

                    }
                }
            } else {
                Projectile psw1 = (Projectile) Damager;
                ProjectileSource ps1 = psw1.getShooter();
                if (ps1 instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
                    LivingEntity p1 = (LivingEntity) ps1;
                    LivingEntity entity1 = (LivingEntity) e.getEntity();
                    List list2 = NewAPI.addAll(NewAPI.getItemInHand(entity1), NewAPI.getItemInOffHand(entity1), entity1.getEquipment().getHelmet(), entity1.getEquipment().getChestplate(), entity1.getEquipment().getLeggings(), entity1.getEquipment().getBoots());
                    if (NewAPI.getReboundDamage(list2).doubleValue() * damage / 100.0D > 0.0D && this.canPVP(p1, entity1)) {
                        p1.damage(NewAPI.getReboundDamage(list2).doubleValue() * damage / 100.0D);
                    }

                    if (this.XxEntityIdMap.get(Integer.valueOf(psw1.getEntityId())) != null && this.ShEntityIdMap.get(Integer.valueOf(psw1.getEntityId())) != null) {
                        damage += ((Double) this.XxEntityIdMap.get(Integer.valueOf(psw1.getEntityId()))).doubleValue() * damage / 100.0D;
                        damage += ((Double) this.ShEntityIdMap.get(Integer.valueOf(psw1.getEntityId()))).doubleValue();
                        if (!p1.isDead()) {
                            if (p1.getHealth() + ((Double) this.XxEntityIdMap.get(Integer.valueOf(psw1.getEntityId()))).doubleValue() * damage / 100.0D < NewAPI.getMaxHealth(p1)) {
                                p1.setHealth(p1.getHealth() + ((Double) this.XxEntityIdMap.get(Integer.valueOf(psw1.getEntityId()))).doubleValue() * damage / 100.0D);
                            } else {
                                p1.setHealth(NewAPI.getMaxHealth(p1));
                            }
                        }
                        //修改
                    }

                    damage -= NewAPI.getDefense(list2).doubleValue();

                    try {
                        NewAPI.removeDurability(list2);
                    } catch (NoSuchMethodError var11) {
                        ;
                    }
                }
            }
        }

        if (damage < 0.0D) {
            damage = 0.0D;
        }

        e.setDamage(damage);
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void ProjectileLaunchEvent(ProjectileLaunchEvent e) {
        try {
            Projectile psw = e.getEntity();
            ProjectileSource ps = psw.getShooter();
            LivingEntity le = (LivingEntity) ps;
            if (psw == null || ps == null || le == null) return;
            this.ShEntityIdMap.put(Integer.valueOf(psw.getEntityId()), NewAPI.getDamage(NewAPI.addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots())));
            this.XxEntityIdMap.put(Integer.valueOf(psw.getEntityId()), NewAPI.getBloodSuck(NewAPI.addAll(NewAPI.getItemInHand(le), NewAPI.getItemInOffHand(le), le.getEquipment().getHelmet(), le.getEquipment().getChestplate(), le.getEquipment().getLeggings(), le.getEquipment().getBoots())));
        } catch (Exception ex) {

        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        boolean level = false;
        int level1 = NewAPI.getJump(NewAPI.addAll(NewAPI.getItemInHand(p), NewAPI.getItemInOffHand(p), p.getEquipment().getHelmet(), p.getEquipment().getChestplate(), p.getEquipment().getLeggings(), p.getEquipment().getBoots()));
        if (level1 != 0) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, level1));
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void PlayerExpChangeEvent(PlayerExpChangeEvent e) {
        int value = e.getAmount();
        Player p = e.getPlayer();
        Double i = NewAPI.getExperience(NewAPI.addAll(NewAPI.getItemInHand(p), NewAPI.getItemInOffHand(p), p.getInventory().getHelmet(), p.getInventory().getChestplate(), p.getInventory().getLeggings(), p.getInventory().getBoots()));
        boolean jy = false;
        if (i.doubleValue() != 0.0D) {
            int jy1 = (int) ((double) value * (100.0D + i.doubleValue()) / 100.0D);
            e.setAmount(jy1);
        }
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (NewCustomCuiLian.playerSuitEffectList.containsKey(p.getUniqueId())) {
            NewAPI.setMaxHealth(p, ((Double) NewCustomCuiLian.playerSuitEffectHealthList.get(p.getUniqueId())).doubleValue());
            NewCustomCuiLian.playerSuitEffectHealthList.remove(p.getUniqueId());
            NewCustomCuiLian.playerSuitEffectList.remove(p.getUniqueId());
        }

    }

    public boolean canPVP(LivingEntity a, LivingEntity b) {
        return false;
    }
}
