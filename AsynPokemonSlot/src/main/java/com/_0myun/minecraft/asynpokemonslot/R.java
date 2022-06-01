package com._0myun.minecraft.asynpokemonslot;

import com._0myun.minecraft.asynpokemonslot.ormlite.data.DBManager;
import com._0myun.minecraft.asynpokemonslot.ormlite.data.PokemonData;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.storage.PCBox;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class R extends JavaPlugin implements Listener {
    public static R INSTANCE;
    List<UUID> asyn = new ArrayList<UUID>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        try {
            Class.forName(getConfig().getString("jdbc.driver"));
            DBManager.conn = new JdbcConnectionSource(getConfig().getString("jdbc.url"), getConfig().getString("jdbc.username"), getConfig().getString("jdbc.password"));
            getLogger().info("数据库连接成功!");
            DBManager.initDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void listenBag(PlayerJoinEvent e) {

        final UUID uuid = e.getPlayer().getUniqueId();
        final String name = e.getPlayer().getName();
        final PlayerPartyStorage party = Pixelmon.storageManager.getParty(uuid);

        try {
            PokemonData data = DBManager.pokemonDataDao.queryForUUID(uuid);
            if (data == null) {
                Thread.sleep(1000);
                data = DBManager.pokemonDataDao.queryForUUID(uuid);
                if (data == null) {
                    asyn.add(uuid);
                    getLogger().info("玩家" + name + "精灵同步完成");
                    if (R.INSTANCE.getConfig().getInt("saver", 300) != 0)
                        new Saver(uuid).runTaskTimer(R.this, R.INSTANCE.getConfig().getInt("saver", 300) * 20, R.INSTANCE.getConfig().getInt("saver", 300) * 20);
                    return;
                }
            }
            data = DBManager.pokemonDataDao.queryForUUID(uuid);

            party.set(0, null);
            party.set(1, null);
            party.set(2, null);
            party.set(3, null);
            party.set(4, null);
            party.set(5, null);

            final PokemonData finalData = data;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (getConfig().getBoolean("pc")) {
                        if (finalData.getPc() != null && !finalData.getPc().isEmpty()) {
                            final PCStorage pc = Pixelmon.storageManager.getPCForPlayer(party.getPlayer());

                            try {
                                pc.readFromNBT(JsonToNBT.func_180713_a(finalData.getPc()));
                            } catch (NBTException ex) {
                                ex.printStackTrace();
                            }
                            pc.setPlayer(uuid, name);

                            pc.setHasChanged(true);
                            pc.setNeedsSaving();
                            pc.shouldSendUpdates = true;

                            for (PCBox box : pc.getBoxes()) {
                                //  box.sendChangesToServer();
                                box.sendContents(party.getPlayer());
                            }
                            //pc.sendContents(party.getPlayer());
                            //Pixelmon.network.sendTo(new ClientInitializePC(pc), party.getPlayer());
                            getLogger().info("玩家" + name + "PC同步完成");
                        }
                    }
                }
            }.runTaskLaterAsynchronously(this, 20L);
            if (data.getP1() != null && !data.getP1().isEmpty())
                party.set(0, Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(data.getP1())));
            if (data.getP2() != null && !data.getP2().isEmpty())
                party.set(1, Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(data.getP2())));
            if (data.getP3() != null && !data.getP3().isEmpty())
                party.set(2, Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(data.getP3())));
            if (data.getP4() != null && !data.getP4().isEmpty())
                party.set(3, Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(data.getP4())));
            if (data.getP5() != null && !data.getP5().isEmpty())
                party.set(4, Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(data.getP5())));
            if (data.getP6() != null && !data.getP6().isEmpty())
                party.set(5, Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(data.getP6())));
            asyn.add(uuid);
            if (R.INSTANCE.getConfig().getInt("saver", 300) != 0)
                new Saver(uuid).runTaskTimer(R.this, R.INSTANCE.getConfig().getInt("saver", 300) * 20, R.INSTANCE.getConfig().getInt("saver", 300) * 20);
            getLogger().info("玩家" + name + "背包精灵同步完成");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void listenBag(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!asyn.contains(p.getUniqueId())) return;//没同步不保存
        asyn.remove(p.getUniqueId());
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

            if (getConfig().getBoolean("pc"))
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
            getLogger().info("玩家" + p.getName() + "背景精灵/PC保存完成");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        UUID uuid = p.getUniqueId();
        String name = p.getName();

        if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
            new Saver(p.getUniqueId()).runTask(this);
            p.sendMessage("已保存");
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("refreshpc")) {
            final PlayerPartyStorage party = Pixelmon.storageManager.getParty(uuid);
            if (getConfig().getBoolean("pc")) {
                final PCStorage pc = Pixelmon.storageManager.getPCForPlayer(party.getPlayer());
                pc.setPlayer(uuid, name);
                pc.setHasChanged(true);
                pc.setNeedsSaving();
                pc.shouldSendUpdates = true;
                for (PCBox box : pc.getBoxes())
                    box.sendContents(party.getPlayer());
            }
        }
        return true;
    }
}
