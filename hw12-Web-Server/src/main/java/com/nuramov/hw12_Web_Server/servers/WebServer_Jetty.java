package com.nuramov.hw12_Web_Server.servers;

import com.nuramov.hw12_Web_Server.filters.SimpleFilter;
import com.nuramov.hw12_Web_Server.servlets.PrivateInfo;
import com.nuramov.hw12_Web_Server.servlets.PublicInfo;
import com.nuramov.hw12_Web_Server.servlets.UserInfo;
import com.nuramov.hw12_Web_Server.servlets.UserSave;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class WebServer_Jetty {

    /**
     * Метод createServer создает локальный сервер Jetty
     * @param port - подключаемый порт
     * @return - возвращаем готовый к работе сервер со всеми необходимыми параметрами
     * @throws MalformedURLException
     */
    public Server createServer(int port) throws MalformedURLException {
        // ServletContext – это инфраструктурная часть, которая содержит сервлеты и прочие
        // компоненты для обработки запросов. ServletContext привязывается к определенному адресу
        // и обрабатывает все запросы, которые на этот адрес приходят
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // Добавляем servlet для обработки запросов
        context.addServlet(new ServletHolder(new PublicInfo()), "/publicInfo");
        context.addServlet(new ServletHolder(new PrivateInfo()), "/privateInfo");
        context.addServlet(new ServletHolder(new UserInfo()), "/userInfo");
        context.addServlet(new ServletHolder(new UserSave()), "/userSave");

        // Добавляем простой фильтр для каждого запроса "/*"
        context.addFilter(new FilterHolder(new SimpleFilter()), "/*", null);

        Server server = new Server(port);

        // Handler'ы в структуре Jetty обрабатывают запросы и добавляют servlet'ы
        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{createResourceHandler(), createSecurityHandler(context)});
        server.setHandler(handlers);
        return server;
    }

    /**
     * Метод createResourceHandler() позволяет работать со статическим контентом.
     * Создаем пустую страничку приветствия Welcome Page на http://localhost:8080/
     * @return - возвращает обработчик статического контента
     */
    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        // Определяем директорию для ресурса
        URL fileDir = WebServer_Jetty.class.getClassLoader().getResource("staticResources");
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }

    /**
     * Метод SecurityHandler реализует контроль доступа для аутентификации пользователя
     * @param context -
     * @return -
     * @throws MalformedURLException -
     */
    private SecurityHandler createSecurityHandler(ServletContextHandler context) throws MalformedURLException {
        // Создаем/описываем ограничения при аутентификации пользователя
        Constraint constraint = new Constraint();
        constraint.setName("authentication");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"user", "admin"});

        // Создаем ограничения при переходе на "/privateInfo/*"
        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/privateInfo/*");
        mapping.setConstraint(constraint);

        // Указываем каким аутентификатором будем пользоваться
        // BasicAuthenticator() - самый простой встроенный аутентификатор
        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать строку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        URL propFile = null;
        File realmFile = new File("./realm.properties");
        if (realmFile.exists()) {
            propFile = realmFile.toURI().toURL();
        }
        if (propFile == null) {
            System.out.println("local realm config not found, looking into Resources");
            propFile = WebServer_Jetty.class.getClassLoader().getResource("realm.properties");
        }

        if (propFile == null) {
            throw new RuntimeException("Realm property file not found");
        }

        security.setLoginService(new HashLoginService("MyRealm", propFile.getPath()));
        security.setHandler(new HandlerList(context));
        security.setConstraintMappings(Collections.singletonList(mapping));

        return security;
    }
}
