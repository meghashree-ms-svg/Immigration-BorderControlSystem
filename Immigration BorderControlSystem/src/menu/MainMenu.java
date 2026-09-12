package menu;

import java.util.Scanner;

public class MainMenu {

    public int displayMainMenu() {

        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println(" IMMIGRATION BORDER CONTROL SYSTEM");
        System.out.println("=================================");
        System.out.println("1. Applicant Registration");
        System.out.println("2. Applicant Login");
        System.out.println("3. Embassy Officer Login");
        System.out.println("4. Border Control Officer Login");
        System.out.println("5. Exit");

        System.out.print("Enter your choice: ");

        return sc.nextInt();
    }
}