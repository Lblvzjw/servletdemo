package com.lb.servlet;
import com.lb.dao.UserDao;

import com.lb.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Creat with IntelliJ IDEA.
 * Description：
 * User:LiuBen
 * Date:2020-01-15
 * Time:10:06
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        if(username == null || username.isEmpty()){
            resp.sendRedirect("/login.html");
            return;
        }
        String password = req.getParameter("password");
        if(password == null || password.isEmpty()){
            resp.sendRedirect("/login.html");
            return;
        }

        User user = null;
        try {
            user = userDao.findUser(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500,e.getMessage());
            return;
        }
        if(user == null){
            resp.sendRedirect("/login.html");
            System.out.println("找不到该用户");
            return;
        }

        System.out.println(session.getId());
        session.setAttribute("user",user);
        resp.sendRedirect("/profile");
    }
}
