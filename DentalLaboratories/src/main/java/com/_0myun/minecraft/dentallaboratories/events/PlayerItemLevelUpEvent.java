package com._0myun.minecraft.dentallaboratories.events;

import com._0myun.minecraft.dentallaboratories.Main;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerItemLevelUpEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    @Getter
    private Player player;
    @Getter
    private ItemStack item;
    @Getter
    private int from;
    @Getter
    @Setter
    private int to;
    @Getter
    @Setter
    private HashMap<String, Integer> attributes;
    @Getter
    private com._0myun.minecraft.dentallaboratories.bin.Item itemType;

    private boolean cancel = false;

    public PlayerItemLevelUpEvent(Player p, ItemStack item, int from, int to, HashMap<String, Integer> attributes) {
        this.player = p;
        this.item = item;
        this.from = from;
        this.to = to;
        this.attributes = attributes;
        this.itemType = Main.plugin.getItemsManager().getItem(this.getItem());
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
