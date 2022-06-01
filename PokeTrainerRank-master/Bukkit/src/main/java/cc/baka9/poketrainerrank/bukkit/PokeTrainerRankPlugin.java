package cc.baka9.poketrainerrank.bukkit;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.ginyai.poketrainerrank.api.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import cc.baka9.poketrainerrank.bukkit.command.PtrCommandHandler;
import net.ginyai.poketrainerrank.api.IPlugin;
import net.ginyai.poketrainerrank.api.PokeTrainerRank;
import net.ginyai.poketrainerrank.api.PtrPlayer;
import net.ginyai.poketrainerrank.api.command.CommandSource;
import net.ginyai.poketrainerrank.api.command.ICommand;
import net.ginyai.poketrainerrank.api.util.Pos;

/**
 * Bukkit plugin main
 * 
 * @author CatSeed
 *
 */
public class PokeTrainerRankPlugin extends JavaPlugin implements IPlugin {
	static PokeTrainerRankPlugin instance;
	private List<String> CommandAlias = new ArrayList<String>();

	public ICommand getPtrCommand() {
		return PokeTrainerRank.getRootCommand();
	}

	public static PokeTrainerRankPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		PtrCommandHandler commandHandler = new PtrCommandHandler();
		getCommand("fspvp").setExecutor(commandHandler);
		getCommand("fspvp").setTabCompleter(commandHandler);
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new PokeTrainerRankPapi().register();
		}
		PokeTrainerRank.setPlugin(this);
	}

	@Override
	public Path getConfigDir() {
		File dirFile = this.getDataFolder();
		if (!dirFile.exists())
			dirFile.mkdirs();
		Path dirPath = dirFile.toPath();
		return dirPath;
	}

	@Override
	public boolean checkPermission(CommandSource source, String permission) {
		CommandSender sender = source.getSource();
		return sender.hasPermission(permission) || sender.isOp();
	}

	@Override
	public void runConsoleCommand(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

	}

	@Override
	public void runPlayerCommand(PtrPlayer player, String command) {
		Bukkit.dispatchCommand(Bukkit.getPlayer(player.getId()), command);
	}

    @Override
    public void setLocation(Tuple<List<PtrPlayer>, List<PtrPlayer>> players) {
		Player player = players.getFirst().get(0).getSource();
		players.getFirst().stream()
				.map(p->(Player)p.getSource())
				.forEach(p->p.teleport(player));
        players.getSecond().stream()
				.map(p->(Player)p.getSource())
				.forEach(p->p.teleport(player));
    }

    @Override
	public String getCommandName() {
		return "fspvp";
	}

	@Override
	public List<String> getCommandAlias() {
		return new ArrayList<>(CommandAlias);
	}

}
