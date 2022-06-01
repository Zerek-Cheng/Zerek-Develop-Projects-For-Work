package net.ginyai.poketrainerrank.core;

import net.ginyai.poketrainerrank.api.IPlugin;
import net.ginyai.poketrainerrank.api.IPokeTrainerRankCore;
import net.ginyai.poketrainerrank.api.PtrPlayer;
import net.ginyai.poketrainerrank.api.command.CommandSource;
import net.ginyai.poketrainerrank.api.command.ICommand;
import net.ginyai.poketrainerrank.core.command.CommandRoot;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.function.Function;

public class PokeTrainerRankCore implements IPokeTrainerRankCore {
    private Function<Object, Object> wrapper;
    private Function<Object, Object> dewrapper;

    @Override
    public ICommand getRootCommand() {
        return CommandRoot.instance;
    }

    @Override
    public String processPlaceholder(PtrPlayer player, String args) {
        return Placeholders.process(player.getId(), args);
    }

    @Override
    public void setPlugin(IPlugin plugin) {
        PokeTrainerRankMod.instance.onSetPlugin(plugin);
    }

    @Override
    public void setCommandSourceWrapper(Function<Object, Object> wrapper, Function<Object, Object> dewrapper) {
        this.wrapper = wrapper;
        this.dewrapper = dewrapper;
    }

    public EntityPlayerMP trans(PtrPlayer ptrPlayer) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(ptrPlayer.getId());
    }

    public PtrPlayer trans(EntityPlayerMP player) {
        return new PtrPlayer(player.getName(), player.getUniqueID(), wrapper.apply(player));
    }

    public ICommandSender trans(CommandSource o) {
        return (ICommandSender) dewrapper.apply(o.getSource());
    }

    public CommandSource trans(ICommandSender sender) {
        return new CommandSource(sender.getName(), wrapper.apply(sender));
    }

}
