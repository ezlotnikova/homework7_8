package com.gmail.zlotnikova.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;
import com.gmail.zlotnikova.service.model.UserDTO;

public class UsersServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<UserDTO> userDTOList = userService.findAll();
        try (PrintWriter out = resp.getWriter()
        ) {
            out.println("<html><body>");
            for (UserDTO userDTO :
                    userDTOList) {
                out.println("<b>User</b> ");
                out.println(userDTO.toString());
                out.println("<br>");
            }
            out.println("</body></html>");
        }
    }

}