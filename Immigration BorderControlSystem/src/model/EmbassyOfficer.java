package model;

public class EmbassyOfficer extends User {

    private String employeeId;
    private String department;

    // Default Constructor
    public EmbassyOfficer() {

    }

    // Parameterized Constructor
    public EmbassyOfficer(String userId, String name, String username,
                          String password, String employeeId,
                          String department) {

        super(userId, name, username, password);

        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public void displayMenu() {

        System.out.println("===== Embassy Officer Menu =====");
        System.out.println("1. View Applications");
        System.out.println("2. Approve Visa");
        System.out.println("3. Reject Visa");
        System.out.println("4. Logout");

    }
}
