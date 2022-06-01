package net.ginyai.poketrainerrank.api;

import net.ginyai.poketrainerrank.api.command.ICommand;

import java.util.function.Function;

public interface IPokeTrainerRankCore {
    /**
     * Get the command obj
     * @return the root command obj
     */
    ICommand getRootCommand();

    /**
     * process the placeholder {PokeTrainerRank_<args>}
     * @param player the player
     * @param args the args
     * @return the result
     */
    String processPlaceholder(PtrPlayer player, String args);

    /**
     * set the plugin
     * @param plugin plugin
     */
    void setPlugin(IPlugin plugin);

    void setCommandSourceWrapper(Function<Object,Object> wrapper,Function<Object,Object> dewrapper);
}
