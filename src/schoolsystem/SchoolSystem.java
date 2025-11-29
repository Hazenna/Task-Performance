package schoolsystem;

import java.util.*;

public class SchoolSystem {

    public StudentsDB students = new StudentsDB();
    static Scanner s = new Scanner(System.in);
    private String admin = "Admin";
    public static SchoolSystem system = new SchoolSystem();
    public static Teachers teacher = new Teachers();
    public static TeachersDB tdb = new TeachersDB();
    private boolean sentinel;

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

    private void changeCredential() {
        sentinel = true;
        while (sentinel) {
            System.out.print("Enter current credential or B to return to the admin menu: ");
            String input = s.nextLine();
            if (input.equals(admin)) {
                sentinel = true;
                while (sentinel) {
                    System.out.print("Enter new credential: ");
                    String changeCred = s.nextLine();
                    if (changeCred.equals(admin)) {
                        System.out.println("That is already the current credential");
                    } else {
                        admin = changeCred;
                        System.out.println("Credentials updated");
                        choicesMenu(admin);
                        sentinel = false;
                    }
                }
            } else if (input.equalsIgnoreCase("b")) {
                choicesMenu(this.admin);
            } else {
                System.out.println("Incorrect credential");
            }
        }
    }

    public void choicesMenu(String user) {
        if (user.equalsIgnoreCase("user")) {
            System.out.println("""
                               1 - Access student database
                               2 - View available classrooms
                               3 - Access teacher database
                               4 - View subjects available
                               5 - Exit""");
            int choice = choice(1, 5);
            switch (choice) {
                case 1 ->
                    students.displayAsUser();
                case 2 ->
                    RoomsTp.menuView();
                case 3 ->
                    tdb.displayAsUser();
                case 4 ->
                    System.out.println("Feature not implemented yet.");
                case 5 ->
                    System.exit(0);
            }
        } else if (user.equals(admin)) {
            System.out.println("""
                               1 - Edit student database
                               2 - Edit teacher database
                               3 - Edit subjects available
                               4 - Edit admin information
                               5 - Exit""");
            int choice = choice(1, 5);
            switch (choice) {
                case 1 ->
                    students.displayAsAdmin();
                case 2 ->
                    tdb.displayAsAdmin();
                case 3 ->
                    System.out.println("Feature not implemented yet.");
                case 4 ->
                    changeCredential();
                case 5 ->
                    System.exit(0);
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
            choicesMenu(admin);
        } else {
            System.out.println("Unknown credential");
            menu();
        }
    }

    public static void main(String[] args) {
        system.students.studentData.loadFromFile("C:\\Users\\Hazenna\\Documents\\NetBeansProjects\\SchoolSystem\\src\\schoolsystem\\Studentdatabase.txt"); //Do not mind this
        SchoolSystem.tdb.teachersData.loadFromFile("C:\\Users\\Hazenna\\Documents\\NetBeansProjects\\SchoolSystem\\src\\schoolsystem\\Teachersdatabase.txt"); //This as well
        system.menu();
    }

}
