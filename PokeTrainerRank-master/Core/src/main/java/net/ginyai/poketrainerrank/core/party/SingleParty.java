package net.ginyai.poketrainerrank.core.party;

import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.ginyai.poketrainerrank.core.data.SeasonDataManager;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.translation.I18n;

import java.util.*;

import static net.ginyai.poketrainerrank.core.util.Utils.*;


public class SingleParty implements Party {
    private UUID uuid;

    public SingleParty(UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }

    public Optional<EntityPlayerMP> getPlayer() {
        return Utils.getPlayer(uuid);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BattleParticipant[] createParticipant() {
        return prepareParticipant(uuid)
                .map(p -> new BattleParticipant[]{p})
                .orElseThrow(() -> new RuntimeException(I18n.translateToLocal("poketrainerrank.battle.cannotBattle")));
    }

    @Override
    public boolean canBattleWith(RankSeason season, Party other) {
        if(!season.getTeamSize().isEmpty()) {
            if(getTeamSize() != other.getTeamSize()) {
                return false;
            }
        }
        SeasonDataManager dataManager = season.getDataManager();
        PlayerData data = dataManager.getDataFromCache(uuid);
        if(data == null) {
            return false;
        }
        if(other instanceof SingleParty) {
            PlayerData otherData = dataManager.getDataFromCache(((SingleParty) other).getUuid());
            if(otherData == null) {
                return false;
            }
            return matching(data,otherData);
        } else if (other instanceof CoupleParty){
            CoupleParty coupleParty = (CoupleParty) other;
            PlayerData other1 = dataManager.getDataFromCache(coupleParty.uuid1);
            PlayerData other2 = dataManager.getDataFromCache(coupleParty.uuid2);
            return matching(data,other1) && matching(data,other2);
        }
        return false;
    }

    @Override
    public boolean canBattle() {
        return getPlayerData(uuid).map(data -> data.countAblePokemon() > 0).orElse(false);
    }

    @Override
    public boolean contains(UUID uuid) {
        return this.uuid.equals(uuid);
    }

    @Override
    public int getTeamSize() {
        return countTeam(uuid);
    }

    @Override
    public boolean find(PokemonSpec spec) {
        return getPlayerData(uuid)
                .map(d->d.findOne(spec))
                .orElse(null) != null;
    }

    @Override
    public boolean isOnline() {
        return getPlayer().isPresent();
    }

    @Override
    public List<UUID> getAllUuid() {
        return Collections.singletonList(uuid);
    }

    @Override
    public List<EntityPlayerMP> getPlayers() {
        return getPlayer().map(Collections::singletonList).orElse(Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleParty that = (SingleParty) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "SingleParty:[" + getPlayer().map(EntityPlayer::getName).orElse(uuid.toString()) + "]";
    }
}
