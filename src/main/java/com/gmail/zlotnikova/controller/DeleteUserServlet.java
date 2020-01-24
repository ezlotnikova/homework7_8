package com.gmail.zlotnikova.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;

public class DeleteUserServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        int expectedRowsDeleted = 1;
        int rowsDeleted = userService.deleteById(id);

        try (PrintWriter out = resp.getWriter()) {
            if (rowsDeleted == expectedRowsDeleted) {
                out.println("<html><body>");
                out.println("User with id <b>" + id + "</b> deleted.");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("Unable to delete user with id <b>" + id + "</b>.<br>");
                out.println("Please check if such id exists.");
                out.println("</body></html>");
            }
        }
    }

}