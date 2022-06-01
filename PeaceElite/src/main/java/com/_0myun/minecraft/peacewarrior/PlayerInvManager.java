package com._0myun.minecraft.peacewarrior;

import com._0myun.minecraft.peacewarrior.dao.data.PlayerInv;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class PlayerInvManager {

    public static void saveInv(Player p) {
        PlayerInventory inv = p.getInventory();
        for (ItemStack itemStack : inv) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                continue;
            try {
                String data = StreamSerializer.getDefault().serializeItemStack(itemStack);
                PlayerInv playerInv = new PlayerInv().builder().uuid(p.getUniqueId().toString()).type(PlayerInv.ItemType.INVENTORY).data(data).build();
                DBManager.playerInvDao.create(playerInv);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        inv.clear();
        /////////////////////////////////////////////////////
        EntityEquipment eq = p.getEquipment();
        for (ItemStack armor : eq.getArmorContents()) {
            if (armor == null || armor.getType().equals(Material.AIR))
                continue;
            try {
                String data = StreamSerializer.getDefault().serializeItemStack(armor);
                PlayerInv playerInv = new PlayerInv().builder().uuid(p.getUniqueId().toString()).type(PlayerInv.ItemType.EQUIPMENT).data(data).build();
                DBManager.playerInvDao.create(playerInv);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        eq.clear();
        R.INSTANCE.getLogger().info("玩家" + p.getName() + "背包保存到数据库并清空背包！");
    }

    public static void loadInv(Player p) {
        PlayerInventory inv = p.getInventory();
        EntityEquipment eq = p.getEquipment();
        inv.clear();
        eq.clear();

        try {

            List<PlayerInv> sqlInvs = DBManager.playerInvDao.queryForUUID(p.getUniqueId());
            for (PlayerInv sqlInv : sqlInvs) {
                ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(sqlInv.getData());
                switch (sqlInv.getType()) {
                    case INVENTORY:
                        inv.addItem(itemStack);
                        break;
                    case EQUIPMENT:
                        if (eq.getHelmet() == null)
                            eq.setHelmet(itemStack);
                        else if (eq.getChestplate() == null)
                            eq.setChestplate(itemStack);
                        else if (eq.getLeggings() == null)
                            eq.setLeggings(itemStack);
                        else
                            eq.setBoots(itemStack);
                        break;
                    default:
                        inv.addItem(itemStack);
                        break;
                }
            }
            DBManager.playerInvDao.delete(sqlInvs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        R.INSTANCE.getLogger().info("玩家" + p.getName() + "背包恢复并清除数据库");
    }
}
