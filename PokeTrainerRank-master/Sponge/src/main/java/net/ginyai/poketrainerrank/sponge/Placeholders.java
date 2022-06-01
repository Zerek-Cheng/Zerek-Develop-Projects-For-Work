package net.ginyai.poketrainerrank.sponge;

import me.rojo8399.placeholderapi.Placeholder;
import me.rojo8399.placeholderapi.PlaceholderService;
import me.rojo8399.placeholderapi.Source;
import me.rojo8399.placeholderapi.Token;
import net.ginyai.poketrainerrank.api.PokeTrainerRank;
import net.ginyai.poketrainerrank.api.PtrPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextParseException;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Placeholders {
    public static void init() {}
    public static final Placeholders INSTANCE = new Placeholders();
    private Placeholders() {
        try {
            PlaceholderService placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService.class);
            placeholderService.load(this,"PokeTrainerRank",PokeTrainerRankPlugin.getInstance())
//                    .tokens("level","exp","all_exp","level_exp","exp_to_upgrade","rank","position")
                    .description("Placeholders of Poke Trainer Rank")
                    .buildAndRegister();
        } catch (Exception e) {
            PokeTrainerRankPlugin.getLogger().error("Failed to register to Placeholder API",e);
        }
    }

    @Placeholder(id = "PokeTrainerRank")
    public Text process(@Token String token, @Source Player player) {
        String value = PokeTrainerRank.processPlaceholder(new PtrPlayer(player.getName(),player.getUniqueId(),player),token);
        try {
            return TextSerializers.JSON.deserialize(value);
        } catch (TextParseException exception) {
            return TextSerializers.FORMATTING_CODE.deserializeUnchecked(value);
        }
    }
}
