package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.battle.RankBattleManager;
import net.ginyai.poketrainerrank.core.party.PartyManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.ginyai.poketrainerrank.core.util.Utils.translate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandLeave extends SimpleCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        if(sender instanceof EntityPlayerMP) {
            return "leave";
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
        if (args.length != 0) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        if(RankBattleManager.instance.contains(player.getUniqueID())) {
            throw new CommandException("poketrainerrank.command.leave.cannot");
        }
        if(!PartyManager.instance.hasCouple(player)) {
            throw new CommandException("poketrainerrank.command.leave.notIn");
        }
        PartyManager.instance.leaveParty(player.getUniqueID());
        sender.sendMessage(translate("poketrainerrank.command.leave.succeed"));
    }
}
