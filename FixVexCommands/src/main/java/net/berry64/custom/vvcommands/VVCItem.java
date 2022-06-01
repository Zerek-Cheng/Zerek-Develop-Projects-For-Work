// 
// Decompiled by Procyon v0.5.30
// 

package net.berry64.custom.vvcommands;

import lk.vexview.VexView;
import lk.vexview.api.VexViewAPI;
import lk.vexview.event.ButtonClickEvent;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexText;
import lk.vexview.gui.components.VexTextField;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VVCItem implements Listener {
    private static VVCMain pl;
    private String command;
    private String nbutton;
    private String hbutton;
    private String tbutton;
    private String bgurl;
    private String complete;
    private String repl;
    private int bgw;
    private int bgh;
    private int btnw;
    private int btnh;
    private int txtid;
    private int btnid;
    private int maxchar;
    private List<String> title;
    private List<String> subtitle;
    private File file;
    private YamlConfiguration yml;
    private ItemStack item;
    private VexGui gui;
    private List<Player> opened;
    private boolean require;

    public VVCItem(final File f) throws UnsupportedEncodingException, FileNotFoundException {
        this.file = f;
        this.load(this.yml = YamlConfiguration.loadConfiguration((Reader) new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8"))));
        VVCItem.pl = VVCMain.instance;
    }

    public void load(final YamlConfiguration yml) {
        this.opened = new ArrayList<Player>();
        this.item = yml.getItemStack("item");
        this.require = yml.getBoolean("require", false);
        this.title = (List<String>) yml.getStringList("titles.title");
        this.subtitle = (List<String>) yml.getStringList("titles.subtitle");
        this.bgw = yml.getInt("bg.width");
        this.bgh = yml.getInt("bg.height");
        this.btnw = yml.getInt("button.width");
        this.btnh = yml.getInt("button.height");
        this.txtid = yml.getInt("txt.id");
        this.maxchar = yml.getInt("txt.char");
        this.btnid = yml.getInt("button.id");
        this.command = yml.getString("command");
        this.repl = yml.getString("button.char");
        this.complete = yml.getString("complete").replace('&', '§');
        this.tbutton = yml.getString("button.text").replace('&', '§');
        this.nbutton = yml.getString("button.normal");
        this.hbutton = yml.getString("button.hover");
        this.bgurl = yml.getString("bg.url");
        this.constructGUI();
    }

    public void constructGUI() {
        final VexText title = new VexText(yml.getInt("titles.x1", -1), yml.getInt("titles.y1", 30), (List) this.title);
        final VexText notif = new VexText(yml.getInt("titles.x2", -1), yml.getInt("titles.y2", 160), (List) this.subtitle);
        final VexTextField field = new VexTextField(yml.getInt("txt.x"), yml.getInt("txt.y"), yml.getInt("txt.w"), this.btnh, this.maxchar, this.txtid);
        final VexButton okbutton = new VexButton(this.btnid, this.tbutton, this.nbutton, this.hbutton, yml.getInt("button.x", 350), yml.getInt("button.y", 160), this.btnw, this.btnh, pl -> {
            //  VexViewAPI.getTextField(pl);
            pl.closeInventory();
        });
        this.gui = new VexGui(this.bgurl, -1, -1, this.bgw, this.bgh, this.bgw, this.bgh, (List) Arrays.asList(title, notif, field, okbutton));
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void openGui(final Player p) {
        if (this.gui == null) {
            this.constructGUI();
        }
        this.opened.add(p);
        VexViewAPI.openGui(p, this.gui);
    }
/*
    @EventHandler
    public void onTextFieldRecieved(TextFieldGetEvent evt) {
        if (evt.getID() == this.txtid && this.opened.contains(evt.getPlayer())) {
            this.opened.remove(evt.getPlayer());
            final String message = evt.getText();
            boolean itemhas = false;
            if (this.require) {
                for (int i = 0; i < evt.getPlayer().getInventory().getSize(); ++i) {
                    final ItemStack item = evt.getPlayer().getInventory().getItem(i);
                    if (item != null) {
                        if (item.isSimilar(this.item)) {
                            if (itemhas) {
                                break;
                            }
                            itemhas = true;
                            if (item.getAmount() >= this.item.getAmount()) {
                                if (item.getAmount() == this.item.getAmount()) {
                                    evt.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
                                } else {
                                    item.setAmount(item.getAmount() - this.item.getAmount());
                                }
                                evt.getPlayer().updateInventory();
                                break;
                            }
                        }
                    }
                }
            }
            if (itemhas || !this.require) {
                if (this.command.startsWith("console:")) {
                    VVCItem.pl.getServer().dispatchCommand((CommandSender) VVCItem.pl.getServer().getConsoleSender(), this.command.substring(8).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                } else if (this.command.startsWith("player:")) {
                    VVCItem.pl.getServer().dispatchCommand((CommandSender) evt.getPlayer(), this.command.substring(7).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                } else {
                    if (!this.command.startsWith("op:")) {
                        evt.getPlayer().sendMessage("§c\u6307\u4ee4\u9519\u8bef\uff0c\u8bf7\u8054\u7cfb\u670d\u52a1\u5668\u7ba1\u7406\u5458");
                        return;
                    }
                    final Player p = evt.getPlayer();
                    if (!p.isOp()) {
                        p.setOp(true);
                        VVCItem.pl.getServer().dispatchCommand((CommandSender) p, this.command.substring(3).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                        p.setOp(false);
                    } else {
                        VVCItem.pl.getServer().dispatchCommand((CommandSender) p, this.command.substring(3).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                    }
                }
                evt.getPlayer().sendMessage(this.complete);
            } else {
                evt.getPlayer().sendMessage("§c\u65e0\u6cd5\u627e\u5230\u7269\u54c1, \u64cd\u4f5c\u5931\u8d25 ");
            }
        }
    }*/


    @EventHandler
    public void onClick(ButtonClickEvent evt) {

        if (evt.getButtonID() == this.btnid && this.opened.contains(evt.getPlayer())) {
            this.opened.remove(evt.getPlayer());
            String message = "";
            for (VexComponents vc :
                    new ArrayList<>(VexViewAPI.getPlayerCurrentGui(evt.getPlayer()).getVexGui().getComponents())) {
                if (!(vc instanceof VexTextField)) continue;
                VexTextField f = (VexTextField) vc;
                message = f.getTypedText();

            }


            boolean itemhas = false;
            if (this.require) {
                for (int i = 0; i < evt.getPlayer().getInventory().getSize(); ++i) {
                    final ItemStack item = evt.getPlayer().getInventory().getItem(i);
                    if (item != null) {
                        if (item.isSimilar(this.item)) {
                            if (itemhas) {
                                break;
                            }
                            itemhas = true;
                            if (item.getAmount() >= this.item.getAmount()) {
                                if (item.getAmount() == this.item.getAmount()) {
                                    evt.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
                                } else {
                                    item.setAmount(item.getAmount() - this.item.getAmount());
                                }
                                evt.getPlayer().updateInventory();
                                break;
                            }
                        }
                    }
                }
            }
            if (itemhas || !this.require) {
                if (this.command.startsWith("console:")) {
                    VVCItem.pl.getServer().dispatchCommand((CommandSender) VVCItem.pl.getServer().getConsoleSender(), this.command.substring(8).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                } else if (this.command.startsWith("player:")) {
                    VVCItem.pl.getServer().dispatchCommand((CommandSender) evt.getPlayer(), this.command.substring(7).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                } else {
                    if (!this.command.startsWith("op:")) {
                        evt.getPlayer().sendMessage("§c\u6307\u4ee4\u9519\u8bef\uff0c\u8bf7\u8054\u7cfb\u670d\u52a1\u5668\u7ba1\u7406\u5458");
                        return;
                    }
                    final Player p = evt.getPlayer();
                    if (!p.isOp()) {
                        p.setOp(true);
                        VVCItem.pl.getServer().dispatchCommand((CommandSender) p, this.command.substring(3).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                        p.setOp(false);
                    } else {
                        VVCItem.pl.getServer().dispatchCommand((CommandSender) p, this.command.substring(3).replace("%player%", evt.getPlayer().getName()).replace("%message%", message.replace(" ", this.repl)));
                    }
                }
                evt.getPlayer().sendMessage(this.complete);
            } else {
                evt.getPlayer().sendMessage("§c\u65e0\u6cd5\u627e\u5230\u7269\u54c1, \u64cd\u4f5c\u5931\u8d25 ");
            }
            evt.getPlayer().closeInventory();
        }
    }

    @EventHandler
    public void onLogout(final PlayerQuitEvent evt) {
        this.opened.remove(evt.getPlayer());
    }

    public void save() {
        try {
            this.yml.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(final String key, final Object item) {
        this.yml.set(key, item);
    }
}
