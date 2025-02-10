package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();//Создание таблицы User(ов)

        // Добавление 4 User(ов) в таблицу с данными на свой выбор.
        userService.saveUser("Егор", "Круглов", (byte) 21);
        userService.saveUser("Михаил", "Бочкарев", (byte) 24);
        userService.saveUser("Александр", "Быстров", (byte) 41);
        userService.saveUser("Николай", "Колянов", (byte) 91);

        System.out.println(userService.getAllUsers());//Получение всех User из базы и вывод в консоль

        userService.cleanUsersTable();// Очистка таблицы User(ов)

        userService.dropUsersTable();//Удаление таблицы
    }
}