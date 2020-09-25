package ru.innopolis.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void start() {
        /**
         * Метод запускает сервер. Прослушивает до тех пор пока клиент не подсоединится.
         * Прокидывает входной и выходной потоки от соединения в метод handle() класса HttpServer
         */
        try (final ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket socket = serverSocket.accept();

                final HttpServer httpServer = new HttpServer();
                httpServer.handle(socket.getInputStream(), socket.getOutputStream());
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
