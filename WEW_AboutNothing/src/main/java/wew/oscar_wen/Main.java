// 
// Decompiled by Procyon v0.5.30
// 

package wew.oscar_wen;

import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.LinkedList;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import com.herocraftonline.heroes.Heroes;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Collection;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Timer;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    private List<String> y;
    private List<String> n;
    private List<String> h;
    private static HashMap<String, String> playerdate;
    private static HashMap<String, List<String>> playerC;
    private static HashMap<String, Integer> playerpoints;

    static {
        Main.playerdate = new HashMap<String, String>();
        Main.playerC = new HashMap<String, List<String>>();
        Main.playerpoints = new HashMap<String, Integer>();
    }

    public Main() {
        this.y = new ArrayList<String>();
        this.n = new ArrayList<String>();
        this.h = new ArrayList<String>();
    }

    public void onEnable() {
        final File file = new File("./plugins/AboutNothing/config.yml");
        if (!file.exists()) {
            this.saveDefaultConfig();
        }
        this.loadCalendar();
        Data.connect();
        Data.createTable();
        this.loadAllPlayers();
        final Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
        while (plist.hasNext()) {
            this.loadPlayers((Player)plist.next());
        }
        Data.close();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
        new Timer().schedule(new task(), 0L, 1200000L);
        this.getLogger().info("成功启动！");
    }

    public void onDisable() {
        final Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
        while (plist.hasNext()) {
            this.savePlayers((Player)plist.next());
        }
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("addpoints") && sender.isOp() && args.length == 2 && Bukkit.getPlayer(args[0]) != null) {
            this.addPlayerPoints(args[0], Integer.parseInt(args[1]));
            return true;
        }
        return false;
    }

    void loadCalendar() {
        final File file = new File("./plugins/AboutNothing/config.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.y = (List<String>)config.getStringList("Y");
        this.n = (List<String>)config.getStringList("N");
        this.h = (List<String>)config.getStringList("H");
        Data.SqlIp = config.getString("sql.ip");
        Data.SqlPort = config.getString("sql.port");
        Data.Sql = config.getString("sql.sql");
        Data.SqlTable = config.getString("sql.table");
        Data.SqlUser = config.getString("sql.user");
        Data.SqlPasd = config.getString("sql.password");
    }

    void loadAllPlayers() {
        for (final String name : Data.getPlayersList()) {
            final String points = Data.getData(name, "Points");
            Main.playerpoints.put(name, Integer.parseInt(points.equals("null") ? "0" : points));
        }
    }

    void loadPlayers(final Player p) {
        final String date = Data.getData(p.getName(), "Date");
        final String points = Data.getData(p.getName(), "Points");
        if (this.isReset(p, (date == null) ? "null" : date)) {
            this.Reset(p);
            final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            this.setDate(p, df.format(new Date()));
        }
        else {
            this.setDate(p, date);
            Main.playerC.put(p.getName(), this.toList(Data.getData(p.getName(), "Calendar")));
        }
        Main.playerpoints.put(p.getName(), (points == null) ? 0 : Integer.parseInt(points.equals("null") ? "0" : points));
    }

    void savePlayers(final Player p) {
        Data.connect();
        Data.setData(p.getName(), "Date", this.getDate(p));
        Data.setData(p.getName(), "Points", new StringBuilder().append(Main.playerpoints.get(p.getName())).toString());
        Data.setData(p.getName(), "Calendar", this.toStrings(Main.playerC.get(p.getName())));
        Data.close();
    }

    public void addPlayerPoints(final String name, final int arg) {
        Main.playerpoints.put(name, Main.playerpoints.get(name) + arg);
    }

    public String getDate(final Player p) {
        return Main.playerdate.get(p.getName());
    }

    public void setDate(final Player p, final String date) {
        Main.playerdate.put(p.getName(), date);
    }

    public boolean isReset(final Player p, final String date) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return !date.equalsIgnoreCase(df.format(new Date()));
    }

    public void Reset(final Player p) {
        final List<String> args = new ArrayList<String>();
        final Random random = new Random();
        final List<String> yes = new ArrayList<String>();
        final List<String> no = new ArrayList<String>();
        for (int i = 0; i < 2; ++i) {
            for (int a = 0; a < 3; ++a) {
                if (i == 0) {
                    yes.add(this.random(this.y.get(random.nextInt(this.y.size())), yes, this.y));
                }
                else if (i == 1) {
                    no.add(this.random(this.n.get(random.nextInt(this.n.size())), no, this.n));
                }
            }
        }
        args.addAll(yes);
        args.addAll(no);
        args.add(this.h.get(random.nextInt(this.h.size())));
        Main.playerC.put(p.getName(), args);
    }

    String random(String get, final List<String> from, final List<String> arg) {
        if (from.contains(get)) {
            get = arg.get(new Random().nextInt(arg.size()));
            return this.random(get, from, arg);
        }
        return get;
    }

    List<String> toList(final String str) {
        final List<String> result = new ArrayList<String>();
        final String[] args = str.split("\\|");
        String[] array;
        for (int length = (array = args).length, i = 0; i < length; ++i) {
            final String arg = array[i];
            result.add(arg);
        }
        return result;
    }

    String toStrings(final List<String> list) {
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            if (i + 1 == list.size()) {
                str.append(list.get(i));
            }
            else {
                str.append(String.valueOf(list.get(i)) + "|");
            }
        }
        return str.toString();
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Data.connect();
        this.loadPlayers(e.getPlayer());
        Data.setheroees(Heroes.getInstance().getCharacterManager().getHero(e.getPlayer()));
        Data.close();
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        this.savePlayers(e.getPlayer());
        Main.playerdate.remove(e.getPlayer().getName());
        Main.playerC.remove(e.getPlayer().getName());
        Main.playerpoints.remove(e.getPlayer().getName());
    }

    public static String getPlayerCalendar(final Player p, final int loc) {
        return Main.playerC.get(p.getName()).get(loc);
    }

    public static int getPoints(final Player p) {
        return Main.playerpoints.get(p.getName());
    }

    public static String getPlayerPoints(final int loc) {
        final Set<Map.Entry<String, Integer>> mapEntries = Main.playerpoints.entrySet();
        final List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
        Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(final Map.Entry<String, Integer> ele1, final Map.Entry<String, Integer> ele2) {
                return ele2.getValue().compareTo(ele1.getValue());
            }
        });
        final List<String> arg = new ArrayList<String>();
        for (final Map.Entry<String, Integer> entry : aList) {
            if (!entry.getKey().equalsIgnoreCase("MCWew_Eas") && !entry.getKey().equalsIgnoreCase("Oscar_Wen") && !entry.getKey().equalsIgnoreCase("MCQUQ")) {
                if (entry.getKey().equalsIgnoreCase("Spring_Autumn")) {
                    continue;
                }
                arg.add("§e" + entry.getKey() + " §9(" + entry.getValue() + ")");
            }
        }
        return arg.get(loc);
    }

    public static String getHeroes(final String job, final int loc) {
        final List<Hero> heroes = Data.getHeroes();
        final HashMap<String, Integer> yyss = new HashMap<String, Integer>();
        final HashMap<String, Integer> dxkm = new HashMap<String, Integer>();
        final HashMap<String, Integer> flqy = new HashMap<String, Integer>();
        final HashMap<String, Integer> dmsl = new HashMap<String, Integer>();
        final HashMap<String, Integer> xwxz = new HashMap<String, Integer>();
        for (final Hero hero : heroes) {
            if (hero.job.contains("银翼射手")) {
                yyss.put(hero.job, hero.Level);
            }
            else if (hero.job.contains("喋血狂魔")) {
                dxkm.put(hero.job, hero.Level);
            }
            else if (hero.job.contains("风灵轻语")) {
                flqy.put(hero.job, hero.Level);
            }
            else if (hero.job.contains("堕魔圣灵")) {
                dmsl.put(hero.job, hero.Level);
            }
            else {
                if (!hero.job.contains("虚无行者")) {
                    continue;
                }
                xwxz.put(hero.job, hero.Level);
            }
        }
        if (job.equalsIgnoreCase("银翼射手")) {
            final Set<Map.Entry<String, Integer>> mapEntries = yyss.entrySet();
            final List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
            Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(final Map.Entry<String, Integer> ele1, final Map.Entry<String, Integer> ele2) {
                    return ele2.getValue().compareTo(ele1.getValue());
                }
            });
            final List<String> arg = new ArrayList<String>();
            for (final Map.Entry<String, Integer> entry : aList) {
                if (aList.indexOf(entry) == 0) {
                    arg.add("§d[银翼射手] §2" + entry.getKey() + " §e★ §cLv.§c§l" + entry.getValue());
                }
                else {
                    arg.add("§d[银翼射手] §2" + entry.getKey() + " §e★ §cLv." + entry.getValue());
                }
            }
            return (loc > arg.size()) ? "NOT" : arg.get(loc);
        }
        if (job.equalsIgnoreCase("喋血狂魔")) {
            final Set<Map.Entry<String, Integer>> mapEntries = dxkm.entrySet();
            final List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
            Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(final Map.Entry<String, Integer> ele1, final Map.Entry<String, Integer> ele2) {
                    return ele2.getValue().compareTo(ele1.getValue());
                }
            });
            final List<String> arg = new ArrayList<String>();
            for (final Map.Entry<String, Integer> entry : aList) {
                if (aList.indexOf(entry) == 0) {
                    arg.add("§d[喋血狂魔] §2" + entry.getKey() + " §e★ §cLv.§c§l" + entry.getValue());
                }
                else {
                    arg.add("§d[喋血狂魔] §2" + entry.getKey() + " §e★ §cLv." + entry.getValue());
                }
            }
            return (loc > arg.size()) ? "NOT" : arg.get(loc);
        }
        if (job.equalsIgnoreCase("风灵轻语")) {
            final Set<Map.Entry<String, Integer>> mapEntries = flqy.entrySet();
            final List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
            Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(final Map.Entry<String, Integer> ele1, final Map.Entry<String, Integer> ele2) {
                    return ele2.getValue().compareTo(ele1.getValue());
                }
            });
            final List<String> arg = new ArrayList<String>();
            for (final Map.Entry<String, Integer> entry : aList) {
                if (aList.indexOf(entry) == 0) {
                    arg.add("§d[风灵轻语] §2" + entry.getKey() + " §e★ §cLv.§c§l" + entry.getValue());
                }
                else {
                    arg.add("§d[风灵轻语] §2" + entry.getKey() + " §e★ §cLv." + entry.getValue());
                }
            }
            return (loc > arg.size()) ? "NOT" : arg.get(loc);
        }
        if (job.equalsIgnoreCase("堕魔圣灵")) {
            final Set<Map.Entry<String, Integer>> mapEntries = dmsl.entrySet();
            final List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
            Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(final Map.Entry<String, Integer> ele1, final Map.Entry<String, Integer> ele2) {
                    return ele2.getValue().compareTo(ele1.getValue());
                }
            });
            final List<String> arg = new ArrayList<String>();
            for (final Map.Entry<String, Integer> entry : aList) {
                if (aList.indexOf(entry) == 0) {
                    arg.add("§d[堕魔圣灵] §2" + entry.getKey() + " §e★ §cLv.§c§l" + entry.getValue());
                }
                else {
                    arg.add("§d[堕魔圣灵] §2" + entry.getKey() + " §e★ §cLv." + entry.getValue());
                }
            }
            return (loc > arg.size()) ? "NOT" : arg.get(loc);
        }
        if (job.equalsIgnoreCase("虚无行者")) {
            final Set<Map.Entry<String, Integer>> mapEntries = xwxz.entrySet();
            final List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
            Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(final Map.Entry<String, Integer> ele1, final Map.Entry<String, Integer> ele2) {
                    return ele2.getValue().compareTo(ele1.getValue());
                }
            });
            final List<String> arg = new ArrayList<String>();
            for (final Map.Entry<String, Integer> entry : aList) {
                if (aList.indexOf(entry) == 0) {
                    arg.add("§d[虚无行者] §2" + entry.getKey() + " §e★ §cLv.§c§l" + entry.getValue());
                }
                else {
                    arg.add("§d[虚无行者] §2" + entry.getKey() + " §e★ §cLv." + entry.getValue());
                }
            }
            return (loc > arg.size()) ? "NOT" : arg.get(loc);
        }
        return null;
    }

    class task extends TimerTask
    {
        @Override
        public void run() {
            final Iterator<? extends Player> plist = Bukkit.getOnlinePlayers().iterator();
            while (plist.hasNext()) {
                Main.this.savePlayers((Player)plist.next());
            }
            Main.this.getLogger().info("排行榜数据保存！");
        }
    }
}
