package com._0myun.minecraf.vexslotpokemonimg;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexSlot;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class CheckTask extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            OpenedVexGui openedGui = VexViewAPI.getPlayerCurrentGui(p);
            if (openedGui == null) return;
            VexGui gui = openedGui.getVexGui();
            boolean edit[] = {false};
            gui.getComponents().forEach(component -> {
                if (!(component instanceof VexSlot)) return;
                VexSlot slot = (VexSlot) component;
                ItemStack item = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(slot.getItem()));
                if (!item.hasItemMeta()) return;
                if (!item.getItemMeta().hasLore()) return;
                ItemMeta itemMeta = item.getItemMeta();
                List<String> lores = itemMeta.getLore();
                lores = PlaceholderAPI.setPlaceholders(p, lores);
                itemMeta.setLore(lores);
                item.setItemMeta(itemMeta);
                lores.forEach(lore -> {
                    if (!lore.startsWith("精灵")) return;
                    if (item.getTypeId() == Main.INSTANCE.id) return;
                    edit[0] = true;
                    item.setTypeId(Main.INSTANCE.id);
                    item.setDurability((short) 0);
                    int id = Integer.valueOf(lore.substring("精灵".length()));
                    NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(item);
                    NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
                    nbt.put("form", new byte[]{1});
                    nbt.put("gender", new byte[]{0});
                    nbt.put("ndex", (short) id);
                    nbt.put("Shiny", new byte[]{1});
                    NbtFactory.setItemTag(item, nbt);
                });
                slot.setItem(item);
            });
            if (edit[0]) {
                p.closeInventory();
                VexViewAPI.openGui(p, gui);
            }
        });
    }
}
