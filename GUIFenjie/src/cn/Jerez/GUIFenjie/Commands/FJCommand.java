// 
// Decompiled by Procyon v0.5.30
// 

package cn.Jerez.GUIFenjie.Commands;

import cn.Jerez.GUIFenjie.Beans.ConfigSetting;
import cn.Jerez.GUIFenjie.Beans.Settings;
import cn.Jerez.GUIFenjie.Main;
import cn.Jerez.Library.Annotation.Command.CommandAnntation;
import cn.Jerez.Library.Utils.Commands.SubCommand;
import cn.Jerez.Library.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class FJCommand extends SubCommand {
    private static boolean listenerSet;
    private static boolean listenerOpen;
    private static boolean listenerResult;

    @CommandAnntation(args = {"编号"}, describe = "创建一个分解项目", needOp = true)
    public void create() {
        final Player p = (Player) this.sender;
        if (this.checkId(this.args, (CommandSender) p)) {
            final String id = this.args[1];
            final Settings set = new Settings();
            set.setId(id);
            if (Main.getEc().getEntity(id) != null) {
                this.send("当前存在[" + id + "]的分解项目!请先进行删除后再试!", (CommandSender) p);
            } else {
                Main.getEc().putEntity(id, set);
                this.send("创建分解项目: [" + id + "§a]成功！", (CommandSender) p);
            }
        }
    }

    @CommandAnntation(args = {"编号"}, describe = "设置分解装备", needOp = true)
    public void zb() {
        final Player p = (Player) this.sender;
        if (this.checkId(this.args, (CommandSender) p)) {
            final String id = this.args[1];
            final Settings set = (Settings) Main.getEc().getEntity(id);
            if (set == null) {
                this.send("抱歉!没有找到对应的分解项目!", (CommandSender) p);
            } else {
                final ItemStack itemInHand = p.getItemInHand();
                if (ItemUtils.isItem(itemInHand)) {
                    set.setFenjieItem(itemInHand);
                    this.send("设置被分解物品成功!", (CommandSender) p);
                } else {
                    this.send("请手上的物品无法进行分解!", (CommandSender) p);
                }
            }
        }
    }

    @CommandAnntation(describe = "重载插件", needAdmin = true)
    public void reload() {
        Main.getInstance().reload();
        this.send("重载成功!", this.sender);
    }

    @CommandAnntation(args = {"编号"}, describe = "设置分解项目的分解材料", needOp = true)
    public void set() {
        final Player p = (Player) this.sender;
        if (this.checkId(this.args, (CommandSender) p)) {
            final Settings judgeSet = (Settings) Main.getEc().getEntity(this.args[1]);
            if (judgeSet == null) {
                this.send("未找到对应的分解项目", (CommandSender) p);
            } else {
                final Inventory meIn = Bukkit.createInventory((InventoryHolder) null, 9, "§b正在设定§7[§e" + this.args[1] + "§7]§b的材料物品");
                if (!FJCommand.listenerSet) {
                    FJCommand.listenerSet = true;
                    Bukkit.getServer().getPluginManager().registerEvents((Listener) new Listener() {
                        @EventHandler
                        public void open(final InventoryOpenEvent e) {
                            final Player player = (Player) e.getPlayer();
                            final Inventory opIn = e.getInventory();
                            final String title = opIn.getTitle();
                            if (title != null && title.startsWith("§b正在设定§7[§e") && title.endsWith("§7]§b的材料物品")) {
                                final String id = title.replace("§b正在设定§7[§e", "").replace("§7]§b的材料物品", "");
                                final Settings set = (Settings) Main.getEc().getEntity(id);
                                final List<ItemStack> materialItems = set.getMaterialItems();
                                if (materialItems != null && materialItems.size() > 0) {
                                    opIn.addItem((ItemStack[]) materialItems.toArray(new ItemStack[0]));
                                }
                            }
                        }

                        @EventHandler
                        public void close(final InventoryCloseEvent e) {
                            final Inventory closeIn = e.getInventory();
                            final String title = closeIn.getTitle();
                            if (title != null && title.startsWith("§b正在设定§7[§e") && title.endsWith("§7]§b的材料物品")) {
                                final String id = title.replace("§b正在设定§7[§e", "").replace("§7]§b的材料物品", "");
                                final Settings set = (Settings) Main.getEc().getEntity(id);
                                final List<ItemStack> meList = new ArrayList<ItemStack>();
                                for (int i = 0; i < closeIn.getSize(); ++i) {
                                    final ItemStack inItem = closeIn.getItem(i);
                                    if (inItem != null) {
                                        meList.add(inItem);
                                    }
                                }
                                set.setMaterialItems(meList);
                                FJCommand.this.send("保存分解项目[" + FJCommand.this.args[1] + "]成功!", (CommandSender) p);
                            }
                        }
                    }, (Plugin) Main.getInstance());
                }
                p.openInventory(meIn);
            }
        }
    }

    @CommandAnntation(args = {"编号"}, describe = "删除某个分解项", needOp = true)
    public void remove() {
        final Player p = (Player) this.sender;
        if (this.checkId(this.args, (CommandSender) p)) {
            final String id = this.args[1];
            if (Main.getEc().getEntity(id) != null) {
                Main.getEc().remove(id);
                this.send("删除分解项目成功!", (CommandSender) p);
            } else {
                this.send("没有找到分解项目[" + id + "]", (CommandSender) p);
            }
        }
    }

    @CommandAnntation(describe = "列出所有分解项目", needAdmin = true, needOp = true)
    public void list() {
        final Set<Map.Entry<String, Settings>> entrySet = (Set<Map.Entry<String, Settings>>) Main.getEc().getEntrySet();
        if (entrySet != null && entrySet.size() > 0) {
            this.send("当前可以分解的项目有:", this.sender);
            for (final Map.Entry<String, Settings> entry : entrySet) {
                this.send("  §b" + entry.getKey(), this.sender);
            }
        } else {
            this.send("抱歉!目前没有任何分解项目!快创建吧!", this.sender);
        }
    }

    @CommandAnntation(describe = "开始分解某项目", canPlayer = true, canConsole = false)
    public void open() {
        final Player p = (Player) this.sender;
        final Inventory opIn = Bukkit.createInventory((InventoryHolder) null, 9, "§c分解项目");
        final boolean fenjieSlot = true;
        final boolean startFenjieSlot = true;
        final ItemStack redItem = new ItemStack(160, 1, (short) 14);
        ItemUtils.setItemDisplayName(redItem, "§不§能§点§c隔离板");
        for (int i = 0; i < 9; ++i) {
            if (i != 2 || i != 6) {
                opIn.setItem(i, redItem);
            }
        }
        opIn.setItem(2, (ItemStack) null);
        final ItemStack startItem = new ItemStack(327);
        ItemUtils.setItemDisplayName(startItem, "§不§能§点§c点击开始分解!");
        opIn.setItem(6, startItem);
        if (!FJCommand.listenerOpen) {
            FJCommand.listenerOpen = true;
            Bukkit.getServer().getPluginManager().registerEvents((Listener) new Listener() {
                @EventHandler
                public void click(final InventoryClickEvent e) {
                    final Inventory clickIn = e.getInventory();
                    final Player cPlayer = (Player) e.getWhoClicked();
                    final String title = clickIn.getTitle();
                    if (title != null && title.startsWith("§c分解项目")) {
                        try {
                            final ItemStack currentItem = e.getCurrentItem();
                            if (currentItem.getItemMeta().getDisplayName().startsWith("§不§能§点")) {
                                e.setCancelled(true);
                            }
                            if (e.getRawSlot() == 6) {
                                final ItemStack fenjieItem = clickIn.getItem(2);
                                boolean canFenjie = false;
                                Settings set = null;
                                final Set<Map.Entry<String, Settings>> entrySet = (Set<Map.Entry<String, Settings>>) Main.getEc().getEntrySet();
                                if (entrySet != null && entrySet.size() > 0) {
                                    for (final Map.Entry<String, Settings> en : entrySet) {
                                        final Settings enV = en.getValue();
                                        if (enV != null) {
                                            try {
                                                if (enV.getFenjieItem().getItemMeta().getDisplayName().equals(fenjieItem.getItemMeta().getDisplayName())) {
                                                    canFenjie = true;
                                                    set = enV;
                                                    break;
                                                }
                                                continue;
                                            } catch (Exception ex) {
                                            }
                                        }
                                    }
                                }
                                if (canFenjie && set != null) {
                                    try {
                                        ItemUtils.subItemAmount(fenjieItem, 1);
                                    } catch (ItemUtils.EmptyAmountException var11) {
                                        clickIn.setItem(2, (ItemStack) null);
                                    }
                                    if (!FJCommand.listenerResult) {
                                        FJCommand.listenerResult = true;
                                        Bukkit.getServer().getPluginManager().registerEvents((Listener) new Listener() {
                                            @EventHandler
                                            public void close(final InventoryCloseEvent e) {
                                                final Inventory ci = e.getInventory();
                                                final Player cp = (Player) e.getPlayer();
                                                if ("请拿走您的分解物品!".equalsIgnoreCase(ci.getTitle())) {
                                                    for (int i = 0; i < ci.getSize(); ++i) {
                                                        final ItemStack it = ci.getItem(i);
                                                        if (it != null) {
                                                            cp.getInventory().addItem(new ItemStack[]{it});
                                                        }
                                                    }
                                                }
                                            }
                                        }, (Plugin) Main.getInstance());
                                    }
                                    final ConfigSetting configSetting = Main.getCONFIG_CACHE().get(set.getId());
                                    if (configSetting != null) {
                                        final String msg = configSetting.getMsg();
                                        final List<String> cmds = configSetting.getCmds();
                                        if (msg != null && !"".equals(msg)) {
                                            p.sendMessage(msg.replaceAll("%p%", p.getName()).replaceAll("&", "§"));
                                        }
                                        if (cmds != null && cmds.size() > 0) {
                                            final String cmd = FJCommand.this.getCmds(cmds);
                                            if (cmd != null) {
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd.replaceAll("%p%", p.getName()).replaceAll("&", "§"));
                                            }
                                        }
                                    }
                                    final Inventory reIn = Bukkit.createInventory((InventoryHolder) null, 54, "请拿走您的分解物品!");
                                    reIn.addItem((ItemStack[]) set.getMaterialItems().toArray(new ItemStack[0]));
                                    cPlayer.closeInventory();
                                    cPlayer.openInventory(reIn);
                                } else {
                                    FJCommand.this.send("分解物品不正确!", (CommandSender) cPlayer);
                                }
                            }
                        } catch (Exception ex2) {
                        }
                    }
                }

                @EventHandler
                public void close(final InventoryCloseEvent e) {
                    final Player closePlayer = (Player) e.getPlayer();
                    final Inventory closeIn = e.getInventory();
                    final String title = closeIn.getTitle();
                    if (title != null && title.startsWith("§c分解项目")) {
                        final ItemStack fjItem = closeIn.getItem(2);
                        if (fjItem != null) {
                            closePlayer.getInventory().addItem(new ItemStack[]{fjItem});
                        }
                    }
                }
            }, (Plugin) Main.getInstance());
        }
        p.openInventory(opIn);
    }

    private String getCmds(final List<String> cmds) {
        final List<String> results = new ArrayList<String>();
        final int randomNum = new Random().nextInt(100);
        for (final String cmd : cmds) {
            if (!cmd.contains(":")) {
                System.out.println("cmd: " + cmd + "  格式错误!");
                return null;
            }
            final String[] cmdInfo = cmd.split(":");
            final double cmdJL = Double.parseDouble(cmdInfo[1]);
            if (cmdJL < randomNum) {
                continue;
            }
            results.add(cmdInfo[0]);
        }
        if (results.size() == 1) {
            return results.get(0);
        }
        if (results.size() > 1) {
            return results.get(new Random().nextInt(results.size()));
        }
        return this.getCmds(cmds);
    }

    private boolean checkId(final String[] args, final CommandSender s) {
        if (args.length < 2) {
            this.send("请输入编号!", s);
            return false;
        }
        return true;
    }

    private void send(final String str, final CommandSender s) {
        s.sendMessage("§7[§d系统§7]:§a " + str);
    }

    static {
        FJCommand.listenerSet = false;
        FJCommand.listenerOpen = false;
        FJCommand.listenerResult = false;
    }
}
