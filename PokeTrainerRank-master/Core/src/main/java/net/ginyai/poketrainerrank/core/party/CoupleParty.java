package net.ginyai.poketrainerrank.core.party;

import com.google.common.collect.ImmutableList;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.ginyai.poketrainerrank.core.data.SeasonDataManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.translation.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import static net.ginyai.poketrainerrank.core.util.Utils.*;

public class CoupleParty implements Party {
    public final UUID uuid1;
    public final UUID uuid2;

    private transient List<UUID> uuids;

    public CoupleParty(UUID uuid1, UUID uuid2) {
        this.uuid1 = Objects.requireNonNull(uuid1);
        this.uuid2 = Objects.requireNonNull(uuid2);
        uuids = ImmutableList.of(uuid1,uuid2);
    }

    public UUID getUuid1() {
        return uuid1;
    }

    public UUID getUuid2() {
        return uuid2;
    }

    public UUID findOther(UUID uuid) {
        if (uuid1.equals(uuid)) {
            return uuid2;
        } else {
            return uuid1;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BattleParticipant[] createParticipant() {
        EntityPlayerMP player1 = getPlayer(uuid1).orElseThrow(() -> new RuntimeException(I18n.translateToLocal("poketrainerrank.battle.offlinePlayer")));
        BattleParticipant participant1 = prepareParticipant(uuid1).orElseThrow(() -> new RuntimeException(player1.getName() + I18n.translateToLocal("poketrainerrank.battle.cannotBattle")));
        EntityPlayerMP player2 = getPlayer(uuid2).orElseThrow(() -> new RuntimeException(I18n.translateToLocal("poketrainerrank.battle.offlinePlayer")));
        BattleParticipant participant2 = prepareParticipant(uuid2).orElseThrow(() -> new RuntimeException(player2.getName() + I18n.translateToLocal("poketrainerrank.battle.cannotBattle")));
        return new BattleParticipant[]{participant1, participant2};
    }

    @Override
    public boolean canBattleWith(RankSeason season, Party other) {
        if(!season.getTeamSize().isEmpty()) {
            if(getTeamSize() != other.getTeamSize()) {
                return false;
            }
        }
        SeasonDataManager dataManager = season.getDataManager();
        PlayerData data1 = dataManager.getDataFromCache(uuid1);
        PlayerData data2 = dataManager.getDataFromCache(uuid2);
        if(data1 == null || data2 == null) {
            return false;
        }
        if(other instanceof SingleParty) {
            PlayerData otherData = dataManager.getDataFromCache(((SingleParty) other).getUuid());
            if(otherData == null) {
                return false;
            }
            return matching(data1,otherData) && matching(data2,otherData);
        } else if (other instanceof CoupleParty){
            CoupleParty coupleParty = (CoupleParty) other;
            PlayerData other1 = dataManager.getDataFromCache(coupleParty.uuid1);
            PlayerData other2 = dataManager.getDataFromCache(coupleParty.uuid2);
            return matching(data1,other1) && matching(data2,other1)
                    &&matching(data1,other2) && matching(data2,other2);
        }
        return false;
    }

    @Override
    public boolean canBattle() {
        PlayerPartyStorage data1 = getPlayerData(uuid1).orElse(null);
        if (data1 == null) {
            return false;
        }
        PlayerPartyStorage data2 = getPlayerData(uuid2).orElse(null);
        if (data2 == null) {
            return false;
        }
        return data2.countAblePokemon() > 0 && data1.countAblePokemon() > 0;
    }

    @Override
    public boolean contains(UUID uuid) {
        return uuid1.equals(uuid) || uuid2.equals(uuid);
    }

    @Override
    public int getTeamSize() {
        int team1 = countTeam(uuid1);
        int team2 = countTeam(uuid2);
        return team1 == team2 ? team1 : 0;
    }

    @Override
    public boolean find(PokemonSpec spec) {
        return Objects.nonNull(getPlayerData(uuid1)
                .map(d->d.findOne(spec))
                .orElse(null))
                || Objects.nonNull(getPlayerData(uuid2)
                .map(d->d.findOne(spec))
                .orElse(null));
    }

    @Override
    public boolean isOnline() {
        return getPlayer(uuid1).isPresent() && getPlayer(uuid2).isPresent();
    }

    @Override
    public List<UUID> getAllUuid() {
        return uuids;
    }

    @Override
    public List<EntityPlayerMP> getPlayers() {
        List<EntityPlayerMP> list = new ArrayList<>();
        getPlayer(uuid1).ifPresent(list::add);
        getPlayer(uuid2).ifPresent(list::add);
        return list;
    }

    @Override
    public String toString() {
        Function<UUID, String> function = uuid -> getPlayer(uuid).map(EntityPlayer::getName).orElse(uuid.toString());
        return "CoupleParty:[" + function.apply(uuid1) + "," + function.apply(uuid2) + "]";
    }
}
