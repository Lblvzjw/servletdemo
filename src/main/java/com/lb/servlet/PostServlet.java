package com.lb.servlet;

import com.lb.dao.ArticleDao;
import com.lb.model.Article;
import com.lb.model.User;

import javax.servlet.ServletException;
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
 * Date:2020-01-16
 * Time:11:27
 */

@WebServlet("/post")
public class PostServlet extends HttpServlet {
    private ArticleDao articleDao = new ArticleDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        if(user == null){
            resp.sendRedirect("/login.html");
            return;
        }
        Article article = null;
        try {
            article = articleDao.publishArticle(user,title,content);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println("<h1>发布成功</h1>");
        resp.getWriter().println(article);
    }
}
