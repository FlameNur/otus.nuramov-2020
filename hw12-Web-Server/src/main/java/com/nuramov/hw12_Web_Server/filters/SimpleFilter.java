package com.nuramov.hw12_Web_Server.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;

public class SimpleFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("Данные по запросу фильтра:");
        // Получение IP адреса клиента
        String ipAddress = request.getRemoteAddr();

        // Выдаем IP адрес и текущее время
        System.out.println("IP "+ ipAddress + ", Time " + new Date());

        // Передача запроса в цепочку фильтров
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
