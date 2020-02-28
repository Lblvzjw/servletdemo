package com.lb.dao;

import com.lb.model.Article;
import com.lb.model.User;
import com.lb.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creat with IntelliJ IDEA.
 * Description：
 * User:LiuBen
 * Date:2020-01-16
 * Time:15:02
 */
public class ArticleDao {
    public Article publishArticle(User user, String title, String content) throws SQLException{
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = dateFormat.format(now);

        String sql = "insert into articles (author_id,thilt,content,published_at) values (?,?,?,?)";
        try(Connection connection = Database.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setInt(1,user.getId());
                statement.setString(2,title);
                statement.setString(3,content);
                statement.setString(4,nowDate);
                statement.executeUpdate();

                try(ResultSet resultSet = statement.getGeneratedKeys()){
                    resultSet.next();
                    int id = resultSet.getInt(1);
                    return new Article(id,user,title,content,now);
                }
            }
        }
    }


    public Article getArticleById(int id) throws SQLException {
        String sql = "SELECT articles.id, articles.thilt, articles.content, users.id, users.nickname, users.username FROM articles, users WHERE articles.author_id = users.id AND articles.id = ?";
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }

                    int articleId = rs.getInt(1);
                    String title = rs.getString(2);
                    String content = rs.getString(3);
                    int userId = rs.getInt(4);
                    String nickname = rs.getString(5);
                    String username = rs.getString(6);

                    User author = new User(userId, username, nickname);
                    Article article = new Article(articleId, author, title, content, null);
                    return article;
                }
            }
        }
    }


    public List<Article> getArticleList() throws SQLException {
        List<Article> articleList = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT " +
                    "a.id, a.thilt, a.content, " +
                    "u.id, u.username, u.nickname " +
                    "FROM articles a, users u " +
                    "WHERE a.author_id = u.id " +
                    "ORDER BY a.published_at DESC";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        int articleId = rs.getInt(1);
                        String title = rs.getString(2);
                        String content = rs.getString(3);
                        int userId = rs.getInt(4);
                        String username = rs.getString(5);
                        String nickname = rs.getString(6);

                        User author = new User(userId, username, nickname);
                        Article article = new Article(articleId, author, title, content, null);

                        articleList.add(article);
                    }
                }
            }
        }

        return articleList;
    }
}
