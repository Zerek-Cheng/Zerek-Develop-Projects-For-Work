package _new.custom.cuilian.event;

import _new.custom.cuilian.CustomCuiLian;
import _new.custom.cuilian.language.Language;
import _new.custom.cuilian.level.Level;
import _new.custom.cuilian.manager.BHFManager;
import _new.custom.cuilian.manager.FurnaceManager;
import _new.custom.cuilian.manager.Manager;
import _new.custom.cuilian.newapi.NewAPI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BEvent
        implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        int level = NewAPI.getTiaoYue(NewAPI.addAll(NewAPI.getItemInHand(p), p.getInventory().getHelmet(), p.getInventory().getChestplate(), p.getInventory().getLeggings(), p.getInventory().getBoots()));
        if (level > 0) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, level));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        try {
            Player p = e.getPlayer();
            Iterator localIterator;
            if (FurnaceManager.FurnaceUsingMap.containsValue(p.getName())) {
                for (localIterator = FurnaceManager.FurnaceUsingMap.keySet().iterator(); localIterator.hasNext(); ) {
                    int i = ((Integer) localIterator.next()).intValue();
                    if (((String) FurnaceManager.FurnaceUsingMap.get(Integer.valueOf(i))).equalsIgnoreCase(p.getName())) {
                        FurnaceManager.FurnaceUsingMap.remove(Integer.valueOf(i));
                    }
                }
            }
            if (CustomCuiLian.playerTZXGList.containsKey(p.getName())) {
                CustomCuiLian.playerTZXGList.remove(p.getName());
            }
        } catch (Exception ex) {

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerExpChangeEvent(PlayerExpChangeEvent e) {
        int value = e.getAmount();
        Player p = e.getPlayer();
        int i = NewAPI.getJingYan(NewAPI.addAll(NewAPI.getItemInHand(p), p.getInventory().getHelmet(), p.getInventory().getChestplate(), p.getInventory().getLeggings(), p.getInventory().getBoots()));
        int jy = value * (100 + i) / 100;
        e.setAmount(jy);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ItemStack Helmet = p.getInventory().getHelmet();
        ItemStack Chestplate = p.getInventory().getChestplate();
        ItemStack Leggings = p.getInventory().getLeggings();
        ItemStack Boots = p.getInventory().getBoots();
        int hdj = 0;
        int cdj = 0;
        int ldj = 0;
        int bdj = 0;
        if ((Helmet != null) && (Helmet.getType() != Material.AIR)) {
            hdj = NewAPI.getLevelByItem(Helmet).LevelValue.intValue();
        }
        if ((Chestplate != null) && (Chestplate.getType() != Material.AIR)) {
            cdj = NewAPI.getLevelByItem(Chestplate).LevelValue.intValue();
        }
        if ((Leggings != null) && (Leggings.getType() != Material.AIR)) {
            ldj = NewAPI.getLevelByItem(Leggings).LevelValue.intValue();
        }
        if ((Boots != null) && (Boots.getType() != Material.AIR)) {
            bdj = NewAPI.getLevelByItem(Boots).LevelValue.intValue();
        }
        if ((hdj == cdj) && (ldj == bdj) && (cdj == ldj) && (ldj != -1) &&
                (((Level) CustomCuiLian.cclm.cclllist.get(ldj)).CanSendNotice.booleanValue())) {
            Bukkit.broadcastMessage(Language.JOIN_SERVER_NOTICE.replace("%p", p.getName()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getRawSlot() < 0) {
            return;
        }
        ItemStack item = e.getCurrentItem();
        if (!e.isRightClick()) {
            return;
        }
        if ((e.getInventory().getType() != InventoryType.CRAFTING) && (e.getInventory().getType() != InventoryType.PLAYER)) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        boolean state;
        if (BHFManager.BhfUsingMap.containsKey(p.getName())) {
            if (e.isRightClick()) {
                if ((e.getInventory().getType() != InventoryType.PLAYER) && (e.getInventory().getType() != InventoryType.CRAFTING)) {
                    p.sendMessage(Language.APPEND_TO_THE_RIGHT_KEY);
                    return;
                }
                if ((item == null) || (item.getType().equals(Material.AIR))) {
                    e.setCancelled(true);
                    p.closeInventory();
                    p.sendMessage(Language.ADD_CANCEL_PROMPT);
                    BHFManager.BhfUsingMap.remove(p.getName());
                    BHFManager.BhfFirstItemMap.remove(p.getName());
                    return;
                }
                ItemStack fitem = (ItemStack) BHFManager.BhfFirstItemMap.get(p.getName());
                state = false;
                if (CustomCuiLian.cclm.ItemList.contains(item.getType())) {
                    state = true;
                } else {
                    return;
                }
                if (!state) {
                    e.setCancelled(true);
                    BHFManager.BhfUsingMap.remove(p.getName());
                    BHFManager.BhfFirstItemMap.remove(p.getName());
                    p.sendMessage(Language.CAN_NOT_ADD_PROMPT);
                    return;
                }
                if (!p.getInventory().contains(fitem)) {
                    p.sendMessage(Language.CAN_NOT_ADD_PROMPT);
                    BHFManager.BhfUsingMap.remove(p.getName());
                    BHFManager.BhfFirstItemMap.remove(p.getName());
                    e.setCancelled(true);
                    return;
                }
                Level l = NewAPI.getLevelByBhf(fitem);
                item = NewAPI.addCuilianBhf(item, fitem);
                p.sendMessage(Language.CAN_ADD_PROMPT.replace("%s", l.BhfString));
                e.setCancelled(true);
                int sl = fitem.getAmount() - 1;
                p.getInventory().remove(fitem);
                ItemStack i = fitem.clone();
                i.setAmount(sl);
                p.getInventory().addItem(new ItemStack[]{i});
                p.closeInventory();
                BHFManager.BhfUsingMap.remove(p.getName());
                BHFManager.BhfFirstItemMap.remove(p.getName());
            }
        } else if ((e.isRightClick()) &&
                (NewAPI.isBhfMapItemMetaHasItemMeta(meta))) {
            if (!p.getInventory().contains(item)) {
                p.sendMessage(Language.APPEND_TO_THE_RIGHT_KEY);
                e.setCancelled(true);
                return;
            }
            BHFManager.BhfUsingMap.put(p.getName(), meta);
            BHFManager.BhfFirstItemMap.put(p.getName(), item);
            Level l = NewAPI.getLevelByBhf(item);
            for (String str : Language.ADD_PROMPT_LIST) {
                p.sendMessage(str.replace("%s", l.BhfString));
            }
            e.setCancelled(true);
            p.closeInventory();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        if ((!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (!e.hasBlock()) || (!e.getClickedBlock().getType().equals(Material.FURNACE))) {
            return;
        }
        Player p = e.getPlayer();
        int hash = e.getClickedBlock().hashCode();
        if (FurnaceManager.FurnaceUsingMap.get(Integer.valueOf(hash)) != null) {
            FurnaceManager.FurnaceUsingMap.remove(Integer.valueOf(hash));
        }
        FurnaceManager.FurnaceUsingMap.put(Integer.valueOf(hash), p.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void FurnaceBurnEvent(FurnaceBurnEvent e) {
        ItemStack fuel = e.getFuel();
        Furnace f = (Furnace) e.getBlock().getState();
        if (NewAPI.getLevelByItem(f.getInventory().getSmelting()).LevelValue.intValue() >= CustomCuiLian.Max) {
            e.setCancelled(true);
            return;
        }
        if ((f.getInventory().getResult() != null) && (NewAPI.isStoneMapItemMetaHasItemMeta(fuel.getItemMeta()))) {
            e.setCancelled(true);
            FurnaceManager.FurnaceFuelMap.remove(f.getLocation());
        }
        if (NewAPI.isStoneMapItemMetaHasItemMeta(fuel.getItemMeta())) {
            if (CustomCuiLian.cclm.ItemList.contains(f.getInventory().getSmelting().getType())) {
                FurnaceManager.FurnaceFuelMap.put(f.getLocation(), fuel);
                e.setBurning(true);
                e.setBurnTime(200);
            } else {
                e.setCancelled(true);
                FurnaceManager.FurnaceFuelMap.remove(f.getLocation());
            }
        }
        if ((CustomCuiLian.cclm.ItemList.contains(f.getInventory().getSmelting().getType())) && (!NewAPI.isStoneMapItemMetaHasItemMeta(fuel.getItemMeta()))) {
            e.setCancelled(true);
            FurnaceManager.FurnaceFuelMap.remove(f.getLocation());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void FurnaceSmeltEvent(FurnaceSmeltEvent e) {
        boolean state = false;
        ItemStack smelt = e.getSource();
        Furnace f = (Furnace) e.getBlock().getState();
        ItemStack fuel = (ItemStack) FurnaceManager.FurnaceFuelMap.get(f.getLocation());
        if (CustomCuiLian.cclm.ItemList.contains(smelt.getType())) {
            state = true;
        } else {
            return;
        }
        if (((FurnaceManager.FurnaceUsingMap.get(Integer.valueOf(e.getBlock().hashCode())) == null) || (fuel == null)) &&
                (CustomCuiLian.cclm.ItemList.contains(smelt.getType()))) {
            smelt.setAmount(1);
            e.setResult(smelt);
            return;
        }
        if ((state) &&
                (NewAPI.isStoneMapItemMetaHasItemMeta(fuel.getItemMeta())) && (CustomCuiLian.cclm.ItemList.contains(smelt.getType()))) {
            Player p = ((CustomCuiLian) JavaPlugin.getPlugin(CustomCuiLian.class)).getServer().getPlayer((String) FurnaceManager.FurnaceUsingMap.get(Integer.valueOf(e.getBlock().hashCode())));
            smelt = NewAPI.cuilian(fuel, smelt, p);
            smelt.setAmount(1);
            e.setResult(smelt);
            FurnaceManager.FurnaceUsingMap.remove(Integer.valueOf(e.getBlock().hashCode()));
            FurnaceManager.FurnaceFuelMap.remove(f.getLocation());
        }
    }
}
