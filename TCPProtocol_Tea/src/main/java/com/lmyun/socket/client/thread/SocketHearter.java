package com.lmyun.socket.client.thread;

import com.lmyun.socket.packages.HeartPackage;
import com.lmyun.socket.thread.SocketSendThread;

public class SocketHearter extends Thread {
    SocketSendThread sender;

    public SocketHearter(SocketSendThread sendThread) {
        this.sender = sendThread;
    }

    @Override
    public void run() {
        while (true) {
            this.sender.addMsg(new HeartPackage().contentUpdate());
            try {

                Thread.sleep(10 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
