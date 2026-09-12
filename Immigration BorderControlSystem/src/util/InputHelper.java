package util;

import java.util.Scanner;

public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int readInt(String message) {
        System.out.print(message);
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return value;
    }

    public static void closeScanner() {
        scanner.close();
    }
}
