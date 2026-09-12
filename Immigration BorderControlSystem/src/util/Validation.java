package util;

import model.Applicant;

public class Validation {

    // =========================
    // NAME
    // =========================

    public static boolean isValidName(String name) {

        return name != null
                && !name.trim().isEmpty()
                && name.matches("[A-Za-z ]+");
    }

    public static String getNameError(String name) {

        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty.";
        }

        if (!name.matches("[A-Za-z ]+")) {
            return "Name must contain letters and spaces only.";
        }

        return "";
    }


    // =========================
    // USERNAME
    // =========================

    public static boolean isValidUsername(String username) {

        return username != null
                && username.matches("[A-Za-z0-9_]{4,20}");
    }

    public static String getUsernameError(String username) {

        if (username == null || username.isEmpty()) {
            return "Username cannot be empty.";
        }

        if (username.length() < 4 || username.length() > 20) {
            return "Username must contain 4-20 characters.";
        }

        if (!username.matches("[A-Za-z0-9_]+")) {
            return "Username can contain only letters, numbers and underscore (_).";
        }

        return "";
    }


    // =========================
    // PASSWORD
    // =========================

    public static boolean isValidPassword(String password) {

        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^A-Za-z0-9].*")
                && !password.contains(" ");
    }

    public static String getPasswordError(String password) {

        if (password == null || password.isEmpty()) {
            return "Password cannot be empty.";
        }

        if (password.length() < 8) {
            return "Password must contain at least 8 characters.";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least 1 uppercase letter.";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least 1 lowercase letter.";
        }

        if (!password.matches(".*[0-9].*")) {
            return "Password must contain at least 1 number.";
        }

        if (!password.matches(".*[^A-Za-z0-9].*")) {
            return "Password must contain at least 1 special character.";
        }

        if (password.contains(" ")) {
            return "Password must not contain spaces.";
        }

        return "";
    }


    // =========================
    // PASSPORT
    // =========================

    public static boolean isValidPassport(String passportNumber) {

        return passportNumber != null
                && passportNumber.matches("[A-Za-z][0-9]{7}");
    }

    public static String getPassportError(String passportNumber) {

        if (passportNumber == null || passportNumber.isEmpty()) {
            return "Passport number cannot be empty.";
        }

        if (!passportNumber.matches("[A-Za-z][0-9]{7}")) {
            return "Passport must contain 1 letter followed by 7 digits. Example: P1234567";
        }

        return "";
    }


    // =========================
    // AGE
    // =========================

    public static boolean isValidAge(int age) {

        return age >= 18 && age <= 100;
    }

    public static String getAgeError(int age) {

        if (age < 18) {
            return "Applicant must be at least 18 years old.";
        }

        if (age > 100) {
            return "Please enter a valid age.";
        }

        return "";
    }


    // =========================
    // NATIONALITY
    // =========================

    public static boolean isValidNationality(String nationality) {

        return nationality != null
                && !nationality.trim().isEmpty()
                && nationality.matches("[A-Za-z ]+");
    }

    public static String getNationalityError(String nationality) {

        if (nationality == null || nationality.trim().isEmpty()) {
            return "Nationality cannot be empty.";
        }

        if (!nationality.matches("[A-Za-z ]+")) {
            return "Nationality must contain letters and spaces only.";
        }

        return "";
    }


    // =========================
    // VISA ELIGIBILITY CHECK
    // =========================

    public static boolean isEligibleForVisa(Applicant applicant, String visaType) {

        if (getVisaEligibilityError(applicant, visaType).isEmpty()) {
            return true;
        }

        return false;
    }

    public static String getVisaEligibilityError(Applicant applicant, String visaType) {

        if (applicant == null) {
            return "Applicant details not found.";
        }

        // 1. Age Rule for Work/Business
        if ((visaType.equalsIgnoreCase("Work") || visaType.equalsIgnoreCase("Business")) && applicant.getAge() < 18) {
            return "Applicant must be at least 18 years old for Work or Business visa.";
        }

        // 2. Active Visa Rule
        if (applicant.getVisaStatus().equalsIgnoreCase("Pending")
                || applicant.getVisaStatus().equalsIgnoreCase("Approved")) {
            return "Applicant already has an active or pending visa application.";
        }

        // 3. Passport Validation
        if (!isValidPassport(applicant.getPassportNumber())) {
            return "A valid passport number is required to apply for a visa.";
        }

        return "";
    }
}