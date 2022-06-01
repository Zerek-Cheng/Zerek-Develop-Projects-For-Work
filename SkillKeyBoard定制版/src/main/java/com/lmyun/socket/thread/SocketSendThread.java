package com.lmyun.socket.thread;

import com.lmyun.socket.SocketUtils;
import com.lmyun.socket.packages.BasePackage;
import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketSendThread extends Thread {
    @Getter  private Socket socket;
    private BlockingQueue<byte[]> msg = new LinkedBlockingQueue<>();

    public SocketSendThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.sendMsg(msg.take());
                Thread.sleep(100);
            } catch (SocketException e) {
                if (this.socket.isClosed()) {
                    this.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.msg.size() == 0) {
                this.suspend();
            }
        }
    }

    private void addMsg(byte[] msg) {
        this.msg.add(msg);
        this.resume();
    }

    public void addMsg(BasePackage p) {
        if (p.isCancel()) {
            return;
        }
        this.addMsg(SocketUtils.byteMerger(SocketUtils.IntToByte(p.getType()), p.getContent()));
    }

    public void sendMsg(byte[] b) throws IOException {
        OutputStream os = this.socket.getOutputStream();
        os.write(SocketUtils.IntToByte(b.length));
        os.write(b);
        os.flush();
    }
}
