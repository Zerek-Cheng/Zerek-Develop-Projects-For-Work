package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommandReload extends SimpleCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "reload";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            PokeTrainerRankMod.instance.reload();
            sender.sendMessage(new TextComponentString("Config reloaded.").setStyle(new Style().setColor(TextFormatting.GREEN)));
        } catch (Exception e) {
            PokeTrainerRankMod.logger.error("Failed to reload the config.", e);
            sender.sendMessage(new TextComponentString("Failed to reload").setStyle(new Style().setColor(TextFormatting.RED)));
        }
    }
}
