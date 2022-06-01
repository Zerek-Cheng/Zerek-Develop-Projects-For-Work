package com._0myun.minecraft.catserver.pokemoneffectbroadcast;

import catserver.api.bukkit.event.ForgeEvent;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NetLoader implements Listener, CommandExecutor {
    public NetLoader() {
        Bukkit.getPluginCommand("fs").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, Main.INSTANCE);
    }


    @EventHandler
    public void onSuccess(ForgeEvent tmpe) {
        if (!(tmpe.getForgeEvent() instanceof CaptureEvent.SuccessfulCapture)) return;
        CaptureEvent.SuccessfulCapture event = (CaptureEvent.SuccessfulCapture) tmpe.getForgeEvent();

        EntityPixelmon pixelmon = event.getPokemon();
        Pokemon pokemon = pixelmon.getStoragePokemonData();
        IVStore iVs = pokemon.getIVs();
        EVStore eVs = pokemon.getEVs();
        String displayName = pokemon.getDisplayName();
        String nature = pokemon.getNature().getLocalizedName();
        String growth = pokemon.getGrowth().getLocalizedName();
        Moveset moveset = pokemon.getMoveset();
        String moveName1 = "无", moveName2 = "无", moveName3 = "无", moveName4 = "无";
        if (moveset.get(0) != null && moveset.get(0).baseAttack != null)
            moveName1 = moveset.get(0).baseAttack.getLocalizedName();
        if (moveset.get(1) != null && moveset.get(1).baseAttack != null)
            moveName2 = moveset.get(1).baseAttack.getLocalizedName();
        if (moveset.get(2) != null && moveset.get(2).baseAttack != null)
            moveName3 = moveset.get(2).baseAttack.getLocalizedName();
        if (moveset.get(3) != null && moveset.get(3).baseAttack != null)
            moveName4 = moveset.get(3).baseAttack.getLocalizedName();

        if (getConfig().contains("name." + displayName.toLowerCase()))
            displayName = getConfig().getString("name." + displayName.toLowerCase());
        if (getConfig().contains("nature." + nature.toLowerCase()))
            nature = getConfig().getString("nature." + nature.toLowerCase());
        if (getConfig().contains("growth." + growth.toLowerCase()))
            growth = getConfig().getString("growth." + growth.toLowerCase());
        if (getConfig().contains("moveset." + moveName1.toLowerCase()))
            moveName1 = getConfig().getString("moveset." + moveName1.toLowerCase());
        if (getConfig().contains("moveset." + moveName2.toLowerCase()))
            moveName2 = getConfig().getString("moveset." + moveName2.toLowerCase());
        if (getConfig().contains("moveset." + moveName3.toLowerCase()))
            moveName3 = getConfig().getString("moveset." + moveName3.toLowerCase());
        if (getConfig().contains("moveset." + moveName4.toLowerCase()))
            moveName4 = getConfig().getString("moveset." + moveName4.toLowerCase());

        boolean shiny = pokemon.isShiny();
        String str = "";
        if (shiny) {
            str = getConfig().getString("lang");
        } else if (getConfig().getBoolean("normal")) {
            str = getConfig().getString("lang1");
        }
        if (getConfig().getStringList("rare").contains(displayName.toLowerCase()) || getConfig().getStringList("rare").contains(pokemon.getDisplayName().toLowerCase())) {
            str = getConfig().getString("lang2");
        }
        if (str == null || str.isEmpty()) return;
        str = str.replace("<player>", event.player.getDisplayNameString())
                .replace("<pokemon>", pokemon.writeToNBT(new NBTTagCompound()).func_74779_i("Name"));
        String raw = getConfig().getString("showraw")
                .replace("[IVS.SPEED]", String.valueOf(iVs.speed))
                .replace("[IVS.ATTACK]", String.valueOf(iVs.attack))
                .replace("[IVS.DEFENCE]", String.valueOf(iVs.defence))
                .replace("[IVS.HP]", String.valueOf(iVs.hp))
                .replace("[IVS.SPECIALATTACK]", String.valueOf(iVs.specialAttack))
                .replace("[IVS.SPECIALDEFENCE]", String.valueOf(iVs.specialDefence))
                .replace("[EVS.SPEED]", String.valueOf(eVs.speed))
                .replace("[EVS.ATTACK]", String.valueOf(eVs.attack))
                .replace("[EVS.DEFENCE]", String.valueOf(eVs.defence))
                .replace("[EVS.HP]", String.valueOf(eVs.hp))
                .replace("[EVS.SPECIALATTACK]", String.valueOf(eVs.specialAttack))
                .replace("[EVS.SPECIALDEFENCE]", String.valueOf(eVs.specialDefence))
                .replace("[LEVEL]", String.valueOf(pokemon.getLevel()))
                .replace("[NATURE]", nature)
                .replace("[GROWTH]", growth)
                .replace("[MOVESET1]", moveName1)
                .replace("[MOVESET2]", moveName2)
                .replace("[MOVESET3]", moveName3)
                .replace("[MOVESET4]", moveName4)
                .replace("<dpokemon>", displayName)
                .replace("<pokemon>", pokemon.writeToNBT(new NBTTagCompound()).func_74779_i("Name"));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a " + raw.replace("<lang>", str)
                .replace("<locpokemon>", pixelmon.getStoragePokemonData().getDisplayName())
                .replace("[ABLITY]", pokemon.getAbility().getLocalizedName()));
    }

    private FileConfiguration getConfig() {
        return Main.INSTANCE.getConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            Main.INSTANCE.reloadConfig();
            sender.sendMessage("reload ok");
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("show")) {
            Player p = (Player) sender;
            int row = Integer.valueOf(args[1]);
            if (row < 1 || row > 6) {
                p.sendMessage("位置不正确,必须为1~6");
                return true;
            }
            Pokemon pokemon = Pixelmon.storageManager.getParty(p.getUniqueId()).get(row - 1);
            IVStore iVs = pokemon.getIVs();
            EVStore eVs = pokemon.getEVs();
            String displayName = pokemon.getDisplayName();
            String nature = pokemon.getNature().getLocalizedName();
            String growth = pokemon.getGrowth().getLocalizedName();
            Moveset moveset = pokemon.getMoveset();
            String moveName1 = "无", moveName2 = "无", moveName3 = "无", moveName4 = "无";
            if (moveset.get(0) != null && moveset.get(0).baseAttack != null)
                moveName1 = moveset.get(0).baseAttack.getLocalizedName();
            if (moveset.get(1) != null && moveset.get(1).baseAttack != null)
                moveName2 = moveset.get(1).baseAttack.getLocalizedName();
            if (moveset.get(2) != null && moveset.get(2).baseAttack != null)
                moveName3 = moveset.get(2).baseAttack.getLocalizedName();
            if (moveset.get(3) != null && moveset.get(3).baseAttack != null)
                moveName4 = moveset.get(3).baseAttack.getLocalizedName();

            if (getConfig().contains("name." + displayName.toLowerCase()))
                displayName = getConfig().getString("name." + displayName.toLowerCase());
            if (getConfig().contains("nature." + nature.toLowerCase()))
                nature = getConfig().getString("nature." + nature.toLowerCase());
            if (getConfig().contains("growth." + growth.toLowerCase()))
                growth = getConfig().getString("growth." + growth.toLowerCase());
            if (getConfig().contains("moveset." + moveName1.toLowerCase()))
                moveName1 = getConfig().getString("moveset." + moveName1.toLowerCase());
            if (getConfig().contains("moveset." + moveName2.toLowerCase()))
                moveName2 = getConfig().getString("moveset." + moveName2.toLowerCase());
            if (getConfig().contains("moveset." + moveName3.toLowerCase()))
                moveName3 = getConfig().getString("moveset." + moveName3.toLowerCase());
            if (getConfig().contains("moveset." + moveName4.toLowerCase()))
                moveName4 = getConfig().getString("moveset." + moveName4.toLowerCase());

            String raw = getConfig().getString("showraw2")
                    .replace("[IVS.SPEED]", String.valueOf(iVs.speed))
                    .replace("[IVS.ATTACK]", String.valueOf(iVs.attack))
                    .replace("[IVS.DEFENCE]", String.valueOf(iVs.defence))
                    .replace("[IVS.HP]", String.valueOf(iVs.hp))
                    .replace("[IVS.SPECIALATTACK]", String.valueOf(iVs.specialAttack))
                    .replace("[IVS.SPECIALDEFENCE]", String.valueOf(iVs.specialDefence))
                    .replace("[EVS.SPEED]", String.valueOf(eVs.speed))
                    .replace("[EVS.ATTACK]", String.valueOf(eVs.attack))
                    .replace("[EVS.DEFENCE]", String.valueOf(eVs.defence))
                    .replace("[EVS.HP]", String.valueOf(eVs.hp))
                    .replace("[EVS.SPECIALATTACK]", String.valueOf(eVs.specialAttack))
                    .replace("[EVS.SPECIALDEFENCE]", String.valueOf(eVs.specialDefence))
                    .replace("[LEVEL]", String.valueOf(pokemon.getLevel()))
                    .replace("[NATURE]", nature)
                    .replace("[GROWTH]", growth)
                    .replace("[MOVESET1]", moveName1)
                    .replace("[MOVESET2]", moveName2)
                    .replace("[MOVESET3]", moveName3)
                    .replace("[MOVESET4]", moveName4);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a " + raw.replace("[ABLITY]", pokemon.getAbility().getLocalizedName())
                    .replace("<pokemon>", pokemon.writeToNBT(new NBTTagCompound()).func_74779_i("Name"))
                    .replace("<locpokemon>", pokemon.getBaseStats().pixelmonName)
                    .replace("<player>", p.getDisplayName())
                    .replace("<dpokemon>", displayName));
            return true;
        }
        return true;
    }
}
