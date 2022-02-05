package com.nuramov.hw12_Web_Server;

import com.nuramov.hw12_Web_Server.servers.WebServer;
import com.nuramov.hw12_Web_Server.servlets.PublicInfo;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        WebServer webServer = new WebServer();
        Server server = webServer.createServer(PORT);

        ServletHandler servletHandler = new ServletHandler();

        // ?????
        // Пока не понятно почему это создаем. Возможно запускаем перехватчик сервлетов
        servletHandler.addServletWithMapping(PublicInfo.class, "/publicInfo");

        server.setHandler(servletHandler);

        // Запускает новый поток для Server
        server.start();


        // server.join() должен заставить текущий поток дождаться завершения работы сервера перед выполнением
        // следующего шага в своем потоке, т.е. заставляет текущий поток ждать завершения потока,
        // к которому его просили присоединиться. Если бы было действие после server.join(),
        // оно бы не выполнелось, пока серверный поток не завершится
        server.join();
    }
}
