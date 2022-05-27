package com.example.ui;

public class ChatUser {
    int id;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatUser(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
