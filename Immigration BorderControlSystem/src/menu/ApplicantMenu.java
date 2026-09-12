package menu;

import model.Applicant;
import model.Visa;
import service.VisaService;
import java.util.Scanner;

public class ApplicantMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final VisaService visaService = new VisaService();

    public void showMenu(Applicant currentApplicant) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n===== APPLICANT MENU =====");
            System.out.println("1. View Profile");
            System.out.println("2. Apply for Visa");
            System.out.println("3. View Visa Status");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("\n--- PROFILE DETAILS ---");
                    // Used getUserId() from User superclass
                    System.out.println("Applicant ID : " + currentApplicant.getUserId());
                    System.out.println("Name         : " + currentApplicant.getName());
                    System.out.println("Passport No  : " + currentApplicant.getPassportNumber());
                }
                case 2 -> {
                    String visaId = "V-" + (1000 + (int)(Math.random() * 9000));

                    System.out.print("Enter Visa Type (Tourist/Business/Student): ");
                    String visaType = scanner.nextLine();

                    System.out.print("Enter Destination Country: ");
                    String destination = scanner.nextLine();

                    System.out.print("Enter Purpose of Travel: ");
                    String purpose = scanner.nextLine();

                    Visa visa = new Visa(
                            visaId,
                            currentApplicant.getUserId(), // Used getUserId()
                            visaType,
                            destination,
                            purpose,
                            "PENDING"
                    );

                    visaService.applyVisa(visa);
                }
                // Used getUserId()
                case 3 -> visaService.displayVisaStatusByApplicantId(currentApplicant.getUserId());
                case 4 -> {
                    System.out.println("\n[✓] Logged out successfully.");
                    loggedIn = false;
                }
                default -> System.out.println("\n[!] Invalid choice! Try again.");
            }
        }
    }
}
