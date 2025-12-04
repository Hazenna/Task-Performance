package schoolsystem;

import java.util.*;

/**
 *
 * @author James
 */
public class Teachers {

    private static final Scanner s = new Scanner(System.in);

    private final List<String> handouts = new ArrayList<>();
    public Map<String, double[]> grades = new HashMap<>();
    private String schedule = "No schedule assigned.";
    private String subject = "No subject assigned.";
    private boolean sentinel;

    public void login() {
        sentinel = true;
        while (sentinel) {
            System.out.println("Teacher Login");
            System.out.print("Enter teacher name: ");
            String input = s.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Name cannot be empty. Try again");
                continue;
            }

            boolean found = false;
            String teacherID = null;
            String storedCredential = null;

            for (Map.Entry<String, ArrayList<String>> entry : SchoolSystem.tdb.teachersData.info.entrySet()) {
                ArrayList<String> details = entry.getValue();
                if (!details.isEmpty() && details.get(0).equals(input)) {
                    found = true;
                    teacherID = entry.getKey();
                    storedCredential = details.size() > 6 ? details.get(6) : null;
                    break;
                }
            }

            if (!found) {
                System.out.println("Teacher not found in database. Access denied");
                continue;
            }

            System.out.print("Enter credentials: ");
            String cred = s.nextLine().trim();
            if (storedCredential != null && cred.equals(storedCredential)) {
                System.out.println("Login successful, welcome " + input);
                System.out.println("1 - View regular menu\n2 - Admin menu");
                int choice = SchoolSystem.choice(1, 2);
                if (choice == 1) {
                    teacherUserMenu();
                    sentinel = false;
                } else if (choice == 2) {
                    teacherAdminMenu();
                    sentinel = false;
                }
                sentinel = false;
            } else {
                System.out.println("Incorrect password, please try again");
            }
        }
    }

    public void teacherUserMenu() {
        System.out.println("\n--- Teacher User Menu ---");
        System.out.println("1 - View Grades");
        System.out.println("2 - View Handouts / Modules");
        System.out.println("3 - View Teacher Time Schedule");
        System.out.println("4 - View Assigned Subject");
        System.out.println("5 - View Available rooms");
        System.out.println("6 - Back");
        System.out.println("7 - Exit");

        int c = SchoolSystem.choice(1, 6);
        switch (c) {
            case 1 ->
                viewGrades();
            case 2 ->
                viewHandouts();
            case 3 ->
                viewSchedule();
            case 4 ->
                viewSubject();
            case 5 ->
                RoomsTp.menuView();
            case 6 ->
                SchoolSystem.tdb.displayAsAdmin();
            case 7 ->
                System.exit(0);
        }
    }

    public void teacherAdminMenu() {
        System.out.println("\n--- Teacher Admin Menu ---");
        System.out.println("1 - Read/Write Grades");
        System.out.println("2 - Read/Write Handouts");
        System.out.println("3 - Compute Grade & Display");
        System.out.println("4 - Set Teacher Time Schedule");
        System.out.println("5 - Set Assigned Subject");
        System.out.println("6 - Book Available Room");
        System.out.println("7 - Compute GWA for a student");
        System.out.println("8 - Exit");

        int c = SchoolSystem.choice(1, 8);
        switch (c) {
            case 1 ->
                writeGrades();
            case 2 ->
                writeHandouts();
            case 3 ->
                computeGrade();
            case 4 ->
                setSchedule();
            case 5 ->
                setSubject();
            case 6 ->
                RoomsTp.menuBooking();
            case 7 ->
                computeGWAForStudent();
            case 8 ->
                SchoolSystem.tdb.displayAsAdmin();
        }
    }

    private void viewGrades() {
        System.out.println("\n--- Student Grades ---");

        SchoolSystem.system.students.studentData.loadFromFile(SchoolSystem.studentFile);
        Map<String, ArrayList<String>> studentInfo = SchoolSystem.system.students.studentData.info;

        if (studentInfo.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (String studentId : studentInfo.keySet()) {
            ArrayList<String> details = studentInfo.get(studentId);
            if (details == null || details.isEmpty()) {
                continue;
            }
            if (details.size() >= 11) {
                String studentName = details.get(0);
                double[] studentGrades = grades.get(studentName.toLowerCase());
                if (studentGrades != null && studentGrades.length == 4) {
                    System.out.println(studentName + " - Prelims: " + studentGrades[0] + " | Midterms: " + studentGrades[1]
                            + " | Prefinals: " + studentGrades[2] + " | Finals: " + studentGrades[3]);
                    System.out.println("1 - Back\n2 - Exit");
                    int choice = SchoolSystem.choice(1, 2);
                    if (choice == 1) {
                        teacherUserMenu();
                    } else if (choice == 2) {
                        System.exit(0);
                    }
                } else {
                    System.out.println(studentName + " - Prelims: 0 | Midterms: 0 | Prefinals: 0 | Finals: 0");
                    System.out.println("1 - Back\n2 - Exit");
                    int choice = SchoolSystem.choice(1, 2);
                    if (choice == 1) {
                        teacherUserMenu();
                    } else if (choice == 2) {
                        System.exit(0);
                    }
                }
            }
        }
    }

    private void viewHandouts() {
        System.out.println("\n--- Handouts / Modules ---");
        if (handouts.isEmpty()) {
            System.out.println("No handouts.");
        } else {
            handouts.forEach(System.out::println);
        }
    }

    private void viewSchedule() {
        System.out.println("\n--- Teacher Time Schedule ---");
        System.out.println(schedule);
    }

    private void viewSubject() {
        System.out.println("\n--- Assigned Subject ---");
        System.out.println(subject);
    }

    public double calculateGWA(String studentName) {
        double[] studentGrades = grades.get(studentName.toLowerCase());
        if (studentGrades == null || studentGrades.length != 4) {
            return 0.0;
        }
        return (studentGrades[0] + studentGrades[1] + studentGrades[2] + studentGrades[3]) / 4.0;
    }

    private void writeGrades() {
        System.out.print("Enter Student Name: ");
        String name = s.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double[] studentGrades = new double[4];
        System.out.print("Enter Grade(0 - 100): ");
        try {
            System.out.print("Enter Prelim Grade (0-100): ");
            studentGrades[0] = Double.parseDouble(s.nextLine().trim());
            System.out.print("Enter Midterm Grade (0-100): ");
            studentGrades[1] = Double.parseDouble(s.nextLine().trim());
            System.out.print("Enter Prefinals Grade (0-100): ");
            studentGrades[2] = Double.parseDouble(s.nextLine().trim());
            System.out.print("Enter Finals Grade (0-100): ");
            studentGrades[3] = Double.parseDouble(s.nextLine().trim());

            for (int i = 0; i < 4; i++) {
                if (studentGrades[i] < 0 || studentGrades[i] > 100) {
                    System.out.println("Grades must be between 0 and 100.");
                    return;
                } 
                studentGrades[i] = Math.round(studentGrades[i] * 100) / 100.0;
            }
            SchoolSystem.system.students.updateGradesForStudent(name, studentGrades[0], studentGrades[1], studentGrades[2], studentGrades[3]);
            System.out.println("Grades saved and GPA updated.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid grade input.");
            writeGrades();
        }
    }

    private void writeHandouts() {
        System.out.print("Enter Handout Title: ");
        String h = s.nextLine().trim();
        handouts.add(h);
        System.out.println("Handout Added.");
    }

    private void computeGrade() {
        try {
            System.out.print("Enter Prelim: ");
            double p = Double.parseDouble(s.nextLine());
            System.out.print("Enter Midterm: ");
            double m = Double.parseDouble(s.nextLine());
            System.out.print("Enter Prefinals");
            double pf = Double.parseDouble(s.nextLine());
            System.out.print("Enter Finals: ");
            double f = Double.parseDouble(s.nextLine());

            double finalGrade = (p + m + pf + f) / 4;
            System.out.println("Computed Final Grade: " + finalGrade);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input, use numbers only.");
        }
    }

    private void computeGWAForStudent() {
        System.out.print("Enter Student Name: ");
        String studentName = s.nextLine().trim();
        if (studentName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double gwa = calculateGWA(studentName);
        System.out.println("GWA for " + studentName + ": " + String.format("%.2f", gwa));
    }

    public void updateStudentGWA(String studentName) {
        try {
            SchoolSystem.system.students.studentData.loadFromFile("C:\\Users\\Hazenna\\Documents\\NetBeansProjects\\SchoolSystem\\src\\schoolsystem\\Studentdatabase.txt");
        } catch (Exception e) {
            return;
        }

        for (String key : SchoolSystem.system.students.studentData.info.keySet()) {
            ArrayList<String> details = SchoolSystem.system.students.studentData.info.get(key);
            if (details.get(0).equalsIgnoreCase(studentName)) {
                double gwaValue = SchoolSystem.teacher.calculateGWA(studentName);
                details.set(4, String.format("%.2f", gwaValue));
                break;
            }
        }

        try {
            SchoolSystem.system.students.studentData.saveToFile("C:\\Users\\Hazenna\\Documents\\NetBeansProjects\\SchoolSystem\\src\\schoolsystem\\Studentdatabase.txt");
            System.out.println("GWA updated and saved for " + studentName);
        } catch (Exception e) {
        }
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
