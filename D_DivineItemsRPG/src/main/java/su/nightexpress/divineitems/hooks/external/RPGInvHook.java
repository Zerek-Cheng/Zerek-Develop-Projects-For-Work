/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  ru.endlesscode.rpginventory.api.InventoryAPI
 */
package su.nightexpress.divineitems.hooks.external;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import ru.endlesscode.rpginventory.api.InventoryAPI;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;

public class RPGInvHook {
    private DivineItems plugin;

    public RPGInvHook(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    public ItemStack[] getEquip(Player player) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        HashSet<ItemStack> hashSet = new HashSet<ItemStack>();
        arrayList.addAll(InventoryAPI.getActiveItems((Player)player));
        arrayList.addAll(InventoryAPI.getPassiveItems((Player)player));
        if (player.getEquipment().getItemInOffHand() != null && (this.plugin.getCM().getCFG().allowAttributesToOffHand() || player.getEquipment().getItemInOffHand().getType() == Material.SHIELD)) {
            arrayList.add(player.getEquipment().getItemInOffHand());
        }
        if (player.getEquipment().getItemInMainHand() != null && !this.plugin.getCM().getCFG().getArmors().contains((Object)player.getEquipment().getItemInMainHand().getType())) {
            arrayList.add(player.getEquipment().getItemInMainHand());
        }
        arrayList.addAll(Arrays.asList(player.getInventory().getArmorContents()));
        for (ItemStack itemStack : arrayList) {
            if (ItemAPI.getDurability(itemStack, 0) == 0) continue;
            hashSet.add(itemStack);
        }
        return hashSet.toArray((T[])new ItemStack[hashSet.size()]);
    }
}

