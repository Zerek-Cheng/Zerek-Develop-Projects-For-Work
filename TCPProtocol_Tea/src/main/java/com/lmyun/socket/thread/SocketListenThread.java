package com.lmyun.socket.thread;

import com.lmyun.socket.SocketUtils;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketListenThread extends Thread {
    @Getter
    private Socket socket;
    @Getter
    private BlockingQueue<byte[]> msg = new LinkedBlockingQueue<>();

    public SocketListenThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.socket.isClosed()) {
                    this.stop();
                }
                int packageLength = this.getNextPackageLength();
                byte[] packageContent = this.getNextPackageContent(packageLength);
                this.msg.add(packageContent);
                //System.out.println(new String(packageContent));
                Thread.sleep(100);
            }
        } catch (SocketException e) {
            System.out.println("连接" + this.socket.getRemoteSocketAddress().toString() + "已断开");
            try {
                this.socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getNextPackageLength() throws IOException {
        return SocketUtils.Byte2Int(SocketUtils.toObjects(this.receiveMsgForce(4)));
    }

    private byte[] getNextPackageContent(int byteLength) throws IOException {
        return this.receiveMsgForce(byteLength);
    }

    //收信息
    private byte[] receiveMsg(int byteLength) throws IOException {
        InputStream is = this.socket.getInputStream();
        int available = is.available();
        if (available <= 0) {
            return new byte[]{};
        }
        if (available < byteLength) {
            byteLength = available;
        }
        byte[] byteConents = new byte[byteLength];
        int readLength = is.read(byteConents, 0, byteLength);
        //byteConents = SocketUtils.subBytes(byteConents, 0, readLength);
        return byteConents;
    }

    private byte[] receiveMsgForce(int byteLength) throws IOException {
        InputStream is = this.socket.getInputStream();
        byte[] byteConents = new byte[byteLength];
        int readLength = is.read(byteConents, 0, byteLength);
        int less = byteLength - readLength;
        if (less > 0) {
            if (readLength > 0) {
                byteConents = SocketUtils.subBytes(byteConents, 0, readLength);
            }
            while (is.available() < less) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            byte[] addByteContents = new byte[less];
            is.read(addByteContents, 0, less);
            byteConents = SocketUtils.byteMerger(byteConents, addByteContents);
        }
        return byteConents;
    }
}
