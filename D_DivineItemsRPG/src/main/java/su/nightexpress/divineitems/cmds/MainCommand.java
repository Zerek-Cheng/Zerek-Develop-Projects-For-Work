/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.TabExecutor
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.cmds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.list.HelpCommand;
import su.nightexpress.divineitems.cmds.list.InfoCommand;
import su.nightexpress.divineitems.cmds.list.ModifyCommand;
import su.nightexpress.divineitems.cmds.list.ReloadCommand;
import su.nightexpress.divineitems.cmds.list.SetCommand;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.modules.abyssdust.AbyssDustManager;
import su.nightexpress.divineitems.modules.arrows.ArrowManager;
import su.nightexpress.divineitems.modules.consumes.ConsumeManager;
import su.nightexpress.divineitems.modules.enchant.EnchantManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.identify.IdentifyManager;
import su.nightexpress.divineitems.modules.magicdust.MagicDustManager;
import su.nightexpress.divineitems.modules.runes.RuneManager;
import su.nightexpress.divineitems.modules.scrolls.ScrollManager;
import su.nightexpress.divineitems.modules.sets.SetManager;
import su.nightexpress.divineitems.modules.tiers.TierManager;
import su.nightexpress.divineitems.utils.Utils;

public class MainCommand
implements CommandExecutor,
TabExecutor {
    private DivineItems plugin;
    private Map<String, CommandBase> commands = new HashMap<String, CommandBase>();
    private HelpCommand help;

    public MainCommand(DivineItems divineItems) {
        this.plugin = divineItems;
        this.help = new HelpCommand();
        this.commands.put("set", new SetCommand(divineItems));
        this.commands.put("modify", new ModifyCommand());
        this.commands.put("reload", new ReloadCommand(divineItems));
        this.commands.put("info", new InfoCommand(divineItems));
    }

    public void registerCmd(String string, CommandBase commandBase) {
        this.commands.put(string, commandBase);
    }

    public void unregisterCmd(String string) {
        this.commands.remove(string);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring) {
        CommandBase commandBase = this.help;
        if (arrstring.length > 0 && this.commands.containsKey(arrstring[0])) {
            commandBase = this.commands.get(arrstring[0]);
        }
        commandBase.execute(commandSender, arrstring);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<String> onTabComplete(CommandSender commandSender, Command command, String string, String[] arrstring) {
        if (!(commandSender instanceof Player)) {
            return null;
        }
        if (arrstring.length == 1) {
            List<String> list = this.plugin.getMM().getModuleLabels();
            list.add("help");
            list.add("info");
            list.add("modify");
            list.add("reload");
            list.add("set");
            list.remove("itemhints");
            list.remove("combatlog");
            return list;
        }
        Player player = (Player)commandSender;
        if (arrstring.length <= 1) return null;
        if (arrstring[0].equalsIgnoreCase("gems")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getGemManager().getGemNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getGemManager().getGemNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getGemManager().getGemNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("abilities")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getAbilityManager().getAbilityNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getAbilityManager().getAbilityNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getAbilityManager().getAbilityNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("abyssdust")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getAbyssDustManager().getDustNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getAbyssDustManager().getDustNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getAbyssDustManager().getDustNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 8) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("arrows")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getArrowManager().getArrowNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getArrowManager().getArrowNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getArrowManager().getArrowNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 8) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("buffs")) {
            if (arrstring.length == 2) {
                return Arrays.asList("add", "reset", "resetall");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("add")) {
                    return Utils.getEnumsList(ItemStat.class);
                }
                if (!arrstring[1].equalsIgnoreCase("reset")) return null;
                return Utils.getEnumsList(ItemStat.class);
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("add")) {
                    return Arrays.asList("50");
                }
                if (!arrstring[1].equalsIgnoreCase("reset")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("add")) {
                    return Arrays.asList("60");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length <= 6) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("consumables")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getConsumeManager().getConsumeNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getConsumeManager().getConsumeNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getConsumeManager().getConsumeNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 8) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("enchants")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getEnchantManager().getEnchantNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getEnchantManager().getEnchantNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getEnchantManager().getEnchantNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("identify")) {
            if (arrstring.length == 2) {
                return Arrays.asList("item", "tome", "force");
            }
            if (arrstring.length == 3) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 4) {
                if (arrstring[2].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[2].equalsIgnoreCase("get")) {
                    if (arrstring[1].equalsIgnoreCase("item")) {
                        return this.plugin.getMM().getIdentifyManager().getUINames();
                    }
                    if (!arrstring[1].equalsIgnoreCase("tome")) return null;
                    return this.plugin.getMM().getIdentifyManager().getTomeNames();
                }
                if (!arrstring[2].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[2].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[2].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[2].equalsIgnoreCase("give")) {
                    if (arrstring[1].equalsIgnoreCase("item")) {
                        return this.plugin.getMM().getIdentifyManager().getUINames();
                    }
                    if (!arrstring[1].equalsIgnoreCase("tome")) return null;
                    return this.plugin.getMM().getIdentifyManager().getTomeNames();
                }
                if (!arrstring[2].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[2].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[2].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[2].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[2].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[2].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[2].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[2].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[2].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[2].equalsIgnoreCase("drop")) {
                    if (arrstring[1].equalsIgnoreCase("item")) {
                        return this.plugin.getMM().getIdentifyManager().getUINames();
                    }
                    if (!arrstring[1].equalsIgnoreCase("tome")) return null;
                    return this.plugin.getMM().getIdentifyManager().getTomeNames();
                }
                if (arrstring[2].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[2].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[2].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 9) {
                if (arrstring[2].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[2].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[2].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[2].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 10) return Arrays.asList("");
            if (arrstring[2].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[2].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[2].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[2].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("magicdust")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getMagicDustManager().getDustNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getMagicDustManager().getDustNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getMagicDustManager().getDustNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("repair")) {
            if (arrstring.length == 2) {
                return Arrays.asList("item", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("item")) return null;
                return Arrays.asList("99999");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("item")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("item")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("item")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("item")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 8) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("item")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("runes")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getRuneManager().getRuneNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getRuneManager().getRuneNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getRuneManager().getRuneNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("scrolls")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getScrollManager().getScrollNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getScrollManager().getScrollNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getScrollManager().getScrollNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring[0].equalsIgnoreCase("tiers")) {
            if (arrstring.length == 2) {
                return Arrays.asList("list", "get", "give", "drop");
            }
            if (arrstring.length == 3) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Utils.getWorldNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return this.plugin.getMM().getTierManager().getTierNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 4) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return this.plugin.getMM().getTierManager().getTierNames();
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 5) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 6) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("1");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 7) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return this.plugin.getMM().getTierManager().getTierNames();
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length == 8) {
                if (arrstring[1].equalsIgnoreCase("drop")) {
                    return Arrays.asList("1");
                }
                if (arrstring[1].equalsIgnoreCase("get")) {
                    return Arrays.asList("");
                }
                if (arrstring[1].equalsIgnoreCase("give")) {
                    return Arrays.asList("");
                }
                if (!arrstring[1].equalsIgnoreCase("list")) return null;
                return Arrays.asList("");
            }
            if (arrstring.length != 9) return Arrays.asList("");
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (!arrstring[0].equalsIgnoreCase("sets")) return null;
        if (arrstring.length == 2) {
            return Arrays.asList("list", "get", "give", "drop");
        }
        if (arrstring.length == 3) {
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Utils.getWorldNames();
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return this.plugin.getMM().getSetManager().getSetNames();
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring.length == 4) {
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getX())));
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Utils.getEnumsList(SetManager.PartType.class);
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return this.plugin.getMM().getSetManager().getSetNames();
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring.length == 5) {
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getY())));
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("1");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Utils.getEnumsList(SetManager.PartType.class);
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring.length == 6) {
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Arrays.asList(String.valueOf(Utils.round3(player.getLocation().getZ())));
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("1");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring.length == 7) {
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return this.plugin.getMM().getSetManager().getSetNames();
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring.length == 8) {
            if (arrstring[1].equalsIgnoreCase("drop")) {
                return Utils.getEnumsList(SetManager.PartType.class);
            }
            if (arrstring[1].equalsIgnoreCase("get")) {
                return Arrays.asList("");
            }
            if (arrstring[1].equalsIgnoreCase("give")) {
                return Arrays.asList("");
            }
            if (!arrstring[1].equalsIgnoreCase("list")) return null;
            return Arrays.asList("");
        }
        if (arrstring.length != 9) return Arrays.asList("");
        if (arrstring[1].equalsIgnoreCase("drop")) {
            return Arrays.asList("1");
        }
        if (arrstring[1].equalsIgnoreCase("get")) {
            return Arrays.asList("");
        }
        if (arrstring[1].equalsIgnoreCase("give")) {
            return Arrays.asList("");
        }
        if (!arrstring[1].equalsIgnoreCase("list")) return null;
        return Arrays.asList("");
    }
}

