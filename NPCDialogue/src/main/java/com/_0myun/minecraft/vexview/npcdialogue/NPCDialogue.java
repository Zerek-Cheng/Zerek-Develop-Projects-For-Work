package com._0myun.minecraft.vexview.npcdialogue;

import com.lmyun.socket.SocketUtils;
import com.lmyun.socket.packages.request.rsa.RequestRsaKeyPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseCheckPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseSendClassPackage;
import com.lmyun.socket.thread.SocketListenThread;
import com.lmyun.socket.thread.SocketSendThread;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;

public final class NPCDialogue extends JavaPlugin {

    public static NPCDialogue plugin;


    private String publicKey = "V4V 0VA1S1V1CE0B1SA4V4";
    private SocketSendThread sender;
    private SocketListenThread listener;
    private String licenseString;
    private Socket socket;

    private static Class<?> BukkitClass;
    private static Object serverObject = null;
    private static Object PMObject;

    private static Class<?> bqClass;
    private static Object bqObject = null;
    private static Method bqRegConIOMethod;

    static {
        try {
            BukkitClass = Class.forName("org.bukkit.Bukkit");
            serverObject = BukkitClass.getMethod("getServer").invoke(BukkitClass);
            PMObject = serverObject.getClass().getMethod("getPluginManager").invoke(serverObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;


        try {
            bqClass = Class.forName("pl.betoncraft.betonquest.BetonQuest");
            bqObject = bqClass.getMethod("getInstance").invoke(bqClass);
            bqRegConIOMethod = bqClass.getMethod("registerConversationIO", String.class, Class.class);
            bqRegConIOMethod.invoke(bqObject, "_0MVexview", NPCDialogueUI.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //this.validate();
        if (!this.isEnabled()) return;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void validate() {
        try {
            bqClass = Class.forName("pl.betoncraft.betonquest.BetonQuest");
            bqObject = bqClass.getMethod("getInstance").invoke(bqClass);
            bqRegConIOMethod = bqClass.getMethod("registerConversationIO", String.class, Class.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogger().log(Level.WARNING, "?????????,????????????????????????,??????????????????????????????,??????????????????????????????!");
        int license = 0;
        System.out.println("==========================");
        try {
            license = 100;
            licenseString = this.getConfig().getString("license");
            getLogger().log(Level.INFO, "NPCDialogue Loading");
            try {
                //??????????????????
                this.socket = new Socket("61.164.253.173", 9999);
                sender = new SocketSendThread(socket);
                listener = new SocketListenThread(socket);
                sender.start();
                listener.start();
                //////////////////////////////////////////////////////////////////////////////
                sender.addMsg(new RequestRsaKeyPackage().setPublicKey(String.valueOf(UUID.randomUUID())).contentUpdate());
                long timeStart = System.currentTimeMillis();//???????????????
                while (!this.socket.isClosed()) {//???????????????????????????????????????
                    while (listener.getMsg().size() != 0) {
                        byte[] msg = listener.getMsg().take();
                        int msgType = SocketUtils.Byte2Int(SocketUtils.toObjects(SocketUtils.subBytes(msg, 0, 4)));
                        msg = SocketUtils.subBytes(msg, 4, msg.length - 4);
                        deal(msgType, msg);//????????????
                        if (msgType == 2002 || msgType == 2004) {
                            getLogger().log(Level.INFO, "??????????????????");
                            license = 1;
                        } else if (msgType == 2003) {
                            throw new Exception("????????????,?????????????????????IP?????????");
                        }
                        if (msgType == 2004) {//2004???????????????????????????
                            return;
                        }
                    }
                    if ((System.currentTimeMillis() - timeStart) > (1000 * 30)) {
                        throw new Exception("????????????,?????????????????????");
                    }
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "????????????");
                getLogger().log(Level.WARNING, e.getMessage());
                return;
            }
        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(this);
            e.printStackTrace();
        } finally {
            if (license != 1) {
                try {
                    Bukkit.getPluginManager().disablePlugin(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getLogger().log(Level.WARNING, "????????????,???????????????&????????????");
            }
        }
        Bukkit.getServer().shutdown();
        getLogger().log(Level.INFO, "?????????????????????..Enjoy!");


        //BetonQuest.getInstance().registerConversationIO("_0MVexview", NPCDialogueUI.class);
    }

    public void deal(int msgType, byte[] msg) {
        switch (msgType) {
            case 1001:
                RequestRsaKeyPackage requestRsaKeyPackage = new RequestRsaKeyPackage(new String(msg));
                this.publicKey = requestRsaKeyPackage.getPublicKey();
                //System.out.println("??????????????????????????????" + this.publicKey);
                LicenseCheckPackage licenseCheckPackage = new LicenseCheckPackage();
                licenseCheckPackage.setLicense(licenseString);
                licenseCheckPackage.setLicenseType(this.getName());
                licenseCheckPackage.setPublicKey(this.publicKey);
                licenseCheckPackage.setVersion(this.getDescription().getVersion());
                this.sender.addMsg(licenseCheckPackage.contentUpdate());
                break;
            case 2004:
                LicenseSendClassPackage classPackage = new LicenseSendClassPackage(new String(msg));
                classPackage.setPublicKey(this.publicKey);
                classPackage.decode();
                String classFile = (String) classPackage.getData().get("classFile");
                try {
                    new ClassLoader(this.getClassLoader()) {
                        public void load() throws Exception {
                            byte[] classByte = Base64.getDecoder().decode(classFile);
                            Class<?> aClass = super.defineClass(classByte, 0, classByte.length);
                            bqRegConIOMethod.invoke(bqObject, "_0MVexview", aClass);
                        }
                    }.load();
                    this.socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        return true;
    }
}
