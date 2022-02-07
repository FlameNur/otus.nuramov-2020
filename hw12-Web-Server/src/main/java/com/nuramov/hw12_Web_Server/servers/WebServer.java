package com.nuramov.hw12_Web_Server.servers;

import com.nuramov.hw12_Web_Server.servlets.PublicInfo;
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

public class WebServer {

    public Server createServer(int port) throws MalformedURLException {
        // Создаем context для обработки запросов
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // Добавляем servlet для обработки запросов "/publicInfo"
        context.addServlet(new ServletHolder(new PublicInfo()), "/publicInfo");

        // Добавляем простой фильтр для каждого запроса "/*"
        //context.addFilter(new FilterHolder(new SimpleFilter()), "/*", null);


        Server server = new Server(port);

        // Handler'ы в структуре Jetty обрабатывают запросы, на конкретный URL формируется свой Сontext,
        // который добавляет servlet'ы
        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{createResourceHandler(), createSecurityHandler(context)});
        server.setHandler(handlers);
        return server;
    }

    // Для работы с ресурсами (папка resources)
    // Надо разобраться
    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        // ??? Возможно не сработает с WebServer.class
        URL fileDir = WebServer.class.getClassLoader().getResource("static");
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }

    // Метод для аутентификации пользователя
    private SecurityHandler createSecurityHandler(ServletContextHandler context) throws MalformedURLException {
        // Создаем ограничения при аутентификации пользователя
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"user", "admin"});

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/privateInfo/*");
        mapping.setConstraint(constraint);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());

        URL propFile = null;
        File realmFile = new File("./realm.properties");
        if (realmFile.exists()) {
            propFile = realmFile.toURI().toURL();
        }
        if (propFile == null) {
            System.out.println("local realm config not found, looking into Resources");
            // ??? Возможно не сработает с WebServer.class
            propFile = WebServer.class.getClassLoader().getResource("realm.properties");
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
