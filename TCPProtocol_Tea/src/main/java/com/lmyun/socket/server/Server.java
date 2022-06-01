package com.lmyun.socket.server;

import com.lmyun.socket.thread.SocketListenThread;
import com.lmyun.socket.thread.SocketSendThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private int serverPort = 9999;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(this.serverPort);
    }

    public static void main(String[] args) throws IOException {
        new Server().service();
    }

    public void service() {
        this.log("服务启动,端口:" + this.serverPort);
        while (true) {
            Socket socket = null;
            try {
                // 通过accept()方法进行监听，返回一个socket
                socket = this.serverSocket.accept();
                //输出客户端端口
                if (socket.isConnected()) {
                    System.out.println(socket.getRemoteSocketAddress().toString() + "已连接");
                }
                SocketSendThread sendThread = new SocketSendThread(socket);
                SocketListenThread receiveThread = new SocketListenThread(socket);
                sendThread.start();
                receiveThread.start();
                new ServerDealer(receiveThread, sendThread).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void log(String log) {
        System.out.println(log);
    }
}
