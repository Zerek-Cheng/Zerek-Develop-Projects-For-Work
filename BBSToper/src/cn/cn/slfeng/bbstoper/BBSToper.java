package cn.slfeng.bbstoper;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public final class BBSToper extends JavaPlugin {

    public static BBSToper INSTANCE;

    public static String mcbbsurl = new String();
    public static long changeidcooldown;
    public static int rwoneday;
    public static int pagesize;
    public static String prefix = new String();
    public static String repeat = new String();
    public static String bdsuccess = new String();
    public static String rwsuccess = new String();
    public static String notsame = new String();
    public static String oncooldown = new String();
    public static String noaccount = new String();
    public static String reward = new String();
    public static String noreward = new String();
    public static String reload = new String();
    public static String nopermission = new String();
    public static String invalid = new String();
    public static String enable = new String();
    public static String usage = new String();
    public static String posterid = new String();
    public static String postertime = new String();
    public static String noposter = new String();
    public static String posternum = new String();
    public static String overtime = new String();
    public static String waitamin = new String();
    public static String samebinding = new String();
    public static String ownsamebinding = new String();
    public static String overpage = new String();
    public static String pageinfo = new String();
    public static List<String> cmds = new ArrayList<String>();
    public static List<String> help = new ArrayList<String>();
    public static List<String> ID = new ArrayList<String>();
    public static List<String> Time = new ArrayList<String>();
    public static Map<String, String> binding = new HashMap<>();
    private FileConfiguration poster = null;
    private File posterFile = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.saveDefaultConfig();
        this.saveDefaultPoster();
        try {
            this.Load();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            getLogger().info("onEnable Error!");
        }
        getLogger().info(enable);// log??????
        new GetterRunnable().runTaskTimerAsynchronously(this, 50, 50);
    }

    @Override
    public void onDisable() {// ???????????????????????????????????????
        this.savePoster();// ??????????????????
    }

    public void Reload() throws UnsupportedEncodingException {// ?????????????????????????????????
        this.reloadConfig();// ????????????
        this.reloadPoster();// ????????????
        this.Load();// ?????????????????????
    }

    public void Load() throws UnsupportedEncodingException {// ?????????????????????
        this.getConfig();
        this.getPoster();
        mcbbsurl = this.getConfig().getString("mcbbsurl");
        changeidcooldown = this.getConfig().getLong("changeidcooldown");
        rwoneday = this.getConfig().getInt("rwoneday");
        pagesize = this.getConfig().getInt("pagesize");
        cmds = this.getConfig().getStringList("rewards");
        prefix = this.getConfig().getString("messages.prefix").replaceAll("&", "??");// ??????
        help = this.getConfig().getStringList("messages.help");// ??????
        for (int i = 0; i < help.size(); i++) {
            help.set(i, prefix + help.get(i).replaceAll("&", "??"));
        }
        repeat = prefix + this.getConfig().getString("messages.repeat").replaceAll("&", "??");
        bdsuccess = prefix + this.getConfig().getString("messages.bdsuccess").replaceAll("&", "??");
        rwsuccess = prefix + this.getConfig().getString("messages.rwsuccess").replaceAll("&", "??");
        notsame = prefix + this.getConfig().getString("messages.notsame").replaceAll("&", "??");
        oncooldown = prefix + this.getConfig().getString("messages.oncooldown").replaceAll("&", "??");
        noaccount = prefix + this.getConfig().getString("messages.noaccount").replaceAll("&", "??");
        reward = prefix + this.getConfig().getString("messages.reward").replaceAll("&", "??");
        noreward = prefix + this.getConfig().getString("messages.noreward").replaceAll("&", "??");
        reload = prefix + this.getConfig().getString("messages.reload").replaceAll("&", "??");
        nopermission = prefix + this.getConfig().getString("messages.nopermission").replaceAll("&", "??");
        invalid = prefix + this.getConfig().getString("messages.invalid").replaceAll("&", "??");
        enable = this.getConfig().getString("messages.enable").replaceAll("&", "??");
        usage = prefix + this.getConfig().getString("messages.usage").replaceAll("&", "??");
        posterid = this.getConfig().getString("messages.posterid").replaceAll("&", "??");
        postertime = this.getConfig().getString("messages.postertime").replaceAll("&", "??");
        noposter = prefix + this.getConfig().getString("messages.noposter").replaceAll("&", "??");
        posternum = prefix + this.getConfig().getString("messages.posternum").replaceAll("&", "??");
        overtime = prefix + this.getConfig().getString("messages.overtime").replaceAll("&", "??");
        waitamin = prefix + this.getConfig().getString("messages.waitamin").replaceAll("&", "??");
        samebinding = prefix + this.getConfig().getString("messages.samebinding").replaceAll("&", "??");
        ownsamebinding = prefix + this.getConfig().getString("messages.ownsamebinding").replaceAll("&", "??");
        overpage = prefix + this.getConfig().getString("messages.overpage").replaceAll("&", "??");
        pageinfo = prefix + this.getConfig().getString("messages.pageinfo").replaceAll("&", "??");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("poster")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("binding")) {// ????????????
                    sender.sendMessage(usage);// ????????????
                } else if (args[0].equalsIgnoreCase("list")) {
                    if (topList().size() != 0) {
                        outputToplist(sender, 0);
                    } else {
                        sender.sendMessage(noposter);
                    }
                } else if (args[0].equalsIgnoreCase("reward")) {// ??????
                    try {
                        if (this.getPoster().getString(sender.getName() + ".id") == null) {// ????????????????????????
                            sender.sendMessage(noaccount);
                        } else {// ?????????????????????
                            boolean b = new Boolean(false);
                            boolean isovertime = new Boolean(false);
                            List<String> list = new ArrayList<String>();
                            boolean iswaitamin = new Boolean(false);
                            //Getter();// ??????????????????
                            for (int i = 0; i < ID.size() && i < Time.size(); i++) {// ??????????????????????????????
                                if (ID.get(i).equalsIgnoreCase(this.getPoster().getString(sender.getName() + ".id"))) {// ???????????????ID???????????????????????????
                                    if (!list.contains(Time.get(i))) {// ??????list????????????????????????????????????
                                        list.add(Time.get(i));// ????????????
                                    } else {// ??????List??????????????????
                                        if (!this.getPoster().getStringList(sender.getName() + ".time")
                                                .contains(Time.get(i))) {// ?????????????????????????????????????????????
                                            iswaitamin = true;// ??????????????????
                                        }
                                    }
                                    if (!this.getPoster().getStringList(sender.getName() + ".time")
                                            .contains(Time.get(i))) {// ????????????????????????time?????????????????????????????????
                                        String datenow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                        if (!datenow
                                                .equals(this.getPoster().getString(sender.getName() + ".daybefore"))) {// ???????????????????????????????????????????????????
                                            this.getPoster().set(sender.getName() + ".rwaday", 0);// ????????????
                                            this.getPoster().set(sender.getName() + ".daybefore", datenow);// ????????????????????????"??????"
                                        }
                                        if ((this.getPoster().getInt(sender.getName() + ".rwaday") < rwoneday)) {// ???????????????????????????????????????
                                            for (int r = 0; r < cmds.size(); r++) {// ??????????????????
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                                        cmds.get(r).replaceAll("%PLAYER%", sender.getName()));// ??????????????????
                                            }
                                            List<String> time = this.getPoster()
                                                    .getStringList(sender.getName() + ".time");// ????????????time List
                                            time.add(Time.get(i));// ??????????????????????????????
                                            this.getPoster().set(sender.getName() + ".time", time);// ????????????time List
                                            this.getPoster().set(sender.getName() + ".rwaday",
                                                    this.getPoster().getInt(sender.getName() + ".rwaday") + 1);// ??????+1
                                            this.savePoster();// ????????????
                                            sender.sendMessage(reward.replaceAll("%TIME%", Time.get(i)));// ????????????
                                            b = true;// ??????????????????
                                        } else {// ??????????????????
                                            isovertime = true;// ??????????????????
                                        }
                                    }
                                }
                            } // ??????????????????
                            if (b) {// ??????????????????
                                sender.sendMessage(rwsuccess);
                            } else {// ?????????????????????
                                if (isovertime) {// ??????????????????
                                    sender.sendMessage(overtime.replaceAll("%RWONEDAY%", String.valueOf(rwoneday)));// ??????????????????
                                } else {// ??????????????????
                                    if (iswaitamin) {// ????????????,????????????1min
                                        sender.sendMessage(waitamin);// ??????????????????
                                    } else {
                                        sender.sendMessage(noreward);// ???????????????
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage("Error!");
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {// ??????
                    if (sender.hasPermission("bbstoper.admin")) {
                        try {
                            this.Reload();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            sender.sendMessage("Error!");
                        }
                        sender.sendMessage(reload);
                    } else {
                        sender.sendMessage(nopermission);// ?????????
                    }
                } else {
                    sender.sendMessage(invalid);
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("binding")) {// ????????????
                    try {
                        String id = this.getPoster().getString(sender.getName() + ".id");
                        Long date = System.currentTimeMillis() - this.getPoster().getLong(sender.getName() + ".date");
                        Boolean iscd = date > changeidcooldown * 86400000;
                        if (id == null || iscd) {// ????????????????????????????????????????????????
                            if (binding.get(sender.getName()) == null) {// ?????????????????????????????????
                                binding.put(sender.getName(), args[1]);
                                sender.sendMessage(repeat);
                            } else if (binding.get(sender.getName()).equals(args[1])) {// ??????????????????
                                if (!issame(args[1])) {// ??????????????????
                                    this.getPoster().set(sender.getName() + ".id", args[1]);
                                    this.getPoster().set(sender.getName() + ".date", System.currentTimeMillis());// ????????????
                                    this.getPoster().set(sender.getName() + ".daybefore", "");// ???????????????
                                    this.getPoster().set(sender.getName() + ".rwaday", 0);// ???????????????0
                                    this.savePoster();// ????????????
                                    binding.put(sender.getName(), null);// ????????????,??????map
                                    sender.sendMessage(bdsuccess);
                                } else {
                                    if (this.getPoster().getString(sender.getName() + ".id") != null
                                            && this.getPoster().getString(sender.getName() + ".id").contains(args[1])) {
                                        sender.sendMessage(ownsamebinding);// ?????????????????????ID
                                    } else {
                                        sender.sendMessage(samebinding);// ??????????????????
                                    }
                                    binding.put(sender.getName(), null);// ????????????,??????map
                                }
                            } else if (!binding.get(sender.getName()).equals(args[1])) {
                                binding.put(sender.getName(), null);// ????????????????????????map????????????
                                sender.sendMessage(notsame);
                            }
                        } else {// ?????????????????????
                            sender.sendMessage(oncooldown);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        sender.sendMessage("Error!");
                    }
                } else if (args[0].equalsIgnoreCase("list")) {// ???????????????
                    if (sender.hasPermission("bbstoper.admin")) {
                        if (topList().size() != 0) {
                            boolean b = true;
                            for (int i = 0; i < args[1].length(); i++) {// ???????????????????????????
                                if (!Character.isDigit(args[1].charAt(i))) {
                                    b = false;
                                }
                            }
                            if (b) {
                                int page = Integer.parseInt(args[1]) - 1;// ?????????java
                                if (page + 1 <= paging().size()) {
                                    outputToplist(sender, page);// ???????????????
                                } else {
                                    sender.sendMessage(overpage);// ????????????
                                }
                            } else {
                                sender.sendMessage(invalid);// ????????????
                            }
                        } else {
                            sender.sendMessage(noposter);
                        }
                    } else {
                        sender.sendMessage(nopermission);
                    }
                } else {
                    sender.sendMessage(invalid);
                }
            }
            if (args.length == 0) {
                for (int i = 0; i < help.size(); i++) {// ??????????????????
                    sender.sendMessage(help.get(i));
                }
            }
            if (args.length > 2) {
                sender.sendMessage(invalid);
            }
        }
        return false;

    }

    public boolean issame(String id) throws UnsupportedEncodingException {// ?????????????????????
        boolean b = false;
        Map<String, Object> map = this.getPoster().getValues(true);// ?????????????????????Map
        List<String> list = new ArrayList<String>();
        for (String key : map.keySet()) {// ??????key
            if (key.contains(".id")) {// ??????key????????????id
                list.add((String) map.get(key));// ?????????key??????????????????list
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(id)) {// ????????????????????????ID???????????????????????????id
                b = true;
            }
        }
        return b;
    }

    public static synchronized void Getter() throws IOException {// ????????????,???????????????
        String url = mcbbsurl;
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select(".f_c a[target=\"_blank\"]"); // "a[href]" //??????href?????????a??????
        Elements imports = doc.select(".f_c td");
        ID.clear();
        Time.clear();
        for (Element link : links) {
            //System.out.println(link.text());
            ID.add(trim(link.text(), 35));
        }
        for (int i = 0; i < imports.size(); i++) {
            Element link1 = imports.get(i);
            if (i<=4)continue;
            if ((i-1) % 4 != 0) continue;
           // System.out.println(i + ":    "+link1.text());

            Time.add(link1.text());
        }
        /*
        for (Element link1 : imports) {
            //System.out.println(link1.text());
            Time.add(link1.attr("title"));
        }*/
    }

    private static String trim(String s, int width) {// ??????ID
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }

    public static void outputToplist(CommandSender p, Integer page) {
        String[] FinalList = paging().get(page).toArray(new String[paging().get(page).size()]);
        p.sendMessage(posternum + ":" + Time.size());
        p.sendMessage(FinalList);
        p.sendMessage(pageinfo.replaceAll("%PAGE%", String.valueOf(page + 1)).replaceAll("%TOTALPAGE%",
                String.valueOf(paging().size())));
    }

    public static List<List<String>> paging() {// ??????
        List<String> list = topList();
        int totalCount = list.size(); // ?????????
        int pageCount; // ?????????
        int m = totalCount % pagesize; // ??????
        if (m > 0) {
            pageCount = totalCount / pagesize + 1;
        } else {
            pageCount = totalCount / pagesize;
        }
        List<List<String>> totalList = new ArrayList<List<String>>();
        for (int i = 1; i <= pageCount; i++) {
            if (m == 0) {
                List<String> subList = list.subList((i - 1) * pagesize, pagesize * (i));
                totalList.add(subList);
            } else {
                if (i == pageCount) {
                    List<String> subList = list.subList((i - 1) * pagesize, totalCount);
                    totalList.add(subList);
                } else {
                    List<String> subList = list.subList((i - 1) * pagesize, pagesize * i);
                    totalList.add(subList);
                }
            }
        }
        return totalList;
    }

    public static List<String> topList() {// ??????????????????
/*        try {
            Getter();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < ID.size() && i < Time.size(); i++) {
            list.add(posterid + ":" + ID.get(i) + " " + postertime + ":" + Time.get(i));
        }
        return list;
    }

    public void reloadPoster() throws UnsupportedEncodingException {// ??????
        if (posterFile == null) {
            posterFile = new File(getDataFolder(), "poster.yml");
        }
        poster = YamlConfiguration.loadConfiguration(posterFile);
        Reader posterStream = new InputStreamReader(this.getResource("poster.yml"), "UTF8");// ??????jar????????????
        if (posterStream != null) {
            YamlConfiguration post = YamlConfiguration.loadConfiguration(posterStream);
            poster.setDefaults(post);
        }
    }

    public FileConfiguration getPoster() throws UnsupportedEncodingException {// ??????
        if (poster == null) {
            reloadPoster();
        }
        return poster;
    }

    public void savePoster() {// ??????
        if (poster == null || posterFile == null) {
            return;
        }
        try {
            this.getPoster().save(posterFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + posterFile, ex);
            getLogger().info("Save poster Error!");
        }
    }

    public void saveDefaultPoster() {// ????????????
        if (posterFile == null) {
            posterFile = new File(getDataFolder(), "poster.yml");
        }
        if (!posterFile.exists()) {
            this.saveResource("poster.yml", false);
        }
    }
}
