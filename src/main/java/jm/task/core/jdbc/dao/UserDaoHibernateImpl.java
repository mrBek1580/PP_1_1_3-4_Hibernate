package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getConnection();

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = """
                    CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY ,
                    firstName VARCHAR(64),
                    lastName VARCHAR(64),
                    age INT)
                    """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sql = """
                    DROP TABLE IF EXISTS users
                    """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь сохранен");
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("Пользователь удален");
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> allUsers = null;
        String sql = """
                    SELECT * FROM users
                    """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            allUsers = session.createSQLQuery(sql).addEntity(User.class).list();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String sql = """
                    DELETE FROM users
                    """;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException hibernateException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
