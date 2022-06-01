package com.lmyun.socket.server;

import com.lmyun.socket.SocketUtils;
import com.lmyun.socket.packages.JsonPackage;
import com.lmyun.socket.packages.JsonRSAPackage;
import com.lmyun.socket.packages.request.rsa.RequestRsaKeyPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseCheckPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseFailedPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseSendClassPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseSuccessPackage;
import com.lmyun.socket.thread.SocketListenThread;
import com.lmyun.socket.thread.SocketSendThread;
import com.lmyun.utils.RSAUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Map;

public class ServerDealer extends Thread {
    private final SocketListenThread listener;
    private final SocketSendThread sender;
    private String privateKey;

    public ServerDealer(SocketListenThread listener, SocketSendThread sender) {
        this.listener = listener;
        this.sender = sender;
    }

    @Override
    public void run() {
        while (true) {
            while (this.listener.getMsg().size() != 0) {
                try {
                    byte[] msg = this.listener.getMsg().take();
                    int msgType = SocketUtils.Byte2Int(SocketUtils.toObjects(SocketUtils.subBytes(msg, 0, 4)));
                    msg = SocketUtils.subBytes(msg, 4, msg.length - 4);
                    this.deal(msgType, msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void deal(int msgType, byte[] msg) {
        switch (msgType) {
            case 2:
                this.deal_0(msg);
                break;
            case 1001:
                this.deal_1001(msg);
                break;
            case 2001:
                this.deal_2001(msg);
                break;
        }
    }

    public void log(String log) {
        System.out.println("【" + this.sender.getSocket().getRemoteSocketAddress().toString() + "】" + log);
    }

    private void deal_0(byte[] msg) {
        this.log("收到心跳包");
        //this.sender.addMsg(new HeartPackage());
    }

    private void deal_1001(byte[] msg) {
        this.log("收到公钥请求");
        RequestRsaKeyPackage requestRsaKeyPackage = new RequestRsaKeyPackage();
        Map<String, String> keys = RSAUtils.createKeys(1024);
        requestRsaKeyPackage.setPublicKey(keys.get("publicKey")).contentUpdate();
        this.privateKey = keys.get("privateKey");
        this.sender.addMsg(requestRsaKeyPackage);
        this.log("公钥已发送");
    }

    private void deal_2001(byte[] msg) {
        //授权请求解包
        LicenseCheckPackage licenseCheckPackage = new LicenseCheckPackage(new String(msg));
        licenseCheckPackage.setPrivateKey(this.privateKey);
        JsonPackage decode = licenseCheckPackage.decode();
        Map<String, Object> data = decode.getData();
        //授权信息
        String licenseVersion = (String) data.get("version");
        String licenseType = (String) data.get("licenseType");
        String license = (String) data.get("license");
        this.log("授权验证：[" + licenseType + "-" + licenseVersion + "]" + license);
        //授权有效性判定
        JsonRSAPackage resultPackage;
        boolean licenseStatus = this.hasLicense(license, licenseType);
        resultPackage = licenseStatus ? new LicenseSuccessPackage() : new LicenseFailedPackage();
        resultPackage.setPrivateKey(this.privateKey);
        this.sender.addMsg(resultPackage.contentUpdate());
        if (!licenseStatus) {
            return;
        }
        this.log("类发送中...");
        LicenseSendClassPackage classSender = new LicenseSendClassPackage();
        classSender.setPrivateKey(this.privateKey);
        classSender.setClassFile(this.getClassFileBase64(licenseType, licenseVersion));

        this.sender.addMsg(classSender.contentUpdate());
    }

    private boolean hasLicense(String license, String type) {
        boolean hasLicense = false;
        try {
            //加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            //1.getConnection()方法，连接MySQL数据库！！
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1/java?serverTimezone=GMT%2B8&characterEncoding=utf-8", "java", "1q2w3e4rR!");
            // String sql = "select * from `licenses` WHERE `license`=? AND `type`=? AND (`ip`=? OR `ip`='*');";
            String sql = "select * from `licenses` WHERE `license`=? AND `type`=?;";
            PreparedStatement preStat = con.prepareStatement(sql);
            preStat.setString(1, license);
            preStat.setString(2, type);
            //preStat.setString(3, this.listener.getSocket().getInetAddress().getHostAddress());
            ResultSet rs = preStat.executeQuery();
            hasLicense = rs.next();
            if (hasLicense) {
                String ip = rs.getString("ip");
                String fromIp = this.listener.getSocket().getInetAddress().getHostAddress();/*
                System.out.println("ip = " + ip);
                System.out.println("ip.substring(0, ip.length() - 1) = " + ip.substring(0, ip.length() - 1));
                System.out.println("fromIp = " + fromIp);*/

                if (ip.endsWith("*")) {
                    if (!fromIp.startsWith(ip.substring(0, ip.length() - 1))//不在授权范围内
                            && !ip.equalsIgnoreCase("*"))//不是全局授权
                        hasLicense = false;
                } else if (!fromIp.equalsIgnoreCase(ip))
                    hasLicense = false;
            }
            rs.close();
            con.close();
            return hasLicense;
        } catch (Exception e) {
            System.out.println("数据库读取异常");
            e.printStackTrace();
        }
        return false;
    }

    private String getClassFileBase64(String type, String version) {
        try {
            String jarPath = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
            InputStream is = new FileInputStream(jarPath + "//class//" + type + "//" + version + ".class");
            byte[] classContent = new byte[is.available()];
            is.read(classContent);
            is.close();
            return Base64.getEncoder().encodeToString(classContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
