package net.ginyai.poketrainerrank.core;

import com.google.common.collect.ImmutableList;
import net.ginyai.poketrainerrank.api.IPlugin;
import net.ginyai.poketrainerrank.api.PokeTrainerRank;
import net.ginyai.poketrainerrank.api.PtrPlayer;
import net.ginyai.poketrainerrank.api.command.CommandSource;
import net.ginyai.poketrainerrank.core.battle.MatchingManager;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.data.SeasonDataManager;
import net.ginyai.poketrainerrank.core.data.SqlDataManager;
import net.ginyai.poketrainerrank.core.data.SqlDataSourceManager;
import net.ginyai.poketrainerrank.core.party.PartyManager;
import net.ginyai.poketrainerrank.core.util.DateParser;
import net.ginyai.poketrainerrank.core.util.Utils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

@Mod(
        modid = PokeTrainerRankMod.MOD_ID,
        name = "PokeTrainerRankMod",
        version = PokeTrainerRankMod.VERSION,
        dependencies = "required:pixelmon@[7.0.0,)",
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
@Mod.EventBusSubscriber
public class PokeTrainerRankMod implements Executor {
    public static final String MOD_ID = "poketrainerrankmod";
    public static final String VERSION = "@version@";

    public static PokeTrainerRankMod instance;
    public static Logger logger;
    private IPlugin iPlugin;
    private PokeTrainerRankCore core;
    private Path configDir;
    private String jdbcUrl;
    private String refreshIn;
    private long maxQueueTime;
    private long battleStartDelay;
    private boolean showRules = false;
    private String infoString = "";
    private List<RankSeason> seasons = Collections.emptyList();

    private long nextRefresh;
    public static boolean showRules() {
        return instance.showRules;
    }
    public static IPlugin getPlugin() {
        return instance.iPlugin;
    }

    public static List<RankSeason> getSeasons() {
        return instance.seasons;
    }

    public static ICommandSender trans(CommandSource o) {
        return instance.core.trans(o);
    }

    public static PtrPlayer trans(EntityPlayerMP player) {
        return instance.core.trans(player);
    }

    public static EntityPlayerMP trans(PtrPlayer ptrPlayer) {
        return instance.core.trans(ptrPlayer);
    }

    public static CommandSource trans(ICommandSender sender) {
        return instance.core.trans(sender);
    }

    public static String infoString() {
        return instance.infoString;
    }

    public static long maxQueueTime() {
        return instance.maxQueueTime;
    }

    public static long battleStartDelay() {
        return instance.battleStartDelay;
    }

    public PokeTrainerRankMod() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public Path getConfigDir() {
        return configDir;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        core = new PokeTrainerRankCore();
        ReflectionHelper.setPrivateValue(PokeTrainerRank.class, null, core, "core");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void posInit(FMLPostInitializationEvent event) {

    }

    void onSetPlugin(IPlugin plugin) {
        this.iPlugin = plugin;
        this.configDir = plugin.getConfigDir();
        try {
            reload();
        } catch (Exception e) {
            logger.error("Failed to load config.", e);
        }
    }

    public void reload() throws ObjectMappingException, IOException {
        MatchingManager.instance.clear();
        SqlDataSourceManager.INSTANCE.startReload();
        Path configPath = configDir.resolve("PokeTrainerRank.conf");
        Utils.copyToFile(PokeTrainerRankMod.class.getResource("/assets/poketrainerrankmod/default_config.conf"), configPath);
        HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setPath(configPath).build();
        ConfigurationNode node = loader.load().getNode("poketrainerrank");
        jdbcUrl = node.getNode("general", "jdbcurl").getString("jdbc:h2:file:./database");
        refreshIn = node.getNode("general", "top-refresh").getString("30m");
        maxQueueTime = DateParser.parseTime(node.getNode("general", "max-queue-time").getString("0"));
        battleStartDelay = DateParser.parseTime(node.getNode("general", "battle-start-delay").getString("5s"));
        showRules = node.getNode("general", "show-rules").getBoolean(false);
        infoString = node.getNode("general", "info").getString("");
        ImmutableList.Builder<RankSeason> builder = ImmutableList.builder();
        int pos = 1;
        ObjectMappingException exception = null;
        for (ConfigurationNode node1 : node.getNode("seasons").getChildrenList()) {
            try {
                builder.add(RankSeason.deserialize(node1));
            } catch (ObjectMappingException e) {
                logger.error("Failed to load the " + pos + " season.", e);
                if(exception == null) {
                    exception = e;
                } else {
                    exception.addSuppressed(e);
                }
            }
        }
        seasons = builder.build();
        nextRefresh = DateParser.parseTime(refreshIn) + System.currentTimeMillis();
        SqlDataSourceManager.INSTANCE.endReload();
        if(exception != null) {
            throw exception;
        }
    }

    public SeasonDataManager createDataManager(RankSeason season) {
        return new SqlDataManager(season, jdbcUrl);
    }

    @SubscribeEvent
    public void onTickServerTick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            MatchingManager.instance.tick();
            if (System.currentTimeMillis() > nextRefresh) {
                seasons.forEach(RankSeason::updateTops);
                nextRefresh = DateParser.parseTime(refreshIn) + System.currentTimeMillis();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        String name = event.player.getName();
        UUID uuid = event.player.getGameProfile().getId();
        seasons.forEach(rankSeason -> rankSeason.getDataManager().updateName(uuid, name));
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        PartyManager.instance.onPlayerLeave(event);
        MatchingManager.instance.onPlayerLeave(event);
    }

    @Override
    public void execute(@Nonnull Runnable command) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(command);
    }
}
