package com._0myun.minecraft.damagecompensate;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class DamageCompensate extends JavaPlugin implements Listener {
    HashMap<Integer, Integer> healthPair = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> pairDamage = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("ok");
            reloadConfig();
            return true;
        }
        if (!sender.isOp()) return true;
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        List<String> items = getConfig().getStringList("items");
        if (items.contains(itemInHand.getType().toString() + ":" + itemInHand.getData().toString())) {
            p.sendMessage("already");
            return true;
        }
        items.add(itemInHand.getType().toString() + ":" + itemInHand.getData().toString());
        getConfig().set("items", items);
        saveConfig();
        p.sendMessage("ok");
        return true;
    }

    //生命=》数组下标
    //数组下表=》配置=》伤害配置=》伤害=》伤害配置下标
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().warning("§4定制插件找灵梦云科技q2025255093");
        getLogger().warning("§4定制插件找灵梦云科技q2025255093");
        getLogger().warning("§4定制插件找灵梦云科技q2025255093");

        getLogger().info("数据初始化...");

        int pairId = 0;
        for (Map<?, ?> rule : getConfig().getMapList("rules")) {
            int healthMin = (Integer) rule.get("health-min");
            int healthMax = (Integer) rule.get("health-max");
            for (int i = healthMin; i <= healthMax; i++) {
                this.healthPair.put(i, pairId);
            }
            pairId++;
        }
        this.healthPair.forEach((_health, _pairId) -> {//血量 下标
            HashMap<Integer, Integer> damagePair = new HashMap<>();//伤害 下标
            Map<?, ?> pairConfig = getConfig().getMapList("rules").get(_pairId);
            List<Map<?, ?>> damageRules = (List<Map<?, ?>>) pairConfig.get("damage");
            int damagePairId = 0;
            for (Map<?, ?> damageRule : damageRules) {
                int damageMin = (Integer) damageRule.get("damage-min");
                int damageMax = (Integer) damageRule.get("damage-max");
                for (int i = damageMin; i <= damageMax; i++) {
                    damagePair.put(i, damagePairId);
                }
                damagePairId++;
            }
            this.pairDamage.put(_pairId, damagePair);
        });

        getLogger().info("数据初始化结束");

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Player dP = (Player) e.getDamager();
     //   p.setMaxHealth(110);
      //  dP.setMaxHealth(20);
        if (dP.getItemInHand() == null || dP.getItemInHand().getType().equals(Material.AIR)) return;
        if (!getConfig().getStringList("worlds").contains(p.getWorld().getName())) return;
        if (!getConfig().getStringList("items").contains(dP.getItemInHand().getType().toString() + ":" + dP.getItemInHand().getData().toString()))
            return;
        int damage = Double.valueOf(e.getDamage()).intValue();
        int health = Double.valueOf(p.getMaxHealth() - dP.getMaxHealth()).intValue();
     //   System.out.println("damage = " + damage);
      //  System.out.println("health = " + health);

        if (!this.healthPair.containsKey(health)) return;
        int pairId = this.healthPair.get(health);
    //    System.out.println("pairId = " + pairId);
        HashMap<Integer, Integer> damagePair = this.pairDamage.get(pairId);
        if (!damagePair.containsKey(damage)) return;
        int damagePairId = damagePair.get(damage);
   //     System.out.println("damagePairId = " + damagePairId);
        Map damageConfig = ((List<Map>) getConfig().getMapList("rules").get(pairId).get("damage")).get(damagePairId);
        if (damageConfig == null || damageConfig.isEmpty()) return;
        int additionMin = (int) damageConfig.get("addition-min");
        int additionMax = (int) damageConfig.get("addition-max");
        int add = additionMax - additionMin != 0 ? new Random().nextInt(additionMax - additionMin) + additionMin : additionMin;
 //       System.out.println("add = " + add);
   //     System.out.println("e.getDamage() = " + e.getDamage());
        e.setDamage(e.getDamage() + add);
  //      System.out.println("e.getDamage() = " + e.getDamage());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageArrow(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Projectile) || !(((Projectile) e.getDamager()).getShooter() instanceof Player))
            return;
        Player p = (Player) e.getEntity();
        Player dP = (Player) ((Projectile) e.getDamager()).getShooter();
       /* p.setMaxHealth(110);
        dP.setMaxHealth(20);*/
        if (dP.getItemInHand() == null || dP.getItemInHand().getType().equals(Material.AIR)) return;
        if (!getConfig().getStringList("worlds").contains(p.getWorld().getName())) return;
        if (!getConfig().getStringList("items").contains(dP.getItemInHand().getType().toString() + ":" + dP.getItemInHand().getData().toString()))
            return;
        int damage = Double.valueOf(e.getDamage()).intValue();
        int health = Double.valueOf(p.getMaxHealth() - dP.getMaxHealth()).intValue();
      //  System.out.println("damage = " + damage);
      //  System.out.println("health = " + health);

        if (!this.healthPair.containsKey(health)) return;
        int pairId = this.healthPair.get(health);
    //    System.out.println("pairId = " + pairId);
        HashMap<Integer, Integer> damagePair = this.pairDamage.get(pairId);
        if (!damagePair.containsKey(damage)) return;
        int damagePairId = damagePair.get(damage);
   //     System.out.println("damagePairId = " + damagePairId);
        Map damageConfig = ((List<Map>) getConfig().getMapList("rules").get(pairId).get("damage")).get(damagePairId);
        if (damageConfig == null || damageConfig.isEmpty()) return;
        int additionMin = (int) damageConfig.get("addition-min");
        int additionMax = (int) damageConfig.get("addition-max");
        int add = additionMax - additionMin != 0 ? new Random().nextInt(additionMax - additionMin) + additionMin : additionMin;
    //    System.out.println("add = " + add);
     //   System.out.println("e.getDamage() = " + e.getDamage());
        e.setDamage(e.getDamage() + add);
     //   System.out.println("e.getDamage() = " + e.getDamage());
    }
}
