/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.chat.Chat
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World
 *  org.bukkit.attribute.Attribute
 *  org.bukkit.attribute.AttributeInstance
 *  org.bukkit.attribute.AttributeModifier
 *  org.bukkit.attribute.AttributeModifier$Operation
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.scoreboard.Scoreboard
 *  org.bukkit.scoreboard.Team
 */
package xin.tianwc.tprefix;

import github.saukiya.sxattribute.SXAttribute;
import github.saukiya.sxattribute.data.attribute.SXAttributeData;
import github.saukiya.sxattribute.data.condition.SXConditionType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

public class Commands
        implements CommandExecutor {
    private final Main plugin;

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (s.equalsIgnoreCase("hao")) {
            if (!(sender instanceof Player) || args.length == 0) {
                return false;
            }
            Player player = (Player) sender;
            switch (args[0]) {
                case "open": {
                    PreInventory.PreMenu(player);
                    break;
                }
                case "p": {
                    if (!Prefix.isPrefix(args[1])) {
                        player.sendMessage("§4这个称号并不存在");
                        return true;
                    }
                    Prefix prefix = new Prefix(args[1]);
                    if (!prefix.getPermission().equals("tpre.default") && !player.hasPermission(prefix.getPermission())) {
                        player.sendMessage("§4你没有获得这个称号");
                        return true;
                    }
                    for (World world : Bukkit.getWorlds()) {
                        Main.chat.setPlayerPrefix(world.getName(), player, "[&" + prefix.getColor() + prefix.getPre() + "&f]");
                    }
                    this.removeModifiers(player);
                    FileConfiguration config = Main.INSTANCE.getConfig();
                    config.set("data." + player.getUniqueId().toString(),prefix.getPre());
                    Main.INSTANCE.saveConfig();
                    /*AttributeInstance damageInstance = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                    damageInstance.addModifier(new AttributeModifier("TpreDamage", prefix.getDamage().doubleValue(), AttributeModifier.Operation.ADD_SCALAR));
                    AttributeInstance armorInstance = player.getAttribute(Attribute.GENERIC_ARMOR);
                    armorInstance.addModifier(new AttributeModifier("TpreArmor", prefix.getArmor().doubleValue(), AttributeModifier.Operation.ADD_SCALAR));
                    AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    healthInstance.addModifier(new AttributeModifier("TpreHealth", prefix.getHealth().doubleValue(), AttributeModifier.Operation.ADD_SCALAR));
                    AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                    speedInstance.addModifier(new AttributeModifier("TpreSpeed", prefix.getSpeed().doubleValue(), AttributeModifier.Operation.ADD_SCALAR));
                    */
                    SXAttributeData data = new SXAttributeData();
                    //data.getSubAttribute("Damage").addAttribute(new double[]{0, 0, 0, 0, 0, 0});
                    data.add(SXAttribute.getApi().getLoreData(player, SXConditionType.ALL, prefix.getLores()));
                    try {
                        Class<? extends SXAttributeData> dataClass = data.getClass();
                        Field valid = dataClass.getDeclaredField("valid");
                        valid.setAccessible(true);
                        valid.setBoolean(data, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SXAttribute.getApi().setEntityAPIData(Main.class, player.getUniqueId(), data);
                    //SXAttribute.getApi().removeEntityAPIData(Main.class, player.getUniqueId());
                    SXAttribute.getApi().updateHandData(player);
                    SXAttribute.getApi().updateStats(player);
                    SXAttribute.getApi().updateEquipmentData(player);
                    SXAttribute.getApi().updateRPGInventoryData(player);
                    SXAttribute.getApi().updateSlotData(player);
                    try {
                        this.setTag(player, "[§" + prefix.getColor() + prefix.getPre() + "§f]");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    player.sendMessage("§a成功切换到" + prefix.getPre() + "称号");/*
                    player.sendMessage("当前攻击加成：§a" + prefix.getDamage());
                    player.sendMessage("当前防御加成：§a" + prefix.getArmor());
                    player.sendMessage("当前生命加成：§a" + prefix.getHealth());
                    player.sendMessage("当前速度加成：§a" + prefix.getSpeed());*/
                    break;
                }
                case "reload": {
                    if (sender.hasPermission("tpre.reload")) {
                        Main.INSTANCE.reloadConfig();
                        break;
                    }
                    sender.sendMessage("§4你没有权限这样做");
                    break;
                }
                default: {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void removeModifiers(Player player) {
        AttributeInstance damageInstance = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        for (AttributeModifier modifier : damageInstance.getModifiers()) {
            if (!modifier.getName().equals("TpreDamage")) continue;
            damageInstance.removeModifier((AttributeModifier) modifier);
        }
        AttributeInstance armorInstance = player.getAttribute(Attribute.GENERIC_ARMOR);
        for (AttributeModifier modifier : armorInstance.getModifiers()) {
            if (!modifier.getName().equals("TpreArmor")) continue;
            armorInstance.removeModifier((AttributeModifier) modifier);
        }
        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        for (AttributeModifier modifier : healthInstance.getModifiers()) {
            if (!modifier.getName().equals("TpreHealth")) continue;
            healthInstance.removeModifier((AttributeModifier) modifier);
        }
        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        for (AttributeModifier modifier : speedInstance.getModifiers()) {
            if (!modifier.getName().equals("TpreSpeed")) continue;
            speedInstance.removeModifier(modifier);
        }
    }

    public void setTag(Player p, String prefix) throws Exception {
        Scoreboard board;
        Team t;
        if ((prefix = ChatColor.translateAlternateColorCodes((char) '&', (String) prefix)).length() > 16) {
            prefix = prefix.substring(0, 16);
        }
        if ((t = (board = Bukkit.getScoreboardManager().getMainScoreboard()).getTeam(p.getName())) == null) {
            t = board.registerNewTeam(p.getName());
            t.setPrefix(prefix);
            t.addEntry(p.getName());
        } else {
            t = board.getTeam(p.getName());
            t.setPrefix(prefix);
            t.addEntry(p.getName());
        }
        for (Player o : Bukkit.getOnlinePlayers()) {
            o.setScoreboard(board);
        }
    }
}

