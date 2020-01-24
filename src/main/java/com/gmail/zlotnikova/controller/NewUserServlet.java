package com.gmail.zlotnikova.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.zlotnikova.service.UserService;
import com.gmail.zlotnikova.service.impl.UserServiceImpl;
import com.gmail.zlotnikova.service.model.UserDTO;
import com.gmail.zlotnikova.util.BooleanUtil;

public class NewUserServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();
    private BooleanUtil booleanUtil = BooleanUtil.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserDTO userDTO = createUserDTO(req);
        UserDTO newUserDTO = userService.add(userDTO);
        try (PrintWriter out = resp.getWriter()) {
            if (newUserDTO.getId() != null) {
                out.println("<html><body>");
                out.println("<b>New user added:</b><br>");
                out.println("ID: " + newUserDTO.getId() + "<br>");
                out.println("Username: " + newUserDTO.getUsername() + "<br>");
                out.println("Password: " + newUserDTO.getPassword() + "<br>");
                out.println("Status: " + newUserDTO.getActive() + "<br>");
                out.println("Age: " + newUserDTO.getAge() + "<br>");
                out.println("Address: " + newUserDTO.getAddress() + "<br>");
                out.println("Telephone: " + newUserDTO.getTelephone() + "<br>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("Adding new user failed.");
                out.println("</body></html>");
            }
        }
    }

    private UserDTO createUserDTO(HttpServletRequest req) {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Boolean isActive = booleanUtil.parseBoolean(req.getParameter("isActive"));
        Integer age = Integer.parseInt(req.getParameter("age"));
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setTelephone(telephone);
        userDTO.setAddress(address);
        userDTO.setActive(isActive);
        userDTO.setAge(age);

        return userDTO;
    }

}