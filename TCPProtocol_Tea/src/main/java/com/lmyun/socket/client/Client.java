package com.lmyun.socket.client;

import com.lmyun.socket.SocketUtils;
import com.lmyun.socket.client.thread.SocketHearter;
import com.lmyun.socket.packages.request.rsa.RequestRsaKeyPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseCheckPackage;
import com.lmyun.socket.packages.request.rsa.license.LicenseSendClassPackage;
import com.lmyun.socket.thread.SocketListenThread;
import com.lmyun.socket.thread.SocketSendThread;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Base64;
import java.util.UUID;

public class Client {
    private Socket socket;
    private String host = "61.164.253.173";
    private int port = 9999;

    private SocketSendThread sender;
    private SocketListenThread listener;
    @Getter
    private String publicKey;

    public Client() throws IOException {
        socket = new Socket(host, port);
        System.out.println("客户端启动!!!");
    }

    public static void main(String[] args) throws Exception {
        new Client().talk();
    }

    private void talk() throws Exception {
        this.sender = new SocketSendThread(this.socket);
        this.listener = new SocketListenThread(this.socket);
        this.sender.start();
        this.listener.start();
        new SocketHearter(this.sender).start();//心跳开始
        //////////////////////////////////////////////////////////////////////////////
        this.sender.addMsg(new RequestRsaKeyPackage().setPublicKey(String.valueOf(UUID.randomUUID())).contentUpdate());
        while (true) {
            while (this.listener.getMsg().size() != 0) {
                byte[] msg = this.listener.getMsg().take();
                int msgType = SocketUtils.Byte2Int(SocketUtils.toObjects(SocketUtils.subBytes(msg, 0, 4)));
                msg = SocketUtils.subBytes(msg, 4, msg.length - 4);
                this.deal(msgType, msg);
            }
            Thread.sleep(100);
        }
    }

    public void deal(int msgType, byte[] msg) {

        switch (msgType) {
            case 2:
                System.out.println("收到来自服务端的心跳包");
                break;
            case 1001:
                RequestRsaKeyPackage requestRsaKeyPackage = new RequestRsaKeyPackage(new String(msg));
                this.publicKey = requestRsaKeyPackage.getPublicKey();
                System.out.println("收到来自服务端的公钥" + this.publicKey);
                LicenseCheckPackage licenseCheckPackage = new LicenseCheckPackage();
                licenseCheckPackage.setLicense("7ceea31c-7571-47e4-9f87-52296f12c09f");
                licenseCheckPackage.setLicenseType("SkillKeyBoard");
                licenseCheckPackage.setPublicKey(this.getPublicKey());
                this.sender.addMsg(licenseCheckPackage.contentUpdate());
                break;
            case 2002:
                System.out.println("验证成功");
                break;
            case 2003:
                System.out.println("验证失败");
                break;
            case 2004:
                LicenseSendClassPackage classPackage = new LicenseSendClassPackage(new String(msg));
                classPackage.setPublicKey(this.publicKey);
                classPackage.decode();
                String classFile = (String) classPackage.getData().get("classFile");
                System.out.println(classFile);
                try {
                    new ClassLoader(){
                        public void load() throws Exception {
                            byte[] classByte = Base64.getDecoder().decode(classFile);
                            Class<?> aClass = defineClass(classByte, 0, classByte.length);
                            Object o = aClass.newInstance();
                            Method[] main = aClass.getMethods();
                            System.out.println(main[0].invoke(null));
                        }
                    }.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
