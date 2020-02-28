package com.lb.servlet;

import com.lb.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Creat with IntelliJ IDEA.
 * Description：
 * User:LiuBen
 * Date:2020-01-16
 * Time:9:30
 */

@WebServlet("/profile")
public class indexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        HttpSession session  = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            printWriter.println("<h1>用户未登录</h1>");
        }else{
            printWriter.printf("<h1>%s</h1>%n",user.id);
            printWriter.printf("<h1>%s</h1>%n",user.username);
            printWriter.printf("<h1>%s</h1>%n",user.nickname);
            printWriter.println("<button><a href=\"post.html\">写文章</a></button>");
        }
    }
}
