package com.pixelmonmod.pixelmon.battles.rules.teamselection;

import com.pixelmonmod.pixelmon.RandomHelper;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.battles.rules.clauses.BattleClause;
import com.pixelmonmod.pixelmon.comm.ChatHandler;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RankTeamSelection extends TeamSelection {
    private BattleRules rules;
    private ParticipantSelection[] participants;
    private boolean showRules;
    private Consumer<BattleControllerBase> startedBattle;
    private BiConsumer<EntityPlayerMP, EntityPlayerMP> cancelBattle;
    private Method cancelBattleMethod;
    private Method getOtherMethod;

    public RankTeamSelection(int id, BattleRules rules, boolean showRules, Consumer<BattleControllerBase> startedBattle, BiConsumer<EntityPlayerMP, EntityPlayerMP> cancelBattle, PartyStorage... storages) {
        super(id, rules, showRules, storages);
        this.startedBattle = startedBattle;
        this.rules = rules;
        this.showRules = showRules;
        this.cancelBattle = cancelBattle;
        participants = ReflectionHelper.getPrivateValue(TeamSelection.class, this, "participants");
        for (PartyStorage storage : storages) {
            if (!(storage instanceof PlayerPartyStorage)) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void cancelBattle(ParticipantSelection cancelPart) {
        if (cancelBattleMethod == null) {
            cancelBattleMethod = ReflectionHelper.findMethod(TeamSelection.class, "cancelBattle", "cancelBattle", ParticipantSelection.class);
            cancelBattleMethod.setAccessible(true);
        }
        try {
            cancelBattleMethod.invoke(this, cancelPart);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private PlayerParticipant getPlayerPart(ParticipantSelection p) {
        //noinspection ConstantConditions
        return new PlayerParticipant(((PlayerPartyStorage) p.storage).getPlayer(), p.team, this.rules.battleType.numPokemon);
    }

    private ParticipantSelection getOther(ParticipantSelection p) {
        if (getOtherMethod == null) {
            getOtherMethod = ReflectionHelper.findMethod(TeamSelection.class, "getOther", "getOther", ParticipantSelection.class);
            getOtherMethod.setAccessible(true);
        }
        try {
            return (ParticipantSelection) getOtherMethod.invoke(this, p);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeTeamSelect() {
        TeamSelectionList.removeSelection(this.id);
    }

    @Override
    void startBattle() {
        ParticipantSelection cancelPart = null;
        for (ParticipantSelection p : participants) {
            String initValidate = this.rules.validateTeam(p.team);
            if (p.team.isEmpty() || initValidate != null) {
                EntityPlayerMP player = ((PlayerPartyStorage) p.storage).getPlayer();
                if (player != null && initValidate != null) {
                    ChatHandler.sendChat(player, "gui.battlerules.teamviolatedforce", BattleClause.getLocalizedName(initValidate));
                }
                while (!p.team.isEmpty()) {
                    p.removeTeamMember();
                    if (this.rules.validateTeam(p.team) == null) {
                        break;
                    }
                }
                if (p.team.isEmpty()) {
                    int[] teamIndices = RandomHelper.getRandomDistinctNumbersBetween(0, 5, 6);
                    for (int index : teamIndices) {
                        p.addTeamMember(index);
                        if (!p.team.isEmpty()) {
                            if (this.rules.validateTeam(p.team) == null) {
                                break;
                            }
                            p.removeTeamMember();
                        }
                    }
                }
                if (p.team.isEmpty()) {
                    cancelPart = p;
                    break;
                }
            }
        }
        if (cancelPart != null) {
            cancelBattle(cancelPart);
            cancelBattle.accept(((PlayerPartyStorage) cancelPart.storage).getPlayer(), ((PlayerPartyStorage) getOther(cancelPart).storage).getPlayer());
        } else {
            PlayerParticipant part1 = getPlayerPart(this.participants[0]);
            PlayerParticipant part2 = getPlayerPart(this.participants[1]);
            BattleControllerBase controllerBase = BattleRegistry.startBattle(new BattleParticipant[]{part1}, new BattleParticipant[]{part2}, this.rules);
            startedBattle.accept(controllerBase);
            this.removeTeamSelect();
        }
    }

    @Override
    public void initializeClient() {
        super.initializeClient();
    }
}