// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.util.Date;
import java.util.TimeZone;
import java.util.SimpleTimeZone;
import java.text.SimpleDateFormat;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Calendar;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.bukkit.potion.PotionEffectType;
import java.util.Map;
import java.util.Arrays;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.lang.reflect.Method;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;
import java.util.Iterator;
import think.rpgitems.Plugin;
import org.bukkit.ChatColor;
import java.util.List;
import org.bukkit.command.CommandSender;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class aE
{
    private static HashMap<String, ArrayList<aB>> a;
    private static U<Class<? extends aA>> b;
    
    public static void a() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        aE.a.clear();
    }
    
    private static List<String> a(final CommandSender sender, final String locale, final String comName) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final List<String> contents = new ArrayList<String>();
        if (sender.hasPermission("rpgitem.command.help")) {
            contents.add(String.format(ChatColor.GREEN + aO.a("message.command.usage", locale), comName, Plugin.c.getDescription().getVersion()));
            final ArrayList<aB> command = aE.a.get(comName);
            for (final aB c : command) {
                final StringBuilder buf = new StringBuilder();
                buf.append(ChatColor.GREEN).append('/').append(comName);
                for (final aA a : c.f) {
                    buf.append(' ');
                    if (a.f.length() != 0) {
                        buf.append(ChatColor.RED);
                        buf.append(aO.a("command.info." + a.f, locale));
                    }
                    buf.append(a.a() ? ChatColor.GREEN : ChatColor.GOLD);
                    buf.append(a.c(locale));
                }
                contents.add(buf.toString());
            }
            contents.add(ChatColor.GREEN + aO.a("message.command.info", locale));
        }
        else {
            sender.sendMessage(ChatColor.RED + aO.a("message.error.permission", locale));
        }
        return contents;
    }
    
    public static void a(final CommandSender sender, String com) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        com = com.trim();
        if (com.length() == 0) {
            return;
        }
        final int pos = com.indexOf(32);
        String comName;
        if (pos == -1) {
            comName = com;
        }
        else {
            comName = com.substring(0, pos);
        }
        com = com.substring(pos + 1);
        final String locale = aO.a(sender);
        if (com.startsWith("h:")) {
            final List<String> contents = a(sender, locale, comName);
            final int lines = Plugin.d.getInt("linesPerPage", 15);
            final int maxPage = contents.size() / lines + (((contents.size() & lines) != 0x0) ? 1 : 0);
            try {
                final int page = Integer.parseInt(com.substring(2));
                if (contents.isEmpty()) {
                    return;
                }
                final int fromIndex = (page - 1) * lines;
                if (page > 0 && page <= maxPage) {
                    int toIndex = fromIndex + lines;
                    if (toIndex > contents.size()) {
                        toIndex = contents.size();
                    }
                    sender.sendMessage(ChatColor.AQUA + String.format(aO.a("message.helppage.top", locale), page, maxPage));
                    sender.sendMessage((String[])contents.subList(fromIndex, toIndex).toArray(new String[0]));
                    sender.sendMessage(ChatColor.AQUA + String.format(aO.a("message.helppage.bottom", locale), page, maxPage));
                    return;
                }
            }
            catch (Exception ex2) {}
            sender.sendMessage(ChatColor.RED + String.format(aO.a("message.helppage.error", locale), maxPage));
            return;
        }
        final ArrayList<aB> command = aE.a.get(comName);
        if (command == null) {
            sender.sendMessage(ChatColor.RED + String.format(aO.a("message.error.unknown.command", locale), comName));
            return;
        }
        if (pos == -1) {
            for (final aB c : command) {
                if (c.f.length == 0) {
                    try {
                        if (c.i == aI.a.PLAYER && !(sender instanceof Player)) {
                            sender.sendMessage(ChatColor.RED + aO.a("message.error.only.player", locale));
                        }
                        else if (c.i == aI.a.CONSOLE && sender instanceof Player) {
                            sender.sendMessage(ChatColor.RED + aO.a("message.error.only.console", locale));
                        }
                        else if (c.a || sender.hasPermission(c.b)) {
                            c.e.invoke(c.d, sender);
                        }
                        else {
                            sender.sendMessage(ChatColor.RED + aO.a("message.error.permission", locale));
                        }
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e2) {
                        e2.printStackTrace();
                    }
                    catch (InvocationTargetException e3) {
                        e3.printStackTrace();
                    }
                    return;
                }
            }
            if (sender.hasPermission("rpgitem.command.help")) {
                sender.sendMessage(String.format(ChatColor.GREEN + aO.a("message.command.usage", locale), comName, Plugin.c.getDescription().getVersion()));
                for (final aB c : command) {
                    final StringBuilder buf = new StringBuilder();
                    buf.append(ChatColor.GREEN).append('/').append(comName);
                    for (final aA a : c.f) {
                        buf.append(' ');
                        if (a.f.length() != 0) {
                            buf.append(ChatColor.RED);
                            buf.append(aO.a("command.info." + a.f, locale));
                        }
                        buf.append(a.a() ? ChatColor.GREEN : ChatColor.GOLD);
                        buf.append(a.c(locale));
                    }
                    sender.sendMessage(buf.toString());
                }
                sender.sendMessage(ChatColor.GREEN + aO.a("message.command.info", locale));
                sender.sendMessage(ChatColor.GREEN + aO.a("message.helppage.append", locale));
            }
            else {
                sender.sendMessage(ChatColor.RED + aO.a("message.error.permission", locale));
            }
            return;
        }
        final ArrayList<String> args = new ArrayList<String>();
        while (true) {
            while (com.length() != 0) {
                boolean quote = false;
                int end;
                if (com.charAt(0) == '`') {
                    com = com.substring(1);
                    end = com.indexOf(96);
                    quote = true;
                }
                else {
                    end = com.indexOf(32);
                }
                if (end == -1) {
                    args.add(com);
                }
                else {
                    args.add(com.substring(0, end));
                }
                if (quote) {
                    com = com.substring(end + 1);
                    end = com.indexOf(32);
                }
                if (end == -1) {
                    aC lastError = null;
                Label_1254:
                    for (final aB c2 : command) {
                        if (c2.f.length != args.size()) {
                            if (c2.f.length == 0 || !(c2.f[c2.f.length - 1] instanceof az)) {
                                continue;
                            }
                            if (args.size() < c2.f.length) {
                                continue;
                            }
                        }
                        final ArrayList<Object> outArgs = new ArrayList<Object>();
                        outArgs.add(sender);
                        for (int i = 0; i < c2.f.length; ++i) {
                            final aA a = c2.f[i];
                            if (!a.a()) {
                                if (i == c2.f.length - 1 && a instanceof az) {
                                    final StringBuilder joined = new StringBuilder();
                                    for (int j = i; j < args.size(); ++j) {
                                        joined.append(args.get(j)).append(' ');
                                    }
                                    args.set(i, joined.toString().trim());
                                }
                                final Object res = a.a(args.get(i), locale);
                                if (res instanceof aC) {
                                    lastError = (aC)res;
                                    continue Label_1254;
                                }
                                outArgs.add(res);
                            }
                            else {
                                final aq cst = (aq)a;
                                if (!cst.a.equals(args.get(i))) {
                                    continue Label_1254;
                                }
                            }
                        }
                        try {
                            if (c2.i == aI.a.PLAYER && !(sender instanceof Player)) {
                                sender.sendMessage(ChatColor.RED + aO.a("message.error.only.player", locale));
                            }
                            else if (c2.i == aI.a.CONSOLE && sender instanceof Player) {
                                sender.sendMessage(ChatColor.RED + aO.a("message.error.only.console", locale));
                            }
                            else if (c2.a || sender.hasPermission(c2.b)) {
                                c2.e.invoke(c2.d, outArgs.toArray());
                            }
                            else {
                                sender.sendMessage(ChatColor.RED + aO.a("message.error.permission", locale));
                            }
                        }
                        catch (IllegalAccessException e4) {
                            e4.printStackTrace();
                        }
                        catch (IllegalArgumentException e5) {
                            e5.printStackTrace();
                        }
                        catch (InvocationTargetException e6) {
                            e6.printStackTrace();
                        }
                        return;
                    }
                    if (sender.hasPermission("rpgitem")) {
                        if (lastError != null) {
                            sender.sendMessage(ChatColor.RED + String.format(aO.a("message.error.command", locale), lastError.a));
                        }
                        else {
                            final ArrayList<String> consts = new ArrayList<String>();
                            for (final aB c3 : command) {
                                for (int i = 0; i < c3.f.length && i < args.size(); ++i) {
                                    final aA a = c3.f[i];
                                    if (!a.a()) {
                                        if (i == c3.f.length - 1 && a instanceof az) {
                                            final StringBuilder joined = new StringBuilder();
                                            for (int j = i; j < args.size(); ++j) {
                                                joined.append(args.get(j)).append(' ');
                                            }
                                            args.set(i, joined.toString().trim());
                                        }
                                        final Object res = a.a(args.get(i), locale);
                                        if (res instanceof aC) {
                                            lastError = (aC)res;
                                            break;
                                        }
                                    }
                                    else {
                                        final aq cst = (aq)a;
                                        if (!cst.a.equals(args.get(i))) {
                                            break;
                                        }
                                        consts.add(cst.a);
                                    }
                                }
                            }
                            final StringBuilder search = new StringBuilder();
                            for (final String term : consts) {
                                search.append(term).append(' ');
                            }
                            c(sender, search.toString());
                        }
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + aO.a("message.error.permission", locale));
                    }
                    return;
                }
                com = com.substring(end + 1);
            }
            continue;
        }
    }
    
    public static List<String> b(final CommandSender sender, String com) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        com = com.trim();
        if (com.length() == 0) {
            return new ArrayList<String>();
        }
        final int pos = com.indexOf(32);
        String comName;
        if (pos == -1) {
            comName = com;
        }
        else {
            comName = com.substring(0, pos);
        }
        com = com.substring(pos + 1);
        final String locale = aO.a(sender);
        final ArrayList<aB> command = aE.a.get(comName);
        if (command != null) {
            final ArrayList<String> args = new ArrayList<String>();
            while (true) {
                while (com.length() != 0) {
                    boolean quote = false;
                    int end;
                    if (com.charAt(0) == '`') {
                        com = com.substring(1);
                        end = com.indexOf(96);
                        quote = true;
                    }
                    else {
                        end = com.indexOf(32);
                    }
                    if (end == -1) {
                        args.add(com);
                    }
                    else {
                        args.add(com.substring(0, end));
                    }
                    if (quote) {
                        com = com.substring(end + 1);
                        end = com.indexOf(32);
                    }
                    if (end == -1) {
                        final HashMap<String, Boolean> out = new HashMap<String, Boolean>();
                        for (final aB c : command) {
                            for (int i = 0; i < c.f.length; ++i) {
                                final aA a = c.f[i];
                                if (i == args.size() - 1) {
                                    final List<String> res = a.b(args.get(i));
                                    if (res != null) {
                                        for (final String s : res) {
                                            out.put(s, true);
                                        }
                                        break;
                                    }
                                }
                                else if (!a.a()) {
                                    final Object res2 = a.a(args.get(i), locale);
                                    if (res2 instanceof aC) {
                                        break;
                                    }
                                }
                                else {
                                    final aq cst = (aq)a;
                                    if (!cst.a.equals(args.get(i))) {
                                        break;
                                    }
                                }
                            }
                        }
                        final ArrayList<String> outList = new ArrayList<String>();
                        for (final String s2 : out.keySet()) {
                            outList.add(s2);
                        }
                        return outList;
                    }
                    com = com.substring(end + 1);
                }
                continue;
            }
        }
        if (pos == -1) {
            final ArrayList<String> out2 = new ArrayList<String>();
            for (final String n : aE.a.keySet()) {
                if (n.startsWith(comName)) {
                    out2.add("/" + n);
                }
            }
            return out2;
        }
        return new ArrayList<String>();
    }
    
    public static void a(final aD handler) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final Method[] arr$;
        final Method[] methods = arr$ = handler.getClass().getMethods();
        for (final Method method : arr$) {
            final Class<?>[] params = method.getParameterTypes();
            final aK comString = method.getAnnotation(aK.class);
            if (comString != null) {
                if (params.length == 0 || !params[0].isAssignableFrom(CommandSender.class)) {
                    throw new RuntimeException("First argument must be CommandSender @ " + method.getName());
                }
                a(comString.a(), method, handler);
            }
        }
        final Collection<ArrayList<aB>> coms = aE.a.values();
        for (final ArrayList<aB> c : coms) {
            Collections.sort(c);
        }
    }
    
    private static void a(String com, final Method method, final aD handler) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t2 = null;
            if (asdhqjefhusfer != null) {
                if (t2 != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t2.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        com = com.trim();
        int pos = com.indexOf(32);
        String comName;
        if (pos == -1) {
            comName = com;
        }
        else {
            comName = com.substring(0, pos);
        }
        final aB def = new aB();
        def.c = com;
        def.e = method;
        def.d = handler;
        final Class<?>[] params = method.getParameterTypes();
        if (method.isAnnotationPresent(aH.class)) {
            def.g = method.getAnnotation(aH.class).a();
        }
        else {
            def.g = "";
        }
        if (method.isAnnotationPresent(aJ.class)) {
            def.h = method.getAnnotation(aJ.class).a();
        }
        else {
            def.h = "";
        }
        if (method.isAnnotationPresent(aI.class)) {
            def.i = method.getAnnotation(aI.class).a();
        }
        else {
            def.i = aI.a.BOTH;
        }
        final aK comString = method.getAnnotation(aK.class);
        def.a = comString.b();
        def.b = comString.c();
        if (!aE.a.containsKey(comName)) {
            aE.a.put(comName, new ArrayList<aB>());
        }
        aE.a.get(comName).add(def);
        if (pos == -1) {
            def.f = new aA[0];
            return;
        }
        com = com.substring(pos + 1);
        final ArrayList<aA> arguments = new ArrayList<aA>();
        int realArgumentsCount = 0;
        while (true) {
            pos = com.indexOf(32);
            String a;
            if (pos == -1) {
                a = com;
            }
            else {
                a = com.substring(0, pos);
                com = com.substring(pos + 1);
            }
            if (a.charAt(0) == '$') {
                String name = "";
                if (a.contains(":")) {
                    final String[] as = a.split(":");
                    name = as[0].substring(1);
                    a = "$" + as[1];
                }
                final char t = a.charAt(1);
                final Class<? extends aA> cAT = aE.b.b(t);
                if (cAT == null) {
                    throw new RuntimeException("Invalid command argument type " + t + ", full argument: " + a);
                }
                try {
                    final aA arg = (aA)cAT.newInstance();
                    arg.a(a.substring(3, a.length() - 1));
                    if (!params[realArgumentsCount + 1].isAssignableFrom(arg.b())) {
                        throw new RuntimeException("Type mismatch for " + method.getName());
                    }
                    arg.f = name;
                    arguments.add(arg);
                    ++realArgumentsCount;
                }
                catch (Exception e) {
                    try {
                        System.out.println("error register: ( " + com + ", " + method + ", " + handler + " )");
                        System.out.println(Arrays.asList(params));
                        System.out.println(realArgumentsCount);
                        System.out.println(((aA)cAT.newInstance()).b());
                        e.printStackTrace();
                    }
                    catch (Exception ex2) {}
                }
            }
            else {
                arguments.add(new aq(a));
            }
            if (pos == -1) {
                if (params.length != realArgumentsCount + 1) {
                    throw new RuntimeException("Argument count mis-match for " + method.getName());
                }
                arguments.toArray(def.f = new aA[arguments.size()]);
            }
        }
    }
    
    public static void c(final CommandSender sender, final String terms) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t2 = null;
            if (asdhqjefhusfer != null) {
                if (t2 != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t2.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (terms.equalsIgnoreCase("_genhelp")) {
            for (final String locale : aO.a()) {
                a(locale);
            }
            return;
        }
        final String locale2 = aO.a(sender);
        sender.sendMessage(ChatColor.GREEN + String.format(aO.a("message.help.for", locale2), terms));
        final String[] term = terms.toLowerCase().split(" ");
        for (final Map.Entry<String, ArrayList<aB>> command : aE.a.entrySet()) {
            for (final aB c : command.getValue()) {
                int count = 0;
                for (final String t : term) {
                    if (c.c.toLowerCase().contains(t)) {
                        ++count;
                    }
                }
                if (count == term.length) {
                    final StringBuilder buf = new StringBuilder();
                    buf.append(ChatColor.GREEN).append(ChatColor.BOLD).append('/').append(command.getKey());
                    for (final aA a : c.f) {
                        buf.append(' ');
                        if (a.f.length() != 0) {
                            buf.append(ChatColor.RED).append(ChatColor.BOLD);
                            buf.append(aO.a("command.info." + a.f, locale2));
                        }
                        buf.append(a.a() ? ChatColor.GREEN : ChatColor.GOLD).append(ChatColor.BOLD);
                        buf.append(a.c(locale2));
                    }
                    sender.sendMessage(buf.toString());
                    String docStr = c.g;
                    if (docStr.charAt(0) == '$') {
                        if (docStr.contains("+")) {
                            final String[] dArgs = docStr.split("\\+");
                            docStr = aO.a(dArgs[0].substring(1), locale2);
                            if (dArgs[1].equalsIgnoreCase("PotionEffectType")) {
                                final StringBuilder out = new StringBuilder();
                                for (final PotionEffectType type : PotionEffectType.values()) {
                                    if (type != null) {
                                        out.append(type.getName().toLowerCase()).append(", ");
                                    }
                                }
                                docStr += out.toString();
                            }
                        }
                        else {
                            docStr = aO.a(docStr.substring(1), locale2);
                        }
                    }
                    docStr = docStr.replaceAll("@", "" + ChatColor.BLUE).replaceAll("#", "" + ChatColor.WHITE);
                    final StringBuilder docBuf = new StringBuilder();
                    final char[] chars = docStr.toCharArray();
                    docBuf.append(ChatColor.WHITE);
                    for (int i = 0; i < chars.length; ++i) {
                        char l = chars[i];
                        if (l == '&') {
                            ++i;
                            l = chars[i];
                            switch (l) {
                                case '0': {
                                    docBuf.append(ChatColor.BLACK);
                                    break;
                                }
                                case '1': {
                                    docBuf.append(ChatColor.DARK_BLUE);
                                    break;
                                }
                                case '2': {
                                    docBuf.append(ChatColor.DARK_GREEN);
                                    break;
                                }
                                case '3': {
                                    docBuf.append(ChatColor.DARK_AQUA);
                                    break;
                                }
                                case '4': {
                                    docBuf.append(ChatColor.DARK_RED);
                                    break;
                                }
                                case '5': {
                                    docBuf.append(ChatColor.DARK_PURPLE);
                                    break;
                                }
                                case '6': {
                                    docBuf.append(ChatColor.GOLD);
                                    break;
                                }
                                case '7': {
                                    docBuf.append(ChatColor.GRAY);
                                    break;
                                }
                                case '8': {
                                    docBuf.append(ChatColor.DARK_GRAY);
                                    break;
                                }
                                case '9': {
                                    docBuf.append(ChatColor.BLUE);
                                    break;
                                }
                                case 'a': {
                                    docBuf.append(ChatColor.GREEN);
                                    break;
                                }
                                case 'b': {
                                    docBuf.append(ChatColor.AQUA);
                                    break;
                                }
                                case 'c': {
                                    docBuf.append(ChatColor.RED);
                                    break;
                                }
                                case 'd': {
                                    docBuf.append(ChatColor.LIGHT_PURPLE);
                                    break;
                                }
                                case 'e': {
                                    docBuf.append(ChatColor.YELLOW);
                                    break;
                                }
                                case 'f': {
                                    docBuf.append(ChatColor.WHITE);
                                    break;
                                }
                                case 'r': {
                                    docBuf.append(ChatColor.WHITE);
                                    break;
                                }
                            }
                        }
                        else {
                            docBuf.append(l);
                        }
                    }
                    sender.sendMessage(docBuf.toString());
                }
            }
        }
    }
    
    private static HashMap<String, String> b() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final HashMap<String, String> langMap = new HashMap<String, String>();
        langMap.put("en_US", "English (US)");
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(Plugin.c.getResource("languages.txt"), "UTF-8"));
            String line = null;
            while ((line = r.readLine()) != null) {
                final String[] args = line.split("=");
                langMap.put(args[0], args[1]);
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            try {
                r.close();
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return langMap;
    }
    
    public static void a(final String locale) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        BufferedWriter w = null;
        final HashMap<String, String> langMap = b();
        try {
            final File out = new File(Plugin.c.getDataFolder(), Calendar.getInstance().get(1) + "-" + Calendar.getInstance().get(2) + "-" + Calendar.getInstance().get(5) + "-" + locale + ".md");
            if (out.exists()) {
                out.delete();
            }
            w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "UTF-8"));
            w.write("---\n");
            w.write("layout: locale\n");
            w.write("title: " + langMap.get(locale) + "\n");
            w.write("permalink: " + locale + ".html\n");
            w.write("---\n");
            for (final Map.Entry<String, ArrayList<aB>> command : aE.a.entrySet()) {
                w.write(String.format("## Commands /%s ", command.getKey()));
                w.write("\n\n");
                for (final aB c : command.getValue()) {
                    final StringBuilder buf = new StringBuilder();
                    buf.append("### /");
                    buf.append(command.getKey()).append(" ");
                    for (final aA a : c.f) {
                        if (a.f.length() != 0) {
                            buf.append("<span style='color:#006EFF'>");
                            buf.append(aO.a("command.info." + a.f, locale));
                            buf.append("</span>");
                        }
                        if (a.a()) {
                            buf.append("<span style='color:#b5e853'>");
                        }
                        else {
                            buf.append("<span style='color:#1BE0BF'>");
                        }
                        buf.append(a.c(locale));
                        buf.append("</span> ");
                    }
                    buf.append("\n");
                    String docStr = c.g;
                    if (docStr.charAt(0) == '$') {
                        if (docStr.contains("+")) {
                            final String[] dArgs = docStr.split("\\+");
                            docStr = aO.a(dArgs[0].substring(1), locale);
                            if (dArgs[1].equalsIgnoreCase("PotionEffectType")) {
                                final StringBuilder out2 = new StringBuilder();
                                for (final PotionEffectType type : PotionEffectType.values()) {
                                    if (type != null) {
                                        out2.append(type.getName().toLowerCase()).append(", ");
                                    }
                                }
                                docStr += out2.toString();
                            }
                        }
                        else {
                            docStr = aO.a(docStr.substring(1), locale);
                        }
                    }
                    docStr = docStr.replaceAll("#", "</span>").replaceAll("@", "<span style='color:#0000ff'>").replaceAll("`", "`` ` ``");
                    final StringBuilder docBuf = new StringBuilder();
                    final char[] chars = docStr.toCharArray();
                    for (int i = 0; i < chars.length; ++i) {
                        char l = chars[i];
                        if (l == '&') {
                            ++i;
                            l = chars[i];
                            if (l != 'r') {
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
                                    break;
                                }
                            }
                            docBuf.append("'>");
                        }
                        else {
                            docBuf.append(l);
                        }
                    }
                    buf.append(docBuf.toString());
                    buf.append("\n\n");
                    w.write(buf.toString());
                }
            }
            w.write("\n\n");
            w.write("Generated at: ");
            final SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
            sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
            w.write(sdf.format(new Date()));
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                w.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                w.close();
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    static {
        aE.a = new HashMap<String, ArrayList<aB>>();
        aE.b = new U<Class<? extends aA>>();
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        aE.b.a('s', az.class);
        aE.b.a('i', at.class);
        aE.b.a('l', av.class);
        aE.b.a('f', ar.class);
        aE.b.a('p', ay.class);
        aE.b.a('o', ax.class);
        aE.b.a('n', au.class);
        aE.b.a('m', aw.class);
        aE.b.a('e', as.class);
        a(new aD() {
            @aK(a = "rpgitem help $terms:s[]")
            @aH(a = "$command.rpgitem.help")
            @aJ(a = "help")
            public void a(final CommandSender sender, final String query) {
                aE.c(sender, query);
            }
        });
    }
}
