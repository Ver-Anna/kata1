package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();
        us.createUsersTable();
        us.saveUser("Anna", "Vershinina", (byte) 24);
        us.saveUser("Vladimir", "Klepikov", (byte) 25);
        us.saveUser("Alexandr", "Anisin", (byte) 23);
        us.saveUser("Ivan", "Ivanov", (byte) 50);
        System.out.println(us.getAllUsers());
        us.removeUserById(4);
        System.out.println(us.getAllUsers());
        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
