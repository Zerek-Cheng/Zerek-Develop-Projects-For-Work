package net.ginyai.poketrainerrank.core.command;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.MatchingManager;
import net.ginyai.poketrainerrank.core.battle.RankBattleManager;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.ginyai.poketrainerrank.core.party.CoupleParty;
import net.ginyai.poketrainerrank.core.party.Party;
import net.ginyai.poketrainerrank.core.party.PartyManager;
import net.ginyai.poketrainerrank.core.party.SingleParty;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.stream.Collectors;

import static net.ginyai.poketrainerrank.core.util.Utils.translate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandBattle extends SimpleCommand {
    @Override
    public String getName() {
        return "battle";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        if (sender instanceof EntityPlayerMP) {
            List<RankSeason> seasons = PokeTrainerRankMod.getSeasons().stream()
                    .filter(RankSeason::isEnable)
                    .filter(season -> season.getPermission().isEmpty() || checkPermission(sender, season.getPermission()))
                    .collect(Collectors.toList());
            if (seasons.isEmpty()) {
                return "";
            } else if (seasons.size() == 1) {
                RankSeason season = seasons.get(0);
                if (season.getPartySize() == 2 && !PartyManager.instance.hasCouple((EntityPlayerMP) sender)) {
                    return "battle [-m] [Season]";
                } else {
                    return "battle [Season]";
                }
            } else {
                boolean f = !PartyManager.instance.hasCouple((EntityPlayerMP) sender)
                        && seasons.stream().anyMatch(season -> season.getPartySize() == 2);
                if (f) {
                    return "battle [-m] <Season>";
                } else {
                    return "battle [Season]";
                }
            }
        } else {
            return "*PLAYER ONLY*";
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("poketrainerrank.command.playerOnly");
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;
        UUID uuid = player.getUniqueID();
        if (MatchingManager.instance.check(uuid)) {
            Party party = MatchingManager.instance.remove(uuid);
            if (party instanceof SingleParty) {
                player.sendMessage(translate("poketrainerrank.command.battle.leave"));
            } else {
                Utils.getPlayer(party.asCoupleParty().findOther(uuid))
                        .ifPresent(playerMP -> playerMP.sendMessage(translate("poketrainerrank.command.battle.leave")));
            }
            return;
        }
        List<RankSeason> seasons = PokeTrainerRankMod.getSeasons().stream()
                .filter(RankSeason::isEnable)
                .filter(season -> season.getPermission().isEmpty() || checkPermission(sender, season.getPermission()))
                .collect(Collectors.toList());
        if (seasons.isEmpty()) {
            throw new CommandException("poketrainerrank.command.noSeason");
        }
        RankSeason season;
        boolean flag = false;
        if (args.length == 0) {
            if (seasons.size() == 1) {
                season = seasons.get(0);
            } else {
                return;
            }
        } else if (args.length == 1) {
            if (args[0].equals("-m") && seasons.size() == 1) {
                season = seasons.get(0);
                flag = true;
            } else {
                season = seasons.stream()
                        .filter(season1 -> season1.getName().equalsIgnoreCase(args[0]))
                        .findAny().orElseThrow(() -> new CommandException("poketrainerrank.command.noSuchSeason"));
            }
        } else if (args.length == 2) {
            if (!args[0].equals("-m")) {
                sender.sendMessage(new TextComponentString(getUsage(sender)));
                return;
            }
            flag = true;
            season = seasons.stream()
                    .filter(season1 -> season1.getName().equalsIgnoreCase(args[1]))
                    .findAny().orElseThrow(() -> new CommandException("poketrainerrank.command.noSuchSeason"));
        } else {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        flag = flag || PartyManager.instance.hasCouple(player) || season.getPartySize() == 1;
        if (flag && season.getPartySize() == 0) {
            throw new CommandException("poketrainerrank.command.singleOnly");
        }
        if(check(season, player)) {
            return;
        }
        if (flag) {
            MatchingManager.instance.addCouple(season, PartyManager.instance.getParty(player));
        } else {
            MatchingManager.instance.addSingle(season, PartyManager.instance.getParty(player).asSingleParty());
        }
        player.sendMessage(translate("poketrainerrank.command.battle.added"));
        if (PartyManager.instance.hasCouple(player)) {
            Utils.getPlayer(PartyManager.instance.getParty(player).asCoupleParty().findOther(uuid))
                    .ifPresent(playerMP -> playerMP.sendMessage(translate("poketrainerrank.command.battle.added")));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (!(sender instanceof EntityPlayerMP)) {
            return Collections.emptyList();
        }
        List<RankSeason> seasons = PokeTrainerRankMod.getSeasons().stream()
                .filter(RankSeason::isEnable)
                .filter(season -> season.getPermission().isEmpty() || checkPermission(sender, season.getPermission()))
                .collect(Collectors.toList());
        if (seasons.isEmpty()) {
            return Collections.emptyList();
        }
        if (seasons.size() == 1) {
            RankSeason season = seasons.get(0);
            boolean f = season.getPartySize() == 2 && !PartyManager.instance.hasCouple((EntityPlayerMP) sender);
            if (args.length == 1) {
                if (f && args[0].equals("-")) {
                    return Collections.singletonList("-m");
                } else if (season.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    return Collections.singletonList(season.getName());
                }
            } else if (args.length == 2) {
                if (f && args[0].equals("-m") && season.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    return Collections.singletonList(season.getName());
                }
            }
            return Collections.emptyList();
        }
        boolean f = !PartyManager.instance.hasCouple((EntityPlayerMP) sender)
                && seasons.stream().anyMatch(season -> season.getPartySize() == 2);
        String p = null;
        if (args.length == 1) {
            if (f && args[0].startsWith("-")) {
                return Collections.singletonList("-m");
            } else {
                p = args[0];
            }
        } else if (args.length == 2) {
            if (args[0].equals("-m") && f) {
                p = args[1];
            }
        }
        if (p != null) {
            String prefix = p.toLowerCase();
            return seasons.stream()
                    .map(RankSeason::getName)
                    .filter(s -> s.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    public static boolean check(RankSeason season, EntityPlayerMP player, boolean outBattleManager) {

        UUID uuid = player.getUniqueID();
        PlayerData playerData = season.getDataManager().getDataFromCache(uuid);
        if (playerData == null) {
            player.sendMessage(translate("poketrainerrank.command.battle.retry"));
            return true;
        }
        if (playerData.isBlock()) {
            player.sendMessage(translate("poketrainerrank.command.battle.self.blocked"));
            return true;
        }
        EntityPlayerMP teammate;
        if (!season.getPermission().isEmpty() && PartyManager.instance.hasCouple(player)) {
            CoupleParty party = (CoupleParty) PartyManager.instance.getParty(player);
            UUID mateId = party.findOther(uuid);
            teammate = Utils.getPlayer(mateId).orElse(null);
            if (teammate == null) {
                player.sendMessage(translate("poketrainerrank.data.error"));
                return true;
            } else {
                PlayerData mateData = season.getDataManager().getDataFromCache(mateId);
                if (mateData == null) {
                    player.sendMessage(translate("poketrainerrank.command.battle.retry"));
                    return true;
                }
                if (mateData.isBlock()) {
                    player.sendMessage(translate("poketrainerrank.command.battle.mate.blocked"));
                    return true;
                }
                if (!checkPermission(player, season.getPermission())) {
                    player.sendMessage(translate("poketrainerrank.command.battle.mate.noPermission"));
                    return true;
                }
            }
        } else {
            teammate = null;
        }
        Optional<PlayerPartyStorage> optData = Utils.getPlayerData(player);
        if (!optData.isPresent()) {
            player.sendMessage(translate("poketrainerrank.data.error"));
            return true;
        }
        PlayerPartyStorage data = optData.get();
        Set<Integer> set = season.getTeamSize();
        if (!set.isEmpty() && !set.contains(Utils.countTeam(data))) {
            player.sendMessage(translate("poketrainerrank.command.battle.self.teamsize"));
            return true;
        }
        for (PokemonSpec spec : season.getBlocks()) {
            Pokemon pokemon = data.findOne(spec);
            if (pokemon != null) {
                player.sendMessage(translate("poketrainerrank.command.battle.self.cannotTake", pokemon.getDisplayName()));
                return true;
            }
        }
        if(BattleRegistry.getBattle(player)!= null || (outBattleManager && RankBattleManager.instance.contains(uuid))) {
            player.sendMessage(translate("poketrainerrank.command.battle.self.inBattle"));
            return true;
        }
        if (teammate != null) {
            if(BattleRegistry.getBattle(teammate)!= null || (outBattleManager && RankBattleManager.instance.contains(teammate.getUniqueID()))) {
                player.sendMessage(translate("poketrainerrank.command.battle.mete.inBattle"));
                return true;
            }
            Optional<PlayerPartyStorage> optTeam = Utils.getPlayerData(player);
            if (!optTeam.isPresent()) {
                player.sendMessage(translate("poketrainerrank.data.error"));
                return true;
            }
            PlayerPartyStorage meat = optTeam.get();
            if (!set.isEmpty() && !set.contains(Utils.countTeam(meat))) {
                teammate.sendMessage(translate("poketrainerrank.command.battle.self.teamsize"));
                player.sendMessage(translate("poketrainerrank.command.battle.mate.teamsize"));
                return true;
            }
            for (PokemonSpec spec : season.getBlocks()) {
                Pokemon pokemon = meat.findOne(spec);
                if (pokemon != null) {
                    teammate.sendMessage(translate("poketrainerrank.command.battle.self.cannotTake", pokemon.getDisplayName()));
                    player.sendMessage(translate("poketrainerrank.command.battle.mete.cannotTake", pokemon.getDisplayName()));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param season The season
     * @param player The player
     * @return true if not pass
     */
    public static boolean check(RankSeason season, EntityPlayerMP player) {
        return check(season,player,true);
    }
}
