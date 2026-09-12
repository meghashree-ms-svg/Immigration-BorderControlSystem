package service;

import model.Applicant;
import repository.ApplicantRepository;

public class ApplicantService {

    private final ApplicantRepository repository;

    public ApplicantService() {
        repository = new ApplicantRepository();
    }

    // Register Applicant
    public void registerApplicant(Applicant applicant) {

        if (applicant == null) {
            System.out.println("Applicant details cannot be empty!");
            return;
        }

        // Check whether username already exists
        Applicant existingApplicant =
                repository.findByUsername(applicant.getUsername());

        if (existingApplicant != null) {
            System.out.println("Username already exists!");
            return;
        }

        repository.addApplicant(applicant);

        System.out.println("Applicant registered successfully!");
    }

    // Login Applicant
    public Applicant loginApplicant(String username, String password) {

        if (username == null || password == null) {
            System.out.println("Username and password cannot be empty!");
            return null;
        }

        Applicant applicant =
                repository.findByUsernameAndPassword(username, password);

        if (applicant != null) {
            System.out.println("Applicant login successful!");
            return applicant;
        }

        System.out.println("Invalid username or password!");
        return null;
    }

    // Get Applicant by username
    public Applicant getApplicantByUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        return repository.findByUsername(username);
    }
}
