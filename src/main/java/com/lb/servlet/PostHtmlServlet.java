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
 * Time:14:37
 */

@WebServlet("/post.html")
public class PostHtmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        if(user == null){
            resp.sendRedirect("/login.html");
            return;
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<!DOCTYPE html>");
        printWriter.println("<html>");
        printWriter.println("<head>");
        printWriter.println("<meta charset=\"UTF-8\">");
        printWriter.println("<title> 发布文章 </title>");
        printWriter.println("</head>");
        printWriter.println("<body>");
        printWriter.println("<form method=\"post\" action=\"/post\">");
        printWriter.println("    <input type=\"text\" name=\"title\"/>");
        printWriter.println("    <textarea name=\"content\"></textarea>");
        printWriter.println("    <button type=\"submit\">发布</button>");
        printWriter.println("</form>");
        printWriter.println("</body>");
        printWriter.println("</html>");
    }
}
