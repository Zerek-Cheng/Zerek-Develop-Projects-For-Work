package com._0myun.minecraft.pixelmonknockout.party;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.*;


public class SingleParty implements Party {
    private UUID uuid;

    public SingleParty(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public BattleParticipant[] createParticipant() {
        Optional<PlayerParticipant> participant = prepareParticipant(uuid);
        return new BattleParticipant[]{participant.get()};
    }

    @Override
    public boolean contains(UUID uuid) {
        return this.uuid.equals(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleParty that = (SingleParty) o;
        return Objects.equals(uuid, that.uuid);
    }

    public static Optional<PlayerParticipant> prepareParticipant(UUID uuid) {
        EntityPlayerMP player = Pixelmon.storageManager.getParty(uuid).getPlayer();
        PlayerPartyStorage data = Pixelmon.storageManager.getParty(player);
        for (Pokemon pokemon : data.getAll()) {
            if (pokemon==null)continue;
            pokemon.heal();
        }
        EntityPixelmon pixelmon = data.getAndSendOutFirstAblePokemon(player);
        if (pixelmon == null) {
            return Optional.empty();
        }
        return Optional.of(new PlayerParticipant(player, pixelmon));
    }
}
