/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package xin.tianwc.tprefix;

import github.saukiya.sxattribute.SXAttribute;
import github.saukiya.sxattribute.data.attribute.SXAttributeData;
import github.saukiya.sxattribute.data.condition.SXConditionType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.lang.reflect.Field;

public class Listeners
        implements Listener {
    private Main plugin;

    public Listeners(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickGui(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (PreInventory.inventorys.containsKey(player.getName()) && PreInventory.inventorys.get(player.getName()).equals(event.getInventory())) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
            String pre = event.getCurrentItem().getItemMeta().getDisplayName();
            player.chat("/hao p " + pre);
            player.closeInventory();
            PreInventory.inventorys.remove(player.getName());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String pre = Main.INSTANCE.getConfig().getString("data." + player.getUniqueId().toString());
        Prefix prefix = new Prefix(pre);
        if (prefix == null) return;
        SXAttributeData data = new SXAttributeData();
        //data.getSubAttribute("Damage").addAttribute(new double[]{0, 0, 0, 0, 0, 0});
        data.add(SXAttribute.getApi().getLoreData(player, SXConditionType.ALL, prefix.getLores()));
        try {
            Class<? extends SXAttributeData> dataClass = data.getClass();
            Field valid = dataClass.getDeclaredField("valid");
            valid.setAccessible(true);
            valid.setBoolean(data, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SXAttribute.getApi().setEntityAPIData(Main.class, player.getUniqueId(), data);
        //SXAttribute.getApi().removeEntityAPIData(Main.class, player.getUniqueId());
        SXAttribute.getApi().updateHandData(player);
        SXAttribute.getApi().updateStats(player);
        SXAttribute.getApi().updateEquipmentData(player);
        SXAttribute.getApi().updateRPGInventoryData(player);
        SXAttribute.getApi().updateSlotData(player);
        player.sendMessage("§a成功切换到" + prefix.getPre() + "称号");
    }
}

