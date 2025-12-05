package schoolsystem;

import java.util.*;

/**
 *
 * @author James
 */
public class Teachers {

    private static final Scanner s = new Scanner(System.in);

    public DB handoutsData = new DB();
    private final List<String> handouts = new ArrayList<>();
    private boolean sentinel;

    private void loadHandouts() {
        handoutsData.loadFromFile(SchoolSystem.handoutFile);
        handouts.clear();
        for (Map.Entry<String, ArrayList<String>> entry : handoutsData.info.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                handouts.add(entry.getValue().get(0));
            }
        }
    }

    private void saveHandouts() {
        handoutsData.info.clear();
        for (int i = 0; i < handouts.size(); i++) {
            ArrayList<String> details = new ArrayList<>();
            details.add(handouts.get(i));
            handoutsData.info.put(String.valueOf(i + 1), details);
        }
        handoutsData.saveToFile(SchoolSystem.handoutFile);
    }

    public void login() {
        loadHandouts();
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
            String storedCredential = null;

            for (Map.Entry<String, ArrayList<String>> entry : SchoolSystem.tdb.teachersData.info.entrySet()) {
                ArrayList<String> details = entry.getValue();
                if (!details.isEmpty() && details.get(0).equals(input)) {
                    found = true;
                    entry.getKey();
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
        System.out.println("1 - View Grades of Students");
        System.out.println("2 - View Handouts");
        System.out.println("3 - View Available rooms");
        System.out.println("4 - Back");
        System.out.println("5 - Exit");

        int c = SchoolSystem.choice(1, 5);
        switch (c) {
            case 1 ->
                viewGrades();
            case 2 ->
                viewHandouts();
            case 3 ->
                RoomsTp.menuView();
            case 4 ->
                SchoolSystem.tdb.displayAsAdmin();
            case 5 ->
                System.exit(0);
        }
    }

    public void teacherAdminMenu() {
        System.out.println("\n--- Teacher Admin Menu ---");
        System.out.println("1 - Edit Grades");
        System.out.println("2 - Write Handouts");
        System.out.println("3 - Book Available Room");
        System.out.println("4 - Back");
        System.out.println("5 - Exit");

        int c = SchoolSystem.choice(1, 5);
        switch (c) {
            case 1 ->
                writeGrades();
            case 2 ->
                writeHandouts();
            case 3 ->
                RoomsTp.menuBooking();
            case 4 ->
                SchoolSystem.tdb.displayAsAdmin();
            case 5 ->
                System.exit(0);
        }
    }

    private void viewGrades() {
        System.out.println("\n--- Student Grades ---");

        SchoolSystem.students.studentData.loadFromFile(SchoolSystem.studentFile);
        Map<String, ArrayList<String>> studentInfo = SchoolSystem.students.studentData.info;

        if (studentInfo.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Map.Entry<String, ArrayList<String>> entry : studentInfo.entrySet()) {
            ArrayList<String> details = entry.getValue();
            if (details.size() >= 10) {
                String studentName = details.get(0);
                double[] studentGrades = SchoolSystem.students.getGradesForStudent(studentName);
                System.out.println(studentName + " - Prelims: " + studentGrades[0] + " | Midterms: " + studentGrades[1]
                        + " | Prefinals: " + studentGrades[2] + " | Finals: " + studentGrades[3] + " | GWA: " + studentGrades[4]);
            }
        }
        System.out.println("1 - Back\n2 - Exit");
        int choice = SchoolSystem.choice(1, 2);
        if (choice == 1) {
            teacherUserMenu();
        } else if (choice == 2) {
            System.exit(0);
        }
    }

    private void viewHandouts() {
        loadHandouts();
        System.out.println("\n--- Handouts / Modules ---");
        if (handouts.isEmpty()) {
            System.out.println("No handouts.");
        } else {
            for (int i = 0; i < handouts.size(); i++) {
                System.out.println((i + 1) + ". " + handouts.get(i));
            }
        }
    }

    public double calculateGWA(String studentName) {
        double[] studentGrades = SchoolSystem.students.getGradesForStudent(studentName);
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
        System.out.println("Enter Grade(0 - 100): ");
        try {
            System.out.print("Enter Prelim Grade: ");
            studentGrades[0] = Double.parseDouble(s.nextLine().trim());
            System.out.print("Enter Midterm Grade: ");
            studentGrades[1] = Double.parseDouble(s.nextLine().trim());
            System.out.print("Enter Prefinals Grade: ");
            studentGrades[2] = Double.parseDouble(s.nextLine().trim());
            System.out.print("Enter Finals Grade: ");
            studentGrades[3] = Double.parseDouble(s.nextLine().trim());

            for (int i = 0; i < 4; i++) {
                if (studentGrades[i] < 0 || studentGrades[i] > 100) {
                    System.out.println("Grades must be between 0 and 100.");
                    return;
                }
                studentGrades[i] = Math.round(studentGrades[i] * 100) / 100.0;
            }
            boolean success = SchoolSystem.students.updateGradesForStudent(name, studentGrades[0], studentGrades[1], studentGrades[2], studentGrades[3]);
            if (success) {
                System.out.println("Grades saved and GPA updated.");
                System.out.println("1 - Back\n2 - Exit");
                int choice = SchoolSystem.choice(1, 2);
                if (choice == 1) {
                    teacherAdminMenu();
                } else if (choice == 2) {
                    System.exit(0);
                }
            } else {
                System.out.println("Student '" + name + "' not found. Grades not saved.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid grade input.");
            writeGrades();
        }
    }

    private void writeHandouts() {
        System.out.print("Enter Handout Title: ");
        String h = s.nextLine().trim();
        if (!h.isEmpty()) {
            handouts.add(h);
            saveHandouts();
            System.out.println("Handout Added and Saved.");
        } else {
            System.out.println("Handout title cannot be empty.");
        }
    }

    public void updateStudentGWA(String studentName) {
        try {
            SchoolSystem.students.studentData.loadFromFile(SchoolSystem.studentFile);
        } catch (Exception e) {
            return;
        }

        for (String key : SchoolSystem.students.studentData.info.keySet()) {
            ArrayList<String> details = SchoolSystem.students.studentData.info.get(key);
            if (details.get(0).equalsIgnoreCase(studentName)) {
                double gwaValue = SchoolSystem.teacher.calculateGWA(studentName);
                details.set(4, String.format("%.2f", gwaValue));
                break;
            }
        }

        try {
            SchoolSystem.students.studentData.saveToFile(SchoolSystem.studentFile);
            System.out.println("GWA updated and saved for " + studentName);
        } catch (Exception e) {
        }
    }
}
