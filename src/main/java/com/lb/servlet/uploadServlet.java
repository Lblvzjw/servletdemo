package com.lb.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Creat with IntelliJ IDEA.
 * Description：
 * User:LiuBen
 * Date:2020-01-16
 * Time:17:13
 */
@WebServlet("/upload")
@MultipartConfig
public class uploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Part avatar = req.getPart("avatar");
        System.out.println(avatar.getName());
        System.out.println(avatar.getSubmittedFileName());
        System.out.println(avatar.getSize());
        System.out.println(avatar.getContentType());
        System.out.println(avatar.getInputStream());

        InputStream is = avatar.getInputStream();
        OutputStream os = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\javacode\\servletdemo\\上传的文件\\wenjian.jps");
        byte[] buf = new byte[4096];
        int len;

        while ((len = is.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        os.close();
    }
}
