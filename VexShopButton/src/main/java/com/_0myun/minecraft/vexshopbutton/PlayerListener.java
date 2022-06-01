package com._0myun.minecraft.vexshopbutton;

import lk.vexview.api.VexViewAPI;
import lk.vexview.event.KeyBoardPressEvent;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PlayerListener implements Listener {

    public PlayerListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.INSTANCE);
    }

    List<UUID> ps = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new HudSender(e.getPlayer()).runTaskLater(Main.INSTANCE, 20 * 10);
    }

    @EventHandler
    public void onKeyOpen(KeyBoardPressEvent e) {
        int keyboard = e.getKey();
        if (keyboard != 56 && keyboard != 184) return;
        if (!e.getEventKeyState()) return;
        if (e.getType().toString().equalsIgnoreCase("GUI")) return;
        VexGui gui = new VexGui(getConfig().getString("vex.button-img"), 0, 0, 0, 0, 0, 0);


        getConfig().getKeys(false).forEach(key -> {
            if (!getConfig().isConfigurationSection(key)) return;
            VexButton button = new VexButton(20190407 + new Random().nextInt(20190410),
                    "",
                    getConfig().getString(key + ".button-img"),
                    getConfig().getString(key + ".button-img-on"),
                    getConfig().getInt(key + ".x"),
                    getConfig().getInt(key + ".y"),
                    getConfig().getInt(key + ".w"),
                    getConfig().getInt(key + ".h")
            );
            button.setFunction(player -> {
                boolean op = player.isOp();
                try {
                    if (!op) player.setOp(true);
                    player.performCommand(getConfig().getString("command_" + key).replace("<player>", player.getName()));
                } finally {
                    if (!op) player.setOp(false);
                }
            });
            gui.addComponent(button);
        });

        VexViewAPI.openGui(e.getPlayer(), gui);
        if (!ps.contains(e.getPlayer().getUniqueId())) ps.add(e.getPlayer().getUniqueId());
    }


    @EventHandler
    public void onKeyClose(KeyBoardPressEvent e) {
        int key = e.getKey();
        if (key != 56 && key != 184) return;
        if (e.getEventKeyState()) return;
        if (!e.getType().toString().equalsIgnoreCase("GUI")) return;
        if (!ps.contains(e.getPlayer().getUniqueId())) return;
        ps.remove(e.getPlayer().getUniqueId());
        e.getPlayer().closeInventory();
    }

    public static FileConfiguration getConfig() {
        return Main.INSTANCE.getConfig();
    }
}
