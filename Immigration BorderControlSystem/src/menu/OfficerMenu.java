package menu;

import java.util.Scanner;

public class OfficerMenu {

    public int displayOfficerMenu() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\n===== Embassy Officer Menu =====");
        System.out.println("1. View All Applications");
        System.out.println("2. Approve Visa");
        System.out.println("3. Reject Visa");
        System.out.println("4. Logout");

        System.out.print("Enter your choice: ");

        return sc.nextInt();
    }
}
