/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.pauldavdesign.mineauz.minigames.MinigamePlayer
 *  com.pauldavdesign.mineauz.minigames.Minigames
 *  com.pauldavdesign.mineauz.minigames.PlayerData
 *  org.bukkit.Location
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import com.pauldavdesign.mineauz.minigames.MinigamePlayer;
import com.pauldavdesign.mineauz.minigames.Minigames;
import com.pauldavdesign.mineauz.minigames.PlayerData;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import yo.aq_0;
import yo.cv;

public class cw
extends cv {
    private Minigames a;

    @Override
    public void a(think.rpgitems.Plugin plugin2) throws Exception {
        Plugin plugin = plugin2.getServer().getPluginManager().getPlugin("Minigames");
        this.a(think.rpgitems.Plugin.c.c().getBoolean("support.minigames", false));
        Class<?> clzz = Class.forName("com.pauldavdesign.mineauz.minigames.Minigames", false, this.getClass().getClassLoader());
        if (plugin == null || !clzz.isAssignableFrom(plugin.getClass())) {
            throw new RuntimeException();
        }
        this.a = (Minigames)plugin;
    }

    @Override
    public Plugin a() {
        return this.a;
    }

    @Override
    public boolean a(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        MinigamePlayer ply = this.a.getPlayerData().getMinigamePlayer(player);
        return ply == null || !ply.isInMinigame();
    }

    @Override
    public boolean b(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        MinigamePlayer ply = this.a.getPlayerData().getMinigamePlayer(player);
        return ply == null || !ply.isInMinigame();
    }

    @Override
    public boolean c(Player player, Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        MinigamePlayer ply = this.a.getPlayerData().getMinigamePlayer(player);
        return ply == null || !ply.isInMinigame();
    }

    @Override
    public String b() {
        return "Minigames";
    }
}

