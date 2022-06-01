package com._0myun.minecraft.pixelmonknockout.commands;

import com._0myun.minecraft.pixelmonknockout.R;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BaseCommand implements CommandExecutor {

    HashMap<String, Method> subCommands = new HashMap<>();

    public BaseCommand() {
        for (Method method : this.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            SubCommand command = method.getAnnotation(SubCommand.class);
            if (command == null) continue;
            String label = command.label();
            subCommands.put(label.toLowerCase(), method);
            System.out.println("注册子命令" + label);
        }
    }

    boolean subCommand(String subLabel, Player p, List<String> args) {
        subLabel = subLabel.toLowerCase();
        if (!subCommands.containsKey(subLabel)) return false;
        Method method = subCommands.get(subLabel);
        SubCommand command = method.getAnnotation(SubCommand.class);
        if (args.size() < command.args()) {
            p.sendMessage(R.INSTANCE.lang("lang2"));
            return false;
        }
        if (!command.permission().isEmpty() && !p.isOp() && !p.hasPermission(command.permission())) {
            p.sendMessage(R.INSTANCE.lang("lang32"));
            return false;
        }
        try {
            method.invoke(this, p, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> subArgs = new ArrayList<>(Arrays.asList(args));
        if (args.length < 1) return true;
        Player p = (Player) sender;
        subArgs.remove(0);
        subCommand(args[0], p, subArgs);
        return true;
    }
}
