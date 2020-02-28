package com.lb.dao;

import com.lb.model.User;
import com.lb.util.Database;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Creat with IntelliJ IDEA.
 * Description：
 * User:LiuBen
 * Date:2020-01-15
 * Time:10:13
 */
public class UserDao {
    private static final String secret = "只有你知道";


    public User registerUser(String username, String nickname, String password) throws SQLException {
        password = encrypted(password);
        String sql = "INSERT INTO users (username, nickname, password) VALUES (?, ?, ?)";

        try (Connection connection = Database.getConnection()) {
            // Statement.RETURN_GENERATED_KEYS 表示返回插入的自增 id 值
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, username);
                statement.setString(2, nickname);
                statement.setString(3, password);

                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    int id = rs.getInt(1);
                    return new User(id, username, nickname);
                }
            }
        } catch (SQLException e) {
            // 如果是用户名重复异常，属于客户端的问题，特殊处理下
            if (e.getMessage().contains("Duplicate entry")) {
                return null;
            }
            throw e;
        }
    }


    public User findUser(String username, String password) throws SQLException {
        String sql = "select id,username,nickname from users where username=? and password=?";
        try(Connection connection = Database.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if(!resultSet.next()){
                        return null;
                    }
                    int id = resultSet.getInt(1);
                    String nickname = resultSet.getString(3);
                    return new User(id,username,nickname);
                }
            }
        }
    }


    private String encrypted(String password){
        password = password + secret;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] input = password.getBytes("UTF-8");
            byte[] output = messageDigest.digest(input);

            for(byte b : output){
                stringBuilder.append(String.format("%02x",b));
            }
        } catch (NoSuchAlgorithmException|IOException e) {
            e.printStackTrace();
            return "";
        }
        return stringBuilder.toString();
    }
}
