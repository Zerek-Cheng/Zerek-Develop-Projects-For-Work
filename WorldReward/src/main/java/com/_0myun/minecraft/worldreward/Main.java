package com._0myun.minecraft.worldreward;

import com.comphenix.protocol.utility.StreamSerializer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Getter
    public HashMap<String, Inventory> rewards = new HashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new Saver().runTaskTimer(this, 20 * 10, 20 * 10);
        new Refresher().runTaskTimerAsynchronously(this, 20, 20);
        loadRewards();
    }

    @Override
    public void onDisable() {
        saveRewards();
        saveConfig();
    }

    private void loadRewards() {
        Bukkit.getWorlds().forEach(world -> {
            List<String> items = getConfig().getStringList("inv." + world.getName());
            if (items == null) items = new ArrayList<>();
            Inventory inv = Bukkit.createInventory(null, 54);
            rewards.put(world.getName(), inv);
            items.forEach(item -> {
                try {
                    ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(item);
                    inv.addItem(itemStack);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            getLogger().log(Level.INFO, String.format("世界%s奖励盒子读取完成共%s个", world.getName(), String.valueOf(items.size())));
        });
    }

    private void saveRewards() {
        this.rewards.forEach((world, inv) -> {
            List<String> items = new ArrayList<>();
            inv.forEach(itemStack -> {
                if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
                try {
                    items.add(StreamSerializer.getDefault().serializeItemStack(itemStack));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            getConfig().set("inv." + world, items);
            getLogger().log(Level.INFO, String.format("世界%s奖励盒子保存完成剩余%s个", world, String.valueOf(items.size())));
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("need player!");
            return true;
        }
        Player p = (Player) sender;
        if (!p.isOp()) {
            p.sendMessage("必须是OP");
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            p.sendMessage(getLang("lang5"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("create")) {
            Block block = p.getTargetBlock(null, 100);
            if (!block.getType().equals(Material.CHEST)) {
                p.sendMessage(getLang("lang1"));
                return true;
            }
            if (!isBox(block.getLocation())) addBox(block.getLocation());
            p.sendMessage(getLang("lang2"));
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("refreshTime")) {
            int time = Integer.valueOf(args[1]);
            setRefreshTime(p.getWorld(), time);
            setNextRefreshTime(p.getWorld());
            p.sendMessage(getLang("lang5"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("refresh")) {
            refreshBox(p.getWorld());
            setNextRefreshTime(p.getWorld());
            p.sendMessage(getLang("lang5"));
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            ItemStack itemInHand = p.getItemInHand();
            if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) {
                p.sendMessage(getLang("lang6"));
                return true;
            }
            int amountLeast = Integer.valueOf(args[1].split("-")[0]);
            int amountMost = args[1].split("-").length > 1 ? Integer.valueOf(args[1].split("-")[1]) : amountLeast;
            double rand = Double.valueOf(args[2]);
            try {
                addReward(p.getWorld(), StreamSerializer.getDefault().serializeItemStack(itemInHand), amountLeast, amountMost, rand);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage(getLang("lang5"));
            return true;
        }
        return true;
    }

    public String getLang(String n) {
        return getConfig().getString("lang." + n);
    }

    public void addBox(Location loc) {
        List<String> box = getConfig().getStringList("data.box");
        box.add(String.format("%s-%s-%s-%s",
                loc.getWorld().getName(),
                String.valueOf(loc.getBlockX()),
                String.valueOf(loc.getBlockY()),
                String.valueOf(loc.getBlockZ()))
        );
        getConfig().set("data.box", box);
    }

    public boolean isBox(Location loc) {
        return getConfig().getStringList("data.box").contains(String.format("%s-%s-%s-%s",
                loc.getWorld().getName(),
                String.valueOf(loc.getBlockX()),
                String.valueOf(loc.getBlockY()),
                String.valueOf(loc.getBlockZ())));
    }

    public void removeBox(Location loc) {
        List<String> box = getConfig().getStringList("data.box");
        box.remove(String.format("%s-%s-%s-%s",
                loc.getWorld().getName(),
                String.valueOf(loc.getBlockX()),
                String.valueOf(loc.getBlockY()),
                String.valueOf(loc.getBlockZ()))
        );
        getConfig().set("data.box", box);
    }

    public Long getRefreshTime(World world) {
        return getConfig().getLong("data.box-refresh." + world.getName());
    }

    public void setRefreshTime(World world, long time) {
        getConfig().set("data.box-refresh." + world.getName(), time * 1000);
    }

    public Long getNextRefreshTime(World world) {
        return getConfig().getLong("data.box-refresh-next." + world.getName());
    }

    public void setNextRefreshTime(World world) {
        if (getRefreshTime(world) == null || getRefreshTime(world) == 0) return;
        getConfig().set("data.box-refresh-next." + world.getName(), System.currentTimeMillis() + getRefreshTime(world));
    }

    public void refreshBox(World world) {
        List<String> rewards = getReward(world);
        Inventory inv = Bukkit.createInventory(null, 54);
        this.rewards.put(world.getName(), inv);
        rewards.forEach(reward -> {
            String[] split = reward.split("-");
            String itemStr = split[0];
            int amountLeast = Integer.valueOf(split[1]);
            int amountMost = Integer.valueOf(split[2]);
            double rand = Double.valueOf(split[3]);

            int amount = new Random().nextInt(amountMost - amountLeast) + amountLeast;
            boolean add = Math.random() <= rand;
            if (!add) return;
            try {
                ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(itemStr);
                itemStack.setAmount(amount);
                inv.addItem(itemStack);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

    public List<String> getReward(World world) {
        List<String> rewards = getConfig().getStringList("rewards." + world.getName());
        return rewards == null ? new ArrayList<>() : rewards;
    }

    public void addReward(World world, String reward, int least, int most, double rand) {
        List<String> rewards = getReward(world);
        rewards.add(reward + "-" + least + "-" + most + "-" + rand);
        getConfig().set("rewards." + world.getName(), rewards);
    }
}
