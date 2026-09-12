package service;

import model.Visa;
import repository.VisaRepository;

public class VisaService {

    private final VisaRepository repository;

    // Constructor
    public VisaService() {
        repository = new VisaRepository();
    }

    // Apply for Visa
    public void applyVisa(Visa visa) {

        if (visa == null) {
            System.out.println("Visa application cannot be empty!");
            return;
        }

        // Check whether this visa ID already exists
        if (repository.findByVisaId(visa.getVisaId()) != null) {
            System.out.println("Visa ID already exists!");
            return;
        }

        repository.addVisa(visa);

        System.out.println("Visa application submitted successfully!");
    }

    // Display All Visa Applications
    public void displayAllVisas() {

        System.out.println("\n===== ALL VISA APPLICATIONS =====");

        repository.displayAllVisas();

        System.out.println("=================================");
    }

    // Find Visa by Visa ID
    public Visa getVisaById(String visaId) {

        if (visaId == null || visaId.trim().isEmpty()) {
            System.out.println("Visa ID cannot be empty!");
            return null;
        }

        return repository.findByVisaId(visaId);
    }

    // Update Visa Status
    public void updateVisaStatus(String visaId, String status) {

        if (visaId == null || visaId.trim().isEmpty()) {
            System.out.println("Visa ID cannot be empty!");
            return;
        }

        if (status == null || status.trim().isEmpty()) {
            System.out.println("Visa status cannot be empty!");
            return;
        }

        repository.updateVisaStatus(visaId, status);
    }
    public void displayVisaStatusByApplicantId(String applicantId) {
        Visa visa = repository.findByApplicantId(applicantId);
        if (visa != null) {
            System.out.println("\n========================================");
            System.out.println("          YOUR VISA STATUS              ");
            System.out.println("========================================");
            System.out.println("Visa ID             : " + visa.getVisaId());
            System.out.println("Visa Type           : " + visa.getVisaType());
            System.out.println("Destination Country : " + visa.getDestinationCountry());
            System.out.println("Purpose             : " + visa.getPurpose());
            System.out.println("Status              : " + visa.getStatus());
            System.out.println("========================================\n");
        } else {
            System.out.println("\n[!] No visa application found for your account.\n");
        }
    }
    public Visa getVisaByApplicantId(String applicantId) {

        if (applicantId == null || applicantId.trim().isEmpty()) {
            return null;
        }

        return repository.findByApplicantId(applicantId);
    }
}
