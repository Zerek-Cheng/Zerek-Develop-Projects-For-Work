package net.ginyai.poketrainerrank.core.battle;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.RankTeamSelection;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelection;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelectionList;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import net.ginyai.poketrainerrank.api.util.Tuple;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.party.CoupleParty;
import net.ginyai.poketrainerrank.core.party.Party;
import net.ginyai.poketrainerrank.core.party.SingleParty;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static net.ginyai.poketrainerrank.core.util.Utils.translate;

public class RankBattleManager {

    private Map<Integer, TeamSelection> teamSelectMap;

    public static final RankBattleManager instance = new RankBattleManager();

    private Set<UUID> players = new HashSet<>();
    private Map<Integer, RankSeason> watching = new HashMap<>();
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private RankBattleManager() {
        Pixelmon.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void startBattleDelay(RankSeason season, Party party1, Party party2) {
        players.addAll(party1.getAllUuid());
        players.addAll(party2.getAllUuid());
        MatchingManager.instance.remove(party1);
        MatchingManager.instance.remove(party2);
        party1.sendMessage(translate("poketrainerrank.battle.startDelay"));
        party2.sendMessage(translate("poketrainerrank.battle.startDelay"));
        executor.schedule(()->PokeTrainerRankMod.instance.execute(()->startBattle(season,party1,party2)),PokeTrainerRankMod.battleStartDelay(), TimeUnit.MILLISECONDS);
    }

    public boolean contains(UUID uuid) {
        return players.contains(uuid);
    }

    private void startBattle(RankSeason season, Party party1, Party party2) {
        try {
            if( !party1.isOnline()) {
                party2.sendMessage(translate("poketrainerrank.battle.opponentOffline"));
                clear(party1, party2);
                return;
            }
            if( !party2.isOnline()) {
                party1.sendMessage(translate("poketrainerrank.battle.opponentOffline"));
                clear(party1, party2);
                return;
            }
            if(!season.getTeamSize().isEmpty()) {
                if(party1.getTeamSize() != party2.getTeamSize()) {
                    party1.sendMessage(translate("poketrainerrank.battle.differentTeamSize"));
                    party2.sendMessage(translate("poketrainerrank.battle.differentTeamSize"));
                    clear(party1, party2);
                    return;
                }
                if(!season.getTeamSize().contains(party1.getTeamSize())) {
                    party1.sendMessage(translate("poketrainerrank.battle.teamsize"));
                    party2.sendMessage(translate("poketrainerrank.battle.teamsize"));
                    clear(party1, party2);
                    return;
                }
            }
            for(PokemonSpec spec:season.getBlocks()) {
                if(party1.find(spec)) {
                    party1.sendMessage(translate("poketrainerrank.battle.self.cannotTake"));
                    party2.sendMessage(translate("poketrainerrank.battle.oppo.cannotTake"));
                    clear(party1, party2);
                    return;
                }
                if(party2.find(spec)) {
                    party1.sendMessage(translate("poketrainerrank.battle.oppo.cannotTake"));
                    party2.sendMessage(translate("poketrainerrank.battle.self.cannotTake"));
                    clear(party1, party2);
                    return;
                }
            }
            Consumer<EntityPlayerMP> endBattle = player->{
                BattleControllerBase bc = BattleRegistry.getBattle(player);
                if (bc != null) {
                    if (bc.hasSpectator(player)) {
                        bc.removeSpectator(player);
                    } else {
                        bc.endBattle(EnumBattleEndCause.FORCE);
                    }
                }
            };
            party1.getPlayers().forEach(endBattle);
            party2.getPlayers().forEach(endBattle);
            boolean flag = party1 instanceof CoupleParty;
            if ((season.getPartySize() == 0 && flag) || (flag ^ party2 instanceof CoupleParty)) {
                PokeTrainerRankMod.logger.warn("Try to start battle with illegal party size.", new IllegalStateException());
                party1.sendMessage(translate("poketrainerrank.battle.logicError"));
                party2.sendMessage(translate("poketrainerrank.battle.logicError"));
                clear(party1, party2);
                return;
            }
            PokeTrainerRankMod.getPlugin().setLocation(new Tuple<>(
                    party1.getPlayers().stream().map(PokeTrainerRankMod::trans).collect(Collectors.toList()),
                    party2.getPlayers().stream().map(PokeTrainerRankMod::trans).collect(Collectors.toList()))
            );
            BattleRules rules = season.getBattleRules(flag);
            if (flag || rules.isDefault()) {
                BattleControllerBase controller = BattleRegistry.startBattle(party1.createParticipant(), party2.createParticipant(), rules);
                addListening(party1, party2, season, controller);
            } else {
                startTeamSelection(party1.asSingleParty(), party2.asSingleParty(), rules, season);
            }
        } catch (Throwable e) {
            PokeTrainerRankMod.logger.error("Failed to start battle for " + party1 + "&" + party2, e);
            party1.sendMessage(translate("poketrainerrank.battle.failedToStart"));
            party2.sendMessage(translate("poketrainerrank.battle.failedToStart"));
            clear(party1, party2);
        }
    }
    private void startTeamSelection(SingleParty party1, SingleParty party2, BattleRules rules, RankSeason season) {
        if (teamSelectMap == null) {
            teamSelectMap = ReflectionHelper.getPrivateValue(TeamSelectionList.class, null, "teamSelectMap");
        }
        int id = ReflectionHelper.getPrivateValue(TeamSelectionList.class, null, "idCounter");
        ReflectionHelper.setPrivateValue(TeamSelectionList.class, null, id + 1, "idCounter");
        RankTeamSelection selection = new RankTeamSelection(id, rules, PokeTrainerRankMod.showRules(),
                c -> addListening(party1, party2, season, c),
                (p1, p2) -> {
                    onLose(season, p1);
                    onWin(season, p2);
                },
                Utils.getPlayerData(party1.getUuid()).orElseThrow(IllegalStateException::new),
                Utils.getPlayerData(party2.getUuid()).orElseThrow(IllegalStateException::new)
        );
        teamSelectMap.put(id, selection);
        selection.initializeClient();
    }
    private void addListening(Party party1, Party party2, RankSeason season, BattleControllerBase controller) {
        if (controller == null) {
            PokeTrainerRankMod.logger.error("Failed to start battle for " + party1 + "&" + party2);
            party1.sendMessage(translate("poketrainerrank.battle.failedToStart"));
            party2.sendMessage(translate("poketrainerrank.battle.failedToStart"));
            clear(party1, party2);
        } else {
            watching.put(controller.battleIndex, season);
        }
    }

    private void clear(Party party1, Party party2) {
        players.removeAll(party1.getAllUuid());
        players.removeAll(party2.getAllUuid());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            players.remove(player.getUniqueID());
            BattleControllerBase controller = BattleRegistry.getBattle(player);
            if(controller!=null && watching.containsKey(controller.battleIndex)) {
                RankSeason season = watching.remove(controller.battleIndex);
                Optional<PlayerParticipant> optionalParticipant = controller.participants.stream()
                        .filter(battleParticipant ->  battleParticipant instanceof PlayerParticipant)
                        .map(battleParticipant -> (PlayerParticipant)battleParticipant)
                        .filter(playerParticipant -> playerParticipant.player.getUniqueID().equals(player.getUniqueID()))
                        .findAny();
                if(!optionalParticipant.isPresent()) {
                    return;
                }
                PlayerParticipant playerParticipant = optionalParticipant.get();
                for(BattleParticipant participant1:controller.participants) {
                    if(participant1 instanceof PlayerParticipant) {
                        if(participant1.team == playerParticipant.team) {
                            onLose(season,((PlayerParticipant) participant1).player);
                        } else {
                            onWin(season, ((PlayerParticipant) participant1).player);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event) {
        BattleControllerBase controller = event.bc;
        if (event.cause == EnumBattleEndCause.NORMAL || event.cause == EnumBattleEndCause.FORFEIT) {
            if (watching.containsKey(controller.battleIndex)) {
                RankSeason season = watching.remove(controller.battleIndex);
                for (Map.Entry<BattleParticipant, BattleResults> entry : event.results.entrySet()) {
                    if (entry.getKey() instanceof PlayerParticipant) {
                        PlayerParticipant playerParticipant = (PlayerParticipant) entry.getKey();
                        switch (entry.getValue()) {
                            case VICTORY:
                                onWin(season, playerParticipant.player);
                                break;
                            case DEFEAT:
                            case FLEE:
                                onLose(season, playerParticipant.player);
                                break;
                            case DRAW:
                                onDraw(season, playerParticipant.player);
                                break;
                            default:
                        }
                    }
                }
            }
        }
        controller.participants.stream()
                .filter(p->p instanceof PlayerParticipant)
                .map(p->((PlayerParticipant) p).player)
                .map(Entity::getUniqueID)
                .forEach(players::remove);
    }

    private void onWin(RankSeason season, EntityPlayerMP player) {
        UUID uuid = player.getUniqueID();
        season.getDataManager().getOrCreateData(uuid).join()
                .getRank().getWinReward()
                .apply(player,season)
                .thenAccept((v)->season.getDataManager().addWin(uuid));
    }

    private void onLose(RankSeason season, EntityPlayerMP player) {
        UUID uuid = player.getUniqueID();
        season.getDataManager().getOrCreateData(uuid).join()
                .getRank().getLoseReward()
                .apply(player,season)
                .thenAccept((v)->season.getDataManager().addLose(uuid));
    }

    private void onDraw(RankSeason season, EntityPlayerMP player) {
        UUID uuid = player.getUniqueID();
        season.getDataManager().addBattle(uuid);
    }

}
