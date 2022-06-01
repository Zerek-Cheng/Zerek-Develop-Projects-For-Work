/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class rLog {
    public static void info(String string) {
        Bukkit.getConsoleSender().sendMessage(Util.rColor("&e[&7&lReferralSystem&e]&7 " + string + "&7"));
    }

    public static void line(String string) {
        Bukkit.getConsoleSender().sendMessage(Util.rColor("&7" + string + "&7"));
    }

    public static void error(String string, Throwable throwable) {
        ReferralSystem.getInstance().getLogger().log(Level.SEVERE, string, throwable);
    }
}

