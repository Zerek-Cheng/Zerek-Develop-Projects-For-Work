package com._0myun.minecraft.pixelmonknockout.party;

import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;

import java.util.UUID;

public interface Party {
    BattleParticipant[] createParticipant();

    boolean contains(UUID uuid);

    default SingleParty asSingleParty() {
        return (SingleParty) this;
    }

}
