/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.command.ConsoleCommandSender
 */
package cn.timewk.neptune.plugin.afkkicker;

import cn.timewk.neptune.plugin.afkkicker.api.API;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

public class NeptuneAPI
implements API {
    @Override
    public /* varargs */ void info(Object ... param) {
        StringBuilder sb = new StringBuilder(this.getInfoHead());
        Object[] arrobject = param;
        int n = arrobject.length;
        int n2 = 0;
        while (n2 < n) {
            Object object = arrobject[n2];
            sb.append(String.valueOf(object == null ? "null" : String.valueOf(object)) + " ");
            ++n2;
        }
        this.getServer().getConsoleSender().sendMessage(sb.toString());
    }

    @Override
    public /* varargs */ void error(Object ... param) {
        StringBuilder sb = new StringBuilder(this.getErrorHead());
        Object[] arrobject = param;
        int n = arrobject.length;
        int n2 = 0;
        while (n2 < n) {
            Object object = arrobject[n2];
            sb.append(String.valueOf(object == null ? "null" : String.valueOf(object)) + " ");
            ++n2;
        }
        this.getServer().getConsoleSender().sendMessage(sb.toString());
    }
}

