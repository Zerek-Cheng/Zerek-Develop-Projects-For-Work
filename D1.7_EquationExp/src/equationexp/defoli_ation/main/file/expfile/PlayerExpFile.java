/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package equationexp.defoli_ation.main.file.expfile;

import org.bukkit.entity.Player;

public interface PlayerExpFile {
    public void savePlayerExp(Player var1, int var2);

    public int getPlayerExp(Player var1);

    public void disable();

    public boolean exist(Player var1);

    public void save();
}

