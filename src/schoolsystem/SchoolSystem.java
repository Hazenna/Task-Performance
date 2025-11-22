package schoolsystem;

import java.util.*;

public class SchoolSystem {

    public StudentsDB students = new StudentsDB();
    static Scanner s = new Scanner(System.in);
    private final String admin = "Admin";
    public static SchoolSystem system = new SchoolSystem();

    static int choice(int min, int max) {
        while (true) {
            System.out.print("> ");
            String line = s.nextLine().trim();
            try {
                int c = Integer.parseInt(line);
                if (c >= min && c <= max) {
                    return c;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please Enter a Number between " + min + " and " + max);
            }
        }
    }

    public void choicesMenu(String user) {
        if (user.equalsIgnoreCase("user")) {
            System.out.println("1 - Access student database\n2 - View available uniforms\n3 - View available classrooms\n4 - Access teacher database\n5 - View subjects available");
            int choice = choice(1, 5);
            if (choice == 1) {
                students.displayAsUser();
            } else {
                System.out.println("Feature not implemented yet.");
            }
        } else if (user.equalsIgnoreCase("admin")) {
            System.out.println("1 - Edit student database\n2 - Edit available uniforms\n3 - Book available classrooms\n4 - Edit teacher database\n5 - Edit subjects available\n6 - Edit admin information");
            int choice = choice(1, 6);
            if (choice == 1) {
                students.displayAsAdmin();
            } else {
                System.out.println("Feature not implemented yet.");
            }
        }
    }

    public void menu() {
        System.out.println("Welcome to School Management System");
        System.out.print("Enter credentials or type User to log in as user: ");
        String cred = s.nextLine();
        if (cred.equalsIgnoreCase("User")) {
            String user = "user";
            choicesMenu(user);
        } else if (cred.equals(this.admin)) {
            String user = "admin";
            choicesMenu(user);
        } else {
            System.out.println("Unknown credential");
            menu();
        }
    }

    public static void main(String[] args) {
        system.menu();
    }

}
