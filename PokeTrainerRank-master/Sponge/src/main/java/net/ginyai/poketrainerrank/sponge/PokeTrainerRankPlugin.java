package net.ginyai.poketrainerrank.sponge;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import net.ginyai.poketrainerrank.api.IPlugin;
import net.ginyai.poketrainerrank.api.PokeTrainerRank;
import net.ginyai.poketrainerrank.api.PtrPlayer;
import net.ginyai.poketrainerrank.api.command.CommandSource;
import net.ginyai.poketrainerrank.api.util.Pos;
import net.ginyai.poketrainerrank.api.util.Tuple;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.plugin.meta.util.NonnullByDefault;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Plugin(
        id = "poketrainerrankplugin",
        version = "@version@"
)
public class PokeTrainerRankPlugin implements IPlugin {

    private static PokeTrainerRankPlugin instance;
    public static PokeTrainerRankPlugin getInstance() {
        return instance;
    }
    public static Logger getLogger() {
        return instance.logger;
    }

    @Inject
    private Logger logger;

    @Inject@ConfigDir(sharedRoot = true)
    private Path sharedDir;
    private Path configDir;

    private String command = "";
    private List<String> commandAlis = Collections.emptyList();

    public PokeTrainerRankPlugin() {
        instance = this;
    }

    @Listener
    public void onInit(GameInitializationEvent event) throws IOException {
        PokeTrainerRank.setCommandSourceWrapper(
                o -> o,
                o -> o
        );
        configDir = sharedDir.resolve("PokeTrainerRank");
        Files.createDirectories(configDir);
        PokeTrainerRank.setPlugin(this);
        if(Sponge.getPluginManager().isLoaded("placeholderapi")) {
            Placeholders.init();
        }
    }

    @Listener
    public void onServerStart(GameStartingServerEvent event) {
        Sponge.getCommandManager().register(this,
                new CommandCallable() {
                    @Nonnull
                    @Override
                    public CommandResult process(@Nonnull org.spongepowered.api.command.CommandSource source,@Nonnull  String arguments) throws CommandException {
                        try {
                            PokeTrainerRank.getRootCommand().process(new CommandSource(source.getName(),source),arguments);
                            return CommandResult.success();
                        } catch (net.ginyai.poketrainerrank.api.command.CommandException e) {
                            throw new CommandException(Text.of(e.getMessage()));
                        }
                    }

                    @Nonnull
                    @Override
                    public List<String> getSuggestions(@Nonnull org.spongepowered.api.command.CommandSource source,@Nonnull  String arguments, @Nullable Location<World> targetPosition) {
                        return  PokeTrainerRank.getRootCommand().tabComp(new CommandSource(source.getName(),source),arguments,trans(targetPosition));
                    }

                    @Override
                    public boolean testPermission(@Nonnull org.spongepowered.api.command.CommandSource source) {
                        return true;
                    }

                    @Nonnull
                    @Override
                    public Optional<Text> getShortDescription(@Nonnull org.spongepowered.api.command.CommandSource source) {
                        return Optional.empty();
                    }

                    @Nonnull
                    @Override
                    public Optional<Text> getHelp(@Nonnull org.spongepowered.api.command.CommandSource source) {
                        return Optional.empty();
                    }

                    @Nonnull
                    @Override
                    public Text getUsage(@Nonnull org.spongepowered.api.command.CommandSource source) {
                        return Text.EMPTY;
                    }
            },"ptr","poketainerrank"
        ).ifPresent(commandMapping -> {
            command = commandMapping.getPrimaryAlias();
            commandAlis = ImmutableList.copyOf(commandMapping.getAllAliases());
        });
    }

    @Override
    public Path getConfigDir() {
        return configDir;
    }

    @Override
    public String getCommandName() {
        return command;
    }

    @Override
    public List<String> getCommandAlias() {
        return commandAlis;
    }

    @Override
    public boolean checkPermission(CommandSource source, String permission) {
        return ((org.spongepowered.api.command.CommandSource)source.getSource()).hasPermission(permission);
    }

    @Override
    public void runConsoleCommand(String command) {
        Sponge.getCommandManager().process(
                Sponge.getServer().getConsole(),
                command
        );
    }

    @Override
    public void runPlayerCommand(PtrPlayer player, String command) {
        Sponge.getCommandManager().process(
                player.getSource(),
                command
        );
    }

    //todo:生成场地
    @Override
    public void setLocation(Tuple<List<PtrPlayer>, List<PtrPlayer>> players) {
        Player player = players.getFirst().get(0).getSource();
        Location<World> location = player.getLocation();
        players.getFirst().stream()
                .map(p->(Player)p.getSource())
                .forEach(player1 -> player1.setLocation(location));
        players.getSecond().stream()
                .map(p->(Player)p.getSource())
                .forEach(player1 -> player1.setLocation(location));
    }

    private Pos trans(@Nullable Location<World> location) {
        if(location == null) {
            return null;
        } else {
            return new Pos(location.getX(),location.getY(),location.getZ());
        }
    }
}
