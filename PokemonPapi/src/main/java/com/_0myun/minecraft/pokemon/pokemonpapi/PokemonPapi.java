package com._0myun.minecraft.pokemon.pokemonpapi;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.regex.Pattern;

public class PokemonPapi extends PlaceholderExpansion {
    private static final String UNDEFINED = "UNDEFINED";

    public PokemonPapi() {
        this.register();
    }

    @Override
    public String getIdentifier() {
        return "pokemon";
    }

    @Override
    public String getAuthor() {
        return "0MYUN";
    }

    @Override
    public String getVersion() {
        return "RELEASE";
    }

    @Override
    public String onPlaceholderRequest(Player p, String tmp) {
        String result = getValue(p, tmp);
        return result;
    }

    public static boolean isInteger(String str) {
        if (str == null) return false;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public String getValue(Player p, String tmp) {
        String[] params = tmp.split("_");
        PlayerPartyStorage party = Pixelmon.storageManager.getParty(p.getUniqueId());
        if (Character.isDigit(params[0].charAt(0))) {//是数字
            int sort = Integer.valueOf(params[0]);
            if (sort < 1 || sort > 6) return UNDEFINED;
            Pokemon pokemon = party.get(sort - 1);
            if (pokemon == null) return UNDEFINED;
            switch (params[1].toLowerCase()) {
                case "id":
                    return String.valueOf(pokemon.writeToNBT(new NBTTagCompound()).func_74765_d("ndex"));
                case "health":
                    return String.valueOf(pokemon.getHealth());
                case "experience":
                    return String.valueOf(pokemon.getExperience());
                case "displayname":
                    return pokemon.getDisplayName();
                case "nickname":
                    return pokemon.getNickname();
                case "localname":
                    return pokemon.writeToNBT(new NBTTagCompound()).func_74779_i("Name");
                case "sex":
                    return pokemon.getGender().getLocalizedName();
                case "ability":
                    return pokemon.getAbility().getLocalizedName();
                case "experiencetolevelup":
                    return String.valueOf(pokemon.getExperienceToLevelUp());
                case "friendship":
                    return String.valueOf(pokemon.getFriendship());
                case "level":
                    return String.valueOf(pokemon.getLevel());
                case "ivs":
                    if (params.length < 3) return UNDEFINED;
                    IVStore iVs = pokemon.getIVs();
                    switch (params[2].toLowerCase()) {
                        case "attack":
                            return String.valueOf(iVs.attack);
                        case "speed":
                            return String.valueOf(iVs.speed);
                        case "defence":
                            return String.valueOf(iVs.defence);
                        case "hp":
                            return String.valueOf(iVs.hp);
                        case "specialdefence":
                            return String.valueOf(iVs.specialDefence);
                        case "specialattack":
                            return String.valueOf(iVs.specialAttack);
                        default:
                            return UNDEFINED;
                    }
                case "evs":
                    if (params.length < 3) return UNDEFINED;
                    EVStore eVs = pokemon.getEVs();
                    switch (params[2].toLowerCase()) {
                        case "attack":
                            return String.valueOf(eVs.attack);
                        case "speed":
                            return String.valueOf(eVs.speed);
                        case "defence":
                            return String.valueOf(eVs.defence);
                        case "hp":
                            return String.valueOf(eVs.hp);
                        case "specialdefence":
                            return String.valueOf(eVs.specialDefence);
                        case "specialattack":
                            return String.valueOf(eVs.specialAttack);
                        default:
                            return UNDEFINED;
                    }
                case "totalivs":
                    int tmpa[] = {0};
                    Arrays.stream(pokemon.getIVs().getArray()).forEach(tmpb -> {
                        tmpa[0] += tmpb;
                    });
                    return String.valueOf(tmpa[0]);
                case "totalevs":
                    int tmpc[] = {0};
                    Arrays.stream(pokemon.getEVs().getArray()).forEach(tmpb -> {
                        tmpc[0] += tmpb;
                    });
                    return String.valueOf(tmpc[0]);
                case "ivspercentage":
                    int tmpd[] = {0};
                    Arrays.stream(pokemon.getIVs().getArray()).forEach(tmpb -> {
                        tmpd[0] += tmpb;
                    });
                    return String.valueOf(Double.valueOf(((tmpd[0] / (31d * 6d))) * 100d).intValue());
                case "evspercentage":
                    int tmpe[] = {0};
                    Arrays.stream(pokemon.getEVs().getArray()).forEach(tmpb -> {
                        tmpe[0] += tmpb;
                    });
                    return String.valueOf(Double.valueOf(((tmpe[0] / (255d * 2d))) * 100d).intValue());
                case "growth":
                    return pokemon.getGrowth().getLocalizedName();
                case "nature":
                    return pokemon.getNature().getLocalizedName();
                case "moveset":
                    if (params.length < 3) return UNDEFINED;
                    Moveset moveset = pokemon.getMoveset();
                    if (moveset.get(Integer.valueOf(params[2])) == null) return UNDEFINED;
                    return moveset.get(Integer.valueOf(params[2])).baseAttack.getLocalizedName();
                case "shiny":
                    return pokemon.isShiny() ? "闪光" : "普通";
                default:
                    return UNDEFINED;
            }
        } else {
            party.updatePartyCache();
            switch (params[0].toLowerCase()) {
                case "wins":
                    return String.valueOf(party.stats.getWins());
                case "loses":
                    return String.valueOf(party.stats.getLosses());
                case "currentexp":
                    return String.valueOf(party.stats.getCurrentExp());
                case "totalbred":
                    return String.valueOf(party.stats.getTotalBred());
                case "totalevolved":
                    return String.valueOf(party.stats.getTotalEvolved());
                case "totalexp":
                    return String.valueOf(party.stats.getTotalExp());
                case "totalhatched":
                    return String.valueOf(party.stats.getTotalHatched());
                case "totalkills":
                    return String.valueOf(party.stats.getTotalKills());
                case "dexsize":
                    return String.valueOf(party.pokedex.pokedexSize);
                case "dexsizeprecentage":
                    return String.valueOf(Double.valueOf((party.pokedex.countCaught() / Double.valueOf(party.pokedex.pokedexSize)) * 100d).intValue());
                case "dexcaught":
                    return String.valueOf(party.pokedex.countCaught());
                case "money":
                    return String.valueOf(party.getMoney());
                case "bagpokemon":
                    int amount = 0;
                    if (party.get(0) != null) amount++;
                    if (party.get(1) != null) amount++;
                    if (party.get(2) != null) amount++;
                    if (party.get(3) != null) amount++;
                    if (party.get(4) != null) amount++;
                    if (party.get(5) != null) amount++;
                    return String.valueOf(amount);
            }
        }
        return UNDEFINED;
    }

    /**
     * 获取宝可梦的翻译名称
     *
     * @param nbt 宝可梦nbt
     * @return 宝可梦的翻译名称
     */
    public String getLoacalName(NBTTagCompound nbt) {
        return I18n.func_74838_a("pixelmon." + nbt.func_74779_i("Name").toLowerCase() + ".name");
    }

    public String getLoacalName(String nbt) {
        if (nbt == null) return nbt;
        String result = I18n.func_74838_a("pixelmon." + nbt.toLowerCase() + ".name");
        if (result.equalsIgnoreCase("pixelmon." + nbt.toLowerCase() + ".name")) return nbt;
        return result;
    }
}
