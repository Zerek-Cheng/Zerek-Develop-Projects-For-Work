package net.ginyai.poketrainerrank.api;

import net.ginyai.poketrainerrank.api.command.ICommand;

import java.util.function.Function;

public class PokeTrainerRank {
    private PokeTrainerRank() {}
    private static IPokeTrainerRankCore core;

    /**
     * Get the command obj
     * @return the root command obj
     */
    public static ICommand getRootCommand() {
        return core.getRootCommand();
    }

    /**
     * process the placeholder {PokeTrainerRank_<args>}
     * @param player the player
     * @param args the args
     * @return the result
     */
    public static String processPlaceholder(PtrPlayer player, String args) {
        return core.processPlaceholder(player, args);
    }

    /**
     * set the plugin
     * @param plugin plugin
     */
    public static void setPlugin(IPlugin plugin) {
        core.setPlugin(plugin);
    }

    public static void setCommandSourceWrapper(Function<Object,Object> wrapper, Function<Object,Object> dewrapper) {
        core.setCommandSourceWrapper(wrapper,dewrapper);
    }
}
