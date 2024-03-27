package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/schema_name";
    private static final String USER = "root";
    private static final String PASSWORD = "Ver_anna2000";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение с базой данных установлено");
        } catch (SQLException e) {
            System.out.println("Ошибка при установлении соединения с базой данных " + e.getMessage());
        }
        return connection;
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение с базой данных закрыто");
            } catch (SQLException e) {
                System.out.println("Ошибка при закрытии соединения с базой данных " + e.getMessage());
            }
        }
    }

    public static SessionFactory getDbConnectionHibernate() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/schema_name")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "Ver_anna2000")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .addAnnotatedClass(User.class);
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable e) {
            System.out.println("Не удалось установить соединение с БД");
            return null;
        }
//        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
//        try {
//            SessionFactory sessionFactory = configuration.buildSessionFactory();
//            System.out.println("Соединение с БД установлено");
//            return sessionFactory;
//        } catch (HibernateException e) {
//            System.out.println("Не удалось установить соединение с БД");
//            return null;
//        }
    }
}
