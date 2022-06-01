package com.WeiBoss.bossshoptr.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class MainCommand implements CommandExecutor {
    private HelpCommand help;
    private HashMap<String, WeiCommand> commands = new HashMap<>();

    public MainCommand() {
        this.help = new HelpCommand();
        this.commands.put("help", new HelpCommand());
        this.commands.put("check", new CheckCommand());
        this.commands.put("shop", new ShopCommand());
        this.commands.put("day", new DayCommand());
        this.commands.put("select", new SelectCommand());
        this.commands.put("reload",new ReloadCommand());
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Object defaultCmd = this.help;
        if (strings.length > 0 && this.commands.containsKey(strings[0])) {
            defaultCmd = this.commands.get(strings[0]);
        }
        ((WeiCommand) defaultCmd).execute(commandSender, strings);
        return false;
    }
}