package com._0myun.minecraft.sponge.privateblock;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.Location;

;import javax.annotation.Nullable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Plugin(
        id = "privateblock",
        name = "PrivateBlock",
        version = "1.0-SNAPSHOT",
        description = "【灵梦云】私有方块",
        authors = {
                "0MYUN_EVENTMSG"
        }
)
public class Main {
    public static Main plugin;
    @Inject
    private Logger logger;
    @Inject
    private Game game;


    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;
    CommentedConfigurationNode node;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        plugin = this;
        this.logger.info("【灵梦云私有方块】加载");
        this.logger.info("【灵梦云私有方块】定制插件就找灵梦云科技0MYUN.COM,+q2025255093");
    }

    @Listener
    public void onLoad(GameInitializationEvent e) {
        this.configManager.createEmptyNode();
        try {
            node = this.configManager.load();
            if (!node.hasMapChildren()) { // is empty
                List<String> list = new ArrayList<>();
                list.add("minecraft:chest");
                list.add("minecraft:dirt");
                node.getNode("chest").setValue(new TypeToken<List<String>>() {
                }, list);

                node.getNode("lang", "lang1").setValue("方块主人不是你...无法破坏!");
                node.getNode("lang", "lang2").setValue("你是该方块主人,为你移除锁");
            }
            this.configManager.save(node);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Sponge.getEventManager().registerListeners(this, new PlaceListener());
    }

    @Listener
    public void onStop(GameStoppingServerEvent e) {
        try {
            this.configManager.save(node);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public List<String> getList() {
        try {
            return this.node.getNode("chest").getList(new TypeToken<String>() {
            });
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOwner(Location loc, UUID owner) {
        try {
            this.node.getNode("data", loc.toString()).setValue(new TypeToken<UUID>() {
            }, owner);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public UUID getOwner(Location loc) {
        try {
            return this.node.getNode("data", loc.toString()).getValue(new TypeToken<UUID>() {
            });
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeOwner(Location loc) {
        this.node.getNode("data").removeChild(loc.toString());
    }

    public boolean hasOwner(Location loc) {
        return !this.node.getNode("data", loc.toString()).isVirtual();
    }

    public String getLang(String name) {
        return this.node.getNode("lang", name).getString();
    }
}
