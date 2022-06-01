package net.ginyai.poketrainerrank.core.command;

import com.google.common.collect.ImmutableList;
import net.ginyai.poketrainerrank.api.command.CommandException;
import net.ginyai.poketrainerrank.api.command.CommandSource;
import net.ginyai.poketrainerrank.api.command.ICommand;
import net.ginyai.poketrainerrank.api.util.Pos;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.List;

import static net.ginyai.poketrainerrank.core.util.Utils.split;

public class CommandRoot implements ICommand {
    public static final CommandRoot instance = new CommandRoot();
    private ParentCommand root;

    public CommandRoot() {
        List<CommandBase> children = ImmutableList.of(
                new CommandInvite(),
                new CommandLeave(),
                new CommandBattle(),
                new CommandInfo(),
                new CommandReload(),
                new CommandCallback(),
                new CommandTop(),
                new CommandSetScore(),
                new CommandChallenge(),
                new CommandBlock()
        );
        root = new ParentCommand("PokeTrainerRank","poketrainerrank.command.root.base",children);
    }

    public static BlockPos trans(Pos pos) {
        if (pos == null) {
            return null;
        } else {
            return new BlockPos(pos.x, pos.y, pos.z);
        }
    }

    @Override
    public void process(CommandSource source, String arguments) throws CommandException {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        ICommandSender sender = PokeTrainerRankMod.trans(source);
        String[] args = split(arguments, false);
        try {
            root.execute(server, sender, args);
        } catch (net.minecraft.command.CommandException e) {
            sender.sendMessage(new TextComponentString(I18n.translateToLocalFormatted(e.getMessage(), e.getErrorObjects())));
        }
    }

    @Override
    public List<String> tabComp(CommandSource source, String arguments, @Nullable Pos pos) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        ICommandSender sender = PokeTrainerRankMod.trans(source);
        String[] args = split(arguments, true);
        return root.getTabCompletions(server, sender, args, trans(pos));
    }
}
