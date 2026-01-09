package com.example.Projet5;

class Message {
    int clock;
    String content;
    String sender;

    public Message(String sender, String content, int clock) {
        this.sender = sender;
        this.content = content;
        this.clock = clock;
    }
}
