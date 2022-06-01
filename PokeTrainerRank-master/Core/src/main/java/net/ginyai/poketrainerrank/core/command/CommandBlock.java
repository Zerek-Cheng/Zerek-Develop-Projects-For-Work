package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandBlock extends SimpleCommand {
    @Override
    public String getName() {
        return "block";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "block <season> <player>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 2) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        RankSeason season = CommandElement.SEASON.process(args[0]);
        EntityPlayerMP player = getPlayer(server, sender, args[1]);
        String name = player.getName();
        season.getDataManager()
                .block(player.getUniqueID())
                .whenComplete(((data, throwable) -> {
                    if (throwable != null) {
                        sender.sendMessage(Utils.translate("poketrainerrank.data.error"));
                    } else if (data != null) {
                        if (data.isBlock()) {
                            sender.sendMessage(Utils.translate("poketrainerrank.command.block.block", name, season.getName()));
                        } else {
                            sender.sendMessage(Utils.translate("poketrainerrank.command.block.unblock", name, season.getName()));
                        }
                    }
                }));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return CommandElement.SEASON.tabCompletions(args[0]);
        } else if (args.length == 2) {
            String prefix = args[1].toLowerCase();
            EntityPlayerMP player = (EntityPlayerMP) sender;
            String playerName = player.getName();
            return Arrays.stream(server.getOnlinePlayerNames())
                    .filter(s -> s.toLowerCase().startsWith(prefix))
                    .filter(s -> !s.equals(playerName))
                    .collect(Collectors.toList());
        } else {
            return super.getTabCompletions(server, sender, args, targetPos);
        }
    }
}
