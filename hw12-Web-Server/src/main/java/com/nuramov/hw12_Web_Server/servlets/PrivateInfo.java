package com.nuramov.hw12_Web_Server.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/privateInfo")
public class PrivateInfo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getUserPrincipal().getName();

        // Задаем в виде ответа html страницу
        String resultAsString = "<p> Security info page: " + userName + " </p>";

        // Сообщает клиенту (веб-браузеру), какой это тип контента, чтобы он знал, что с ним делать
        response.setContentType("text/html");

        // Устанавливаем код успешного ответа (стандартно - ок = 200)
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }
}
