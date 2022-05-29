package com.example.ui.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThreadHandler extends Thread {
    private ServerThread serverThread;
    private Socket socket;
    private PrintWriter printWriter;

    public ServerThreadHandler(Socket socket, ServerThread serverThread) throws IOException {
        this.serverThread = serverThread;
        this.socket = socket;
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream())
            );
            while(true) {
                serverThread.send(bufferedReader.readLine());
            }
        }catch (Exception e) {
            serverThread.getServerThreadHandler().remove(this);
        }
    }

    public PrintWriter getPrintWriter() {
        return this.printWriter;
    }
}