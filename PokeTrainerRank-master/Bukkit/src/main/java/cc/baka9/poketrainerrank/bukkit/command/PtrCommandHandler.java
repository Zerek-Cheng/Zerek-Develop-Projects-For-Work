package cc.baka9.poketrainerrank.bukkit.command;

import net.ginyai.poketrainerrank.api.util.Pos;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import cc.baka9.poketrainerrank.bukkit.PokeTrainerRankPlugin;
import net.ginyai.poketrainerrank.api.PtrPlayer;
import net.ginyai.poketrainerrank.api.command.CommandException;
import net.ginyai.poketrainerrank.api.command.CommandSource;

import java.util.List;

/**
 * 
 * @author CatSeed
 *
 */
public class PtrCommandHandler implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// commandSource
		CommandSource source;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			source = new PtrPlayer(player.getName(), player.getUniqueId(), player);
		} else {
			source = new CommandSource(sender.getName(), sender);
		}

		// arguments
		String arguments = String.join(" ", args);

		// execute
		try {
			PokeTrainerRankPlugin.getInstance().getPtrCommand().process(source, arguments);
		} catch (CommandException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// commandSource
		CommandSource source;
		if (sender instanceof Player) {
			Player player = (Player) sender;
			source = new PtrPlayer(player.getName(), player.getUniqueId(), player);
		} else {
			source = new CommandSource(sender.getName(), sender);
		}

		// pos
		Pos pos = null;
		if (sender instanceof Entity) {
			Entity entity = (Entity) sender;
			Location loc = entity.getLocation();
			pos = new Pos(loc.getX(), loc.getY(), loc.getZ());
		} else if (sender instanceof BlockCommandSender) {
			BlockCommandSender blockCommandSender = (BlockCommandSender) sender;
			Location loc = blockCommandSender.getBlock().getLocation();
			pos = new Pos(loc.getX(), loc.getY(), loc.getZ());
		}
		// arguments
		String arguments = String.join(" ", args);

		return PokeTrainerRankPlugin.getInstance().getPtrCommand().tabComp(source, arguments, pos);
	}

}
