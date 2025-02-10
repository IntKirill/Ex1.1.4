package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection  =getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (Id INT PRIMARY KEY AUTO_INCREMENT " +
                ", Name VARCHAR (50) " +
                ", LastName VARCHAR (50) " +
                ", Age  INT)";
        // проверяем если таблицы с такими названием не существует , то создаем ее.
        try(Statement statement =  connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        //удаляем таблицу , с проверкой на существование
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (name , LastName, Age) VALUES (?, ?, ?) ";
        //Добавляем новые записи в таблицу
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE Id = ?";
        //удаление записи из таблицы по id
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        //Получение всех юзеров

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet =  preparedStatement.executeQuery(sql)){

            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));
                usersList.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE Users";
        //Очистка содержания таблицы
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
