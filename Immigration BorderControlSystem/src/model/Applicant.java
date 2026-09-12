package model;

public class Applicant extends User {

    private String passportNumber;
    private String nationality;
    private int age;
    private String visaStatus;

    public Applicant() {
    }

    public Applicant(String userId,
                     String name,
                     String username,
                     String password,
                     String passportNumber,
                     String nationality,
                     int age,
                     String visaStatus) {

        super(userId, name, username, password);

        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.age = age;
        this.visaStatus = visaStatus;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(String visaStatus) {
        this.visaStatus = visaStatus;
    }

    @Override
    public void displayMenu() {

        System.out.println("\n========== APPLICANT MENU ==========");
        System.out.println("1. Apply Visa");
        System.out.println("2. View Visa Status");
        System.out.println("3. Complete Missing Documents");
        System.out.println("4. Logout");
        System.out.println("====================================");
    }
}
