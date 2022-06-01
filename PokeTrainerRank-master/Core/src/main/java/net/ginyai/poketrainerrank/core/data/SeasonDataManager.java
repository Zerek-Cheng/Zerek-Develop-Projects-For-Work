package net.ginyai.poketrainerrank.core.data;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import net.ginyai.poketrainerrank.core.battle.RankSeason;
import net.ginyai.poketrainerrank.core.events.PTRPlayerCreateEvent;
import net.ginyai.poketrainerrank.core.events.PTRPlayerScoreAddEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public abstract class SeasonDataManager {
    protected final RankSeason season;
    private Cache<UUID, PlayerData> cache = Caffeine.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
//            .removalListener((UUID uuid, PlayerData data, RemovalCause cause)->saveData(data))
            .build();

    protected SeasonDataManager(RankSeason season) {
        this.season = season;
    }

    public RankSeason getSeason() {
        return season;
    }

    public void init() throws Exception {
        try {
            for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
                getDataFromCache(player.getUniqueID());
            }
        } catch (NullPointerException e) {
            //catch the npe when init before starting.
        }
    }

    @Nullable
    public PlayerData getDataFromCache(UUID uuid) {
        PlayerData data = cache.getIfPresent(uuid);
        if (data == null) {
            getOrCreateData(uuid);
        }
        return data;
    }

    public CompletableFuture<PlayerData> getOrCreateData(UUID uuid) {
        PlayerData data = cache.getIfPresent(uuid);
        if (data != null) {
            return CompletableFuture.completedFuture(data);
        } else {
            return getData(uuid)
                    .handle((optionalData, throwable) -> {
                        if (!log("Failed to load player data", throwable)) {

                            System.out.println("get玩家正在尝试初始化 = " + uuid);
                            PlayerData data1 = optionalData.orElse(new PlayerData(season, uuid, ""));
                            cache.get(uuid, u -> data1);
                            PTRPlayerCreateEvent event = new PTRPlayerCreateEvent();
                            event.player = data1;
                            event.season = this.season;
                            MinecraftForge.EVENT_BUS.post(event);
                            if (data1.getScore() <= 0) this.updateScore(uuid,1200);
                            return data1;
                        }
                        return null;
                    });
        }
    }

    public CompletableFuture<PlayerData> updateOrCreateData(UUID uuid) {
        PlayerData data = cache.getIfPresent(uuid);
        if (data != null) {
            return updateData(data)
                    .handle((v, t) -> {
                        log("Failed to update player data from storage", t);
                        return data;
                    });
        } else {
            return getData(uuid)
                    .handle((optionalData, throwable) -> {
                        if (!log("Failed to load player data", throwable)) {
                            System.out.println("update玩家正在尝试初始化 = " + uuid);
                            PlayerData data1 = optionalData.orElse(new PlayerData(season, uuid, ""));
                            cache.get(uuid, u -> data1);
                            PTRPlayerCreateEvent event = new PTRPlayerCreateEvent();
                            event.player = data1;
                            event.season = this.season;
                            MinecraftForge.EVENT_BUS.post(event);
                            cache.put(uuid, data1);
                            if (data1.getScore() <= 0) this.updateScore(uuid,1200);
                            return data1;
                        }
                        return null;
                    });
        }
    }

    abstract CompletableFuture<Optional<PlayerData>> getData(UUID uuid);

    abstract CompletableFuture<Void> updateData(PlayerData data);

    public CompletableFuture<PlayerData> saveData(UUID uuid) {
        PlayerData data = cache.getIfPresent(uuid);
        if (data != null) {
            return saveData(data);
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }

    abstract CompletableFuture<PlayerData> saveData(PlayerData data);

    public CompletableFuture<PlayerData> updateName(UUID uuid, String name) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.setName(name))
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player name", throwable));
    }

    public CompletableFuture<PlayerData> updateScore(UUID uuid, int score) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.updateScore(score))
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player score", throwable));
    }

    public CompletableFuture<PlayerData> addScore(UUID uuid, int add) {
        PTRPlayerScoreAddEvent event = new PTRPlayerScoreAddEvent();
        event.score = add;
        event.season = this.season;
        event.uuid = uuid;
        MinecraftForge.EVENT_BUS.post(event);
        add = event.score;
        int finalAdd = add;
        return updateOrCreateData(uuid)
                .thenApply(d -> d.addScore(finalAdd)) //todo: compare and set ?
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player score", throwable));
    }

    public CompletableFuture<PlayerData> addWin(UUID uuid) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.addWin(1).setBattles(d.getBattles() + 1)) //todo: compare and set ?
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player battle", throwable));
    }

    public CompletableFuture<PlayerData> addLose(UUID uuid) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.addLose(1).setBattles(d.getBattles() + 1)) //todo: compare and set ?
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player battle", throwable));
    }

    public CompletableFuture<PlayerData> addBattle(UUID uuid) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.addBattles(1)) //todo: compare and set ?
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player battle", throwable));
    }

    public CompletableFuture<PlayerData> block(UUID uuid) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.block(!d.isBlock()))
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player block", throwable));
    }

    public CompletableFuture<PlayerData> block(UUID uuid, boolean block) {
        return updateOrCreateData(uuid)
                .thenApply(d -> d.block(block))
                .thenApply(d -> saveData(d).join())
                .whenComplete((aVoid, throwable) -> log("Failed to update player block", throwable));
    }

    public abstract CompletableFuture<List<PlayerData>> getTops(int top);

    private boolean log(String msg, Throwable t) {
        if (t != null) {
            PokeTrainerRankMod.logger.error(msg, t);
            return true;
        } else {
            return false;
        }
    }
}
