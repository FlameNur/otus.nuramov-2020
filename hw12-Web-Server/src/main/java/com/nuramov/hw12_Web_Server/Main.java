package com.nuramov.hw12_Web_Server;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
import com.nuramov.hw12_Web_Server.servers.WebServerJetty;
import com.nuramov.hw12_Web_Server.services.UserServiceWeb;
import com.nuramov.hw12_Web_Server.services.UserServiceWebImp;
import com.nuramov.hw12_Web_Server.servlets.UserSave;
import freemarker.template.Configuration;
import freemarker.template.Version;
import org.eclipse.jetty.server.Server;

/**
 * Запускаем сервер
 * По ссылке "http://localhost:8080/" открываем страницу приветствия и переходим
 * на страницу "/publicInfo" по кнопке "Submit"
 * На странице PublicInfo может перейти на "userSave" (форма для добавления нового пользователя) или
 * "usersInfo" (таблица со всеми пользователями)
 */
public class Main {
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        // Создаем экземпляр класса UserServiceWebImp для работы с БД
        UserServiceWeb userServiceWeb = new UserServiceWebImp(new UserDAOImpWeb());

        // Создаем объект конфигурации Freemarker
        Configuration configuration = new Configuration(new Version("2.3.31"));
        // Задаем путь, по которому находится файл шаблона. Не совсем понятно что задавать, пока и так работает
        configuration.setClassForTemplateLoading(UserSave.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        WebServerJetty webServerJetty = new WebServerJetty();
        Server server = webServerJetty.createServer(PORT, userServiceWeb, configuration);

        // Запускает новый поток для Server
        server.start();

        // server.join() должен заставить текущий поток дождаться завершения работы сервера перед выполнением
        // следующего шага в своем потоке, т.е. заставляет текущий поток ждать завершения потока,
        // к которому его просили присоединиться. Если бы было действие после server.join(),
        // оно бы не выполнелось, пока серверный поток не завершится
        server.join();
    }
}
