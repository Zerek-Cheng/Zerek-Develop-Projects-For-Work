package net.ginyai.poketrainerrank.core.command;

import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.MatchingRule;
import net.ginyai.poketrainerrank.core.battle.RankBattleManager;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.ginyai.poketrainerrank.core.data.SeasonDataManager;
import net.ginyai.poketrainerrank.core.party.Party;
import net.ginyai.poketrainerrank.core.party.PartyManager;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static net.ginyai.poketrainerrank.core.util.Utils.translate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandChallenge extends SimpleCommand {
    @Override
    public String getName() {
        return "challenge";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        if(sender instanceof EntityPlayerMP) {
            return "challenge <season> <player>";
        } else {
            return "*PLAYER ONLY*";
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("poketrainerrank.command.playerOnly");
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;
        UUID uuid = player.getGameProfile().getId();
        if(args.length!=2) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        RankSeason season = CommandElement.SEASON.process(args[0]);
        EntityPlayerMP target = getPlayer(server,sender,args[1]);
        if(player.getUniqueID().equals(target.getUniqueID())) {
            throw new CommandException("poketrainerrank.command.challenge.challengeSelf");
        }
        boolean flag = PartyManager.instance.hasCouple(player);
        if(flag && season.getPartySize() == 0){
            throw new CommandException("poketrainerrank.command.singleOnly");
        } else if(!flag && season.getPartySize() == 1){
            throw new CommandException("poketrainerrank.command.coupleOnly");
        }
        if(flag == !PartyManager.instance.hasCouple(target)) {
            throw new CommandException("poketrainerrank.command.challenge.differentSize");
        }
        Party party1 = PartyManager.instance.getParty(player);
        Party party2 = PartyManager.instance.getParty(target);
        if(party1 == party2) {
            throw new CommandException("poketrainerrank.command.challenge.challengeSelf");
        }
        if (!season.getTeamSize().isEmpty() && party1.getTeamSize() != party2.getTeamSize()) {
            throw new CommandException("poketrainerrank.command.challenge.differentTeamSize");
        }
        if (checkScore(season, party1, party2)) {
            throw new CommandException("poketrainerrank.command.challenge.wideGap");
        }
        if(CommandBattle.check(season, player)){
            return;
        }
        Instant instant = Instant.now().plus(30, ChronoUnit.SECONDS);
        UUID accept = CommandCallback.create(target, other -> {
            EntityPlayerMP source = Utils.getPlayer(uuid).orElse(null);
            if(BattleRegistry.getBattle(source)!=null || RankBattleManager.instance.contains(uuid)) {
                other.sendMessage(translate("poketrainerrank.command.challenge.sourceInBattle"));
                return;
            }
            if(BattleRegistry.getBattle(other)!=null || RankBattleManager.instance.contains(other.getUniqueID())) {
                other.sendMessage(translate("poketrainerrank.command.challenge.inBattle"));
                return;
            }
            if(source == null) {
                other.sendMessage(translate("poketrainerrank.command.challenge.sourceOffline"));
                return;
            }
            if(instant.isBefore(Instant.now())) {
                other.sendMessage(translate("poketrainerrank.command.challenge.tooLate"));
                return;
            }
            if(CommandBattle.check(season,source)) {
                other.sendMessage(translate("poketrainerrank.command.challenge.sourceChecked"));
                return;
            }
            if(CommandBattle.check(season,other)) {
                return;
            }
            if(flag) {
                if(!PartyManager.instance.hasCouple(other)) {
                    other.sendMessage(translate("poketrainerrank.command.challenge.shouldBeCouple"));
                    return;
                }
                if(!PartyManager.instance.hasCouple(source)) {
                    other.sendMessage(translate("poketrainerrank.command.challenge.teamChange"));
                    return;
                }
            } else {
                if(PartyManager.instance.hasCouple(other)) {
                    other.sendMessage(translate("poketrainerrank.command.challenge.shouldBeSingle"));
                    return;
                }
                if(PartyManager.instance.hasCouple(source)) {
                    other.sendMessage(translate("poketrainerrank.command.challenge.teamChange"));
                    return;
                }
            }
            Party sourceParty = PartyManager.instance.getParty(source);
            Party targetParty = PartyManager.instance.getParty(other);
            if (checkScore(season, sourceParty, targetParty)) {
                throw new CommandException("poketrainerrank.command.challenge.wideGap");
            }
            if (!season.getTeamSize().isEmpty() && sourceParty.getTeamSize() != targetParty.getTeamSize()) {
                other.sendMessage(translate("poketrainerrank.command.challenge.differentTeamSize"));
                return;
            }
            other.sendMessage(translate("poketrainerrank.command.challenge.accept"));
            source.sendMessage(translate("poketrainerrank.command.challenge.accepted",other.getName()));
            RankBattleManager.instance.startBattleDelay(season,sourceParty,targetParty);
        });
        ITextComponent apply = translate("poketrainerrank.command.challenge.apply")
                .setStyle(new Style()
                        .setUnderlined(true)
                        .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translate("poketrainerrank.command.challenge.apply")))
                        .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + PokeTrainerRankMod.getPlugin().getCommandName() + " callback " + accept))
                );
        ITextComponent component = new TextComponentString("")
                .appendSibling(player.getDisplayName())
                .appendSibling(translate("poketrainerrank.command.challenge.challenge"))
                .appendSibling(apply);
        target.sendMessage(component);
        player.sendMessage(translate("poketrainerrank.command.challenge.send"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(sender instanceof EntityPlayerMP) {
            if(args.length == 1){
                return CommandElement.SEASON.tabCompletions(args[0]);
            } else if(args.length == 2) {
                String prefix = args[1].toLowerCase();
                EntityPlayerMP player = (EntityPlayerMP) sender;
                String playerName = player.getName();
                return Arrays.stream(server.getOnlinePlayerNames())
                        .filter(s -> s.toLowerCase().startsWith(prefix))
                        .filter(s -> !s.equals(playerName))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    /**
     *
     * @param season the season
     * @param party1 the one of the party
     * @param party2 the other one of the party
     * @return true if not pass;
     */
    private static boolean checkScore(RankSeason season, Party party1, Party party2) {
        SeasonDataManager dataManager = season.getDataManager();
        List<PlayerData> dataList = new ArrayList<>();
        party1.getPlayers().stream()
                .map(Entity::getUniqueID)
                .map(dataManager::getDataFromCache)
                .forEach(dataList::add);
        party2.getPlayers().stream()
                .map(Entity::getUniqueID)
                .map(dataManager::getDataFromCache)
                .forEach(dataList::add);
        for (PlayerData data : dataList) {
            if (data == null) {
                return true;
            }
            MatchingRule matchingRule = data.getRank().getMatchingRule();
            for (PlayerData data1 : dataList) {
                if (data1 == null) {
                    return true;
                }
                if (data1 == data) {
                    continue;
                }
                if (!matchingRule.matching(data, data1)) {
                    return true;
                }
            }
        }
        return false;
    }

}
