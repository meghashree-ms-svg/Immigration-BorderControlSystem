package model;

public class BorderControlOfficer extends User {

    private String employeeId;
    private String checkpoint;

    public BorderControlOfficer() {
    }

    public BorderControlOfficer(
            String userId,
            String name,
            String username,
            String password,
            String employeeId,
            String checkpoint) {

        super(userId, name, username, password);

        this.employeeId = employeeId;
        this.checkpoint = checkpoint;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    @Override
    public void displayMenu() {

        System.out.println("1. View Approved Travellers");
        System.out.println("2. Record Traveller Entry");
        System.out.println("3. Record Traveller Exit");
        System.out.println("4. Logout");
    }
}
