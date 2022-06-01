package com._0myun.minecraft.pokemongoldchallenge;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class SingleParty {
    public final UUID uuid1;

    private transient List<UUID> uuids;

    public SingleParty(UUID uuid1) {
        this.uuid1 = Objects.requireNonNull(uuid1);
        uuids = Arrays.asList(uuid1);
    }

    public BattleParticipant[] createParticipant() {
        EntityPlayerMP player1 = Pixelmon.storageManager.getParty(uuid1).getPlayer();
        BattleParticipant participant1 = Utils.prepareParticipant(uuid1);
        return new BattleParticipant[]{participant1};
    }

}
