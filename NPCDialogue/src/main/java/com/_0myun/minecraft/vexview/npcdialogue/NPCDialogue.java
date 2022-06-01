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
        getLogger().log(Level.WARNING, "请注意,现在开始授权校验,如果授权错误将会关服,请把授权填入插件配置!");
        int license = 0;
        System.out.println("==========================");
        try {
            license = 100;
            licenseString = this.getConfig().getString("license");
            getLogger().log(Level.INFO, "NPCDialogue Loading");
            try {
                //主要验证步骤
                this.socket = new Socket("61.164.253.173", 9999);
                sender = new SocketSendThread(socket);
                listener = new SocketListenThread(socket);
                sender.start();
                listener.start();
                //////////////////////////////////////////////////////////////////////////////
                sender.addMsg(new RequestRsaKeyPackage().setPublicKey(String.valueOf(UUID.randomUUID())).contentUpdate());
                long timeStart = System.currentTimeMillis();//计时防卡住
                while (!this.socket.isClosed()) {//消息监听，直到验证步骤结束
                    while (listener.getMsg().size() != 0) {
                        byte[] msg = listener.getMsg().take();
                        int msgType = SocketUtils.Byte2Int(SocketUtils.toObjects(SocketUtils.subBytes(msg, 0, 4)));
                        msg = SocketUtils.subBytes(msg, 4, msg.length - 4);
                        deal(msgType, msg);//处理消息
                        if (msgType == 2002 || msgType == 2004) {
                            getLogger().log(Level.INFO, "授权验证成功");
                            license = 1;
                        } else if (msgType == 2003) {
                            throw new Exception("授权失败,序列号错误或者IP不匹配");
                        }
                        if (msgType == 2004) {//2004发完类就结束验证了
                            return;
                        }
                    }
                    if ((System.currentTimeMillis() - timeStart) > (1000 * 30)) {
                        throw new Exception("授权超时,请检查网络环境");
                    }
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "验证失败");
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
                getLogger().log(Level.WARNING, "加载异常,请检查授权&其他配置");
            }
        }
        Bukkit.getServer().shutdown();
        getLogger().log(Level.INFO, "已完成授权校验..Enjoy!");


        //BetonQuest.getInstance().registerConversationIO("_0MVexview", NPCDialogueUI.class);
    }

    public void deal(int msgType, byte[] msg) {
        switch (msgType) {
            case 1001:
                RequestRsaKeyPackage requestRsaKeyPackage = new RequestRsaKeyPackage(new String(msg));
                this.publicKey = requestRsaKeyPackage.getPublicKey();
                //System.out.println("收到来自服务端的公钥" + this.publicKey);
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
