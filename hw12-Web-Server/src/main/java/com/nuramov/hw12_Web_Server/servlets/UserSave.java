package com.nuramov.hw12_Web_Server.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/userSave")
public class UserSave extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/staticResources/index.html").forward(request, response);

        getServletContext().getRequestDispatcher("/staticResources/index.html").forward(request, response);

        response.sendRedirect(request.getContextPath() + "/staticResources/index.html");
    }
}
