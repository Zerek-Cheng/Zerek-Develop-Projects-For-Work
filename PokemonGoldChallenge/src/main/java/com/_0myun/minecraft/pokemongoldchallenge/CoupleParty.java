package com._0myun.minecraft.pokemongoldchallenge;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class CoupleParty {
    public final UUID uuid1;
    public final UUID uuid2;

    private transient List<UUID> uuids;

    public CoupleParty(UUID uuid1, UUID uuid2) {
        this.uuid1 = Objects.requireNonNull(uuid1);
        this.uuid2 = Objects.requireNonNull(uuid2);
        uuids = Arrays.asList(uuid1, uuid2);
    }

    public BattleParticipant[] createParticipant() {
        EntityPlayerMP player1 = Pixelmon.storageManager.getParty(uuid1).getPlayer();
        BattleParticipant participant1 = Utils.prepareParticipant(uuid1);
        EntityPlayerMP player2 = Pixelmon.storageManager.getParty(uuid2).getPlayer();
        BattleParticipant participant2 = Utils.prepareParticipant(uuid2);
        return new BattleParticipant[]{participant1, participant2};
    }

}
