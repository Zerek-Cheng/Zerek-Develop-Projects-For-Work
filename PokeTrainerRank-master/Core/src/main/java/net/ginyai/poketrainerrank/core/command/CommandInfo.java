package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.Placeholders;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandInfo extends SimpleCommand{
    @Override
    public String getName() {
        return "info";
    }

    private boolean checkPermissionOther(ICommandSender sender) {
        return checkPermission(sender,"poketrainerrank.command.info.other");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        if(checkPermissionOther(sender)) {
            if(sender instanceof EntityPlayerMP) {
                return "[player] <season>";
            } else {
                return "<player> <season>";
            }
        } else {
            if(sender instanceof EntityPlayerMP) {
                return "<season>";
            } else {
                return "No permission";
            }
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerMP player;
        RankSeason season;
        if(args.length == 1) {
            if(sender instanceof EntityPlayerMP) {
                player = (EntityPlayerMP) sender;
                season = CommandElement.SEASON.process(args[0]);
            } else {
                sender.sendMessage(new TextComponentString(getUsage(sender)));
                return;
            }
        } else if(args.length == 2 && checkPermissionOther(sender)) {
            player = getPlayer(server,sender,args[0]);
            season = CommandElement.SEASON.process(args[1]);
        } else {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        PlayerData data = season.getDataManager().getDataFromCache(player.getUniqueID());
        if(data == null) {
            throw new CommandException("poketrainerrank.data.loading");
        }
        ITextComponent component = new TextComponentString(Placeholders.replaceAll(data, PokeTrainerRankMod.infoString()));
        sender.sendMessage(component);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1) {
            if (checkPermissionOther(sender)) {
                List<String> result = getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
                if( sender instanceof EntityPlayerMP && result.isEmpty()) {
                    return CommandElement.SEASON.tabCompletions(args[0]);
                } else {
                    return result;
                }
            } else {
                if(sender instanceof EntityPlayerMP) {
                    return CommandElement.SEASON.tabCompletions(args[0]);
                } else {
                    return Collections.emptyList();
                }
            }
        } else if (args.length == 2){
            return CommandElement.SEASON.tabCompletions(args[1]);
        } else {
            return Collections.emptyList();
        }
    }
}
