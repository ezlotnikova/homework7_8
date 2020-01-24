package com.gmail.zlotnikova.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.zlotnikova.service.UserInformationService;
import com.gmail.zlotnikova.service.impl.UserInformationServiceImpl;

public class NewAddressServlet extends HttpServlet {

    UserInformationService userInformationService = UserInformationServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String newAddress = req.getParameter("newAddress");

        int expectedRowsUpdated = 1;
        int rowsUpdated = userInformationService.updateAddressById(id, newAddress);

        try (PrintWriter out = resp.getWriter()) {
            if (rowsUpdated == expectedRowsUpdated) {
                out.println("<html><body>");
                out.println("Address for user with id <b>" + id + "</b> successfully changed.");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("Unable to update address for user with id <b>" + id + "</b>.<br>");
                out.println("Please check if such user exists.");
                out.println("</body></html>");
            }
        }
    }

}