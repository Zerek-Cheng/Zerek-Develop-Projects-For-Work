package net.ginyai.poketrainerrank.core.command;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ParentCommand extends SimpleCommand {
    private String name;
    private List<CommandBase> children;

    public ParentCommand(String name, List<CommandBase> children) {
        this.name = name;
        this.children = children;
    }

    public ParentCommand(String name, String permission, List<CommandBase> children) {
        this.name = name;
        this.permissionString = permission;
        this.children = children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        String childrenString = children.stream()
                .filter(commandBase -> commandBase.checkPermission(FMLCommonHandler.instance().getMinecraftServerInstance(), sender))
                .map(CommandBase::getName)
                .collect(Collectors.joining(" "));
        return "/" + name + " " + childrenString;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        children.stream()
                .filter(b -> b.getName().equalsIgnoreCase(args[0]))
                .filter(b -> b.checkPermission(server, sender))
                .findAny()
                .orElseThrow(() -> new CommandException("poketrainerrank.command.noSuchChiled"))
                .execute(server, sender, Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length <= 1) {
            String prefix = args.length == 0 ? "" : args[0].toLowerCase();
            return children.stream()
                    .filter(b -> b.checkPermission(server, sender))
                    .map(CommandBase::getName)
                    .filter(s -> s.toLowerCase().startsWith(prefix))
                    .collect(Collectors.toList());
        } else {
            return children.stream()
                    .filter(b -> b.getName().equalsIgnoreCase(args[0]))
                    .filter(b -> b.checkPermission(server, sender))
                    .findAny()
                    .map(b -> b.getTabCompletions(server, sender, Arrays.copyOfRange(args, 1, args.length), targetPos))
                    .orElse(Collections.emptyList());
        }
    }
}
