package com.lmyun.event.mainworldfucker;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class WorldFucker implements Listener {
    Main pluign = Main.INSTANCE;
    HashMap<String, String> tmp = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChunkInit(ChunkPopulateEvent e) {
        Chunk chunk = e.getChunk();

        if (!Main.INSTANCE.pluginConfig.get("Config").getStringList("worlds").contains(chunk.getWorld().getName()))
            return;
        if (Main.INSTANCE.inLog(getChunkName(chunk))) return;
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getLogger().log(Level.INFO, getChunkName(chunk) + "初始化...");
                populate(chunk.getWorld(), new Random(), chunk);
                chunk.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
            }
        };
        bukkitRunnable.runTaskLater(Main.INSTANCE,0);
    }

    public String getChunkName(Chunk chunk) {
        return (chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ());
    }


    public void populate(World world, Random random, Chunk chunk) {
        //16*16*256
        List<Thread> ts = new ArrayList<>();
        for (int y = 0; y < 128; y++) {
            int finalY = y;
            Thread thread = new Thread(() -> {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        Block block = chunk.getBlock(x, finalY, z);
                        if (block == null || block.getType().equals(Material.AIR)) {
                            continue;
                        }
                        this.getBlockReplace(world, block);
                    }
                }
            });
            thread.setPriority(10);
            ts.add(thread);
            thread.start();
        }
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Block getBlockReplace(World world, Block block) {
        String replaceConfig = null;
        if (!this.tmp.containsKey(block.getTypeId() + ":" + block.getData())) {
            replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator." + world.getName() + ".blockReplace." + block.getTypeId());
            if (replaceConfig == null || replaceConfig.isEmpty()) {
                replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator." + world.getName() + ".blockReplace." + block.getTypeId() + ":" + block.getType().getMaxDurability());
            }
            if (replaceConfig == null || replaceConfig.isEmpty()) {
                replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator." + world.getName() + ".blockReplace." + block.getTypeId() + ":*");
            }
            if (replaceConfig == null || replaceConfig.isEmpty()) {
                return block;
            }
            if (replaceConfig != null)
                this.tmp.put(block.getTypeId() + ":" + block.getData(), replaceConfig);
        }
        replaceConfig = this.tmp.get(block.getTypeId() + ":" + block.getData());

        int[] replaceId = new int[2];
        if (replaceConfig.contains(":")) {
            String[] tmp = replaceConfig.split(":");
            replaceId[0] = Integer.valueOf(tmp[0]);
            replaceId[1] = Integer.valueOf(tmp[1]);
        } else {
            replaceId[0] = Integer.valueOf(replaceConfig);
            replaceId[1] = 0;
        }
        block.setTypeIdAndData(replaceId[0], Byte.valueOf(String.valueOf(replaceId[1])), false);
        return block;
    }
}
