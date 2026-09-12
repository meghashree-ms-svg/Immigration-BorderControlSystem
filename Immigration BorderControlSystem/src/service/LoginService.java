package service;

import model.Applicant;
import repository.ApplicantRepository;

public class LoginService {

    private ApplicantRepository repository;

    // Constructor
    public LoginService() {
        repository = new ApplicantRepository();
    }

    // Applicant Login
    public boolean login(String username, String password) {

        for (Applicant applicant : repository.getAllApplicants()) {

            if (applicant.getUsername().equals(username)
                    && applicant.getPassword().equals(password)) {

                return true;
            }
        }

        return false;
    }
}
