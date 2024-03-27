package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getDbConnectionHibernate();
    }

    @Override
    public void createUsersTable() { //создает таблицу пользователей в бд сделать с помощью sql
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), lastName VARCHAR(50), age int(10))").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() { //удаляет таблицу пользователей из бд сделать с помощью sql
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        //сохраняет нового пользователя
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) { //удаляет пользователя из бд по айди
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() { //возвращает список всех пользователей
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> user = null;
        try {
            user = session.createQuery("select p from User p", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void cleanUsersTable() { //очищает таблицу пользователей
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
