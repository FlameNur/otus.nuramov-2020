package com.nuramov.hw12_Web_Server.servers;

import com.nuramov.hw10_Hibernate_ORM.dao.UserDAOImpWeb;
import com.nuramov.hw12_Web_Server.filters.SimpleFilter;
import com.nuramov.hw12_Web_Server.services.UserServiceWeb;
import com.nuramov.hw12_Web_Server.services.UserServiceWebImp;
import com.nuramov.hw12_Web_Server.servlets.*;
import freemarker.template.Configuration;
import freemarker.template.Version;
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

/**
 *
 */
public class WebServerJetty {
    /**
     * Метод createServer создает локальный сервер Jetty
     * @param port - подключаемый порт
     * @return - возвращаем готовый к работе сервер со всеми необходимыми параметрами
     * @throws MalformedURLException
     */
    public Server createServer(int port, UserServiceWeb userServiceWeb, Configuration configuration) throws MalformedURLException {
        // ServletContext – это инфраструктурная часть, которая содержит сервлеты и прочие
        // компоненты для обработки запросов. ServletContext привязывается к определенному адресу
        // и обрабатывает все запросы, которые на этот адрес приходят
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // Добавляем servlet для обработки запросов
        // В конструктор каждого сервлета добавлены конфиги Freemarker
        context.addServlet(new ServletHolder(new PublicInfo(configuration)), "/publicInfo");
        context.addServlet(new ServletHolder(new UsersInfo(configuration, userServiceWeb)), "/usersInfo");
        context.addServlet(new ServletHolder(new UserSave(configuration, userServiceWeb)), "/userSave");
        context.addServlet(new ServletHolder(new UserUpdate(configuration, userServiceWeb)), "/userUpdate");
        context.addServlet(new ServletHolder(new MyExceptionServlet(configuration)), "/exceptionServlet");


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
        URL fileDir = WebServerJetty.class.getClassLoader().getResource("staticResources");
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }

    /**
     * Метод SecurityHandler реализует контроль доступа для аутентификации пользователя
     */
    private SecurityHandler createSecurityHandler(ServletContextHandler context) throws MalformedURLException {
        // Создаем/описываем ограничения при аутентификации пользователя
        Constraint constraint = new Constraint();
        constraint.setName("authentication");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"user", "admin"});

        // Создаем ограничения при переходе на "/usersInfo/*"
        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/usersInfo/*");
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
            propFile = WebServerJetty.class.getClassLoader().getResource("realm.properties");
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
