package com.example.ui.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread{
    private ServerSocket serverSocket;
    private Set<ServerThreadHandler> serverThreadHandlers = new HashSet<ServerThreadHandler>();

    public ServerThread(String port) throws IOException {
        this.serverSocket = new ServerSocket(Integer.valueOf(port));
    }

    @Override
    public void run() {
        try {
            while(true) {
                ServerThreadHandler serverThreadHandler = new ServerThreadHandler(serverSocket.accept(), this);
                serverThreadHandlers.add(serverThreadHandler);
                serverThreadHandler.start();
            }
        }catch(Exception e) {
            System.out.println("run() gabim" + e);

        }
    }

    public void send(String message) {
        try {
            this.serverThreadHandlers.forEach(
                    t -> t.getPrintWriter().println(message)
            );
        }catch(Exception e) {
            System.out.println("sendMessage() gabim" + e);
        }
    }

    public Set<ServerThreadHandler> getServerThreadHandler(){
        return this.serverThreadHandlers;
    }
}