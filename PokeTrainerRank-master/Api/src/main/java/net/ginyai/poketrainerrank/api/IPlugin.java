package net.ginyai.poketrainerrank.api;

import net.ginyai.poketrainerrank.api.command.CommandSource;
import net.ginyai.poketrainerrank.api.event.EventBase;
import net.ginyai.poketrainerrank.api.util.Location;
import net.ginyai.poketrainerrank.api.util.Tuple;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface IPlugin {

    /**
     *
     * @return the dir to write/read config file.
     */
    Path getConfigDir();

    /**
     *
     * @return the name of root command that registered by the plugin.
     */
    String getCommandName();

    /**
     *
     * @return the alias of root command that registered by the plugin.
     */
    List<String> getCommandAlias();

    /**
     *
     * @param source the subject to check
     * @param permission the permission to check
     * @return true if the source has that permission
     */
    boolean checkPermission(CommandSource source, String permission);

    /**
     * Run command as console
     * @param command the command string
     */
    void runConsoleCommand(String command);

    /**
     * Run command as player
     * @param player the player to run the command
     * @param command the command string
     */
    void runPlayerCommand(PtrPlayer player, String command);

    void setLocation(Tuple<List<PtrPlayer>,List<PtrPlayer>> players);

    default void onEvent(EventBase base) {}
}
