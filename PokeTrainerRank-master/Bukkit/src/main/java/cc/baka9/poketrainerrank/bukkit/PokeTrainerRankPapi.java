package cc.baka9.poketrainerrank.bukkit;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.ginyai.poketrainerrank.api.PokeTrainerRank;
import net.ginyai.poketrainerrank.api.PtrPlayer;

public class PokeTrainerRankPapi extends PlaceholderExpansion {

	@Override
	public String getAuthor() {
		return "GiNYAi";
	}

	@Override
	public String getIdentifier() {
		return "PokeTrainerRank";
	}

	@Override
	public String getVersion() {
		return "PokeTrainerRank v0.1";
	}

	@Override
	public String onPlaceholderRequest(Player p, String params) {
		PtrPlayer player = new PtrPlayer(p.getName(), p.getUniqueId(), p);
		return PokeTrainerRank.processPlaceholder(player, params);
	}

}
