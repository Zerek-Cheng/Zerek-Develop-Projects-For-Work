/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  cc.zoyn.core.tellraw.Tellraw
 *  lk.vexview.api.VexViewAPI
 *  lk.vexview.gui.VexGui
 *  lk.vexview.gui.components.ButtonFunction
 *  lk.vexview.gui.components.VexButton
 *  lk.vexview.gui.components.VexComponents
 *  lk.vexview.gui.components.VexText
 *  lk.vexview.gui.components.VexTextField
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 */
package cc.kunss.utils;

import cc.kunss.CDKMain;
import cc.kunss.utils.CDKMap;
import cc.kunss.utils.Files;
import cc.zoyn.core.tellraw.Tellraw;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.ButtonFunction;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.VexComponents;
import lk.vexview.gui.components.VexText;
import lk.vexview.gui.components.VexTextField;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Methods {
    public static List<String> CreateCDK(int amout) {
        String data = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        ArrayList<String> cdk = new ArrayList<String>();
        Random random = new Random();
        for (int i = 0; i < amout; ++i) {
            String cdkstr = "";
            for (int ii = 0; ii < 18; ++ii) {
                cdkstr = cdkstr + data.charAt(random.nextInt(52));
            }
            cdk.add(cdkstr);
        }
        return cdk;
    }

    public static List<String> addCDK(String CDK, int amout) {
        List cdk = Files.getCdkdata().getStringList(CDK + ".CDK");
        for (String s : Methods.CreateCDK(amout)) {
            cdk.add(s);
            System.out.println(s);
        }
        return cdk;
    }

    public static void loadCDKMap() {
        CDKMain.getCDK().clear();
        for (String s : Files.getCdkdata().getKeys(false)) {
            CDKMap cdk = new CDKMap();
            cdk.setRename(Files.getCdkdata().getString(s + ".rename"));
            cdk.setCmd(s);
            cdk.setCdk(Files.getCdkdata().getStringList(s + ".CDK"));
            cdk.setMessage(Files.getCdkdata().getString(s + ".Message"));
            CDKMain.getCDK().put(s, cdk);
            Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]:  \u00a7a\u6210\u529f\u52a0\u8f7dCDK\u7ec4: \u00a7f[" + cdk.getRename() + "] --> [" + cdk.getCmd() + "] \u00a7a\u6570\u91cf: \u00a7f" + cdk.getCdk().size());
        }
    }

    public static void getCDKList(CommandSender sender) {
        int size = 1;
        for (String CDK : Files.getCdkdata().getKeys(false)) {
            ArrayList<String> InfoString = new ArrayList<String>();
            InfoString.add("\u00a7aCDK\u6307\u4ee4: \u00a7f" + CDKMain.getCDK().get(CDK).getCmd());
            for (String lore : CDKMain.getCDK().get(CDK).getCdk()) {
                InfoString.add(lore);
            }
            InfoString.add("\u00a7aCDK\u6d88\u606f: \u00a7f" + CDKMain.getCDK().get(CDK).getMessage().replace("&", "\u00a7"));
            new Tellraw("\u00a7a" + size + ". \u00a7e" + CDKMain.getCDK().get(CDK).getRename()).addHover(InfoString).send((Player) sender);
            ++size;
        }
    }

    public static VexText Text() {
        return new VexText(Files.getGuidata().getInt("Text.x"), Files.getGuidata().getInt("Text.y"), Files.getGuidata().getStringList("Text.Message"));
    }

    public static VexButton Button(Player p) {
        return new VexButton(156, Files.getGuidata().getString("Button.name"), Files.getGuidata().getString("Button.URL1"), Files.getGuidata().getString("Button.URL2"), Files.getGuidata().getInt("Button.x"), Files.getGuidata().getInt("Button.y"), Files.getGuidata().getInt("Button.xshow"), Files.getGuidata().getInt("Button.yshow"), player -> VexViewAPI.getTextField((Player) p));
    }

    public static VexTextField TextField() {
        return new VexTextField(Files.getGuidata().getInt("TextField.x"), Files.getGuidata().getInt("TextField.y"), Files.getGuidata().getInt("TextField.width"), Files.getGuidata().getInt("TextField.hight"), Files.getGuidata().getInt("TextField.maxlenght"), Files.getGuidata().getInt("TextField.id"));
    }

    public static void VexGui(Player p) {
        VexGui vexGui = new VexGui(Files.getGuidata().getString("Gui.url"), Files.getGuidata().getInt("Gui.x"), Files.getGuidata().getInt("Gui.y"), Files.getGuidata().getInt("Gui.width"), Files.getGuidata().getInt("Gui.hight"), Files.getGuidata().getInt("Gui.xshow"), Files.getGuidata().getInt("Gui.yshow"), Arrays.asList(new VexComponents[]{Methods.Button(p), Methods.TextField(), Methods.Text()}));
        VexViewAPI.openGui((Player) p, (VexGui) vexGui);
    }

    public static void OPCommand(Player p, String Command2) {
        if (p.isOp()) {
            p.chat(Command2);
            return;
        }
        p.setOp(true);
        p.chat(Command2);
        p.setOp(false);
    }

    public static void RemoveCDK(String CDK1, String CDK2) {
        ArrayList<String> CDKList = new ArrayList<String>();
        for (String ckd : Files.getCdkdata().getStringList(CDK1 + ".CDK")) {
            if (ckd.equalsIgnoreCase(CDK2)) continue;
            CDKList.add(ckd);
        }
        Files.getCdkdata().set(CDK1 + ".CDK", CDKList);
        try {
            Files.getCdkdata().save(new File(CDKMain.getMain().getDataFolder(), "cdk.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Files.Reloadconfig();
    }

    public static void SendMessage(CommandSender sender, String Path2) {
        sender.sendMessage(CDKMain.getMain().getConfig().getString("Message." + Path2).replace("&", "\u00a7"));
    }
}

