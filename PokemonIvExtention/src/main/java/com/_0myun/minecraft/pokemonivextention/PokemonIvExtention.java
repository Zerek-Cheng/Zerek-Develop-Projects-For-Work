package com._0myun.minecraft.pokemonivextention;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public final class PokemonIvExtention implements CommandExecutor {

    public PokemonIvExtention() {
        Bukkit.getPluginCommand("PokemonIvExtention_845008749").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 5) {
            sender.sendMessage("缺少参数");
            return true;
        }
        String pName = args[0];
        int pokemonPosition = Integer.valueOf(args[1]);
        String type = args[2];
        int amount = Math.abs(Integer.valueOf(args[3]));
        int max = Integer.valueOf(args[4]);

        if (pokemonPosition < 1 || pokemonPosition > 6) {
            sender.sendMessage("位置错误");
            return true;
        }
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(Bukkit.getOfflinePlayer(pName).getUniqueId());
        Pokemon pokemon = party.get(pokemonPosition - 1);
        String name = pokemon.getSpecies().getLocalizedName();
        if (getConfig().getBoolean("debug"))
            System.out.println("pokemon.getSpecies().getLocalizedName() = " + pokemon.getSpecies().getLocalizedName());
        if (!getConfig().getStringList("white").contains(name)) {
            sender.sendMessage("物种不能被强化");
            return true;
        }
        if (pokemon == null) {
            sender.sendMessage("该玩家的指定位置没有精灵！");
            return true;
        }
        IVStore iVs = pokemon.getIVs();
        switch (type) {
            case "hp":
                if (iVs.hp > max) {
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                iVs.hp += amount;
                if (iVs.hp > max) {
                    iVs.hp = max;
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                break;
            case "specialAttack":
                if (iVs.specialAttack > max) {
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                iVs.specialAttack += amount;
                if (iVs.specialAttack > max) {
                    iVs.specialAttack = max;
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                break;
            case "specialDefence":
                if (iVs.specialDefence > max) {
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                iVs.specialDefence += amount;
                if (iVs.specialDefence > max) {
                    iVs.specialDefence = max;
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                break;
            case "speed":
                if (iVs.speed > max) {
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                iVs.speed += amount;
                if (iVs.speed > max) {
                    iVs.speed = max;
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                break;
            case "defence":
                if (iVs.defence > max) {
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                iVs.defence += amount;
                if (iVs.defence > max) {
                    iVs.defence = max;
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                break;
            case "attack":
                if (iVs.attack > max) {
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                iVs.attack += amount;
                if (iVs.attack > max) {
                    iVs.attack = max;
                    sender.sendMessage("超过最大值了！");
                    return true;
                }
                break;
            default:
                sender.sendMessage("类型错误");
                return true;
        }
        /*NBTTagCompound nbt = pokemon.writeToNBT(new NBTTagCompound());
        iVs.writeToNBT(nbt);
        pokemon.readFromNBT(nbt);
        */
        party.set(pokemonPosition - 1, pokemon);
        sender.sendMessage("操作成功");
        return true;
    }

    private FileConfiguration getConfig() {
        return Main.INSTANCE.getConfig();
    }
}
