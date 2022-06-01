package com.lmyun.event.mainworldfucker;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class WorldFucker extends BlockPopulator implements Listener {
    Main pluign = Main.INSTANCE;

    ConcurrentHashMap<String, String> tmp = new ConcurrentHashMap<>();


    @EventHandler
    public void onInit(WorldInitEvent event) {
        if (this.pluign.pluginConfig.get("Config").getStringList("worlds").contains(event.getWorld().getName())) {
            event.getWorld().getPopulators().add(new WorldFucker());
        }
    }


/*    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        if (event.getWorld().getName().equalsIgnoreCase(this.pluign.pluginConfig.get("Config").getString("generator.name"))) {
            this.populate(event.getWorld(), new Random(), event.getChunk());
        }
    }*/

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        String name = chunk.getWorld().getName();
        System.out.println("chunk = " + chunk);


        Thread asyncCatcherWatcher = new Thread(new Runnable() {
            public void run() {
                try {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            for (int y = 0; y < 100; y++) {
                                if (chunk.getBlock(x, y, z).getType() != Material.AIR) {
                                    getBlockReplace(chunk.getBlock(x, y, z), name);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        asyncCatcherWatcher.start();
    }

    public Block getBlockReplace(Block block, String world) {
        String replaceConfig = null;
        if (!this.tmp.containsKey(block.getTypeId() + ":" + block.getData())) {
            replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator." + world + ".blockReplace." + block.getTypeId());
            if (replaceConfig == null || replaceConfig.isEmpty()) {
                replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator." + world + ".blockReplace." + block.getTypeId() + ":" + block.getType().getMaxDurability());
            }
            if (replaceConfig == null || replaceConfig.isEmpty()) {
                replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator." + world + ".blockReplace." + block.getTypeId() + ":*");
            }
            if (replaceConfig == null || replaceConfig.isEmpty()) {
                return block;
            }
            if (replaceConfig != null)
                this.tmp.put(block.getTypeId() + ":" + block.getData(), replaceConfig);
        }
        if (replaceConfig == null) replaceConfig = this.tmp.get(block.getTypeId() + ":" + block.getData());

        int[] replaceId = new int[2];
        if (replaceConfig.contains(":")) {
            String[] tmp = replaceConfig.split(":");
            replaceId[0] = Integer.valueOf(tmp[0]);
            replaceId[1] = Integer.valueOf(tmp[1]);
        } else {
            replaceId[0] = Integer.valueOf(replaceConfig);
            replaceId[1] = 0;
        }

        block.setTypeId(replaceId[0]);
        block.setData((byte) replaceId[1]);
        return block;
    }
}
