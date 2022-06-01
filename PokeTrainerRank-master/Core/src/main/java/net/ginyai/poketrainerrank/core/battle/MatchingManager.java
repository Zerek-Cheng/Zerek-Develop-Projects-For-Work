package net.ginyai.poketrainerrank.core.battle;

import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import net.ginyai.poketrainerrank.api.util.Tuple;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.party.CoupleParty;
import net.ginyai.poketrainerrank.core.party.Party;
import net.ginyai.poketrainerrank.core.party.PartyManager;
import net.ginyai.poketrainerrank.core.party.SingleParty;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.*;
import java.util.function.Predicate;

/*
    TODO: 更好的排队算法
        进入队列生成排队用数据
        前置时间,
        契合度 = f(p1,p2,t) ,f由客户定义
        异步计算f 并且排序
        根据当前队列玩家数量 选择契合度最高的几对
 */
public class MatchingManager {
//    public volatile boolean isMatching;

    public static final MatchingManager instance = new MatchingManager();

    private final Map<RankSeason, Map<UUID, Tuple<Long, SingleParty>>> singlePartyMap = new HashMap<>();

    private final Map<RankSeason, Map<UUID, Tuple<Long, Party>>> coupleMap = new HashMap<>();

    private static <T> T random(Collection<T> collection) {
        int random = (int) (collection.size() * Math.random());
        for (T t : collection) {
            if (random-- > 0) {
                return t;
            }
        }
        return null;
    }

    public boolean addSingle(RankSeason season, SingleParty party) {
        if (check(party.getUuid())) {
            return false;
        } else {
            singlePartyMap.computeIfAbsent(season, season1 -> new HashMap<>()).put(party.getUuid(), new Tuple<>(System.currentTimeMillis(), party));
            return true;
        }
    }

    public boolean addCouple(RankSeason season, Party party) {
        Map<UUID, Tuple<Long, Party>> map = coupleMap.computeIfAbsent(season, season1 -> new HashMap<>());
        long time = System.currentTimeMillis();
        if (party instanceof SingleParty) {
            SingleParty singleParty = party.asSingleParty();
            if (check(singleParty.getUuid())) {
                return false;
            } else {
                map.put(singleParty.getUuid(), new Tuple<>(time, singleParty));
                return true;
            }
        } else {
            CoupleParty coupleParty = party.asCoupleParty();
            if (check(coupleParty.uuid1) || check(coupleParty.uuid2)) {
                return false;
            } else {
                map.put(coupleParty.uuid1, new Tuple<>(time, coupleParty));
                map.put(coupleParty.uuid2, new Tuple<>(time, coupleParty));
                return true;
            }
        }
    }

    public boolean check(UUID uuid) {
        for (Map<UUID, Tuple<Long, SingleParty>> map : singlePartyMap.values()) {
            if (map.containsKey(uuid)) {
                return true;
            }
        }
        for (Map<UUID, Tuple<Long, Party>> map : coupleMap.values()) {
            if (map.containsKey(uuid)) {
                return true;
            }
        }
        return false;
    }

    public Party remove(UUID uuid) {
        for (Map<UUID, Tuple<Long, SingleParty>> map : singlePartyMap.values()) {
            Tuple<Long, SingleParty> tuple = map.remove(uuid);
            if (tuple != null) {
                SingleParty party = tuple.getSecond();
                party.getPlayer().ifPresent(player->player.sendMessage(Utils.translate("poketrainerrank.battle.leaveMatchingQueue")));
                return party;
            }
        }
        for (Map<UUID, Tuple<Long, Party>> map : coupleMap.values()) {
            Tuple<Long, Party> tuple = map.remove(uuid);
            if (tuple != null) {
                Party party = tuple.getSecond();
                if (party instanceof CoupleParty) {
                    map.remove(party.asCoupleParty().findOther(uuid));
                }
                party.sendMessage(Utils.translate("poketrainerrank.battle.leaveMatchingQueue"));
                return party;
            }
        }
        return null;
    }

    public SingleParty remove(SingleParty party) {
        remove(party.getUuid());
        return party;
    }

    public CoupleParty remove(CoupleParty party) {
        remove(party.getUuid1());
        return party;
    }

    public Party remove(Party party) {
        if(party instanceof SingleParty) {
            return remove(party.asSingleParty());
        } else {
            return remove(party.asCoupleParty());
        }
    }

    public void clear() {
        singlePartyMap.clear();
        coupleMap.clear();
    }

    public void tick() {
        //remove timeout parties
        if (PokeTrainerRankMod.maxQueueTime() > 0) {
            long l = System.currentTimeMillis() - PokeTrainerRankMod.maxQueueTime();
            Predicate<Tuple<Long, ? extends Party>> predicate = t -> {
                if (t.getFirst() < l) {
                    t.getSecond().sendMessage(Utils.translate("poketrainerrank.matching.timeout"));
                    return true;
                } else {
                    return false;
                }
            };
            singlePartyMap.forEach((season, map) -> map.values().removeIf(predicate));
            coupleMap.forEach((season, map) -> map.values().removeIf(predicate));
        }
        singlePartyMap.forEach((season, map) -> {
            Collection<Tuple<Long, SingleParty>> parties = map.values();
            if (parties.size() > 1) {
                Tuple<Long, SingleParty> tuple = random(parties);
                if (tuple == null) {
                    return;
                }
                SingleParty party = tuple.getSecond();
                if (checkTeam(season, party)) {
                    remove(party);
                    return;
                }
                Optional<SingleParty> optionalTarget = parties.stream()
                        .map(Tuple::getSecond)
                        .filter(p -> p.canBattleWith(season, party))
                        .filter(p -> !p.equals(party))
                        .findAny();
                if (optionalTarget.isPresent()) {
                    SingleParty target = optionalTarget.get();
                    if (checkTeam(season, target)) {
                        remove(target);
                        return;
                    }
                    RankBattleManager.instance.startBattleDelay(season, party, target);
                    remove(party.getUuid());
                    remove(target.getUuid());
                }
            }
        });
        coupleMap.forEach((season, map) -> {
            Collection<Tuple<Long, Party>> parties = map.values();
            if (parties.size() > 1) {
                Tuple<Long, Party> tuple = random(parties);
                if (tuple == null) {
                    return;
                }
                Party party = tuple.getSecond();
                if(checkTeam(season, party)) {
                    remove(party);
                    return;
                }
                if (party instanceof SingleParty) {
                    SingleParty singleParty = (SingleParty) party;
                    Optional<SingleParty> optionalPattern = parties.stream()
                            .map(Tuple::getSecond)
                            .filter(p -> p instanceof SingleParty)
                            .map(p -> (SingleParty) p)
                            .filter(p -> p.canBattleWith(season, party))
                            .filter(p -> !p.equals(party))
                            .findAny();
                    if (optionalPattern.isPresent()) {
                        SingleParty pattern = optionalPattern.get();
                        if(checkTeam(season, pattern)) {
                            remove(pattern);
                            return;
                        }
                        Optional<EntityPlayerMP> optionalPlayer1 = singleParty.getPlayer();
                        Optional<EntityPlayerMP> optionalPlayer2 = pattern.getPlayer();
                        if (!optionalPlayer1.isPresent() || !optionalPlayer2.isPresent()) {
                            return;
                        }
                        Optional<Party> optionalTarget = parties.stream()
                                .map(Tuple::getSecond)
                                .filter(p -> !p.equals(party) && !p.equals(pattern))
                                .filter(p -> p.canBattleWith(season, singleParty))
                                .filter(p -> p.canBattleWith(season, pattern))
                                .findAny();
                        if (!optionalTarget.isPresent()) {
                            return;
                        }
                        Party target = optionalTarget.get();
                        if (target instanceof CoupleParty) {
                            if(checkTeam(season, target)) {
                                remove(target);
                                return;
                            }
                            CoupleParty coupleParty = PartyManager.instance.createCouple(optionalPlayer1.get(), optionalPlayer2.get());
                            RankBattleManager.instance.startBattleDelay(season, coupleParty, target);
                            remove(coupleParty.getUuid1());
                            remove(coupleParty.getUuid2());
                            remove(target.asCoupleParty().getUuid1());
                            remove(target.asCoupleParty().getUuid2());
                        } else {
                            SingleParty target1 = (SingleParty) target;
                            if(checkTeam(season, target1)) {
                                remove(target1);
                                return;
                            }
                            Optional<SingleParty> optionalTarget2 = parties.stream()
                                    .map(Tuple::getSecond)
                                    .filter(p -> p instanceof SingleParty)
                                    .map(p -> (SingleParty) p)
                                    .filter(p -> !p.equals(party) && !p.equals(pattern) && !p.equals(target1))
                                    .filter(p -> p.canBattleWith(season, singleParty))
                                    .filter(p -> p.canBattleWith(season, pattern))
                                    .findAny();
                            if (!optionalTarget2.isPresent()) {
                                return;
                            }
                            if(checkTeam(season, optionalTarget2.get())) {
                                remove(optionalTarget2.get());
                                return;
                            }
                            Optional<EntityPlayerMP> optionalTPlayer1 = target1.getPlayer();
                            Optional<EntityPlayerMP> optionalTPlayer2 = optionalTarget2.get().getPlayer();
                            if (!optionalTPlayer1.isPresent() || !optionalTPlayer2.isPresent()) {
                                return;
                            }
                            CoupleParty coupleParty = PartyManager.instance.createCouple(optionalPlayer1.get(), optionalPlayer2.get());
                            CoupleParty targetCouple = PartyManager.instance.createCouple(optionalTPlayer1.get(), optionalTPlayer2.get());
                            RankBattleManager.instance.startBattleDelay(season, coupleParty, targetCouple);
                            remove(coupleParty.getUuid1());
                            remove(coupleParty.getUuid2());
                            remove(targetCouple.getUuid1());
                            remove(targetCouple.getUuid2());
                        }
                    }
                } else {
                    CoupleParty coupleParty = (CoupleParty) party;
                    Optional<Party> optionalTarget = parties.stream()
                            .map(Tuple::getSecond)
                            .filter(p -> !p.equals(party))
                            .filter(p -> p.canBattleWith(season, coupleParty))
                            .findAny();
                    if (!optionalTarget.isPresent()) {
                        return;
                    }
                    Party target = optionalTarget.get();
                    if (target instanceof CoupleParty) {
                        if(checkTeam(season, target)) {
                            remove(target);
                            return;
                        }
                        RankBattleManager.instance.startBattleDelay(season, coupleParty, target);
                        remove(coupleParty.getUuid1());
                        remove(coupleParty.getUuid2());
                        remove(target.asCoupleParty().getUuid1());
                        remove(target.asCoupleParty().getUuid2());
                    } else {
                        SingleParty target1 = (SingleParty) target;
                        if(checkTeam(season, target1)) {
                            remove(target1);
                            return;
                        }
                        Optional<SingleParty> optionalTarget2 = parties.stream()
                                .map(Tuple::getSecond)
                                .filter(p -> p instanceof SingleParty)
                                .map(p -> (SingleParty) p)
                                .filter(p -> !p.equals(party) && !p.equals(target1))
                                .filter(p -> p.canBattleWith(season, coupleParty))
                                .findAny();
                        if (!optionalTarget2.isPresent()) {
                            return;
                        }
                        if(checkTeam(season, optionalTarget2.get())) {
                            remove(optionalTarget2.get());
                            return;
                        }
                        Optional<EntityPlayerMP> optionalTPlayer1 = target1.getPlayer();
                        Optional<EntityPlayerMP> optionalTPlayer2 = optionalTarget2.get().getPlayer();
                        if (!optionalTPlayer1.isPresent() || !optionalTPlayer2.isPresent()) {
                            return;
                        }
                        CoupleParty targetCouple = PartyManager.instance.createCouple(optionalTPlayer1.get(), optionalTPlayer2.get());
                        RankBattleManager.instance.startBattleDelay(season, coupleParty, targetCouple);
                        remove(coupleParty.getUuid1());
                        remove(coupleParty.getUuid2());
                        remove(targetCouple.getUuid1());
                        remove(targetCouple.getUuid2());
                    }
                }
            }
        });
    }

    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID uuid = event.player.getUniqueID();
        remove(uuid);
    }

    /**
     *
     * @param season The Season
     * @param party The party
     * @return true if not pass
     */
    private static boolean checkTeam(RankSeason season, Party party) {
        for(EntityPlayerMP player : party.getPlayers()) {
            if(BattleRegistry.getBattle(player) != null || RankBattleManager.instance.contains(player.getUniqueID())) {
                return true;
            }
        }
        for(PokemonSpec spec : season.getBlocks()) {
            if(party.find(spec)) {
                return true;
            }
        }
        int size = party.getTeamSize();
        Set<Integer> set = season.getTeamSize();
        return !set.isEmpty() && !set.contains(size);
    }
}
