package com.lmyun.license;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

            OutputStream outputStream = null;
            try {
                outputStream = this.socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.write("test123\r".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
}
