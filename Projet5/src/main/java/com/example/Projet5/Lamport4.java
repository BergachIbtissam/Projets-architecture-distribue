package com.example.Projet5;

public class Lamport4 {
    public static void main(String[] args) {
        Agent A = new Agent("Agent A");
        Agent B = new Agent("Agent B");
        Agent C = new Agent("Agent C");
        Agent D = new Agent("Agent D");

        

        A.sendMessage(B, "Message A→B"); // A envoie un message à B
        B.sendMessage(C, "Message B→C"); // B envoie à C
        C.sendMessage(D, "Message C→D"); // C envoie à D
        D.sendMessage(A, "Message D→A"); // D envoie à A

       
    }
}