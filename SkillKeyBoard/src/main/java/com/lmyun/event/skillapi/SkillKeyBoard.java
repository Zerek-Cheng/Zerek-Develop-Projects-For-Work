package com.lmyun.event.skillapi;

import com.lmyun.socket.SocketUtils;
import com.lmyun.socket.packages.request.rsa.RequestRsaKeyPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseCheckPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseSendClassPackage;
import com.lmyun.socket.thread.SocketListenThread;
import com.lmyun.socket.thread.SocketSendThread;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexImageShow;
import lk.vexview.hud.VexShow;
import lk.vexview.hud.VexTextShow;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;

public class SkillKeyBoard extends JavaPlugin {
    private HashMap<String, FileConfiguration> config = new HashMap<>();
    private HashMap<String, String> inBind = new HashMap<>();
    @Getter
    private String publicKey = "DCFA954V95SAV159AR954B89F4D89V4XC85V1531V4C894VD89A4B89SD4";
    private SocketSendThread sender;
    private SocketListenThread listener;
    private String licenseString;
    private Socket socket;

    private static Class<?> BukkitClass;
    private static Object serverObject = null;
    private static Method shutdownMethod = null;
    private static Object PMObject;
    private static Method regEventsMethod;

    static {
        try {
            BukkitClass = Class.forName("org.bukkit.Bukkit");
            serverObject = BukkitClass.getMethod("getServer").invoke(BukkitClass);
            PMObject = serverObject.getClass().getMethod("getPluginManager").invoke(serverObject);
            shutdownMethod = serverObject.getClass().getMethod("shutdown");
            regEventsMethod = PMObject.getClass().getMethod("registerEvents", Listener.class, Plugin.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.WARNING, "请注意,现在开始授权校验,如果授权错误将会关服,请把授权填入插件配置!");
        int license = 0;
        loadConfig();
        System.out.println("==========================");
        try {
            license = 100;
            licenseString = this.config.get("Config").getString("license");
            getLogger().log(Level.INFO, "SkillKeyBoard Loading");
            try {
                //主要验证步骤
                this.socket = new Socket("auth.plugin.design", 9999);
                sender = new SocketSendThread(socket);
                listener = new SocketListenThread(socket);
                sender.start();
                listener.start();
                //////////////////////////////////////////////////////////////////////////////
                sender.addMsg(new RequestRsaKeyPackage().setPublicKey(String.valueOf(UUID.randomUUID())).contentUpdate());
                long timeStart = System.currentTimeMillis();//计时防卡住
                while (!this.socket.isClosed()) {//消息监听，直到验证步骤结束
                    while (listener.getMsg().size() != 0) {
                        byte[] msg = listener.getMsg().take();
                        int msgType = SocketUtils.Byte2Int(SocketUtils.toObjects(SocketUtils.subBytes(msg, 0, 4)));
                        msg = SocketUtils.subBytes(msg, 4, msg.length - 4);
                        deal(msgType, msg);//处理消息
                        if (msgType == 2002 || msgType == 2004) {
                            getLogger().log(Level.INFO, "授权验证成功");
                            license = 1;
                        } else if (msgType == 2003) {
                            throw new Exception("授权失败,序列号错误或者IP不匹配");
                        }
                        if (msgType == 2004) {//2004发完类就结束验证了
                            return;
                        }
                    }
                    if ((System.currentTimeMillis() - timeStart) > (1000 * 30)) {
                        throw new Exception("授权超时,请检查网络环境");
                    }
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "验证失败");
                getLogger().log(Level.WARNING, e.getMessage());
                return;
            }
        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(this);
            e.printStackTrace();
        } finally {
            if (license != 1) {
                try {
                    Bukkit.getPluginManager().disablePlugin(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getLogger().log(Level.WARNING, "加载异常,请检查授权&其他配置");
            }
        }
        Bukkit.getPluginManager().disablePlugin(this);
        getLogger().log(Level.INFO, "已完成授权校验..Enjoy!");
    }

    public void loadConfig() {
        saveResource("LeftUI.yml", false);
        this.config.put("leftUI", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/LeftUI.yml")));
        saveResource("EditUI.yml", false);
        this.config.put("editUI", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/EditUI.yml")));
        saveResource("Skills.yml", false);
        this.config.put("Skills", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Skills.yml")));
        saveResource("Data.yml", false);
        this.config.put("Data", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Data.yml")));
        saveResource("Config.yml", false);
        this.config.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Config.yml")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String help = "§a=============================================\n" +
                "§e/skb reload                §a重载\n" +
                "§e/skb bind <技能名>         §a绑定技能到热键\n" +
                "§e/skb delbind <技能名>      §a解绑技能绑定\n" +
                "§e/skb delall                §a解绑所有技能绑定\n" +
                "§d其他界面UI配置都在目录下的配置文件中";
        if (args.length == 0) {
            sender.sendMessage(help);
            return true;
        }
        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.loadConfig();
            sender.sendMessage("config reload ok");
            return true;
        }
        if (args[0].equalsIgnoreCase("bind")) {
            int i = 0;
            String skillName = new String();
            for (String arg : args) {
                if (i != 0) {
                    skillName += arg;
                }
                if (i == 1 && i != (args.length - 1)) {
                    skillName += " ";
                }
                i++;
            }
            this.setInBind(player.getName(), skillName);
            sender.sendMessage("已进入技能绑定模式,请按下需要需要绑定的热键");
            return true;
        }
        if (args[0].equalsIgnoreCase("delbind")) {
            int i = 0;
            String skillName = new String();
            for (String arg : args) {
                if (i != 0) {
                    skillName += arg;
                }
                if (i == 1 && i != (args.length - 1)) {
                    skillName += " ";
                }
                i++;
            }
            this.delBindKeyBoard(player, skillName);
            this.saveKeyBoard();
            sender.sendMessage("技能解绑成功");
            return true;
        }
        if (args[0].equalsIgnoreCase("delall")) {
            this.delAllBindKeyBoard(player);
            this.saveKeyBoard();
            sender.sendMessage("技能全部解绑成功");
            return true;
        }
        return true;
    }

    private List<VexShow> getPlayerLeftHud(Player player) {
        List<VexShow> shows = new ArrayList<>();
        FileConfiguration leftUIConfig = this.config.get("leftUI");
        ConfigurationSection imageConfig = leftUIConfig.getConfigurationSection("image");

        ConfigurationSection backgroundConfig = imageConfig.getConfigurationSection("background");
        ConfigurationSection skillsConfig = imageConfig.getConfigurationSection("skills");
        FileConfiguration skillsImgConfig = this.config.get("Skills");
        ConfigurationSection keyBoardConfig = imageConfig.getConfigurationSection("keyBoard");
        ConfigurationSection coolDown = imageConfig.getConfigurationSection("coolDown");
        ConfigurationSection keyBoardUrlConfig = keyBoardConfig.getConfigurationSection("url");
        //===========================================================================================
        VexImageShow background = new VexImageShow(100001, backgroundConfig.getString("url"),
                backgroundConfig.getInt("x"),
                backgroundConfig.getInt("y"),
                backgroundConfig.getInt("w"),
                backgroundConfig.getInt("h"),
                backgroundConfig.getInt("xs"),
                backgroundConfig.getInt("ys"), 1);
        shows.add(background);
        //===========================================================================================
        PlayerData playerData = SkillAPI.getPlayerData(player);
        Collection<PlayerSkill> skills = playerData.getSkills();
        //技能显示在主UI的正中间
        //int skillStartX = (backgroundConfig.getInt("xs") / 2) - (skillsConfig.getInt("xs") / 2);
        int skillStartX = skillsConfig.getInt("startX");
        int i = 0;
        for (PlayerSkill skill : skills) {
            String skillName = skill.getData().getName();
            if (this.getKeyBoard(player, skillName) == -1) {
                continue;
            }
            int skillCD = skill.getCooldown();
            if (skillCD == 1) {
                this.sendPlayerSkillCoolDownOver(player, skillName);
            }
            VexImageShow skillImg = new VexImageShow(100101 + i,
                    skillsImgConfig.getString(skillName),
                    backgroundConfig.getInt("x") + skillStartX,
                    skillsConfig.getInt("startY") + ((skillsConfig.getInt("ys") + skillsConfig.getInt("intervalY")) * i),
                    skillsConfig.getInt("w"),
                    skillsConfig.getInt("h"),
                    skillsConfig.getInt("xs"),
                    skillsConfig.getInt("ys"), 1
            );
            VexImageShow keyBoardImg = new VexImageShow(100201 + i,
                    keyBoardUrlConfig.getString(String.valueOf(this.getKeyBoard(player, skillName))),
                    backgroundConfig.getInt("xs"),
                    skillsConfig.getInt("startY") + ((skillsConfig.getInt("ys") + skillsConfig.getInt("intervalY")) * i) + ((skillsConfig.getInt("ys") - keyBoardConfig.getInt("ys")) / 2),
                    keyBoardConfig.getInt("w"),
                    keyBoardConfig.getInt("h"),
                    keyBoardConfig.getInt("xs"),
                    keyBoardConfig.getInt("ys"), 1
            );
            List<String> cdText = new ArrayList<String>();
            cdText.add(String.valueOf(skillCD));
            VexTextShow keyBoardCoolDown = new VexTextShow(100301 + i,
                    // backgroundConfig.getInt("x") + skillStartX,
                    backgroundConfig.getInt("x") + skillStartX + (keyBoardConfig.getInt("xs") / 2),
                    skillsConfig.getInt("startY") + (skillsConfig.getInt("intervalY") * i) + (skillsConfig.getInt("ys") * (i + 1)) - (skillsConfig.getInt("ys") / 2) - (skillsConfig.getInt("intervalY") / 2),
                    cdText,
                    5);
            shows.add(skillImg);
            shows.add(keyBoardImg);
            if (skillCD > 0) {
                shows.add(keyBoardCoolDown);
            }
            i++;
        }
        return shows;
    }

    public void sendPlayerHud(Player player) {
        List<VexShow> shows = this.getPlayerLeftHud(player);
        for (VexShow show : shows) {
            VexViewAPI.sendHUD(player, show);
        }
    }

    public void bindKeyBoard(Player p, String skill, int keyBoard) {
        if (this.keyBoardToSkill(p, keyBoard) != null) {
            this.delBindKeyBoard(p, this.keyBoardToSkill(p, keyBoard));
        }
        this.getKeyBoards(p).set(skill, keyBoard);
    }

    public void delBindKeyBoard(Player p, String skill) {
        ConfigurationSection keyBoards = this.getKeyBoards(p);
        keyBoards.set(skill, null);

    }

    public void delAllBindKeyBoard(Player p) {
        this.getKeyBoards(p).getParent().set(p.getName(), null);
    }

    public int getKeyBoard(Player p, String skill) {
        if (this.getKeyBoards(p).isInt(skill)) {
            return this.getKeyBoards(p).getInt(skill);
        }
        return -1;
    }

    public ConfigurationSection getKeyBoards(Player p) {
        ConfigurationSection data = this.config.get("Data").getConfigurationSection(p.getName());
        if (data == null) {
            data = this.config.get("Data").createSection(p.getName());
        }

        return data;
    }

    public String keyBoardToSkill(Player p, int key) {
        if (this.getKeyBoards(p) == null) {
            return null;
        }
        Map<String, Object> keys = this.getKeyBoards(p).getValues(true);
        for (Map.Entry<String, Object> entry : keys.entrySet()) {
            if (!entry.getValue().equals(key)) {
                continue;
            }
            return entry.getKey();
        }
        return null;
    }

    public void saveKeyBoard() {
        try {
            this.config.get("Data").save(new File(getDataFolder().getPath() + "/Data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInBind(String p) {
        return this.inBind.get(p) == null || this.inBind.get(p).isEmpty() ? false : true;
    }

    public void setInBind(String p, String skill) {
        if (skill == null) {
            this.inBind.remove(p);
            return;
        }
        this.inBind.put(p, skill);
    }

    public String getInBind(String p) {
        return this.inBind.get(p);
    }

    public void sendPlayerSkillCoolDownOver(Player p, String skill) {
        FileConfiguration skillsImgConfig = this.config.get("Skills");
        FileConfiguration leftUIConfig = this.config.get("leftUI");
        ConfigurationSection imageConfig = leftUIConfig.getConfigurationSection("image");
        ConfigurationSection skillsConfig = imageConfig.getConfigurationSection("skills");

        List<VexShow> vs = new ArrayList<>();
        VexImageShow background = new VexImageShow(100400,
                "[local]gui.png",
                -120,
                -55,
                240,
                158,
                120,
                60,
                5
        );
        VexImageShow skillImg = new VexImageShow(100402,
                skillsImgConfig.getString(skill),
                -110,
                -40,
                skillsConfig.getInt("w"),
                skillsConfig.getInt("h"),
                32,
                32,
                5
        );
        List<String> string = new ArrayList<>();
        string.add("技能冷却结束");
        VexTextShow text = new VexTextShow(100404, -75, -28, string, 5);
        vs.add(background);
        vs.add(skillImg);
        vs.add(text);
        for (VexShow hud : vs) {
            VexViewAPI.sendHUD(p, hud);
        }
    }


    public void deal(int msgType, byte[] msg) {
        switch (msgType) {
            case 1001:
                RequestRsaKeyPackage requestRsaKeyPackage = new RequestRsaKeyPackage(new String(msg));
                this.publicKey = requestRsaKeyPackage.getPublicKey();
                //System.out.println("收到来自服务端的公钥" + this.publicKey);
                LicenseCheckPackage licenseCheckPackage = new LicenseCheckPackage();
                licenseCheckPackage.setLicense(licenseString);
                licenseCheckPackage.setLicenseType("SkillKeyBoard");
                licenseCheckPackage.setPublicKey(this.getPublicKey());
                licenseCheckPackage.setVersion(this.getDescription().getVersion());
                this.sender.addMsg(licenseCheckPackage.contentUpdate());
                break;
            case 2004:
                LicenseSendClassPackage classPackage = new LicenseSendClassPackage(new String(msg));
                classPackage.setPublicKey(this.publicKey);
                classPackage.decode();
                String classFile = (String) classPackage.getData().get("classFile");
                try {
                    new ClassLoader(this.getClassLoader()) {
                        public void load() throws Exception {
                            byte[] classByte = Base64.getDecoder().decode(classFile);
                            Class<?> aClass = super.defineClass(classByte, 0, classByte.length);
                            Class[] parameterTypes = {SkillKeyBoard.class};
                            Constructor constructor = aClass.getConstructor(parameterTypes);
                            Object o = constructor.newInstance(SkillKeyBoard.getPlugin());
                            regEventsMethod.invoke(PMObject, o, SkillKeyBoard.getPlugin());
                        }
                    }.load();
                    this.socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public static SkillKeyBoard getPlugin() {
        return (SkillKeyBoard) Bukkit.getPluginManager().getPlugin("SkillKeyBoard");
    }
}
