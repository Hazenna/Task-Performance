package schoolsystem;

import java.util.*;

/**
 *
 * @author Mika
 */
public class SchoolSystem {

    public static StudentsDB students = new StudentsDB();
    static Scanner s = new Scanner(System.in);
    public static SchoolSystem system = new SchoolSystem();
    public static Teachers teacher = new Teachers();
    public static TeachersDB tdb = new TeachersDB();
    public static Subjects subs = new Subjects();
    public static RoomsTp r = new RoomsTp();
    public static final String studentFile = "Studentdatabase.txt";
    public static final String teacherFile = "Teachersdatabase.txt";
    public static final String handoutFile = "Handoutsdatabase.txt";
    public static final String roomsFile = "Roomsdatabase.txt";
    
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
            System.out.println("""
                               1 - Access student database
                               2 - Access teacher database
                               3 - View subjects available
                               4 - Back
                               5 - Exit""");
            int choice = choice(1, 5);
            switch (choice) {
                case 1 ->
                    students.displayAsUser();
                case 2 ->
                    tdb.displayAsUser();
                case 3 ->
                     subs.displayAsUser();
                case 4 ->
                    menu();
                case 5 ->
                    System.exit(0);
            }
        } else if (user.equalsIgnoreCase("Admin")) {
            System.out.println("""
                               1 - Edit student database
                               2 - Edit teacher database
                               3 - Edit subjects available
                               4 - Back
                               5 - Exit""");
            int choice = choice(1, 5);
            switch (choice) {
                case 1 ->
                    students.displayAsAdmin();
                case 2 ->
                    tdb.displayAsAdmin();
                case 3 ->
                    subs.displayAsAdmin();
                case 4 ->
                    menu();
                case 5 ->
                    System.exit(0);
            }
        }
    }

    public void menu() {
        System.out.println("Welcome to School Management System");
        System.out.println("Enter Admin to log in as Admin or User to log in as User: ");
        System.out.print("> ");
        String cred = s.nextLine();
        if (cred.equalsIgnoreCase("User")) {
            String user = "user";
            choicesMenu(user);
        } else if (cred.equalsIgnoreCase("Admin")) {
            choicesMenu("Admin");
        } else {
            System.out.println("Unknown credential");
            menu();
        }
    }

    public static void main(String[] args) {
        SchoolSystem.students.studentData.loadFromFile(studentFile); 
        SchoolSystem.tdb.teachersData.loadFromFile(teacherFile); 
        SchoolSystem.teacher.handoutsData.loadFromFile(handoutFile);
        SchoolSystem.r.roomsDB.loadFromFile(roomsFile);
        system.menu();
    }
}