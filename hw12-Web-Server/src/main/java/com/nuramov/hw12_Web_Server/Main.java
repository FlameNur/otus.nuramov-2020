package com.nuramov.hw12_Web_Server;

import com.nuramov.hw12_Web_Server.servers.WebServer_Jetty;
import com.nuramov.hw12_Web_Server.servlets.PrivateInfo;
import com.nuramov.hw12_Web_Server.servlets.PublicInfo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        WebServer_Jetty webServerJetty = new WebServer_Jetty();
        Server server = webServerJetty.createServer(PORT);

        // Запускает новый поток для Server
        server.start();

        // server.join() должен заставить текущий поток дождаться завершения работы сервера перед выполнением
        // следующего шага в своем потоке, т.е. заставляет текущий поток ждать завершения потока,
        // к которому его просили присоединиться. Если бы было действие после server.join(),
        // оно бы не выполнелось, пока серверный поток не завершится
        server.join();
    }
}
