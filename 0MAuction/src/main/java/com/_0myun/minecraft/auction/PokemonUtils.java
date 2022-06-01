package com._0myun.minecraft.auction;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.EVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.IVStore;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PokemonUtils {

    public static ItemStack getPokemonPhotoItem(Pokemon pokemon) {
        try {
            ItemStack itemStack = new ItemStack(ConfigUtils.getPokemonPhotoId());
            itemStack = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemStack));
            NBTTagCompound nbt = pokemon.writeToNBT(new NBTTagCompound());
            short id = nbt.func_74765_d("ndex");

            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
            NbtCompound nbtC = NbtFactory.asCompound(nbtWrapper);
            nbtC.put("form", new byte[]{(byte) pokemon.getForm()});
            nbtC.put("gender", new byte[]{0});
            nbtC.put("ndex", id);
            nbtC.put("Shiny", new byte[]{(byte) (pokemon.isShiny() ? 1 : 0)});
            NbtFactory.setItemTag(itemStack, nbtC);
            return itemStack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> pokemonReplace(List<String> list, Pokemon pokemon) {
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);

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

            str = str
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
                    .replace("[ABLITY]", pokemon.getAbility().getLocalizedName())
                    .replace("[SHINY]", pokemon.isShiny() ? "闪光" : "不闪光")
                    .replace("[GENDER]", pokemon.getGender().getLocalizedName())
                    .replace("[ITEM]", pokemon.getHeldItemAsItemHeld().getLocalizedName().equalsIgnoreCase("item..name") ? "无" : pokemon.getHeldItemAsItemHeld().getLocalizedName())
                    .replace("<dpokemon>", displayName)
                    .replace("<locpokemon>", pokemon.getDisplayName())
                    .replace("<pokemon>", pokemon.writeToNBT(new NBTTagCompound()).func_74779_i("Name"));

            list.set(i, str);
        }
        return list;
        /**
         *
         #变量：
         #  [IVS.SPEED]
         #  [IVS.ATTACK]
         #  [IVS.DEFENCE]
         #  [IVS.HP]
         #  [IVS.SPECIALATTACK]
         #  [IVS.SPECIALDEFENCE]
         #  [EVS.SPEED]
         #  [EVS.ATTACK]
         #  [EVS.DEFENCE]
         #  [EVS.HP]
         #  [EVS.SPECIALATTACK]
         #  [EVS.SPECIALDEFENCE]
         #  [LEVEL]
         #  [NATURE]
         #  [GROWTH]
         #  [MOVESET1]
         #  [MOVESET2]
         #  [MOVESET3]
         #  [MOVESET4]
         #  [ABLITY]
         #  [SHINY]
         #  [ITEM]
         #
         #  <dpokemon>
         #  <locpokemon>
         #  <pokemon>
         */
    }
}
