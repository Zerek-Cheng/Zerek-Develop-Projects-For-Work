package com.lmyun.event.mainworldfucker;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class WorldFucker extends BlockPopulator implements Listener {
    Main pluign = Main.getPlugin();


    @EventHandler
    public void onInit(WorldInitEvent event) {

        if (event.getWorld().getName().equalsIgnoreCase(this.pluign.pluginConfig.get("Config").getString("generator.name"))) {
            event.getWorld().getPopulators().add(new WorldFucker());
        }
    }
    @EventHandler
    public void onChunkInit(ChunkPopulateEvent e){
        Chunk chunk = e.getChunk();
    }

/*    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        if (event.getWorld().getName().equalsIgnoreCase(this.pluign.pluginConfig.get("Config").getString("generator.name"))) {
            this.populate(event.getWorld(), new Random(), event.getChunk());
        }
    }*/

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        //16*16*256
        for (int y = 0; y < 255; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Block block = chunk.getBlock(x, y, z);
                    if (block == null || block.getType().equals(Material.AIR)) {
                        continue;
                    }
                    this.getBlockReplace(block);
                }
            }
        }
    }

    public Block getBlockReplace(Block block) {
        String replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator.blockReplace." + block.getTypeId());
        if (replaceConfig == null || replaceConfig.isEmpty()) {
            replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator.blockReplace." + block.getTypeId() + ":" + block.getType().getMaxDurability());
        }
        if (replaceConfig == null || replaceConfig.isEmpty()) {
            replaceConfig = this.pluign.pluginConfig.get("Config").getString("generator.blockReplace." + block.getTypeId() + ":*");
        }
        if (replaceConfig == null || replaceConfig.isEmpty()) {
            return block;
        }

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
