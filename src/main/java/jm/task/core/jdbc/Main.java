package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Aibek", "Akynbekov", (byte)25);
        userService.saveUser("Beksultan", "Raimbekov", (byte)25);
        userService.saveUser("Aslkan", "Alimbekov", (byte)25);
        userService.saveUser("Azaliya", "Akynbekova", (byte)25);

        List<User> allUsers = userService.getAllUsers();
        for (User user: allUsers) {
            System.out.println(user);
        }

        userService.dropUsersTable();

        userService.cleanUsersTable();

    }
}
