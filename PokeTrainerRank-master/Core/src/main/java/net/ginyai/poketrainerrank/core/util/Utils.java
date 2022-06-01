package net.ginyai.poketrainerrank.core.util;

import com.google.gson.*;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.api.pokemon.SpecValue;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import net.ginyai.poketrainerrank.core.data.PlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Utils {

    public static final Gson GSON = (new GsonBuilder())
            .registerTypeAdapter(PokemonSpec.class, new PokeSpacTypeAdapter()).create();
    private static BufferedReader delegationReader;
    private static BufferedWriter delegationWriter;
    private static HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
            .setParseOptions(ConfigParseOptions.defaults().setSyntax(ConfigSyntax.JSON))
            .setSource(() -> delegationReader).setSink(() -> delegationWriter).build();

    public static String toJson(ConfigurationNode node) throws IOException {
        try (StringWriter out = new StringWriter(); BufferedWriter bufferedWriter = new BufferedWriter(out)) {
            delegationWriter = bufferedWriter;
            loader.save(node);
            return out.toString();
        }
    }

    public static ConfigurationNode fromJson(String json) throws IOException {
        try (StringReader in = new StringReader(json); BufferedReader bufferedReader = new BufferedReader(in)) {
            delegationReader = bufferedReader;
            return loader.load();
        }
    }

    public static PokemonSpec deserializeSpec(ConfigurationNode node) throws ObjectMappingException, IOException {
        return Utils.GSON.fromJson(Utils.toJson(node.copy()), PokemonSpec.class) ;
    }

    public static String[] split(String args) {
        return split(args,false);
    }

    public static String[] split(String args, boolean isTab) {
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < args.length(); i++) {
            char c = args.charAt(i);
            switch (c) {
                case '"':
                    if (!flag && builder.length() == 0) {
                        flag = true;
                        break;
                    } else if (flag && (i == args.length() - 1 || args.charAt(i) == ' ')) {
                        flag = false;
                        break;
                    }
                case ' ':
                    if (!flag && c == ' ') {
                        list.add(builder.toString());
                        builder = new StringBuilder();
                        break;
                    }
                default:
                    builder.append(c);
            }
        }
        if (flag && !isTab) {
            list.addAll(Arrays.asList(("\"" + builder).split(" ")));
        } else {
            list.add(builder.toString());
        }
        return list.toArray(new String[]{});
    }

    public static Optional<EntityPlayerMP> getPlayer(UUID uuid) {
        //noinspection ConstantConditions
        return Optional.ofNullable(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid));
    }

    public static Optional<PlayerPartyStorage> getPlayerData(EntityPlayerMP player) {
        return Optional.ofNullable(Pixelmon.storageManager.getParty(player));
    }

    //不通过UUID获取离线玩家的数据, 因为我不想这么做
    public static Optional<PlayerPartyStorage> getPlayerData(UUID uuid) {
        return getPlayer(uuid).flatMap(Utils::getPlayerData);
    }

    public static Optional<PlayerParticipant> prepareParticipant(UUID uuid) {
        Optional<EntityPlayerMP> optionalPlayer = getPlayer(uuid);
        if (!optionalPlayer.isPresent()) {
            return Optional.empty();
        }
        EntityPlayerMP player = optionalPlayer.get();
        PlayerPartyStorage data = Pixelmon.storageManager.getParty(player);
        EntityPixelmon pixelmon = data.getAndSendOutFirstAblePokemon(player);
        if (pixelmon == null) {
            return Optional.empty();
        }
        return Optional.of(new PlayerParticipant(player, pixelmon));
    }

    public static BattleRules copy(BattleRules battleRules, boolean setToDouble) {
        BattleRules newRules = new BattleRules(setToDouble ? EnumBattleType.Double : battleRules.battleType);
        newRules.levelCap = battleRules.levelCap;
        newRules.raiseToCap = battleRules.raiseToCap;
        newRules.battleType = battleRules.battleType;
        newRules.numPokemon = battleRules.numPokemon;
        newRules.turnTime = battleRules.turnTime;
        newRules.teamSelectTime = battleRules.teamSelectTime;
        newRules.teamPreview = battleRules.teamPreview;
        newRules.fullHeal = battleRules.fullHeal;
        newRules.tier = battleRules.tier;
        newRules.setNewClauses(battleRules.getClauseList());
        newRules.validateRules();
        return newRules;
    }

    public static void copyToFile(URL url, Path output) throws IOException {
        copyToFile(url, output, false);
    }

    public static void copyToFile(URL url, Path output, boolean overwrite) throws IOException {
        copyToFile(url, output, overwrite, true);
    }

    public static void copyToFile(URL url, Path output, boolean overwrite, boolean onlyIfAbsent) throws IOException {
        checkNotNull(output, "output");
        if (Files.exists(output)) {
            if (overwrite) {
                Files.delete(output);
            } else if (onlyIfAbsent) {
                return;
            }
        }
        try (InputStream in = url.openStream()) {
            Files.copy(in, output);
        }
    }

    public static boolean matching(PlayerData data1, PlayerData data2) {
        return data1.getRank().getMatchingRule().matching(data1,data2)
                && data2.getRank().getMatchingRule().matching(data2,data1);
    }

    public static int countTeam(UUID uuid) {
        Optional<PlayerPartyStorage> optionalParty = getPlayerData(uuid);
        return optionalParty.map(Utils::countTeam).orElse(0);
    }

    public static int countTeam(PlayerPartyStorage data) {
        int i = 0;
        for(Pokemon pokemon:data.getAll()) {
            if (pokemon != null  && !pokemon.isEgg()) {
                i++;
            }
        }
        return i;
    }

    private static class PokeSpacTypeAdapter implements JsonDeserializer<PokemonSpec> {
        private static Gson gson = (new GsonBuilder())
                .registerTypeAdapter(SpecValue.class, PokemonSpec.SPEC_VALUE_TYPE_ADAPTER).create();

        @Override
        public PokemonSpec deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonElement element = json.getAsJsonObject().get("boss");
            if (element != null) {
                String boss = element.getAsString();
                if (EnumBossMode.hasBossMode(boss)) {
                    json.getAsJsonObject().add("boss", new JsonPrimitive((byte) (EnumBossMode.getBossMode(boss).index)));
                }
            }
            return gson.fromJson(json, typeOfT);
        }
    }

    public static ITextComponent translate(String key, Object... objects) {
        return new TextComponentString(I18n.translateToLocalFormatted(key,objects));
    }
}
