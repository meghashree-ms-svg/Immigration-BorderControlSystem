package service;

import model.EmbassyOfficer;
import repository.EmbassyOfficerRepository;

public class OfficerLoginService {

    private final EmbassyOfficerRepository repository;

    public OfficerLoginService() {
        repository = new EmbassyOfficerRepository();
    }

    public EmbassyOfficer login(
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

        EmbassyOfficer officer =
                repository.findByUsernameAndPassword(
                        username,
                        password
                );

        if (officer != null) {

            System.out.println(
                    "Embassy Officer login successful!"
            );

            return officer;
        }

        System.out.println(
                "Invalid officer username or password!"
        );

        return null;
    }
}
