package net.ginyai.poketrainerrank.core.party;

import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.UUID;

public interface Party {
    BattleParticipant[] createParticipant();

    boolean canBattleWith(RankSeason season, Party other);

    boolean canBattle();

    boolean contains(UUID uuid);

    int getTeamSize();

    boolean find(PokemonSpec spec);

    boolean isOnline();

    List<UUID> getAllUuid();

    List<EntityPlayerMP> getPlayers();

    default void sendMessage(ITextComponent component) {
        getPlayers().forEach(p->p.sendMessage(component));
    }

    default SingleParty asSingleParty() {
        return (SingleParty) this;
    }

    default CoupleParty asCoupleParty() {
        return (CoupleParty) this;
    }
}
