// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.bungee;

import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.io.IOException;
import com.github.shawhoi.nybattle.Main;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.entity.Player;

public class Teleport
{
    final Player p;
    
    public Teleport(final Player p) {
        this.p = p;
        this.tp();
    }
    
    public void tp() {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("ConnectOther");
            out.writeUTF(this.p.getName());
            out.writeUTF(Main.getInstance().lobby);
        }
        catch (IOException ex) {}
        Bukkit.getServer().sendPluginMessage((Plugin)Main.getInstance(), "BungeeCord", b.toByteArray());
    }
}
