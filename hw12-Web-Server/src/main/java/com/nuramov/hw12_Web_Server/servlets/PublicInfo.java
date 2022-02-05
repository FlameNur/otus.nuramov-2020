package com.nuramov.hw12_Web_Server.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class PublicInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем статус для условия успешного получения ответа
        response.setStatus(HttpServletResponse.SC_OK);

        // Сообщает клиенту (веб-браузеру), какой это тип контента, чтобы он знал, что с ним делать
        response.setContentType("text/html");

        String resultAsString = "<p>PublicInfo Page</p>";

        // Получаем сообщение при получении ответа
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }




}
