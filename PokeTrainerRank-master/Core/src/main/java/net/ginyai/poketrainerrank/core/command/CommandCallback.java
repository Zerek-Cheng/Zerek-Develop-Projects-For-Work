package net.ginyai.poketrainerrank.core.command;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import mcp.MethodsReturnNonnullByDefault;
import net.ginyai.poketrainerrank.api.util.Tuple;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandCallback extends SimpleCommand {
    private static Cache<UUID, Tuple<UUID, Callback>> map = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public static UUID create(EntityPlayerMP player, Callback action) {
        UUID uuid = UUID.randomUUID();
        map.put(uuid,new Tuple<>(player.getUniqueID(),action));
        return uuid;
    }

    @Override
    public String getName() {
        return "callback";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "*USED WHEN CLICK*";
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
        UUID uuid;
        try {
            uuid = UUID.fromString(args[0]);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        Tuple<UUID, Callback> tuple = map.getIfPresent(uuid);
        if(tuple == null) {
            throw new CommandException("poketrainerrank.command.callback.error");
        }
        if(!tuple.getFirst().equals(player.getUniqueID())) {
            throw new CommandException("poketrainerrank.command.callback.error");
        }
        map.invalidate(uuid);
        Callback callback = tuple.getSecond();
        callback.accept(player);
    }

    @FunctionalInterface
    public interface Callback {
        void accept(EntityPlayerMP playerMP) throws CommandException;
    }
}
