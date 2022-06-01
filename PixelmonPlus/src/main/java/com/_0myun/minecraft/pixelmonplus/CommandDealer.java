package com._0myun.minecraft.pixelmonplus;

import com._0myun.minecraft.pixelmonplus.utils.Lucky;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.*;

public class CommandDealer implements CommandExecutor {

    public CommandDealer() {
        Bukkit.getPluginCommand("PixelmonPlus").setExecutor(this);
        Bukkit.getPluginCommand("fsgt").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("reload ok");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get("lang1"));
            return true;
        }
        Player p = (Player) sender;
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(p.getUniqueId());
        Pokemon pokemon = party.get(0);
        IVStore iVs = pokemon.getIVs();

        if (pokemon == null) {
            p.sendMessage(Lang.get("lang2"));
            return true;
        }
        if (Main.INSTANCE.getConfig().getStringList("black").contains(pokemon.writeToNBT(new NBTTagCompound()).func_74779_i("Name"))) {
            p.sendMessage(Lang.get("lang7"));
            return true;
        }
        if (args.length < 1) return true;
        if ((args[0].equalsIgnoreCase("add") && args.length < 3)
                || args[0].equalsIgnoreCase("delete") && args.length < 3
                || args[0].equalsIgnoreCase("radd") && args.length < 2
                || args[0].equalsIgnoreCase("rdelete") && args.length < 2) {
            p.sendMessage(Lang.get("lang3"));
            return true;
        }

        if (args[0].equalsIgnoreCase("add") && p.hasPermission("fsgt.add")) {
            iVs = addIVs(p, iVs, Integer.valueOf(args[1]), Integer.valueOf(args[2]));
        }
        if (args[0].equalsIgnoreCase("delete") && p.hasPermission("fsgt.delete")) {
            iVs = addIVs(p, iVs, Integer.valueOf(args[1]), -Integer.valueOf(args[2]));
        }
        if (args[0].equalsIgnoreCase("radd") && p.hasPermission("fsgt.radd")) {
            iVs = addIVs(p, iVs, Integer.valueOf(args[1]), randAdd());
        }
        if (args[0].equalsIgnoreCase("rdelete") && p.hasPermission("fsgt.rdelete")) {
            iVs = addIVs(p, iVs, Integer.valueOf(args[1]), randDel());
        }
        if (args[0].equalsIgnoreCase("random") && p.hasPermission("fsgt.random")) {
            for (int attribute : randAttribute(randAttributeAmount())) {
                addIVs(p, iVs, attribute, randAll());
            }
        }
        if (args[0].equalsIgnoreCase("prandom") && p.hasPermission("fsgt.prandom")) {
            for (int attribute : randAttribute(randAttributeAmount())) {
                addIVs(p, iVs, attribute, randAll(), true);
            }
        }
        party.set(0, pokemon);
        p.sendMessage(Lang.get("lang4"));
        return false;
    }

    public static IVStore addIVs(Player p, IVStore ivs, int type, int value) {
        return addIVs(p, ivs, type, value, false, 31);
    }


    public static IVStore addIVs(Player p, IVStore ivs, int type, int value, boolean safe) {
        return addIVs(p, ivs, type, value, safe, 31);
    }

    public static IVStore addIVs(Player p, IVStore ivs, int type, int value, boolean safe, Integer max) {
        if (max == null) max = 31;
        String typeName = "";
        switch (type) {
            case 1:
                typeName = "血量";
                break;
            case 2:
                typeName = "速度";
                break;
            case 3:
                typeName = "攻击";
                break;
            case 4:
                typeName = "防御";
                break;
            case 5:
                typeName = "特攻";
                break;
            case 6:
                typeName = "特防";
                break;
        }

        if (value < 0 && safe) {
            p.sendMessage(String.format(Lang.get("lang6"), typeName, String.valueOf(value)));
            return ivs;
        }

        switch (type) {
            case 1:
                if (ivs.hp >= max) return ivs;
                ivs.hp += value;
                if (ivs.hp >= max) ivs.hp = max;
                if (ivs.hp < 0) ivs.hp = 0;
                break;
            case 2:
                if (ivs.speed >= max) return ivs;
                ivs.speed += value;
                if (ivs.speed >= max) ivs.speed = max;
                if (ivs.speed < 0) ivs.speed = 0;
                break;
            case 3:
                if (ivs.attack >= max) return ivs;
                ivs.attack += value;
                if (ivs.attack >= max) ivs.attack = max;
                if (ivs.attack < 0) ivs.attack = 0;
                break;
            case 4:
                if (ivs.defence >= max) return ivs;
                ivs.defence += value;
                if (ivs.defence >= max) ivs.defence = max;
                if (ivs.defence < 0) ivs.defence = 0;
                break;
            case 5:
                if (ivs.specialAttack >= max) return ivs;
                ivs.specialAttack += value;
                if (ivs.specialAttack >= max) ivs.specialAttack = max;
                if (ivs.specialAttack < 0) ivs.specialAttack = 0;
                break;
            case 6:
                if (ivs.specialDefence >= max) return ivs;
                ivs.specialDefence += value;
                if (ivs.specialDefence >= max) ivs.specialDefence = max;
                if (ivs.specialDefence < 0) ivs.specialDefence = 0;
                break;
        }
        p.sendMessage(String.format(Lang.get("lang5"), typeName, String.valueOf(value)));
        return ivs;
    }

    public int randAdd() {
        List<Lucky.Prize> choice = new ArrayList<>();
        int i = 0;
        for (Map<?, ?> map : Main.INSTANCE.getConfig().getMapList("radd")) {
            choice.add(new Lucky.Prize(
                    i
                    , String.valueOf(map.get("value"))
                    , BigDecimal.valueOf(Double.valueOf(String.valueOf(map.get("probability"))))
            ));
            i++;
        }
        int result = Lucky.rand(choice);
        return Integer.valueOf(choice.get(result).getValue());
    }

    public int randDel() {
        List<Lucky.Prize> choice = new ArrayList<>();
        int i = 0;
        for (Map<?, ?> map : Main.INSTANCE.getConfig().getMapList("rdel")) {
            choice.add(new Lucky.Prize(
                    i
                    , String.valueOf(map.get("value"))
                    , BigDecimal.valueOf(Double.valueOf(String.valueOf(map.get("probability"))))
            ));
            i++;
        }
        int result = Lucky.rand(choice);
        return Integer.valueOf(choice.get(result).getValue());
    }

    public int randAll() {
        List<Lucky.Prize> choice = new ArrayList<>();
        int i = 0;
        for (Map<?, ?> map : Main.INSTANCE.getConfig().getMapList("random")) {
            Lucky.Prize prize = new Lucky.Prize(
                    i
                    , String.valueOf(map.get("value"))
                    , BigDecimal.valueOf(Double.valueOf(String.valueOf(map.get("probability"))))
            );
            i++;
            choice.add(prize);
        }
        int result = Lucky.rand(choice);
        return Integer.valueOf(choice.get(result).getValue());
    }

    public int randAttributeAmount() {/*
        PCStorage pc = Pixelmon.storageManager.getPCForPlayer(UUID.randomUUID());
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(UUID.randomUUID());
        Pixelmon.storageManager.initializePCForPlayer();
        party.*/

        int min = Main.INSTANCE.getConfig().getInt("random-count-min");
        int max = Main.INSTANCE.getConfig().getInt("random-count-max");
        int result = min == max ? min : new Random().nextInt(max - min) + min;
        return result;
    }

    public List<Integer> randAttribute(int amount) {
        List<Integer> result = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        for (int i = 0; i < amount; i++) {
            result.remove(new Random().nextInt(result.size()));
        }
        return result;
    }
}