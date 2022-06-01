package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.Placeholders;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandTop extends SimpleCommand {
    @Override
    public String getName() {
        return "top";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "<season> [page]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if(args.length==0 || args.length>2) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        int page;
        RankSeason season = CommandElement.SEASON.process(args[0]);
        if(args.length == 1) {
            page = 1;
        } else {
            page = parseInt(args[1],1,10);
        }
        List<PlayerData> tops = season.getTops();
        ITextComponent component = new TextComponentString("").appendText("============").appendText(season.getName()).appendText("============\n");
        int s = (page-1)*10;
        int e = Math.min(page*10,tops.size());
        for(int i = s;i<e;i++) {
            PlayerData data = tops.get(i);
            ITextComponent line = new TextComponentString("")
                    .appendText(String.format("%3d|%10s|%16d\n",i+1,data.getName(),data.getScore()))
                    .setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new TextComponentString(Placeholders.replaceAll(data, PokeTrainerRankMod.infoString())))));
            component.appendSibling(line);
        }

        Style prePage = new Style().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/"+ PokeTrainerRankMod.getPlugin().getCommandName()+" top "+season.getName()+" "+(page-1)));
        Style nextPage = new Style().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/"+ PokeTrainerRankMod.getPlugin().getCommandName()+" top "+season.getName()+" "+(page+1)));
        if(page == 1) {
            component.appendText("============");
        } else {
            component.appendSibling(new TextComponentString("<<<<<<<<<<<<").setStyle(prePage));
        }
        component.appendText("=======");
        if(page==10 || page>((tops.size()-1)/10)) {
            component.appendText("============");
        } else {
            component.appendSibling(new TextComponentString(">>>>>>>>>>>>").setStyle(nextPage));
        }
        sender.sendMessage(component);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 1) {
            return CommandElement.SEASON.tabCompletions(args[0]);
        } else {
            return Collections.emptyList();
        }
    }
}
