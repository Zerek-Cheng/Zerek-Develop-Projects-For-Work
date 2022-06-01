/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.pedrojm96.supercredits.SuperCredits
 *  net.milkbowl.vault.economy.Economy
 *  org.black_ixx.playerpoints.PlayerPoints
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.pedrojm96.referralsystem;

import com.pedrojm96.referralsystem.Storage.Data;
import com.pedrojm96.referralsystem.subcommands.cmsAddPoints;
import com.pedrojm96.referralsystem.subcommands.cmsClaim;
import com.pedrojm96.referralsystem.subcommands.cmsCode;
import com.pedrojm96.referralsystem.subcommands.cmsHelp;
import com.pedrojm96.referralsystem.subcommands.cmsInfo;
import com.pedrojm96.referralsystem.subcommands.cmsList;
import com.pedrojm96.referralsystem.subcommands.cmsNumb;
import com.pedrojm96.referralsystem.subcommands.cmsReload;
import com.pedrojm96.referralsystem.subcommands.cmsSetPoints;
import com.pedrojm96.referralsystem.subcommands.cmsTop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ReferralSystem
extends JavaPlugin {
    public static ReferralSystem instance;
    public static ConfigManager config;
    public static ConfigManager messages;
    public static ConfigManager claim;
    public static boolean econ;
    public static boolean poin;
    public static boolean cred;
    public static Economy economy;
    public static PlayerPoints playerPoints;
    public static Object supercredits;
    public static boolean externalpoints;
    public static boolean externalcred;
    public static boolean externalecon;
    public static boolean Placeholder;
    public static Data data;
    Integer DD;
    public static Map<UUID, LocalData> localdata;

    static {
        localdata = new HashMap<UUID, LocalData>();
    }

    public void onEnable() {
        //ReferralSystem.loadConfig0();
        instance = this;
        this.getCommand("Referral").setExecutor((CommandExecutor)new CommandReferral());
        CommandReferral.addsubcommand(Arrays.asList("help", "ayuda"), new cmsHelp());
        CommandReferral.addsubcommand(Arrays.asList("claim", "reclamar"), new cmsClaim("referral.claim"));
        CommandReferral.addsubcommand(Arrays.asList("code", "codigo"), new cmsCode("referral.code"));
        CommandReferral.addsubcommand(Arrays.asList("list", "lista"), new cmsList("referral.list"));
        CommandReferral.addsubcommand(Arrays.asList("numb"), new cmsNumb());
        CommandReferral.addsubcommand(Arrays.asList("reload", "recargar"), new cmsReload("referral.admin"));
        CommandReferral.addsubcommand(Arrays.asList("info", "stats"), new cmsInfo("referral.info"));
        CommandReferral.addsubcommand(Arrays.asList("top"), new cmsTop("referral.top"));
        CommandReferral.addsubcommand(Arrays.asList("addpoints"), new cmsAddPoints("referral.admin"));
        CommandReferral.addsubcommand(Arrays.asList("setpoints"), new cmsSetPoints("referral.admin"));
        PluginManager pluginManager = this.getServer().getPluginManager();
        EvenListener evenListener = new EvenListener();
        pluginManager.registerEvents((Listener)evenListener, (Plugin)this);
        rLog.line("&e-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        rLog.info("&7Plugin Create by PedroJM96.");
        rLog.info("&7Loading configuration...");
        this.iniConfig();
        this.loadMessages();
        this.iniClaim();
        if (ReferralSystem.setupEconomy()) {
            ReferralSystem.hasValidEconomy();
            rLog.info("&7Hooked Vault");
        }
        if (ReferralSystem.setupPlugin()) {
            ReferralSystem.hasValidPlayerPoints();
            rLog.info("&7Hooked PlayerPoints");
        }
        if (ReferralSystem.setupCredits()) {
            ReferralSystem.hasValidSuperCredits();
            rLog.info("&7Hooked SuperCredits");
        }
        if (config.getBoolean("UseExternalPoints.enable")) {
            if (config.getBoolean("UseExternalPoints.SuperCredits")) {
                if (cred) {
                    externalcred = true;
                    rLog.info("&8Found SuperCredits! The option to use SuperCredits is enabled, due to that the plugin will use SuperCredits instead of Points");
                } else {
                    rLog.info("&cThe option to use SuperCredits is enabled, but SuperCredits doesn't seem to be installed! due to that the plugin will continue using Points system");
                }
            } else if (config.getBoolean("UseExternalPoints.PlayerPoints")) {
                if (poin) {
                    externalpoints = true;
                    rLog.info("&8Found PlayerPoints! The option to use PlayerPoints is enabled, due to that the plugin will use PlayerPoints instead of Points");
                } else {
                    rLog.info("&cThe option to use PlayerPoints is enabled, but PlayerPoints doesn't seem to be installed! due to that the plugin will continue using Points system");
                }
            } else if (config.getBoolean("UseExternalPoints.Vault")) {
                if (econ) {
                    externalecon = true;
                    rLog.info("&8Found Vault! The option to use Vault is enabled, due to that the plugin will use Vault instead of Points");
                } else {
                    rLog.info("&cThe option to use Vault is enabled, but Vault doesn't seem to be installed! due to that the plugin will continue using Points system");
                }
            }
        }
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new RFPlaceholder(this).hook();
            Placeholder = true;
            rLog.info("&7Hooked PlaceholderAPI");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("LeaderHeads")) {
            new com.pedrojm96.referralsystem.ReferralPoints();
            new com.pedrojm96.referralsystem.Referrals();
            rLog.info("&7Hooked LeaderHeads");
        }
        data = new Data();
        this.checkForUpdates();
        rLog.line("&e-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    public void checkForUpdates() {/*
        if (config.getBoolean("Update-Check")) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("http://www.spigotmc.org/api/general.php").openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.getOutputStream().write("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=29709".getBytes("UTF-8"));
                String string = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();
                if (string.length() <= 7) {
                    if (string.equalsIgnoreCase(this.getDescription().getVersion())) {
                        rLog.info("Plugin is up to date.");
                    } else {
                        rLog.info("&cThere is a resource update avaliable for Referral System. Please update to recieve latest version.");
                        rLog.info("&bhttps://www.spigotmc.org/resources/referralsystem.29709/");
                    }
                }
            }
            catch (Exception exception) {
                rLog.info("&cFailed to check for a update on spigot.");
            }
        }*/
    }

    public static boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider registeredServiceProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (registeredServiceProvider == null) {
            return false;
        }
        economy = (Economy)registeredServiceProvider.getProvider();
        if (economy != null) {
            return true;
        }
        return false;
    }

    public static void hasValidEconomy() {
        econ = economy != null;
    }

    public static void hasValidPlayerPoints() {
        poin = playerPoints != null;
    }

    public static void hasValidSuperCredits() {
        cred = supercredits != null;
    }

    public static boolean setupPlugin() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("PlayerPoints");
        if (plugin == null) {
            return false;
        }
        playerPoints = (PlayerPoints)plugin;
        return true;
    }

    public static boolean setupCredits() {/*
        Plugin plugin = Bukkit.getPluginManager().getPlugin("SuperCredits");
        if (plugin == null) {
            return false;
        }
        supercredits = (SuperCredits)plugin;*/
        return false;
    }

    /*
     * Exception decompiling
     */
    public void loadMessages() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:416)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:379)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:140)
        // org.benf.cfr.reader.Main.main(Main.java:241)
        throw new IllegalStateException("Decompilation failed");
    }

    public void iniMessagesEN() {
        messages = new ConfigManager("messages_EN");
        if (messages.Exists()) {
            this.configMessagesEN();
            messages.Load();
        } else {
            this.configMessagesEN();
            messages.Save();
        }
    }

    public void iniMessagesES() {
        messages = new ConfigManager("messages_ES");
        if (messages.Exists()) {
            this.configMessagesES();
            messages.Load();
        } else {
            this.configMessagesES();
            messages.Save();
        }
    }

    public void configdata() {
        config.add("Update-Check", true);
        config.add("Messages", "EN");
        config.add("Points-Reward-Player", 20);
        config.add("Points-Reward-Referrer", 5);
        config.add("Limit-Player-IP", true);
        config.add("Max-Player-IP", 5);
        config.add("Fireworks-Enable", true);
        config.add("Particles-Enable", true);
        config.add("Sounds-Enable", true);
        config.add("Menu-Claim", true);
        config.add("Requires-PlayTime", true);
        config.add("Min-PlayTime", 500);
        config.add("UseExternalPoints.enable", false);
        config.add("UseExternalPoints.SuperCredits", false);
        config.add("UseExternalPoints.PlayerPoints", false);
        config.add("UseExternalPoints.Vault", false);
        config.add("dataStorage.type", "SQLite");
        config.add("dataStorage.host", "localhost");
        config.add("dataStorage.port", 3306);
        config.add("dataStorage.database", "minecraft");
        config.add("dataStorage.username", "root");
        config.add("dataStorage.password", "1234");
    }

    public void configMessagesEN() {
        messages.add("No-Console", "&bNot available in the console.");
        messages.add("No-Permission", "&7You not have permission to use this command.");
        messages.add("Claim-No-Permission", "&7You do not have permission to claim this.");
        messages.add("Claim-No-Points", "&7You do not have enough points to claim this.");
        messages.add("No-Claim", "&7Claim system is disabled.");
        messages.add("Command-Use", "&7Use &e/Referral help");
        messages.add("Reward-Given", "&7Your reward been given");
        messages.add("Command-AddPoints-Use", "&7Use &c/Referral addpoints <player> <value>");
        messages.add("Command-SetPoints-Use", "&7Use &c/Referral setpoints <player> <value>");
        messages.add("Command-Error", "&7Invalid argument");
        messages.add("No-Register", "&7The player is not registered in the database.");
        messages.add("No-Numb", "&7Enter a valid number.");
        messages.add("Points-Added", "&6<points> &7points have been added to the player &6<player>");
        messages.add("Points-Set", "&6<points> &7points have been set to the player &6<player>");
        messages.add("Description-Use", "&7To view the basic commands");
        messages.add("Help-CommandList", "&7Lista de comandos:");
        messages.add("Help-Command-Help", "&e/Referral help");
        messages.add("Help-Description-Help", "&7Show the basic commands for this plugin!");
        messages.add("Help-Command-Code", "&e/Referral code");
        messages.add("Help-Description-Code", "&7Generate your referral code!");
        messages.add("Help-Command-Claim", "&e/Referral claim");
        messages.add("Help-Description-Claim", "&7Use this to claim rewards that you would get when someone has used your code.");
        messages.add("Help-Command-Info", "&e/Referral info");
        messages.add("Help-Description-Info", "&7View your referral information.");
        messages.add("Help-Command-List", "&e/Referral list");
        messages.add("Help-Description-List", "&7View your referral list.");
        messages.add("Help-Command-Top", "&e/Referral top");
        messages.add("Help-Description-Top", "&7It shows the best players with more referrals!.");
        messages.add("Help-Command-AddPoints", "&e/Referral addpoints");
        messages.add("Help-Description-AddPoints", "&7Adds points to a registered player in the database.");
        messages.add("Help-Command-SetPoints", "&e/Referral setpoints");
        messages.add("Help-Description-SetPoints", "&7Set points to a registered player in the database.");
        messages.add("Help-Command-Activate", "&e/Referral <number>");
        messages.add("Help-Description-Activate", "&7 Use this to activate referral code!");
        messages.add("Help-Command-Reload", "&e/Referral reload");
        messages.add("Help-Description-Reload", "&7Reload the configuration and messages file.");
        messages.add("Top-Player", "&7Top Players");
        messages.add("Generating-Code", "&7Generating your referral code...");
        messages.add("Max-IP-Limit", "&7You can not refer more than <numb> players with the same IP");
        messages.add("Your-Code", "&7Your referral code is:");
        messages.add("Your-Referrals", "&7Your Referrals:");
        messages.add("Your-Referral-Points", "&7Your Referral Points:");
        messages.add("Error-Code", "&7Code is not valid!");
        messages.add("No-Use-Your-Code", "&7You can not use your referral code!");
        messages.add("Checking-Code", "&7Checking your code!");
        messages.add("Activate-Code", "&7Code activate! You and the referrer will get a reward now!");
        messages.add("Reward-Player", "&7You get &6<points> &7referral points for using refercode!");
        messages.add("Reward-Referrer", "&7The player &b<player> &7use your referral code, You get &6<points> &7referral points!");
        messages.add("Already-Used", "&7You have already used a referral code!");
        messages.add("Requires-PlayTime", "&7You need a minimum time played of &6<time> &7in order to use a referral code.");
        messages.add("PlayTime", "&7PlayTime: &6<time>");
        messages.add("MenuList", "&6List of referrals");
        messages.add("MenuList-Name", "&a<top># &6<player>");
        messages.add("MenuList-Lore", Arrays.asList("&7--------------", "&aReferral: &f<referral>", "&aPoints: &f<points>", "&7--------------"));
        messages.add("MenuList-Next", "&aNext <page>");
        messages.add("MenuList-Back", "&aBack <page>");
    }

    public void configMessagesES() {
        messages.add("No-Console", "&bNo disponible en la consola.");
        messages.add("No-Permission", "&7No tienes permiso para usar este comando.");
        messages.add("Claim-No-Permission", "&7No tiene permiso para reclamar esto.");
        messages.add("Claim-No-Points", "&7No tienes suficientes puntos para reclamar esto.");
        messages.add("No-Claim", "&7El sistema de reclamaciones est\u00e1 deshabilitado.");
        messages.add("Command-Use", "&7Usa &e/Referido ayuda");
        messages.add("Reward-Given", "&7Su recompensa ha sido dada");
        messages.add("Command-AddPoints-Use", "&7Usa &c/Referido addpoints <player> <value>");
        messages.add("Command-SetPoints-Use", "&7Usa &c/Referido setpoints <player> <value>");
        messages.add("Command-Error", "&7Argumento no v\u00e1lido");
        messages.add("No-Register", "&7El jugador no est\u00e1 registrado en la base de datos.");
        messages.add("No-Numb", "&7Ingrese un n\u00famero v\u00e1lido.");
        messages.add("Points-Added", "&6<points> &7puntos se han a\u00f1adido al jugador &6<player>");
        messages.add("Points-Set", "&6<points> &7puntos se han establecido  al jugador &6<player>");
        messages.add("Description-Use", "&7Para ver los comandos b\u00e1sicos");
        messages.add("Help-CommandList", "&7Lista de comandos:");
        messages.add("Help-Command-Help", "&e/Referido ayuda");
        messages.add("Help-Description-Help", "&7Muestra los comandos b\u00e1sicos para este plugin!");
        messages.add("Help-Command-Code", "&e/Referido codigo");
        messages.add("Help-Description-Code", "&7\u00a1Genere su c\u00f3digo de referencia!");
        messages.add("Help-Command-Claim", "&e/Referido reclamar");
        messages.add("Help-Description-Claim", "&7Use esto para reclamar recompensas que obtendr\u00eda cuando alguien haya usado su c\u00f3digo.");
        messages.add("Help-Command-Info", "&e/Referido info");
        messages.add("Help-Description-Info", "&7Vea su informaci\u00f3n de referencia.");
        messages.add("Help-Command-Top", "&e/Referido top");
        messages.add("Help-Description-Top", "&7Muestra los mejores jugadores con m\u00e1s referencias !");
        messages.add("Help-Command-AddPoints", "&e/Referido addpoints");
        messages.add("Help-Description-AddPoints", "&7Agrega puntos a un jugador registrado en la base de datos.");
        messages.add("Help-Command-SetPoints", "&e/Referido setpoints");
        messages.add("Help-Description-SetPoints", "&7Establece puntos a un jugador registrado en la base de datos.");
        messages.add("Help-Command-Activate", "&e/Referido <number>");
        messages.add("Help-Description-Activate", "&7 \u00a1Util\u00edcelo para activar el c\u00f3digo de referencia!");
        messages.add("Help-Command-Reload", "&e/Referido reload");
        messages.add("Help-Description-Reload", "&7Vuelva a cargar el archivo de configuraci\u00f3n y mensajes.");
        messages.add("Top-Player", "&7Mejores Jugadores");
        messages.add("Generating-Code", "&7Generando su c\u00f3digo de referencia...");
        messages.add("Max-IP-Limit", "&7No puede referirse a m\u00e1s de <numb> jugadores con el mismo IP");
        messages.add("Your-Code", "&7Su c\u00f3digo de referencia es:");
        messages.add("Your-Referrals", "&7Sus referencias:");
        messages.add("Your-Referral-Points", "&7Tus Puntos de Referencia:");
        messages.add("Error-Code", "&7\u00a1El c\u00f3digo no es v\u00e1lido!");
        messages.add("No-Use-Your-Code", "&7\u00a1No puedes usar tu c\u00f3digo de referencia!");
        messages.add("Checking-Code", "&7Comprobando su c\u00f3digo!");
        messages.add("Activate-Code", "&7C\u00f3digo de activado! \u00a1Usted y el referente recibir\u00e1n una recompensa ahora!");
        messages.add("Reward-Player", "&7Usted obtiene &6<points> &7puntos de referencia por usar refercode!");
        messages.add("Reward-Referrer", "&7El jugador &b<player> &7Utilizo su c\u00f3digo de referencia, se obtiene &6<points> &7puntos de referido!");
        messages.add("Already-Used", "&7\u00a1Ya has utilizado un c\u00f3digo de referencia!");
        messages.add("Requires-PlayTime", "&7Necesitas un tiempo m\u00ednimo de &6<time> &7de juego para usar un c\u00f3digo de referencia.");
        messages.add("PlayTime", "&7Tiempo Jugado: &6<time>");
        messages.add("MenuList", "&6Lista de referidos");
        messages.add("MenuList-Lore", Arrays.asList("&7--------------", "&aReferral: &f<referral>", "&aPoints: &f<points>", "&7--------------"));
        messages.add("MenuList-Next", "&aSiguiente <page>");
        messages.add("MenuList-Back", "&aAtras <page>");
        messages.add("MenuList-Name", "&a<top># &6<player>");
        messages.add("Help-Command-List", "&e/Referral list");
        messages.add("Help-Description-List", "&7Mira tu lista de referidos.");
    }

    public void configclaim() {
        claim.add("settings-name", "&6&k..&r &5&lClaim Reward &6&k..");
        claim.add("settings-rows", 3);
        claim.add("items.A1.name", " ");
        claim.add("items.A1.id", 160);
        claim.add("items.A1.data-value", 15);
        claim.add("items.A1.slot", 1);
        claim.add("items.A1.keep-open", true);
        claim.add("items.A2.name", " ");
        claim.add("items.A2.id", 160);
        claim.add("items.A2.data-value", 15);
        claim.add("items.A2.slot", 2);
        claim.add("items.A2.keep-open", true);
        claim.add("items.A3.name", " ");
        claim.add("items.A3.id", 160);
        claim.add("items.A3.data-value", 15);
        claim.add("items.A3.slot", 3);
        claim.add("items.A3.keep-open", true);
        claim.add("items.A4.name", " ");
        claim.add("items.A4.id", 160);
        claim.add("items.A4.data-value", 15);
        claim.add("items.A4.slot", 4);
        claim.add("items.A4.keep-open", true);
        claim.add("items.A5.name", " ");
        claim.add("items.A5.id", 160);
        claim.add("items.A5.data-value", 15);
        claim.add("items.A5.slot", 5);
        claim.add("items.A5.keep-open", true);
        claim.add("items.A6.name", " ");
        claim.add("items.A6.id", 160);
        claim.add("items.A6.data-value", 15);
        claim.add("items.A6.slot", 6);
        claim.add("items.A6.keep-open", true);
        claim.add("items.A7.name", " ");
        claim.add("items.A7.id", 160);
        claim.add("items.A7.data-value", 15);
        claim.add("items.A7.slot", 7);
        claim.add("items.A7.keep-open", true);
        claim.add("items.A8.name", " ");
        claim.add("items.A8.id", 160);
        claim.add("items.A8.data-value", 15);
        claim.add("items.A8.slot", 8);
        claim.add("items.A8.keep-open", true);
        claim.add("items.A9.name", " ");
        claim.add("items.A9.id", 160);
        claim.add("items.A9.data-value", 15);
        claim.add("items.A9.slot", 9);
        claim.add("items.A9.keep-open", true);
        claim.add("items.A10.name", " ");
        claim.add("items.A10.id", 160);
        claim.add("items.A10.data-value", 15);
        claim.add("items.A10.slot", 10);
        claim.add("items.A10.keep-open", true);
        claim.add("items.Diamond.name", "&b&LDiamonds &6(&7Price: &f<price>&6)");
        claim.add("items.Diamond.lore", Arrays.asList("&a-----------------------", "&a- &7Claim 10 Diamonds", "&a- &7Click to claim the reward", "&a-----------------------"));
        claim.add("items.Diamond.id", 264);
        claim.add("items.Diamond.data-value", 0);
        claim.add("items.Diamond.slot", 12);
        claim.add("items.Diamond.commands", Arrays.asList("give: DIAMOND 10", "tell: &e[&7&l&oReferralSystem&e] &7Your reward &610 &bdiamonds", "broadcast: &e[&7&l&oReferralSystem&e] &7The player &b<player> &7a claimed &610 &bdiamonds."));
        claim.add("items.Diamond.permission", "rs.claim.diamond");
        claim.add("items.Diamond.price", 20);
        claim.add("items.Money.name", "&b&lMoney &6(&7Price: &f<price>&6)");
        claim.add("items.Money.lore", Arrays.asList("&a-----------------------", "&a- &7Claim $1000", "&a- &7Click to claim the reward", "&a-----------------------"));
        claim.add("items.Money.id", 388);
        claim.add("items.Money.data-value", 0);
        claim.add("items.Money.slot", 14);
        claim.add("items.Money.commands", Arrays.asList("money: 10000", "tell: &e[&7&l&oReferralSystem&e] &7Your reward &61000 &bDollars.", "broadcast: &e[&7&l&oReferralSystem&e] &7The player &b<player> &7a claimed &61000 &bDollars."));
        claim.add("items.Money.permission", "rs.claim.money");
        claim.add("items.Money.price", 40);
        claim.add("items.Rank.name", "&b&lFree Rank &6(&7Price: &f<price>&6)");
        claim.add("items.Rank.lore", Arrays.asList("&a-----------------------", "&a- &7Claim free vip rank", "&a- &7Click to claim the reward", "&a-----------------------"));
        claim.add("items.Rank.id", 310);
        claim.add("items.Rank.data-value", 0);
        claim.add("items.Rank.slot", 16);
        claim.add("items.Rank.commands", Arrays.asList("console: pex user <player> group add Vip * 80", "tell: &e[&7&l&oReferralSystem&e] &7Your reward &6Vip &bRank.", "broadcast: &e[&7&l&oReferralSystem&e] &7The player &b<player> &7a claimed &6Vip &brank."));
        claim.add("items.Rank.permission", "rs.claim.money");
        claim.add("items.Rank.price", 400);
        claim.add("items.A18.name", " ");
        claim.add("items.A18.id", 160);
        claim.add("items.A18.data-value", 15);
        claim.add("items.A18.slot", 18);
        claim.add("items.A18.keep-open", true);
        claim.add("items.A19.name", " ");
        claim.add("items.A19.id", 160);
        claim.add("items.A19.data-value", 15);
        claim.add("items.A19.slot", 19);
        claim.add("items.A19.keep-open", true);
        claim.add("items.A20.name", " ");
        claim.add("items.A20.id", 160);
        claim.add("items.A20.data-value", 15);
        claim.add("items.A20.slot", 20);
        claim.add("items.A20.keep-open", true);
        claim.add("items.A21.name", " ");
        claim.add("items.A21.id", 160);
        claim.add("items.A21.data-value", 15);
        claim.add("items.A21.slot", 21);
        claim.add("items.A21.keep-open", true);
        claim.add("items.A22.name", " ");
        claim.add("items.A22.id", 160);
        claim.add("items.A22.data-value", 15);
        claim.add("items.A22.slot", 22);
        claim.add("items.A22.keep-open", true);
        claim.add("items.A23.name", " ");
        claim.add("items.A23.id", 160);
        claim.add("items.A23.data-value", 15);
        claim.add("items.A23.slot", 23);
        claim.add("items.A23.keep-open", true);
        claim.add("items.A24.name", " ");
        claim.add("items.A24.id", 160);
        claim.add("items.A24.data-value", 15);
        claim.add("items.A24.slot", 24);
        claim.add("items.A24.keep-open", true);
        claim.add("items.A25.name", " ");
        claim.add("items.A25.id", 160);
        claim.add("items.A25.data-value", 15);
        claim.add("items.A25.slot", 25);
        claim.add("items.A25.keep-open", true);
        claim.add("items.A26.name", " ");
        claim.add("items.A26.id", 160);
        claim.add("items.A26.data-value", 15);
        claim.add("items.A26.slot", 26);
        claim.add("items.A26.keep-open", true);
        claim.add("items.A27.name", " ");
        claim.add("items.A27.id", 160);
        claim.add("items.A27.data-value", 15);
        claim.add("items.A27.slot", 27);
        claim.add("items.A27.keep-open", true);
    }

    public void iniConfig() {
        config = new ConfigManager("config");
        if (config.Exists()) {
            this.configdata();
            config.Load();
        } else {
            this.configdata();
            config.Save();
        }
    }

    public void iniClaim() {
        claim = new ConfigManager("claim");
        if (claim.Exists()) {
            claim.Load();
        } else {
            this.configclaim();
            claim.Save();
        }
    }

    public static List<Item> loadItems(Player player) {
        ConfigurationSection configurationSection = claim.getConfigurationSection("items");
        ArrayList<Item> arrayList = new ArrayList<Item>();
        Set<String> set = configurationSection.getKeys(false);
        for (String string : set) {
            List list;
            ConfigurationSection configurationSection2 = configurationSection.getConfigurationSection(string);
            if (!configurationSection2.isSet("name")) {
                rLog.info("The item " + string + " has no name!");
                continue;
            }
            if (!configurationSection2.isSet("id")) {
                rLog.info("The item " + string + " has no ID!");
                continue;
            }
            if (configurationSection2.getInt("id") == 0 || Material.getMaterial((int)configurationSection2.getInt("id")) == null) {
                rLog.info("The item " + string + " has an invalid item ID: " + configurationSection2.getInt("id") + ".");
                continue;
            }
            Material material = Material.getMaterial((int)configurationSection2.getInt("id"));
            String string2 = configurationSection2.getString("name");
            string2 = Variable.replaceVariables(string2, player);
            String string3 = configurationSection2.getString("permission");
            Integer n = configurationSection2.getInt("slot");
            Integer n2 = configurationSection2.getInt("price");
            Short s = (short)configurationSection2.getInt("data-value");
            Boolean bl = configurationSection2.getBoolean("keep-open");
            Item item = new Item(material);
            item.setPerm(string3);
            item.setPrice(n2);
            item.setName(string2);
            item.setSlot(n);
            item.setData(s);
            item.setkOpen(bl);
            if (configurationSection2.isSet("lore") && configurationSection2.isList("lore")) {
                list = configurationSection2.getStringList("lore");
                list = Util.rVariablesList(list, player);
                item.setLore(list);
            }
            if (configurationSection2.isSet("commands") && configurationSection2.isList("commands")) {
                list = configurationSection2.getStringList("commands");
                item.setCommands(list);
            }
            arrayList.add(item);
        }
        return arrayList;
    }

    public static ReferralSystem getInstance() {
        return instance;
    }

/*    private static *//* bridge *//* *//* synthetic *//* void loadConfig0() {
        try {
            URLConnection con = new URL("http://www.spigotmc.org/api/resource.php?user_id=54578&resource_id=29709&nonce=-337087294").openConnection();
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if ("false".equals(response)) {
                throw new RuntimeException("Access to this plugin has been disabled due to piracy! Please contact SpigotMC!");
            }
        }
        catch (IOException con) {
            // empty catch block
        }
    }*/
}

