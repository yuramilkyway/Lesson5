package ru.innopolis.main;

import ru.innopolis.app.Server;

public class Main {

    public static void main(String[] args) {
        
        final Server server = new Server();
        server.start();
    }
}