package com._0myun.minecraft.auction.godtype;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class PokemonGood extends GoodType<Pokemon> {
    @Override
    public String getName() {
        return "神奇宝贝";
    }

    @Override
    public String toString(Pokemon good) {
        return good.writeToNBT(new NBTTagCompound()).toString();
    }

    @Override
    public Pokemon fromString(String str) {
        try {
            return Pixelmon.pokemonFactory.create(JsonToNBT.func_180713_a(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean giveGood(UUID uuid, Pokemon good) {
        return Pixelmon.storageManager.getPCForPlayer(uuid).add(good);
    }

    @Override
    public String getData(){
        return "pokemon";
    }

}
