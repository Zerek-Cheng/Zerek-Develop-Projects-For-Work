package com._0myun.minecraft.pokemongoldchallenge;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Optional;
import java.util.UUID;

public class Utils {
    public static PlayerParticipant prepareParticipant(UUID uuid) {
        EntityPlayerMP player = getPlayer(uuid);
        PlayerPartyStorage data = Pixelmon.storageManager.getParty(player);
        EntityPixelmon pixelmon = data.getAndSendOutFirstAblePokemon(player);
        return new PlayerParticipant(player, pixelmon);
    }
    public static EntityPlayerMP getPlayer(UUID uuid) {
        //noinspection ConstantConditions
        return Pixelmon.storageManager.getParty(uuid).getPlayer();
    }

}
