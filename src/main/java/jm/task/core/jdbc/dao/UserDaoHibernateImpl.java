package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {//открыли
//создаем таблицу используя SQL запрос
            transaction = session.beginTransaction();//начали транзакцию
            String sql = "CREATE TABLE IF NOT EXISTS Users (Id INT PRIMARY KEY AUTO_INCREMENT " +
                    ", Name VARCHAR (50) " +
                    ", LastName VARCHAR (50) " +
                    ", Age  INT)";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();//сохранили изменения
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS Users";
            //удаляем таблицу , с проверкой на существование
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        User newUser = new User(name, lastName, age);
        //добавление записи
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            session.save(newUser);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            //удаление юзера по ид
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        //получение всех записей
        List<User> usersList = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            String sql = "SELECT * FROM Users";
            Query<User> query = session.createNativeQuery(sql, User.class);
            usersList = query.getResultList();
            for (User user : usersList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE Users";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

}

