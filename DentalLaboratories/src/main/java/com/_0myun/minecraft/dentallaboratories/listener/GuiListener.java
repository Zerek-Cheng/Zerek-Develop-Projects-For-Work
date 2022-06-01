package com._0myun.minecraft.dentallaboratories.listener;

import com._0myun.minecraft.dentallaboratories.Main;
import com._0myun.minecraft.dentallaboratories.gui.GuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiListener implements Listener {

    @EventHandler
    public void onClickGui(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        int rawSlot = e.getRawSlot();
        if (!(inv.getHolder() instanceof GuiHolder)) return;
        if (rawSlot < 53) e.setCancelled(true);
        if (Main.plugin.getGuiManager().isAllow(rawSlot)) e.setCancelled(false);
        ClickType click = e.getClick();
        if (click.equals(ClickType.SHIFT_LEFT) || click.equals(ClickType.SHIFT_RIGHT)
                || click.equals(ClickType.DOUBLE_CLICK)) e.setCancelled(true);
        ItemStack clicked = e.getCurrentItem();
        if (!Main.plugin.getGuiManager().isButtonQuery(clicked)) return;

        List<ItemStack> materialItems = getMaterialItems(inv);
        boolean can = Main.plugin.getItemsManager().checkLeavelUpMaterial((Player) e.getWhoClicked(), ((GuiHolder) inv.getHolder()).getItemStack(), materialItems);
        if (!can) return;
        Main.plugin.getItemsManager().takeLeavelUpMaterial((Player) e.getWhoClicked(), ((GuiHolder) inv.getHolder()).getItemStack(), materialItems);
        Main.plugin.getItemsManager().levelUp(p, ((GuiHolder) inv.getHolder()).getItemStack());
        Main.plugin.getItemsManager().updateLores(((GuiHolder) inv.getHolder()).getItemStack());

        p.closeInventory();
        Main.plugin.getGuiManager().openGui(p, ((GuiHolder) inv.getHolder()).getItemStack());
    }

    @EventHandler
    public void onCloseGui(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();
        if (!(inv.getHolder() instanceof GuiHolder)) return;
        List<ItemStack> materialItems = getMaterialItems(inv);
        Player p = (Player) e.getPlayer();
        materialItems.forEach(itemTmp -> p.getInventory().addItem(itemTmp));
    }

    private List<ItemStack> getMaterialItems(Inventory inv) {
        List<ItemStack> materialItems = new ArrayList<>();
        int materialStart = Main.plugin.getConfig().getInt("gui.materialSetStart");
        int materialEnd = Main.plugin.getConfig().getInt("gui.materialSetEnd");
        for (int i = 0; i < (materialEnd - materialStart); i++) {
            ItemStack materialItemTmp = inv.getItem(materialStart + i);
            if (materialItemTmp == null) continue;
            materialItems.add(materialItemTmp);
        }
        return materialItems;
    }
}
