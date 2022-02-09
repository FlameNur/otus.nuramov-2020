package com.nuramov.hw12_Web_Server.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class PublicInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        // Сообщает клиенту (веб-браузеру), какой это тип контента, чтобы он знал, что с ним делать
        response.setContentType("text/html");

        // Задаем в виде ответа html страницу
        String resultAsString = "<p>PublicInfo Page</p>";

        // Выдаем сформированный ответ
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }




}
