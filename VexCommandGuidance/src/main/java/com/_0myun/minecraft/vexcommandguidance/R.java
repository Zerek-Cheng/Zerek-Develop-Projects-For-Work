package com._0myun.minecraft.vexcommandguidance;

import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexText;
import lk.vexview.gui.components.VexTextField;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public final class R extends JavaPlugin {
    public static int vid = 0;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        vid = getConfig().getInt("vid");
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().warning("定制插件请务必找q2025255093 灵梦云科技");
            }
        }.runTaskLater(this, 100l);
    }

    public String lang(String lang) {
        return getConfig().getString("lang." + lang, lang);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            reloadConfig();
            sender.sendMessage("ok");
        }
/*        if (args.length >= 4 && args[0].equalsIgnoreCase("callback")) {
            //0 callback
            //1 playername
            //2 cmdname
            //3 *******
            Player p = Bukkit.getPlayer(args[1]);
            String cmdName = args[2];

            String cmd = "";
            for (int i = 3; i < args.length; i++) {
                cmd += args[i];
                if (i + 1 < args.length) cmd += " ";
            }
            System.out.println(cmd);
        }*/
        if (args.length >= 3 && args[0].equalsIgnoreCase("run") && sender.isOp()) {
            Player p = Bukkit.getPlayer(args[1]);
            String cmdName = args[2];
            boolean result = openGui(p, cmdName, new String[1], null);
            p.sendMessage(result ? lang("lang1") : lang("lang3"));
        }
        return true;
    }

    public boolean openGui(Player p, String name, String[] cmd, String helpLang) {
        if (!getConfig().isSet("cmds." + name)) return false;
        ConfigurationSection config = getConfig().getConfigurationSection("cmds." + name);
        if (cmd[0] == null) cmd[0] = config.getString("cmd");
        cmd[0] = cmd[0].replace("%player%", p.getName());
        ConfigurationSection guiConfig = config.getConfigurationSection("gui");
        String nowKey = null;

        boolean success = true;
        for (String key : config.getConfigurationSection("lang").getKeys(false)) {
            if (cmd[0].contains("<" + key + ">")) {
                success = false;
                nowKey = key;
                break;
            }
        }
        String help = helpLang != null ? lang(helpLang) : "";//下一个参数  或   输入错误
        if (success) {
            switch (config.getString("type")) {
                case "player":
                    p.performCommand(cmd[0]);
                    break;
                case "op":
                    boolean op = p.isOp();
                    try {
                        if (!op) p.setOp(true);
                        p.performCommand(cmd[0]);
                    } finally {
                        if (!op) p.setOp(false);
                    }
                    break;
                case "console":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd[0]);
                    break;
                default:
                    break;
            }
            p.closeInventory();
            p.updateInventory();
            return true;
        }
        List<String> lang = config.getStringList("lang." + nowKey);//title和提示


        VexGui gui = new VexGui(
                guiConfig.getString("main.url"),
                guiConfig.getInt("main.x"),
                guiConfig.getInt("main.y"),
                guiConfig.getInt("main.w"),
                guiConfig.getInt("main.h"),
                guiConfig.getInt("main.w"),
                guiConfig.getInt("main.h")
        );

        VexText textTop = new VexText(
                guiConfig.getInt("text-top.x"),
                guiConfig.getInt("text-top.y"),
                lang
        );

        VexText textTip = new VexText(
                guiConfig.getInt("text-help.x"),
                guiConfig.getInt("text-help.y"),
                Arrays.asList(help)
        );

        VexTextField textField = new VexTextField(
                guiConfig.getInt("text-field.x"),
                guiConfig.getInt("text-field.y"),
                guiConfig.getInt("text-field.w"),
                guiConfig.getInt("text-field.h"),
                256,
                ++vid
        );
        //加到text-help里,首次打开不会显示text-help,点击确定后才会显示,
        // 根据情况显示,,比如有参数限制,
        // 玩家没有按照参数限制输入然后点击确定,text-help显示的就是lang2,
        // 若是正确输入并点确定后并且还有下一个参数需补全则显示lang4;
        //
        // 和<>扩住的参数是必填不能留空,[]扩住的参数可以选填,必填参数如果没有填点确定就输出lang2
        VexButton button = new VexButton(
                ++vid,
                guiConfig.getString("button.text"),
                guiConfig.getString("button.url1"),
                guiConfig.getString("button.url2"),
                guiConfig.getInt("button.x"),
                guiConfig.getInt("button.y"),
                guiConfig.getInt("button.w"),
                guiConfig.getInt("button.h")
        );
        List<String> canEmpty = config.getStringList("can-empty");
        String finalNowKey = nowKey;
        button.setFunction(buttonPlayer -> {
            List<String> limits = config.getStringList("limit." + finalNowKey);
            String value = textField.getTypedText();
            if (
                    (!canEmpty.contains(finalNowKey) && (value == null || value.isEmpty())) ||
                            (config.isSet("limit." + finalNowKey) && !limits.contains(value))
            ) {
                //buttonPlayer.sendMessage(lang("lang2"));
                p.closeInventory();
                openGui(buttonPlayer, name, cmd, "lang2");//输入的不满足要求
                return;
            }
            cmd[0] = cmd[0].replace("<" + finalNowKey + ">", value);
            //buttonPlayer.sendMessage(lang("lang4"));
            openGui(buttonPlayer, name, cmd, "lang4");//输入下一个参数
        });

        gui.addAllComponents(Arrays.asList(textTop, textTip, textField, button));
        p.closeInventory();
        VexViewAPI.openGui(p, gui);
        return true;
    }
}
