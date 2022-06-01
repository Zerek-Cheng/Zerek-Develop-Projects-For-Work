package com._0myun.minecraft.asynpokemonslot;

import com._0myun.minecraft.asynpokemonslot.ormlite.data.DBManager;
import com._0myun.minecraft.asynpokemonslot.ormlite.data.PokemonData;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Saver extends BukkitRunnable {

    private UUID uuid;

    @Override
    public synchronized void run() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (!offlinePlayer.isOnline()) {
            cancel();
            return;
        }
        Player p = offlinePlayer.getPlayer();
        try {
            PlayerPartyStorage party = Pixelmon.storageManager.getParty(p.getUniqueId());
            PCStorage pc = Pixelmon.storageManager.getPCForPlayer(p.getUniqueId());
            PokemonData data = DBManager.pokemonDataDao.queryForUUID(p.getUniqueId());
            if (data == null) data = new PokemonData();

            data.setUuid(p.getUniqueId().toString());
            data.setP1(null);
            data.setP2(null);
            data.setP3(null);
            data.setP4(null);
            data.setP5(null);
            data.setP6(null);
            data.setPc(null);

            if (R.INSTANCE.getConfig().getBoolean("pc"))
                data.setPc(pc.writeToNBT(new NBTTagCompound()).toString());
            if (party.get(0) != null)
                data.setP1(party.get(0).writeToNBT(new NBTTagCompound()).toString());
            if (party.get(1) != null)
                data.setP2(party.get(1).writeToNBT(new NBTTagCompound()).toString());
            if (party.get(2) != null)
                data.setP3(party.get(2).writeToNBT(new NBTTagCompound()).toString());
            if (party.get(3) != null)
                data.setP4(party.get(3).writeToNBT(new NBTTagCompound()).toString());
            if (party.get(4) != null)
                data.setP5(party.get(4).writeToNBT(new NBTTagCompound()).toString());
            if (party.get(5) != null)
                data.setP6(party.get(5).writeToNBT(new NBTTagCompound()).toString());

            DBManager.pokemonDataDao.createOrUpdate(data);
          //  R.INSTANCE.getLogger().info("玩家" + p.getName() + "精灵/PC保存完成");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
