package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.RankBattleManager;
import net.ginyai.poketrainerrank.core.party.PartyManager;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.ginyai.poketrainerrank.core.util.Utils.translate;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandInvite extends SimpleCommand {
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        if(sender instanceof EntityPlayerMP) {
            return "invite <player>";
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
        if (args.length != 1) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        UUID p1 = player.getUniqueID();
        if(RankBattleManager.instance.contains(p1)) {
            throw new CommandException("poketrainerrank.command.invite.cannot");
        }
        EntityPlayerMP other = getPlayer(server,sender,args[0]);
        if (other.getGameProfile().getId().equals(p1)) {
            throw new CommandException("poketrainerrank.command.invite.self");
        }
        if(PartyManager.instance.hasCouple(player)) {
            throw new CommandException("poketrainerrank.command.invite.self.already");
        }
        if(PartyManager.instance.hasCouple(other)) {
            throw new CommandException("poketrainerrank.command.invite.other.already", other.getName());
        }
        UUID uuid = CommandCallback.create(other,(theOther)->{
            EntityPlayerMP eP1 = Utils.getPlayer(p1).orElse(null);
            if (eP1 == null) {
                theOther.sendMessage(translate("poketrainerrank.command.invite.self.offline"));
                return;
            }
            if(PartyManager.instance.hasCouple(theOther)) {
                theOther.sendMessage(translate("poketrainerrank.command.invite.self.already"));
                return;
            }
            if(PartyManager.instance.hasCouple(eP1)) {
                theOther.sendMessage(translate("poketrainerrank.command.invite.self.teamUp"));
                return;
            }
            PartyManager.instance.createCouple(eP1,theOther);
            theOther.sendMessage(translate("poketrainerrank.command.invite.accept"));
            eP1.sendMessage(translate("poketrainerrank.command.invite.accepted", theOther.getName()));
        });
        ITextComponent apply = translate("poketrainerrank.command.invite.apply")
                .setStyle(new Style()
                        .setUnderlined(true)
                        .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translate("poketrainerrank.command.invite.apply")))
                        .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + PokeTrainerRankMod.getPlugin().getCommandName() + " callback " + uuid))
                );
        ITextComponent component = new TextComponentString("")
                .appendSibling(player.getDisplayName())
                .appendSibling(translate("poketrainerrank.command.invite.invite"))
                .appendSibling(apply);
        other.sendMessage(component);
        player.sendMessage(translate("poketrainerrank.command.invite.send"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1 && sender instanceof EntityPlayerMP) {
            String prefix = args[0].toLowerCase();
            EntityPlayerMP player = (EntityPlayerMP) sender;
            String playerName = player.getName();
            return Arrays.stream(server.getOnlinePlayerNames())
                    .filter(s -> s.toLowerCase().startsWith(prefix))
                    .filter(s -> !s.equals(playerName))
                    .collect(Collectors.toList());
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
