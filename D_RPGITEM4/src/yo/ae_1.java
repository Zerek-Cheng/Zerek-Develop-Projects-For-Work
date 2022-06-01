/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.potion.PotionEffectType
 *  yo.aI
 */
package yo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.Plugin;
import yo.aI;
import yo.aa_1;
import yo.ab_1;
import yo.ac_1;
import yo.ad_0;
import yo.ah_1;
import yo.ai_1;
import yo.aj_1;
import yo.ak_1;
import yo.ao_0;
import yo.aq_0;
import yo.aq_1;
import yo.ar_1;
import yo.as_1;
import yo.at_1;
import yo.au_1;
import yo.av_0;
import yo.aw_0;
import yo.ax_0;
import yo.ay_0;
import yo.az_0;
import yo.u_1;

public abstract class ae_1 {
    private static HashMap<String, ArrayList<ab_1>> a = new HashMap();
    private static u_1<Class<? extends aa_1>> b = new u_1();

    public static void a() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        a.clear();
    }

    private static List<String> a(CommandSender sender, String locale, String comName) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        ArrayList<String> contents = new ArrayList<String>();
        if (sender.hasPermission("rpgitem.command.help")) {
            contents.add(String.format((Object)ChatColor.GREEN + ao_0.a("message.command.usage", locale), comName, Plugin.c.getDescription().getVersion()));
            ArrayList<ab_1> command = a.get(comName);
            for (ab_1 c2 : command) {
                StringBuilder buf = new StringBuilder();
                buf.append((Object)ChatColor.GREEN).append('/').append(comName);
                for (aa_1 a2 : c2.f) {
                    buf.append(' ');
                    if (a2.f.length() != 0) {
                        buf.append((Object)ChatColor.RED);
                        buf.append(ao_0.a("command.info." + a2.f, locale));
                    }
                    buf.append((Object)(a2.a() ? ChatColor.GREEN : ChatColor.GOLD));
                    buf.append(a2.c(locale));
                }
                contents.add(buf.toString());
            }
            contents.add((Object)ChatColor.GREEN + ao_0.a("message.command.info", locale));
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
        }
        return contents;
    }

    public static void a(CommandSender sender, String com) {
        int i;
        StringBuilder joined;
        Object res;
        int j;
        aq_1 cst;
        aa_1 a2;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        com = com.trim();
        if (com.length() == 0) {
            return;
        }
        int pos = com.indexOf(32);
        String comName = pos == -1 ? com : com.substring(0, pos);
        com = com.substring(pos + 1);
        String locale = ao_0.a((Object)sender);
        if (com.startsWith("h:")) {
            int maxPage;
            block64 : {
                List<String> contents = ae_1.a(sender, locale, comName);
                int lines = Plugin.d.getInt("linesPerPage", 15);
                maxPage = contents.size() / lines + ((contents.size() & lines) != 0 ? 1 : 0);
                try {
                    int page = Integer.parseInt(com.substring(2));
                    if (!contents.isEmpty()) {
                        int fromIndex = (page - 1) * lines;
                        if (page > 0 && page <= maxPage) {
                            int toIndex = fromIndex + lines;
                            if (toIndex > contents.size()) {
                                toIndex = contents.size();
                            }
                            sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.helppage.top", locale), page, maxPage));
                            sender.sendMessage(contents.subList(fromIndex, toIndex).toArray(new String[0]));
                            sender.sendMessage((Object)ChatColor.AQUA + String.format(ao_0.a("message.helppage.bottom", locale), page, maxPage));
                            return;
                        }
                        break block64;
                    }
                    return;
                }
                catch (Exception page) {
                    // empty catch block
                }
            }
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.helppage.error", locale), maxPage));
            return;
        }
        ArrayList<ab_1> command = a.get(comName);
        if (command == null) {
            sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.error.unknown.command", locale), comName));
            return;
        }
        if (pos == -1) {
            for (ab_1 c2 : command) {
                if (c2.f.length != 0) continue;
                try {
                    if (c2.i == ai_1.a.PLAYER && !(sender instanceof Player)) {
                        sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.only.player", locale));
                    } else if (c2.i == ai_1.a.CONSOLE && sender instanceof Player) {
                        sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.only.console", locale));
                    } else if (c2.a || sender.hasPermission(c2.b)) {
                        c2.e.invoke(c2.d, new Object[]{sender});
                    } else {
                        sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
                    }
                }
                catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
                catch (IllegalArgumentException e3) {
                    e3.printStackTrace();
                }
                catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                }
                return;
            }
            if (sender.hasPermission("rpgitem.command.help")) {
                sender.sendMessage(String.format((Object)ChatColor.GREEN + ao_0.a("message.command.usage", locale), comName, Plugin.c.getDescription().getVersion()));
                for (ab_1 c2 : command) {
                    StringBuilder buf = new StringBuilder();
                    buf.append((Object)ChatColor.GREEN).append('/').append(comName);
                    for (aa_1 a3 : c2.f) {
                        buf.append(' ');
                        if (a3.f.length() != 0) {
                            buf.append((Object)ChatColor.RED);
                            buf.append(ao_0.a("command.info." + a3.f, locale));
                        }
                        buf.append((Object)(a3.a() ? ChatColor.GREEN : ChatColor.GOLD));
                        buf.append(a3.c(locale));
                    }
                    sender.sendMessage(buf.toString());
                }
                sender.sendMessage((Object)ChatColor.GREEN + ao_0.a("message.command.info", locale));
                sender.sendMessage((Object)ChatColor.GREEN + ao_0.a("message.helppage.append", locale));
            } else {
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
            }
            return;
        }
        ArrayList<String> args = new ArrayList<String>();
        while (com.length() != 0) {
            int end;
            boolean quote = false;
            if (com.charAt(0) == '`') {
                com = com.substring(1);
                end = com.indexOf(96);
                quote = true;
            } else {
                end = com.indexOf(32);
            }
            if (end == -1) {
                args.add(com);
            } else {
                args.add(com.substring(0, end));
            }
            if (quote) {
                com = com.substring(end + 1);
                end = com.indexOf(32);
            }
            if (end == -1) break;
            com = com.substring(end + 1);
        }
        ac_1 lastError = null;
        block18 : for (ab_1 c3 : command) {
            if (c3.f.length != args.size() && (c3.f.length == 0 || !(c3.f[c3.f.length - 1] instanceof az_0) || args.size() < c3.f.length)) continue;
            ArrayList<Object> outArgs = new ArrayList<Object>();
            outArgs.add((Object)sender);
            for (i = 0; i < c3.f.length; ++i) {
                a2 = c3.f[i];
                if (!a2.a()) {
                    if (i == c3.f.length - 1 && a2 instanceof az_0) {
                        joined = new StringBuilder();
                        for (j = i; j < args.size(); ++j) {
                            joined.append((String)args.get(j)).append(' ');
                        }
                        args.set(i, joined.toString().trim());
                    }
                    if ((res = a2.a((String)args.get(i), locale)) instanceof ac_1) {
                        lastError = (ac_1)res;
                        continue block18;
                    }
                    outArgs.add(res);
                    continue;
                }
                cst = (aq_1)a2;
                if (!cst.a.equals(args.get(i))) continue block18;
            }
            try {
                if (c3.i == ai_1.a.PLAYER && !(sender instanceof Player)) {
                    sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.only.player", locale));
                } else if (c3.i == ai_1.a.CONSOLE && sender instanceof Player) {
                    sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.only.console", locale));
                } else if (c3.a || sender.hasPermission(c3.b)) {
                    c3.e.invoke(c3.d, outArgs.toArray());
                } else {
                    sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
                }
            }
            catch (IllegalAccessException e5) {
                e5.printStackTrace();
            }
            catch (IllegalArgumentException e6) {
                e6.printStackTrace();
            }
            catch (InvocationTargetException e7) {
                e7.printStackTrace();
            }
            return;
        }
        if (sender.hasPermission("rpgitem")) {
            if (lastError != null) {
                sender.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.error.command", locale), lastError.a));
            } else {
                ArrayList<String> consts = new ArrayList<String>();
                block21 : for (ab_1 c4 : command) {
                    for (i = 0; i < c4.f.length && i < args.size(); ++i) {
                        a2 = c4.f[i];
                        if (!a2.a()) {
                            if (i == c4.f.length - 1 && a2 instanceof az_0) {
                                joined = new StringBuilder();
                                for (j = i; j < args.size(); ++j) {
                                    joined.append((String)args.get(j)).append(' ');
                                }
                                args.set(i, joined.toString().trim());
                            }
                            if (!((res = a2.a((String)args.get(i), locale)) instanceof ac_1)) continue;
                            lastError = (ac_1)res;
                            continue block21;
                        }
                        cst = (aq_1)a2;
                        if (!cst.a.equals(args.get(i))) continue block21;
                        consts.add(cst.a);
                    }
                }
                StringBuilder search = new StringBuilder();
                for (String term : consts) {
                    search.append(term).append(' ');
                }
                ae_1.c(sender, search.toString());
            }
        } else {
            sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", locale));
        }
    }

    public static List<String> b(CommandSender sender, String com) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        com = com.trim();
        if (com.length() == 0) {
            return new ArrayList<String>();
        }
        int pos = com.indexOf(32);
        String comName = pos == -1 ? com : com.substring(0, pos);
        com = com.substring(pos + 1);
        String locale = ao_0.a((Object)sender);
        ArrayList<ab_1> command = a.get(comName);
        if (command == null) {
            if (pos == -1) {
                ArrayList<String> out = new ArrayList<String>();
                for (String n : a.keySet()) {
                    if (!n.startsWith(comName)) continue;
                    out.add("/" + n);
                }
                return out;
            }
            return new ArrayList<String>();
        }
        ArrayList<String> args = new ArrayList<String>();
        while (com.length() != 0) {
            int end;
            boolean quote = false;
            if (com.charAt(0) == '`') {
                com = com.substring(1);
                end = com.indexOf(96);
                quote = true;
            } else {
                end = com.indexOf(32);
            }
            if (end == -1) {
                args.add(com);
            } else {
                args.add(com.substring(0, end));
            }
            if (quote) {
                com = com.substring(end + 1);
                end = com.indexOf(32);
            }
            if (end == -1) break;
            com = com.substring(end + 1);
        }
        HashMap<String, Boolean> out = new HashMap<String, Boolean>();
        block6 : for (ab_1 c2 : command) {
            for (int i = 0; i < c2.f.length; ++i) {
                Object res;
                aa_1 a2 = c2.f[i];
                if (i == args.size() - 1) {
                    res = a2.b((String)args.get(i));
                    if (res == null) continue;
                    Iterator<String> i$ = res.iterator();
                    while (i$.hasNext()) {
                        String s = i$.next();
                        out.put(s, true);
                    }
                    continue block6;
                }
                if (!a2.a()) {
                    res = a2.a((String)args.get(i), locale);
                    if (!(res instanceof ac_1)) continue;
                    continue block6;
                }
                aq_1 cst = (aq_1)a2;
                if (!cst.a.equals(args.get(i))) continue block6;
            }
        }
        ArrayList<String> outList = new ArrayList<String>();
        for (String s : out.keySet()) {
            outList.add(s);
        }
        return outList;
    }

    public static void a(ad_0 handler) {
        Method[] methods;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        for (Method method : methods = handler.getClass().getMethods()) {
            Class<?>[] params = method.getParameterTypes();
            ak_1 comString = method.getAnnotation(ak_1.class);
            if (comString == null) continue;
            if (params.length == 0 || !params[0].isAssignableFrom(CommandSender.class)) {
                throw new RuntimeException("First argument must be CommandSender @ " + method.getName());
            }
            ae_1.a(comString.a(), method, handler);
        }
        Collection<ArrayList<ab_1>> coms = a.values();
        for (ArrayList<ab_1> c2 : coms) {
            Collections.sort(c2);
        }
    }

    private static void a(String com, Method method, ad_0 handler) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        com = com.trim();
        int pos = com.indexOf(32);
        String comName = pos == -1 ? com : com.substring(0, pos);
        ab_1 def = new ab_1();
        def.c = com;
        def.e = method;
        def.d = handler;
        Class<?>[] params = method.getParameterTypes();
        def.g = method.isAnnotationPresent(ah_1.class) ? method.getAnnotation(ah_1.class).a() : "";
        def.h = method.isAnnotationPresent(aj_1.class) ? method.getAnnotation(aj_1.class).a() : "";
        def.i = method.isAnnotationPresent(aI.class) ? method.getAnnotation(aI.class).a() : ai_1.a.BOTH;
        ak_1 comString = method.getAnnotation(ak_1.class);
        def.a = comString.b();
        def.b = comString.c();
        if (!a.containsKey(comName)) {
            a.put(comName, new ArrayList());
        }
        a.get(comName).add(def);
        if (pos == -1) {
            def.f = new aa_1[0];
            return;
        }
        com = com.substring(pos + 1);
        ArrayList<aa_1> arguments = new ArrayList<aa_1>();
        int realArgumentsCount = 0;
        do {
            String a2;
            if ((pos = com.indexOf(32)) == -1) {
                a2 = com;
            } else {
                a2 = com.substring(0, pos);
                com = com.substring(pos + 1);
            }
            if (a2.charAt(0) == '$') {
                Class<? extends aa_1> cAT;
                char t;
                String name = "";
                if (a2.contains(":")) {
                    String[] as = a2.split(":");
                    name = as[0].substring(1);
                    a2 = "$" + as[1];
                }
                if ((cAT = b.b(t = a2.charAt(1))) == null) {
                    throw new RuntimeException("Invalid command argument type " + t + ", full argument: " + a2);
                }
                try {
                    aa_1 arg = cAT.newInstance();
                    arg.a(a2.substring(3, a2.length() - 1));
                    if (!params[realArgumentsCount + 1].isAssignableFrom(arg.b())) {
                        throw new RuntimeException("Type mismatch for " + method.getName());
                    }
                    arg.f = name;
                    arguments.add(arg);
                    ++realArgumentsCount;
                }
                catch (Exception e2) {
                    try {
                        System.out.println("error register: ( " + com + ", " + method + ", " + handler + " )");
                        System.out.println(Arrays.asList(params));
                        System.out.println(realArgumentsCount);
                        System.out.println(cAT.newInstance().b());
                        e2.printStackTrace();
                    }
                    catch (Exception exception) {}
                }
                continue;
            }
            arguments.add(new aq_1(a2));
        } while (pos != -1);
        if (params.length != realArgumentsCount + 1) {
            throw new RuntimeException("Argument count mis-match for " + method.getName());
        }
        def.f = new aa_1[arguments.size()];
        arguments.toArray(def.f);
    }

    public static void c(CommandSender sender, String terms) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (terms.equalsIgnoreCase("_genhelp")) {
            for (String locale : ao_0.a()) {
                ae_1.a(locale);
            }
            return;
        }
        String locale = ao_0.a((Object)sender);
        sender.sendMessage((Object)ChatColor.GREEN + String.format(ao_0.a("message.help.for", locale), terms));
        String[] term = terms.toLowerCase().split(" ");
        for (Map.Entry<String, ArrayList<ab_1>> command : a.entrySet()) {
            for (ab_1 c2 : command.getValue()) {
                int count = 0;
                for (String t : term) {
                    if (!c2.c.toLowerCase().contains(t)) continue;
                    ++count;
                }
                if (count != term.length) continue;
                StringBuilder buf = new StringBuilder();
                buf.append((Object)ChatColor.GREEN).append((Object)ChatColor.BOLD).append('/').append(command.getKey());
                for (aa_1 a2 : c2.f) {
                    buf.append(' ');
                    if (a2.f.length() != 0) {
                        buf.append((Object)ChatColor.RED).append((Object)ChatColor.BOLD);
                        buf.append(ao_0.a("command.info." + a2.f, locale));
                    }
                    buf.append((Object)(a2.a() ? ChatColor.GREEN : ChatColor.GOLD)).append((Object)ChatColor.BOLD);
                    buf.append(a2.c(locale));
                }
                sender.sendMessage(buf.toString());
                String docStr = c2.g;
                if (docStr.charAt(0) == '$') {
                    if (docStr.contains("+")) {
                        String[] dArgs = docStr.split("\\+");
                        docStr = ao_0.a(dArgs[0].substring(1), locale);
                        if (dArgs[1].equalsIgnoreCase("PotionEffectType")) {
                            StringBuilder out = new StringBuilder();
                            for (PotionEffectType type : PotionEffectType.values()) {
                                if (type == null) continue;
                                out.append(type.getName().toLowerCase()).append(", ");
                            }
                            docStr = docStr + out.toString();
                        }
                    } else {
                        docStr = ao_0.a(docStr.substring(1), locale);
                    }
                }
                docStr = docStr.replaceAll("@", "" + (Object)ChatColor.BLUE).replaceAll("#", "" + (Object)ChatColor.WHITE);
                StringBuilder docBuf = new StringBuilder();
                char[] chars = docStr.toCharArray();
                docBuf.append((Object)ChatColor.WHITE);
                for (int i = 0; i < chars.length; ++i) {
                    char l = chars[i];
                    if (l == '&') {
                        l = chars[++i];
                        switch (l) {
                            case '0': {
                                docBuf.append((Object)ChatColor.BLACK);
                                break;
                            }
                            case '1': {
                                docBuf.append((Object)ChatColor.DARK_BLUE);
                                break;
                            }
                            case '2': {
                                docBuf.append((Object)ChatColor.DARK_GREEN);
                                break;
                            }
                            case '3': {
                                docBuf.append((Object)ChatColor.DARK_AQUA);
                                break;
                            }
                            case '4': {
                                docBuf.append((Object)ChatColor.DARK_RED);
                                break;
                            }
                            case '5': {
                                docBuf.append((Object)ChatColor.DARK_PURPLE);
                                break;
                            }
                            case '6': {
                                docBuf.append((Object)ChatColor.GOLD);
                                break;
                            }
                            case '7': {
                                docBuf.append((Object)ChatColor.GRAY);
                                break;
                            }
                            case '8': {
                                docBuf.append((Object)ChatColor.DARK_GRAY);
                                break;
                            }
                            case '9': {
                                docBuf.append((Object)ChatColor.BLUE);
                                break;
                            }
                            case 'a': {
                                docBuf.append((Object)ChatColor.GREEN);
                                break;
                            }
                            case 'b': {
                                docBuf.append((Object)ChatColor.AQUA);
                                break;
                            }
                            case 'c': {
                                docBuf.append((Object)ChatColor.RED);
                                break;
                            }
                            case 'd': {
                                docBuf.append((Object)ChatColor.LIGHT_PURPLE);
                                break;
                            }
                            case 'e': {
                                docBuf.append((Object)ChatColor.YELLOW);
                                break;
                            }
                            case 'f': {
                                docBuf.append((Object)ChatColor.WHITE);
                                break;
                            }
                            case 'r': {
                                docBuf.append((Object)ChatColor.WHITE);
                            }
                        }
                        continue;
                    }
                    docBuf.append(l);
                }
                sender.sendMessage(docBuf.toString());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static HashMap<String, String> b() {
        HashMap<String, String> langMap;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        langMap = new HashMap<String, String>();
        langMap.put("en_US", "English (US)");
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(Plugin.c.getResource("languages.txt"), "UTF-8"));
            String line = null;
            while ((line = r.readLine()) != null) {
                String[] args = line.split("=");
                langMap.put(args[0], args[1]);
            }
        }
        catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        finally {
            try {
                r.close();
            }
            catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        return langMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(String locale) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        BufferedWriter w = null;
        HashMap<String, String> langMap = ae_1.b();
        try {
            File out = new File(Plugin.c.getDataFolder(), Calendar.getInstance().get(1) + "-" + Calendar.getInstance().get(2) + "-" + Calendar.getInstance().get(5) + "-" + locale + ".md");
            if (out.exists()) {
                out.delete();
            }
            w = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(out), "UTF-8"));
            w.write("---\n");
            w.write("layout: locale\n");
            w.write("title: " + langMap.get(locale) + "\n");
            w.write("permalink: " + locale + ".html\n");
            w.write("---\n");
            for (Map.Entry<String, ArrayList<ab_1>> command : a.entrySet()) {
                w.write(String.format("## Commands /%s ", command.getKey()));
                w.write("\n\n");
                for (ab_1 c2 : command.getValue()) {
                    StringBuilder buf = new StringBuilder();
                    buf.append("### /");
                    buf.append(command.getKey()).append(" ");
                    for (aa_1 a2 : c2.f) {
                        if (a2.f.length() != 0) {
                            buf.append("<span style='color:#006EFF'>");
                            buf.append(ao_0.a("command.info." + a2.f, locale));
                            buf.append("</span>");
                        }
                        if (a2.a()) {
                            buf.append("<span style='color:#b5e853'>");
                        } else {
                            buf.append("<span style='color:#1BE0BF'>");
                        }
                        buf.append(a2.c(locale));
                        buf.append("</span> ");
                    }
                    buf.append("\n");
                    String docStr = c2.g;
                    if (docStr.charAt(0) == '$') {
                        if (docStr.contains("+")) {
                            String[] dArgs = docStr.split("\\+");
                            docStr = ao_0.a(dArgs[0].substring(1), locale);
                            if (dArgs[1].equalsIgnoreCase("PotionEffectType")) {
                                StringBuilder out2 = new StringBuilder();
                                for (PotionEffectType type : PotionEffectType.values()) {
                                    if (type == null) continue;
                                    out2.append(type.getName().toLowerCase()).append(", ");
                                }
                                docStr = docStr + out2.toString();
                            }
                        } else {
                            docStr = ao_0.a(docStr.substring(1), locale);
                        }
                    }
                    docStr = docStr.replaceAll("#", "</span>").replaceAll("@", "<span style='color:#0000ff'>").replaceAll("`", "`` ` ``");
                    StringBuilder docBuf = new StringBuilder();
                    char[] chars = docStr.toCharArray();
                    for (int i = 0; i < chars.length; ++i) {
                        char l = chars[i];
                        if (l == '&') {
                            if ((l = chars[++i]) != 'r') {
                                docBuf.append("<span style='color:#");
                            }
                            switch (l) {
                                case '0': {
                                    docBuf.append("000000");
                                    break;
                                }
                                case '1': {
                                    docBuf.append("0000aa");
                                    break;
                                }
                                case '2': {
                                    docBuf.append("00aa00");
                                    break;
                                }
                                case '3': {
                                    docBuf.append("00aaaa");
                                    break;
                                }
                                case '4': {
                                    docBuf.append("aa0000");
                                    break;
                                }
                                case '5': {
                                    docBuf.append("aa00aa");
                                    break;
                                }
                                case '6': {
                                    docBuf.append("ffaa00");
                                    break;
                                }
                                case '7': {
                                    docBuf.append("aaaaaa");
                                    break;
                                }
                                case '8': {
                                    docBuf.append("555555");
                                    break;
                                }
                                case '9': {
                                    docBuf.append("5555ff");
                                    break;
                                }
                                case 'a': {
                                    docBuf.append("55ff55");
                                    break;
                                }
                                case 'b': {
                                    docBuf.append("55ffff");
                                    break;
                                }
                                case 'c': {
                                    docBuf.append("ff5555");
                                    break;
                                }
                                case 'd': {
                                    docBuf.append("ff55ff");
                                    break;
                                }
                                case 'e': {
                                    docBuf.append("ffff55");
                                    break;
                                }
                                case 'f': {
                                    docBuf.append("ffffff");
                                    break;
                                }
                                case 'r': {
                                    docBuf.append("'></span'");
                                }
                            }
                            docBuf.append("'>");
                            continue;
                        }
                        docBuf.append(l);
                    }
                    buf.append(docBuf.toString());
                    buf.append("\n\n");
                    w.write(buf.toString());
                }
            }
            w.write("\n\n");
            w.write("Generated at: ");
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
            sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
            w.write(sdf.format(new Date()));
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            try {
                w.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    static {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        b.a('s', az_0.class);
        b.a('i', at_1.class);
        b.a('l', av_0.class);
        b.a('f', ar_1.class);
        b.a('p', ay_0.class);
        b.a('o', ax_0.class);
        b.a('n', au_1.class);
        b.a('m', aw_0.class);
        b.a('e', as_1.class);
        ae_1.a(new ad_0(){

            @ak_1(a="rpgitem help $terms:s[]")
            @ah_1(a="$command.rpgitem.help")
            @aj_1(a="help")
            public void a(CommandSender sender, String query) {
                ae_1.c(sender, query);
            }
        });
    }

}

