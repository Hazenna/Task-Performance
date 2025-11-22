package schoolsystem;
import java.util.*;
public class Teachers {
       private static final Scanner s = new Scanner(System.in);

   
    private final String adminUser = "Admin";
    private final String adminPass = "1234";

 
    private final Map<String, Integer> grades = new HashMap<>();
    private final List<String> handouts = new ArrayList<>();
    private String schedule = "No schedule assigned.";
    private String subject = "No subject assigned.";

    public void login() {
        System.out.println("Teacher Login");
        System.out.print("Enter Teacher Credentials (User/Admin): ");
        String input = s.nextLine().trim();

        if (input.equalsIgnoreCase("User")) {
            teacherUserMenu();
        } else if (input.equalsIgnoreCase(adminUser)) {
            System.out.print("Enter Password: ");
            String pass = s.nextLine();
            if (pass.equals(adminPass)) {
                teacherAdminMenu();
            } else {
                System.out.println("Incorrect Password.");
            }
        } else {
            System.out.println("Unknown Credential");
        }
    }
    private void teacherUserMenu() {
        System.out.println("\n--- Teacher User Menu ---");
        System.out.println("1 - View Grades");
        System.out.println("2 - View Handouts / Modules");
        System.out.println("3 - View Teacher Time Schedule");
        System.out.println("4 - View Assigned Subject");
        System.out.println("5 - Exit");

        int c = choice(1, 5);
        switch (c) {
            case 1 -> viewGrades();
            case 2 -> viewHandouts();
            case 3 -> viewSchedule();
            case 4 -> viewSubject();
        }
    }
    private void teacherAdminMenu() {
        System.out.println("\n--- Teacher Admin Menu ---");
        System.out.println("1 - Read/Write Grades");
        System.out.println("2 - Read/Write Handouts");
        System.out.println("3 - Compute Grade & Display");
        System.out.println("4 - Set Teacher Time Schedule");
        System.out.println("5 - Set Assigned Subject");
        System.out.println("6 - Exit");

        int c = choice(1, 6);
        switch (c) {
            case 1 -> writeGrades();
            case 2 -> writeHandouts();
            case 3 -> computeGrade();
            case 4 -> setSchedule();
            case 5 -> setSubject();
        }
    }
    private int choice(int min, int max) {
        while (true) {
            System.out.print("> ");
            String line = s.nextLine();
            try {
                int c = Integer.parseInt(line);
                if (c >= min && c <= max) return c;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
    private void viewGrades() {
        System.out.println("\n--- Student Grades ---");
        if (grades.isEmpty()) {
            System.out.println("No grades recorded.");
        } else {
            grades.forEach((name, grade) ->
                System.out.println(name + " : " + grade)
            );
        }
    }
    private void viewHandouts() {
        System.out.println("\n--- Handouts / Modules ---");
        if (handouts.isEmpty()) System.out.println("No handouts.");
        else handouts.forEach(System.out::println);
    }
    private void viewSchedule() {
        System.out.println("\n--- Teacher Time Schedule ---");
        System.out.println(schedule);
    }
    private void viewSubject() {
        System.out.println("\n--- Assigned Subject ---");
        System.out.println(subject);
    }
    private void writeGrades() {
        System.out.print("Enter Student Name: ");
        String name = s.nextLine();
        System.out.print("Enter Grade: ");
        int g = Integer.parseInt(s.nextLine());
        grades.put(name, g);
        System.out.println("Grade saved.");
    }
    private void writeHandouts() {
        System.out.print("Enter Handout Title: ");
        String h = s.nextLine();
        handouts.add(h);
        System.out.println("Handout Added.");
    }
    private void computeGrade() {
        System.out.print("Enter Prelim: ");
        double p = Double.parseDouble(s.nextLine());
        System.out.print("Enter Midterm: ");
        double m = Double.parseDouble(s.nextLine());
        System.out.print("Enter Finals: ");
        double f = Double.parseDouble(s.nextLine());

        double finalGrade = (p + m + f) / 3;
        System.out.println("Computed Final Grade: " + finalGrade);
    }
    private void setSchedule() {
        System.out.print("Enter New Schedule: ");
        schedule = s.nextLine();
        System.out.println("Schedule Updated.");
    }
    private void setSubject() {
        System.out.print("Enter Assigned Subject: ");
        subject = s.nextLine();
        System.out.println("Subject Updated.");
    }
}
    
    
