// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import com.pauldavdesign.mineauz.minigames.MinigamePlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import think.rpgitems.Plugin;
import com.pauldavdesign.mineauz.minigames.Minigames;

public class cw extends cv
{
    private Minigames a;
    
    @Override
    public void a(final Plugin plugin2) throws Exception {
        final org.bukkit.plugin.Plugin plugin3 = plugin2.getServer().getPluginManager().getPlugin("Minigames");
        this.a(Plugin.c.c().getBoolean("support.minigames", false));
        final Class clzz = Class.forName("com.pauldavdesign.mineauz.minigames.Minigames", false, this.getClass().getClassLoader());
        if (plugin3 == null || !clzz.isAssignableFrom(plugin3.getClass())) {
            throw new RuntimeException();
        }
        this.a = (Minigames)plugin3;
    }
    
    @Override
    public org.bukkit.plugin.Plugin a() {
        return (org.bukkit.plugin.Plugin)this.a;
    }
    
    @Override
    public boolean a(final Player player, final Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        final MinigamePlayer ply = this.a.getPlayerData().getMinigamePlayer(player);
        return ply == null || !ply.isInMinigame();
    }
    
    @Override
    public boolean b(final Player player, final Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        final MinigamePlayer ply = this.a.getPlayerData().getMinigamePlayer(player);
        return ply == null || !ply.isInMinigame();
    }
    
    @Override
    public boolean c(final Player player, final Location location) {
        if (!this.g() || !this.d()) {
            return true;
        }
        final MinigamePlayer ply = this.a.getPlayerData().getMinigamePlayer(player);
        return ply == null || !ply.isInMinigame();
    }
    
    @Override
    public String b() {
        return "Minigames";
    }
}
