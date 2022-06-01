package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

import static net.ginyai.poketrainerrank.core.util.Utils.translate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSetScore extends SimpleCommand {
    @Override
    public String getName() {
        return "setscore";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "setscore <player> <season> <value>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 3) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        }
        EntityPlayerMP player = getPlayer(server, sender, args[0]);
        RankSeason season = CommandElement.SEASON.process(args[1]);
        int value = parseInt(args[2], -1, season.getMaxScore());
        season.getDataManager().updateScore(player.getUniqueID(), value);
        sender.sendMessage(translate("poketrainerrank.command.setscore.succeed"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        } else if (args.length == 2) {
            return CommandElement.SEASON.tabCompletions(args[1]);
        } else {
            return Collections.emptyList();
        }
    }
}
