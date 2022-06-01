package com._0myun.minecraft.skill.attackmultiple;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {
    private HashMap<UUID, Integer> activeMult = new HashMap<UUID, Integer>();
    private HashMap<UUID, Long> activeTime = new HashMap<>();
    private HashMap<UUID, Long> cd = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().log(Level.WARNING, "§b灵梦云插件定制就找灵梦云科技q2025255093");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        if (args.length <= 3) {
            sender.sendMessage("指令格式/" + label + " <倍数> <有效时间> <冷却时间>");
            Player p = (Player) sender;
            ItemStack itemInHand = p.getItemInHand();
            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemInHand);
            NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
            nbt.put("com._0myun.minecraft.skill.attackmultiple.mult", Integer.valueOf(args[0]));
            nbt.put("com._0myun.minecraft.skill.attackmultiple.active", Integer.valueOf(args[1]));
            nbt.put("com._0myun.minecraft.skill.attackmultiple.cd", Integer.valueOf(args[2]));
            NbtFactory.setItemTag(itemInHand, nbt);
            p.sendMessage("设置成功");
        }
        return true;
    }

    private void setSkill(UUID p, int mult, int time) {
        this.activeMult.put(p, mult);
        this.activeTime.put(p, System.currentTimeMillis() + (time * 1000l));
    }

    private void setCD(UUID p, int cd) {
        this.cd.put(p, System.currentTimeMillis() + (cd * 1000l));
    }

    private boolean isInCd(UUID p) {
        Long time = this.cd.get(p);
        if (time == null) time = 0l;
        return System.currentTimeMillis() < time;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        Player p = e.getPlayer();
        ItemStack itemInHand = p.getItemInHand();
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemInHand);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        try {
            nbt.getInteger("com._0myun.minecraft.skill.attackmultiple.active");
        } catch (Exception ex) {
            return;
        }
        int mult = nbt.getIntegerOrDefault("com._0myun.minecraft.skill.attackmultiple.mult");
        int active = nbt.getIntegerOrDefault("com._0myun.minecraft.skill.attackmultiple.active");
        int cd = nbt.getIntegerOrDefault("com._0myun.minecraft.skill.attackmultiple.cd");
        if (mult == 0) return;
        if (this.isInCd(p.getUniqueId())) {
            Long cdTime = this.cd.get(p.getUniqueId());
            if (cdTime == null) cdTime = 0l;
            p.sendMessage(getConfig().getString("lang2").replace("<cd>",
                    String.valueOf((cdTime - System.currentTimeMillis()) / 1000)
            ));
            return;
        }
        this.setCD(p.getUniqueId(), cd);
        this.setSkill(p.getUniqueId(), mult, active);
        p.sendMessage(
                getConfig().getString("lang1")
                        .replace("<time>", String.valueOf(active))
                        .replace("<mult>", String.valueOf(mult))
        );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        double damage = e.getDamage();
        if (!(damager instanceof Player)) return;
        Player p = (Player) damager;
        Long active = this.activeTime.get(p.getUniqueId());
        if (active == null) active = 0l;
        if (System.currentTimeMillis() > active) return;
        Integer mult = this.activeMult.get(p.getUniqueId());
        if (mult == null) mult = 1;
        e.setDamage(e.getDamage() * mult);
    }
}
