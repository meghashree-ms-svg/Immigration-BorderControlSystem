package service;

import model.BorderControlOfficer;
import repository.BorderControlOfficerRepository;

public class BorderControlLoginService {

    private final BorderControlOfficerRepository repository;

    public BorderControlLoginService() {
        repository = new BorderControlOfficerRepository();
    }

    public BorderControlOfficer login(
            String username,
            String password) {

        if (username == null || username.trim().isEmpty()) {

            System.out.println(
                    "Username cannot be empty!"
            );

            return null;
        }

        if (password == null || password.trim().isEmpty()) {

            System.out.println(
                    "Password cannot be empty!"
            );

            return null;
        }

        BorderControlOfficer officer =
                repository.findByUsernameAndPassword(
                        username,
                        password
                );

        if (officer != null) {

            System.out.println(
                    "Border Control Officer login successful!"
            );

            return officer;
        }

        System.out.println(
                "Invalid Border Control Officer username or password!"
        );

        return null;
    }
}
