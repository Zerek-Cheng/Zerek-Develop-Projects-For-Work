package com.lmyun.event.skillapi;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.player.PlayerData;
import com.sucy.skill.api.player.PlayerSkill;
import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexImageShow;
import lk.vexview.hud.VexShow;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class SkillKeyBoard extends JavaPlugin {
    private HashMap<String, FileConfiguration> config = new HashMap<>();
    private HashMap<String, String> inBind = new HashMap<>();

    @Override
    public void onEnable() {
        loadConfig();
        getLogger().log(Level.WARNING, "=============================================================");
        getLogger().log(Level.WARNING, "您正在使用的是预览版，仅支持固定的一个技能一个键位");
        getLogger().log(Level.WARNING, "禁止将DEMO版本应用于任何商业服务器,如需无限制版本请联系作者或各级代理商购买");
        Bukkit.getPluginManager().registerEvents(new SkillKeyBoardListener(this), this);
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
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String help = "§a=============================================\n" +
                "§e/skb reload                §a重载\n" +
                "§e/skb bind <技能名>         §a绑定技能到热键\n" +
                "§e/skb delbind <技能名>      §a解绑技能绑定\n" +
                "§e/skb delall                §a解绑所有技能绑定\n" +
                "§d其他界面UI配置都在目录下的配置文件中" +
                "§4以上是商业版的菜单,DEMO演示版没有以上功能,只有固定技能固定按键!!!!!!!!!!!!";
        sender.sendMessage(help);
        sender.sendMessage("您正在使用的是预览版，仅支持固定的一个技能一个键位,且命令均不可用,演示版本功能键为W,支持的技能是获取到的第一个技能");
        return true;
    }

    private List<VexShow> getPlayerLeftHud(Player player) {
        List<VexShow> shows = new ArrayList<>();
        FileConfiguration leftUIConfig = this.config.get("leftUI");
        ConfigurationSection imageConfig = leftUIConfig.getConfigurationSection("image");

        ConfigurationSection backgroundConfig = imageConfig.getConfigurationSection("background");
        ConfigurationSection skillsConfig = imageConfig.getConfigurationSection("skills");
        FileConfiguration skillsImgConfig = this.config.get("Skills");
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
        if (skills.size() < 1) {
            return shows;
        }
        int skillStartX = (backgroundConfig.getInt("xs") / 2) - (skillsConfig.getInt("xs") / 2);
        VexImageShow skillImg = new VexImageShow(100101,
                skillsImgConfig.getString("Skill 1"),
                backgroundConfig.getInt("x") + skillStartX,
                skillsConfig.getInt("startY"),
                skillsConfig.getInt("w"),
                skillsConfig.getInt("h"),
                skillsConfig.getInt("xs"),
                skillsConfig.getInt("ys"), 1
        );
        shows.add(skillImg);
        return shows;
    }

    public void sendPlayerHud(Player player) {
        List<VexShow> shows = this.getPlayerLeftHud(player);
        for (VexShow show : shows) {
            VexViewAPI.sendHUD(player, show);
        }
    }

}
