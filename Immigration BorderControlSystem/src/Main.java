import model.Applicant;
import model.Visa;
import repository.BorderTravelRepository;
import repository.VisaRepository;
import service.ApplicantService;
import service.OfficerLoginService;
import service.VisaDocumentService;
import service.VisaService;
import util.CloudinaryUploader;
import util.DatabaseConnection;
import model.EmbassyOfficer;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final ApplicantService applicantService =
            new ApplicantService();

    private static final VisaService visaService =
            new VisaService();

    private static final VisaDocumentService visaDocumentService =
            new VisaDocumentService();

    private static final OfficerLoginService officerLoginService =
            new OfficerLoginService();

    private static final VisaRepository visaRepository =
            new VisaRepository();

    private static final BorderTravelRepository borderTravelRepository =
            new BorderTravelRepository();


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        System.out.println("\n==============================================");
        System.out.println("     IMMIGRATION BORDER CONTROL SYSTEM");
        System.out.println("==============================================");

        while (true) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Applicant Registration");
            System.out.println("2. Applicant Login");
            System.out.println("3. Embassy Officer Login");
            System.out.println("4. Border Control Officer Login");
            System.out.println("5. Exit");
            System.out.println("===============================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    registerApplicant();
                    break;

                case 2:
                    applicantLogin();
                    break;

                case 3:
                    embassyOfficerLogin();
                    break;

                case 4:
                    borderControlOfficerLogin();
                    break;

                case 5:
                    System.out.println(
                            "\nThank you for using the system!"
                    );
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice!");
            }
        }
    }


    // =========================================================
    // APPLICANT REGISTRATION
    // =========================================================

    private static void registerApplicant() {

        System.out.println("\n========== APPLICANT REGISTRATION ==========");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter passport number: ");
        String passportNumber = scanner.nextLine();

        System.out.print("Enter nationality: ");
        String nationality = scanner.nextLine();

        int age = readInt("Enter age: ");

        String userId = generateApplicantId();

        Applicant applicant = new Applicant(
                userId,
                name,
                username,
                password,
                passportNumber,
                nationality,
                age,
                "NOT_APPLIED"
        );

        applicantService.registerApplicant(applicant);

        System.out.println("\n==========================================");
        System.out.println("Applicant registration process completed!");
        System.out.println("Applicant ID : " + userId);
        System.out.println("==========================================");
    }


    // =========================================================
    // APPLICANT LOGIN
    // =========================================================

    private static void applicantLogin() {

        while (true) {

            System.out.println(
                    "\n========== APPLICANT LOGIN =========="
            );

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            Applicant applicant =
                    applicantService.loginApplicant(
                            username,
                            password
                    );

            if (applicant != null) {

                System.out.println(
                        "\n======================================"
                );

                System.out.println(
                        "Login successful!"
                );

                System.out.println(
                        "Welcome, " + applicant.getName() + "!"
                );

                System.out.println(
                        "======================================"
                );

                applicantMenu(applicant);
                return;
            }

            System.out.println(
                    "\n❌ Invalid username or password!"
            );

            System.out.println(
                    "\nWhat would you like to do?"
            );

            System.out.println("1. Try Login Again");
            System.out.println("2. Forgot Password");
            System.out.println("3. Logout");

            int choice =
                    readInt("Enter your choice: ");

            if (choice == 1) {
                continue;
            }

            if (choice == 2) {
                resetPassword("Applicant", "applicants", "user_id");
                continue;
            }

            if (choice == 3) {
                System.out.println(
                        "\nReturning to main menu..."
                );
                return;
            }

            System.out.println("Invalid choice!");
        }
    }


    // =========================================================
    // APPLICANT MENU
    // =========================================================

    private static void applicantMenu(Applicant applicant) {

        while (true) {

            applicant.displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    applyForVisa(applicant);
                    break;

                case 2:
                    visaService.displayVisaStatusByApplicantId(
                            applicant.getUserId());
                    break;

                case 3:
                    completeMissingDocuments(applicant);
                    break;

                case 4:
                    System.out.println("\nLogged out successfully.");
                    return;

                default:
                    System.out.println("\nInvalid choice!");
            }
        }
    }


    // =========================================================
    // APPLY VISA
    // =========================================================

    private static void applyForVisa(Applicant applicant) {

        System.out.println("\n========== APPLY FOR VISA ==========");

        Visa existingVisa =
                visaRepository.findByApplicantId(applicant.getUserId());

        if (existingVisa != null) {

            System.out.println("\nYou already have a visa application.");

            System.out.println("Visa ID : "
                    + existingVisa.getVisaId());

            System.out.println("Status  : "
                    + existingVisa.getStatus());

            return;
        }

        System.out.println("\nVisa Types:");
        System.out.println("1. Student");
        System.out.println("2. Tourist");
        System.out.println("3. Business");
        System.out.println("4. Work");
        System.out.println("5. General");

        int typeChoice = readInt("Choose visa type: ");

        String visaType;

        switch (typeChoice) {

            case 1:
                visaType = "Student";
                break;

            case 2:
                visaType = "Tourist";
                break;

            case 3:
                visaType = "Business";
                break;

            case 4:
                visaType = "Work";
                break;

            case 5:
                visaType = "General";
                break;

            default:
                System.out.println("Invalid visa type!");
                return;
        }

        System.out.print("Destination country: ");
        String destinationCountry = scanner.nextLine();

        System.out.print("Purpose of travel: ");
        String purpose = scanner.nextLine();

        String visaId = generateVisaId();

        Visa visa = new Visa(
                visaId,
                applicant.getUserId(),
                visaType,
                destinationCountry,
                purpose,
                "DOCUMENTS_INCOMPLETE"
        );

        visaService.applyVisa(visa);

        System.out.println("\nVisa ID generated: " + visaId);

        System.out.println("\n========== DOCUMENT CHECKLIST ==========");

        uploadRequiredDocuments(
                applicant,
                visaId,
                visaType
        );

        updateDocumentCompletionStatus(
                visaId,
                visaType
        );
    }


    // =========================================================
    // DOCUMENT CHECKLIST
    // =========================================================

    private static List<DocumentRequirement> getChecklist(
            String visaType) {

        List<DocumentRequirement> checklist =
                new ArrayList<>();

        // Common mandatory documents
        checklist.add(new DocumentRequirement(
                "Passport",
                "MANDATORY"
        ));

        checklist.add(new DocumentRequirement(
                "Passport-size Photograph",
                "MANDATORY"
        ));

        if (visaType.equalsIgnoreCase("Student")) {

            checklist.add(new DocumentRequirement(
                    "University Admission Letter",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Academic Qualification Certificate",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Proof of Financial Support",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Accommodation Proof",
                    "CONDITIONAL"
            ));
        }

        else if (visaType.equalsIgnoreCase("Tourist")) {

            checklist.add(new DocumentRequirement(
                    "Travel Itinerary",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Accommodation Proof",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Travel Medical Insurance",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Proof of Financial Means",
                    "MANDATORY"
            ));
        }

        else if (visaType.equalsIgnoreCase("Business")) {

            checklist.add(new DocumentRequirement(
                    "Travel Itinerary",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Company Covering Letter",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Invitation Letter",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Proof of Financial Means",
                    "MANDATORY"
            ));
        }

        else if (visaType.equalsIgnoreCase("Work")) {

            checklist.add(new DocumentRequirement(
                    "Employment Offer Letter",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Employment Contract",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Educational Qualification Certificate",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Proof of Financial Support",
                    "MANDATORY"
            ));
        }

        else {

            checklist.add(new DocumentRequirement(
                    "Proof of Purpose of Travel",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Proof of Financial Means",
                    "MANDATORY"
            ));

            checklist.add(new DocumentRequirement(
                    "Accommodation Proof",
                    "CONDITIONAL"
            ));
        }

        // Supporting document available for all visa types
        checklist.add(new DocumentRequirement(
                "Additional Supporting Document",
                "SUPPORTING"
        ));

        return checklist;
    }


    // =========================================================
    // INITIAL DOCUMENT UPLOAD
    // =========================================================

    private static void uploadRequiredDocuments(
            Applicant applicant,
            String visaId,
            String visaType) {

        List<DocumentRequirement> checklist =
                getChecklist(visaType);

        for (DocumentRequirement requirement : checklist) {

            System.out.println("\n--------------------------------------");
            System.out.println("Document : "
                    + requirement.documentType);

            System.out.println("Requirement : "
                    + requirement.requirementType);

            if (requirement.requirementType.equals("SUPPORTING")) {

                System.out.println(
                        "This document is optional."
                );

                System.out.print(
                        "Do you want to upload it? (yes/no): "
                );

                String answer =
                        scanner.nextLine();

                if (!answer.equalsIgnoreCase("yes")) {
                    continue;
                }
            }

            else if (requirement.requirementType.equals("CONDITIONAL")) {

                System.out.println(
                        "This document is conditional."
                );

                System.out.print(
                        "Do you want to upload it? (yes/no): "
                );

                String answer =
                        scanner.nextLine();

                if (!answer.equalsIgnoreCase("yes")) {
                    continue;
                }
            }

            else {

                System.out.println(
                        "This document is mandatory."
                );

                System.out.print(
                        "Do you want to upload it now? (yes/no): "
                );

                String answer =
                        scanner.nextLine();

                if (!answer.equalsIgnoreCase("yes")) {

                    System.out.println(
                            "Document skipped."
                    );

                    continue;
                }
            }

            uploadSingleDocument(
                    applicant,
                    visaId,
                    requirement
            );
        }
    }


    // =========================================================
    // COMPLETE MISSING DOCUMENTS
    // =========================================================

    private static void completeMissingDocuments(
            Applicant applicant) {

        Visa visa =
                visaRepository.findByApplicantId(
                        applicant.getUserId());

        if (visa == null) {

            System.out.println(
                    "\nYou do not have a visa application yet."
            );

            return;
        }

        if (visa.getStatus().equalsIgnoreCase("Approved")) {

            System.out.println(
                    "\nYour visa has already been approved."
            );

            return;
        }

        if (visa.getStatus().equalsIgnoreCase("Rejected")) {

            System.out.println(
                    "\nYour visa application has been rejected."
            );

            return;
        }

        System.out.println(
                "\n========== MISSING MANDATORY DOCUMENTS =========="
        );

        List<DocumentRequirement> checklist =
                getChecklist(visa.getVisaType());

        Set<String> uploadedDocuments =
                getUploadedDocumentTypes(visa.getVisaId());

        boolean missingFound = false;

        for (DocumentRequirement requirement : checklist) {

            if (!requirement.requirementType
                    .equals("MANDATORY")) {
                continue;
            }

            if (!uploadedDocuments.contains(
                    requirement.documentType)) {

                missingFound = true;

                System.out.println(
                        "\nMissing Document: "
                                + requirement.documentType
                );

                System.out.print(
                        "Enter local file path: "
                );

                String path =
                        scanner.nextLine();

                String documentUrl =
                        uploadToCloudinary(path);

                if (documentUrl == null) {
                    continue;
                }

                boolean saved =
                        visaDocumentService.saveDocument(
                                visa.getVisaId(),
                                applicant.getUserId(),
                                requirement.documentType,
                                documentUrl,
                                requirement.requirementType
                        );

                if (saved) {

                    System.out.println(
                            "Verification Status: VERIFIED"
                    );

                    System.out.println(
                            "Document uploaded successfully!"
                    );
                }
            }
        }

        if (!missingFound) {

            System.out.println(
                    "\nAll mandatory documents are already uploaded."
            );
        }

        updateDocumentCompletionStatus(
                visa.getVisaId(),
                visa.getVisaType()
        );
    }


    // =========================================================
    // SINGLE DOCUMENT UPLOAD
    // =========================================================

    private static void uploadSingleDocument(
            Applicant applicant,
            String visaId,
            DocumentRequirement requirement) {

        System.out.print(
                "Enter local file path: "
        );

        String path =
                scanner.nextLine();

        String documentUrl =
                uploadToCloudinary(path);

        if (documentUrl == null) {
            return;
        }

        boolean saved =
                visaDocumentService.saveDocument(
                        visaId,
                        applicant.getUserId(),
                        requirement.documentType,
                        documentUrl,
                        requirement.requirementType
                );

        if (saved) {

            System.out.println(
                    "\nDocument uploaded successfully!"
            );

            System.out.println(
                    "Verification Status: VERIFIED"
            );

            System.out.println(
                    "Cloudinary URL: "
                            + documentUrl
            );
        }
    }


    // =========================================================
    // CLOUDINARY UPLOAD
    // =========================================================

    private static String uploadToCloudinary(
            String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {

            System.out.println(
                    "File does not exist!"
            );

            return null;
        }

        if (!file.isFile()) {

            System.out.println(
                    "Invalid file path!"
            );

            return null;
        }

        try {

            String url =
                    CloudinaryUploader.uploadDocument(
                            filePath
                    );

            if (url == null ||
                    url.trim().isEmpty()) {

                System.out.println(
                        "Cloudinary upload failed!"
                );

                return null;
            }

            return url;

        } catch (Exception e) {

            System.out.println(
                    "Cloudinary upload failed!"
            );

            e.printStackTrace();

            return null;
        }
    }


    // =========================================================
    // UPDATE DOCUMENT COMPLETION STATUS
    // =========================================================

    private static void updateDocumentCompletionStatus(
            String visaId,
            String visaType) {

        List<DocumentRequirement> checklist =
                getChecklist(visaType);

        Set<String> uploadedDocuments =
                getUploadedDocumentTypes(visaId);

        boolean allMandatoryPresent = true;

        for (DocumentRequirement requirement :
                checklist) {

            if (!requirement.requirementType
                    .equals("MANDATORY")) {
                continue;
            }

            if (!uploadedDocuments.contains(
                    requirement.documentType)) {

                allMandatoryPresent = false;
                break;
            }
        }

        if (allMandatoryPresent) {

            visaRepository.updateVisaStatus(
                    visaId,
                    "DOCUMENTS_VERIFIED"
            );

            System.out.println(
                    "\n======================================"
            );

            System.out.println(
                    "ALL MANDATORY DOCUMENTS VERIFIED!"
            );

            System.out.println(
                    "Visa is now ready for Embassy Officer review."
            );

            System.out.println(
                    "======================================"
            );

        } else {

            visaRepository.updateVisaStatus(
                    visaId,
                    "DOCUMENTS_INCOMPLETE"
            );

            System.out.println(
                    "\nSome mandatory documents are still missing."
            );

            System.out.println(
                    "Visa Status: DOCUMENTS_INCOMPLETE"
            );
        }
    }


    // =========================================================
    // GET UPLOADED DOCUMENT TYPES
    // =========================================================

    private static Set<String> getUploadedDocumentTypes(
            String visaId) {

        Set<String> documents =
                new HashSet<>();

        String sql = """
                SELECT document_type
                FROM visa_documents
                WHERE visa_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visaId);

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                documents.add(
                        resultSet.getString(
                                "document_type"
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to fetch uploaded documents!"
            );

            e.printStackTrace();
        }

        return documents;
    }


    // =========================================================
    // EMBASSY OFFICER LOGIN
    // =========================================================

    private static void embassyOfficerLogin() {

        while (true) {

            System.out.println(
                    "\n========== EMBASSY OFFICER LOGIN =========="
            );

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            EmbassyOfficer officer =
                    officerLoginService.login(username, password);

            if (officer != null) {

                System.out.println(
                        "\nEmbassy Officer Login Successful!"
                );

                embassyOfficerMenu();
                return;
            }

            System.out.println(
                    "\n❌ Invalid username or password!"
            );

            System.out.println("\n1. Try Login Again");
            System.out.println("2. Forgot Password");
            System.out.println("3. Logout");

            int choice = readInt("Enter your choice: ");

            if (choice == 1) {
                continue;
            }

            if (choice == 2) {
                resetPassword("Embassy Officer", "embassy_officers", "officer_id");
                continue;
            }

            if (choice == 3) {
                System.out.println(
                        "\nReturning to main menu..."
                );
                return;
            }

            System.out.println("Invalid choice!");
        }
    }


    // =========================================================
    // EMBASSY OFFICER MENU
    // =========================================================

    private static void embassyOfficerMenu() {

        while (true) {

            System.out.println(
                    "\n========== EMBASSY OFFICER MENU =========="
            );

            System.out.println(
                    "1. View Visa Applications"
            );

            System.out.println(
                    "2. Review Visa Application"
            );

            System.out.println(
                    "3. Approve Visa"
            );

            System.out.println(
                    "4. Reject Visa"
            );

            System.out.println(
                    "5. Logout"
            );

            int choice =
                    readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    visaService.displayAllVisas();
                    break;

                case 2:
                    reviewVisaApplication();
                    break;

                case 3:
                    approveVisa();
                    break;

                case 4:
                    rejectVisa();
                    break;

                case 5:
                    System.out.println(
                            "Officer logged out."
                    );
                    return;

                default:
                    System.out.println(
                            "Invalid choice!"
                    );
            }
        }
    }


    // =========================================================
    // REVIEW VISA
    // =========================================================

    private static void reviewVisaApplication() {

        System.out.print(
                "\nEnter Visa ID: "
        );

        String visaId =
                scanner.nextLine();

        Visa visa =
                visaRepository.findByVisaId(
                        visaId
                );

        if (visa == null) {

            System.out.println(
                    "Visa not found!"
            );

            return;
        }

        displayVisaDetails(visa);

        visaDocumentService.displayDocuments(
                visaId
        );
    }


    // =========================================================
    // APPROVE VISA
    // =========================================================

    private static void approveVisa() {

        System.out.print(
                "\nEnter Visa ID to approve: "
        );

        String visaId =
                scanner.nextLine();

        Visa visa =
                visaRepository.findByVisaId(
                        visaId
                );

        if (visa == null) {

            System.out.println(
                    "Visa not found!"
            );

            return;
        }

        if (!visa.getStatus().equalsIgnoreCase(
                "DOCUMENTS_VERIFIED")) {

            System.out.println(
                    "\nVisa cannot be approved."
            );

            System.out.println(
                    "Current Status: "
                            + visa.getStatus()
            );

            System.out.println(
                    "All mandatory documents must be verified first."
            );

            return;
        }

        visaDocumentService.displayDocuments(
                visaId
        );

        System.out.print(
                "\nConfirm approval? (yes/no): "
        );

        String confirmation =
                scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                    "Approval cancelled."
            );

            return;
        }

        visaRepository.updateVisaStatus(
                visaId,
                "Approved"
        );

        System.out.println(
                "\n===================================="
        );

        System.out.println(
                "VISA APPROVED SUCCESSFULLY!"
        );

        System.out.println(
                "===================================="
        );
    }


    // =========================================================
    // REJECT VISA
    // =========================================================

    private static void rejectVisa() {

        System.out.print(
                "\nEnter Visa ID to reject: "
        );

        String visaId =
                scanner.nextLine();

        Visa visa =
                visaRepository.findByVisaId(
                        visaId
                );

        if (visa == null) {

            System.out.println(
                    "Visa not found!"
            );

            return;
        }

        System.out.print(
                "Enter rejection reason: "
        );

        String reason =
                scanner.nextLine();

        visaRepository.updateVisaStatus(
                visaId,
                "Rejected"
        );

        System.out.println(
                "\nVisa rejected."
        );

        System.out.println(
                "Reason: " + reason
        );
    }


    // =========================================================
    // BORDER CONTROL OFFICER LOGIN
    // =========================================================

    private static void borderControlOfficerLogin() {

        while (true) {

            System.out.println(
                    "\n========== BORDER CONTROL OFFICER LOGIN =========="
            );

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (loginBorderOfficer(username, password)) {

                System.out.println(
                        "\nBorder Control Officer Login Successful!"
                );

                borderControlOfficerMenu();
                return;
            }

            System.out.println(
                    "\n❌ Invalid username or password!"
            );

            System.out.println("\n1. Try Login Again");
            System.out.println("2. Forgot Password");
            System.out.println("3. Logout");

            int choice = readInt("Enter your choice: ");

            if (choice == 1) {
                continue;
            }

            if (choice == 2) {
                resetPassword("Border Control Officer", "border_control_officers", "user_id");
                continue;
            }

            if (choice == 3) {
                System.out.println(
                        "\nReturning to main menu..."
                );
                return;
            }

            System.out.println("Invalid choice!");
        }
    }

    // =========================================================
    // BORDER OFFICER LOGIN CHECK
    // =========================================================

    private static boolean loginBorderOfficer(
            String username,
            String password) {

        String sql = """
                SELECT user_id
                FROM border_control_officers
                WHERE BINARY username = ?
                AND BINARY password = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            System.out.println(
                    "Border officer login failed!"
            );

            e.printStackTrace();
        }

        return false;
    }


    // =========================================================
    // BORDER CONTROL OFFICER MENU
    // =========================================================

    private static void borderControlOfficerMenu() {

        while (true) {

            System.out.println(
                    "\n========== BORDER CONTROL OFFICER MENU =========="
            );

            System.out.println(
                    "1. Check Traveller"
            );

            System.out.println(
                    "2. Record Traveller Entry"
            );

            System.out.println(
                    "3. Record Traveller Exit"
            );

            System.out.println(
                    "4. View Travel Records"
            );

            System.out.println(
                    "5. Logout"
            );

            int choice =
                    readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    checkTraveller();
                    break;

                case 2:
                    recordTravellerEntry();
                    break;

                case 3:
                    recordTravellerExit();
                    break;

                case 4:
                    borderTravelRepository
                            .displayTravelRecords();
                    break;

                case 5:
                    System.out.println(
                            "Border officer logged out."
                    );
                    return;

                default:
                    System.out.println(
                            "Invalid choice!"
                    );
            }
        }
    }


    // =========================================================
    // CHECK TRAVELLER
    // =========================================================

    private static boolean checkTraveller() {

        System.out.println(
                "\n========== TRAVELLER VERIFICATION =========="
        );

        System.out.print(
                "Enter Applicant ID: "
        );

        String applicantId =
                scanner.nextLine();

        Applicant applicant =
                findApplicantById(
                        applicantId
                );

        if (applicant == null) {

            System.out.println(
                    "\nTraveller not found!"
            );

            return false;
        }

        System.out.println(
                "\nApplicant Name: "
                        + applicant.getName()
        );

        System.out.println(
                "Passport Number: "
                        + applicant.getPassportNumber()
        );

        // BLACKLIST CHECK
        if (isBlacklisted(
                applicant.getUserId(),
                applicant.getPassportNumber())) {

            System.out.println(
                    "\n=========================================="
            );

            System.out.println(
                    "        🚨 TRAVELLER BLACKLISTED 🚨"
            );

            System.out.println(
                    "ENTRY DENIED"
            );

            System.out.println(
                    "=========================================="
            );

            return false;
        }

        Visa visa =
                visaRepository.findByApplicantId(
                        applicant.getUserId()
                );

        if (visa == null) {

            System.out.println(
                    "\nNo visa application found."
            );

            return false;
        }

        System.out.println(
                "Visa ID: " + visa.getVisaId()
        );

        System.out.println(
                "Visa Status: " + visa.getStatus()
        );

        if (!visa.getStatus().equalsIgnoreCase(
                "Approved")) {

            System.out.println(
                    "\nVisa is not approved."
            );

            System.out.println(
                    "ENTRY DENIED"
            );

            return false;
        }

        System.out.println(
                "\nTraveller verification successful!"
        );

        System.out.println(
                "Visa is valid."
        );

        System.out.println(
                "Traveller is NOT blacklisted."
        );

        System.out.println(
                "ENTRY ELIGIBLE"
        );

        return true;
    }


    // =========================================================
    // RECORD ENTRY
    // =========================================================

    private static void recordTravellerEntry() {

        System.out.println(
                "\n========== TRAVELLER ENTRY =========="
        );

        System.out.print(
                "Enter Applicant ID: "
        );

        String applicantId =
                scanner.nextLine();

        Applicant applicant =
                findApplicantById(
                        applicantId
                );

        if (applicant == null) {

            System.out.println(
                    "Traveller not found!"
            );

            return;
        }

        // AUTOMATIC BLACKLIST CHECK
        if (isBlacklisted(
                applicant.getUserId(),
                applicant.getPassportNumber())) {

            System.out.println(
                    "\n🚨 ENTRY DENIED — TRAVELLER IS BLACKLISTED!"
            );

            return;
        }

        Visa visa =
                visaRepository.findByApplicantId(
                        applicant.getUserId()
                );

        if (visa == null) {

            System.out.println(
                    "No visa found!"
            );

            return;
        }

        // VISA APPROVAL CHECK
        if (!visa.getStatus().equalsIgnoreCase(
                "Approved")) {

            System.out.println(
                    "\nENTRY DENIED."
            );

            System.out.println(
                    "Visa is not approved."
            );

            return;
        }

        // DUPLICATE ENTRY CHECK
        if (borderTravelRepository.hasActiveEntry(
                applicant.getUserId(),
                visa.getVisaId())) {

            System.out.println(
                    "\nTraveller is already inside."
            );

            return;
        }

        borderTravelRepository.recordEntry(
                applicant.getUserId(),
                visa.getVisaId(),
                applicant.getPassportNumber()
        );

        System.out.println(
                "\nENTRY ALLOWED."
        );
    }


    // =========================================================
    // RECORD EXIT
    // =========================================================

    private static void recordTravellerExit() {

        System.out.println(
                "\n========== TRAVELLER EXIT =========="
        );

        System.out.print(
                "Enter Applicant ID: "
        );

        String applicantId =
                scanner.nextLine();

        Visa visa =
                visaRepository.findByApplicantId(
                        applicantId
                );

        if (visa == null) {

            System.out.println(
                    "Visa not found!"
            );

            return;
        }

        boolean success =
                borderTravelRepository.recordExit(
                        applicantId,
                        visa.getVisaId()
                );

        if (success) {

            System.out.println(
                    "\nTraveller exit recorded successfully."
            );

        } else {

            System.out.println(
                    "\nNo active entry record found."
            );
        }
    }


    // =========================================================
    // BLACKLIST CHECK
    // =========================================================

    private static boolean isBlacklisted(
            String applicantId,
            String passportNumber) {

        String sql = """
                SELECT blacklist_id,
                       reason,
                       status
                FROM blacklist
                WHERE (applicant_id = ?
                       OR passport_number = ?)
                AND status = 'ACTIVE'
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, applicantId);
            statement.setString(2, passportNumber);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                System.out.println(
                        "\nBlacklist Reason: "
                                + resultSet.getString(
                                "reason")
                );

                return true;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to perform blacklist check!"
            );

            e.printStackTrace();
        }

        return false;
    }


    // =========================================================
    // FIND APPLICANT
    // =========================================================

    private static Applicant findApplicantById(
            String applicantId) {

        String sql = """
                SELECT user_id,
                       name,
                       username,
                       password,
                       passport_number,
                       nationality,
                       age,
                       visa_status
                FROM applicants
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, applicantId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new Applicant(
                        resultSet.getString("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("passport_number"),
                        resultSet.getString("nationality"),
                        resultSet.getInt("age"),
                        resultSet.getString("visa_status")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to find applicant!"
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // DISPLAY VISA
    // =========================================================

    private static void displayVisaDetails(
            Visa visa) {

        System.out.println(
                "\n========== VISA APPLICATION =========="
        );

        System.out.println(
                "Visa ID             : "
                        + visa.getVisaId()
        );

        System.out.println(
                "Applicant ID        : "
                        + visa.getApplicantId()
        );

        System.out.println(
                "Visa Type           : "
                        + visa.getVisaType()
        );

        System.out.println(
                "Destination Country : "
                        + visa.getDestinationCountry()
        );

        System.out.println(
                "Purpose             : "
                        + visa.getPurpose()
        );

        System.out.println(
                "Status              : "
                        + visa.getStatus()
        );

        System.out.println(
                "======================================"
        );
    }


    // =========================================================
    // RESET PASSWORD
    // =========================================================

    private static void resetPassword(String role, String tableName, String idColumnName) {
        System.out.println("\n========== FORGOT PASSWORD (" + role.toUpperCase() + ") ==========");
        System.out.print("Enter " + role + " ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        // Verify ID + Username dynamically based on table
        String checkSql = "SELECT * FROM " + tableName + " WHERE BINARY " + idColumnName + " = ? AND BINARY username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(checkSql)) {

            statement.setString(1, id);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("\n❌ " + role + " ID or Username is incorrect!");
                System.out.println("Password was NOT changed.");
                return;
            }

        } catch (SQLException e) {
            System.out.println("\nFailed to verify " + role + "!");
            e.printStackTrace();
            return;
        }

        // Get New Password
        System.out.print("\nEnter New Password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();

        if (newPassword.isEmpty()) {
            System.out.println("\n❌ Password cannot be empty!");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("\n❌ Passwords do not match!");
            return;
        }

        // Update password dynamically
        String updateSql = "UPDATE " + tableName + " SET password = ? WHERE BINARY " + idColumnName + " = ? AND BINARY username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSql)) {

            statement.setString(1, newPassword);
            statement.setString(2, id);
            statement.setString(3, username);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("\n======================================");
                System.out.println("PASSWORD CHANGED SUCCESSFULLY!");
                System.out.println("Please login using your new password.");
                System.out.println("======================================");
            } else {
                System.out.println("\n❌ Password reset failed!");
            }

        } catch (SQLException e) {
            System.out.println("\n❌ Failed to update password!");
            e.printStackTrace();
        }
    }


    // =========================================================
    // ID GENERATORS
    // =========================================================

    private static String generateApplicantId() {

        Random random =
                new Random();

        return "APP"
                + (10000 + random.nextInt(90000));
    }


    private static String generateVisaId() {

        Random random =
                new Random();

        return "VISA"
                + (10000 + random.nextInt(90000));
    }


    // =========================================================
    // INTEGER INPUT
    // =========================================================

    private static int readInt(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // =========================================================
    // DOCUMENT REQUIREMENT CLASS
    // =========================================================

    private static class DocumentRequirement {

        private final String documentType;
        private final String requirementType;

        public DocumentRequirement(
                String documentType,
                String requirementType) {

            this.documentType =
                    documentType;

            this.requirementType =
                    requirementType;
        }
    }
}