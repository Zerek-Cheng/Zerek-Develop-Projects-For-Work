/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package cc.kunss.utils;

import cc.kunss.CDKMain;
import cc.kunss.utils.Methods;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class Files {
    private static File GuiConfig = new File(CDKMain.getMain().getDataFolder(), "Gui.yml");
    private static File CDKConfig = new File(CDKMain.getMain().getDataFolder(), "cdk.yml");
    private static YamlConfiguration cdkdata = YamlConfiguration.loadConfiguration((File)CDKConfig);
    private static YamlConfiguration Guidata = YamlConfiguration.loadConfiguration((File)GuiConfig);

    public static YamlConfiguration getCdkdata() {
        return cdkdata;
    }

    public static void loadconfig() {
        if (!CDKConfig.exists()) {
            CDKMain.getMain().saveResource(CDKConfig.getName(), false);
            Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]:  \u00a7a\u521b\u5efa \u00a7fcdk.yml \u00a7a\u6210\u529f\uff01");
        }
        Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]:  \u00a7a\u8f7d\u5165 \u00a7fcdk.yml \u00a7a\u6210\u529f\uff01");
        cdkdata = YamlConfiguration.loadConfiguration((File)CDKConfig);
        if (!GuiConfig.exists()) {
            CDKMain.getMain().saveResource(GuiConfig.getName(), false);
            Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]:  \u00a7a\u521b\u5efa \u00a7fGui.yml \u00a7a\u6210\u529f\uff01");
        }
        Bukkit.getConsoleSender().sendMessage("\u00a7f[\u00a7aLR-CDK\u00a7f]:  \u00a7a\u8f7d\u5165 \u00a7fGui.yml \u00a7a\u6210\u529f\uff01");
        Guidata = YamlConfiguration.loadConfiguration((File)GuiConfig);
    }

    public static void SaveConfig() {
        try {
            cdkdata.save(CDKConfig);
            Guidata.save(GuiConfig);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Reloadconfig() {
        cdkdata = YamlConfiguration.loadConfiguration((File)CDKConfig);
        Guidata = YamlConfiguration.loadConfiguration((File)GuiConfig);
        Methods.loadCDKMap();
    }

    public static YamlConfiguration getGuidata() {
        return Guidata;
    }
}

