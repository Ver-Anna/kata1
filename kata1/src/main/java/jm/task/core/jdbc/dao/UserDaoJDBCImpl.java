package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    public void createUsersTable() { //создаем таблицу юзеров в базе данных
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
                            "name VARCHAR(45), " +
                            "lastName VARCHAR(45), " +
                            "age TINYINT)");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() { //удаляем таблицу юзеров из базы данных
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DROP TABLE IF EXISTS users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) { //добавляем нового пользователя в таблицу
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) { //удаляет пользователя из таблицы
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() { //получает всех пользователей из таблицы
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id, name, lastName, age FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() { //очищает таблицу удаляя все записи из нее
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("TRUNCATE TABLE users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
