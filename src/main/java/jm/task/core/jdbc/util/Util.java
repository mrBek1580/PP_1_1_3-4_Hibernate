package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/preproject";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "9669Ayba";
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getConnection() {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", DRIVER);
            configuration.setProperty("hibernate.connection.url", URL);
            configuration.setProperty("hibernate.connection.username", LOGIN);
            configuration.setProperty("hibernate.connection.password", PASSWORD);
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}