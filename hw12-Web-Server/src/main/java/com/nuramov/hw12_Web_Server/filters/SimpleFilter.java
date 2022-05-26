package com.nuramov.hw12_Web_Server.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;

/**
 * Фильтр SimpleFilter на каждый запрос выдает в консоль IP адрес и время обращения
 */
@WebFilter
public class SimpleFilter implements Filter {

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
}
