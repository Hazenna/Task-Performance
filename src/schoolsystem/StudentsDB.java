package schoolsystem;

import java.util.*;

interface PDI {

    public void studentID();

    public void name();

    public void dob();

    public void contactDetails();

    public void gender();

}

interface APD {

    public void GPA();

}

interface ASF {

    public void courseInformation();

}

public class StudentsDB implements PDI, APD, ASF {

    public DB studentData = new DB();
    static Scanner s = new Scanner(System.in);
    public static SchoolSystem system = new SchoolSystem();
    private boolean sentinel;
    private String idString;
    private String name;
    private String dob;
    private String contact;
    private String gender;
    private String getGPA = "0.0";
    private String course;
    private static int idCounter = 1;

    @Override
    public void studentID() {

        this.idString = String.valueOf(idCounter++);

    }

    @Override
    public void name() {
        System.out.print("Enter Name: ");
        String nameString = s.nextLine();
        this.name = nameString;
    }

    @Override
    public void dob() {
        System.out.print("Enter Date of Birth (MM/DD/YYYY): ");
        sentinel = true;
        while (sentinel) {
            String dateInput = s.nextLine();
            if (dateInput.matches("\\d{2}/\\d{2}/\\d{4}")) {
                this.dob = dateInput;
                sentinel = false;
            } else {
                System.out.print("Invalid format. Enter Date of Birth (MM/DD/YYYY): ");
            }
        }
    }

    @Override
    public void contactDetails() {
        sentinel = true;
        System.out.print("Enter Contact Number: ");
        while (sentinel) {
            String contactString = s.nextLine().trim();
            if (contactString.isEmpty()) {
                System.out.print("Number is Needed, Please Enter Contact Number: ");
            } else {
                this.contact = contactString;
                sentinel = false;
            }
        }
    }

    @Override
    public void gender() {
        System.out.print("Enter Gender(Optional, Leave Blank): ");
        String genderString = s.nextLine().strip();
        if (genderString.isEmpty()) {
            this.gender = "Not given";
        } else {
            this.gender = genderString;
        }
    }

    @Override
    public void GPA() {
        double total = 0.0;
        int count = 0;

        for (Map.Entry<String, Integer> entry : SchoolSystem.teacher.grades.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(this.name)) {
                total += entry.getValue();
                count++;
            }
        }
        this.getGPA = count > 0 ? String.format("%.2f", total / count) : "0.00";
    }

    @Override
    public void courseInformation() {
        System.out.print("Enter Course or 'NA' if Undicided: ");
        String courseString = s.nextLine().trim();
        if (courseString.equalsIgnoreCase("") || courseString.equalsIgnoreCase("NA")) {
            this.course = "Undecided";
        } else {
            this.course = courseString;
        }
    }

    public void displayAsUser() {
        System.out.println("Students in database:");
        if (studentData.info.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (String key : studentData.info.keySet()) {
                ArrayList<String> details = studentData.info.get(key);
                System.out.println("ID: " + key + " | Name: " + details.get(0) + " | DOB: "
                        + details.get(1) + " | Contact: " + details.get(2) + " | Gender: " + details.get(3)
                        + " | GPA: " + details.get(4) + " | Course: " + details.get(5));
            }
        }
        System.out.println("1 - Back\n2 - Exit");
        int choice = SchoolSystem.choice(1, 2);
        if (choice == 1) {
            system.menu();
        } else if (choice == 2) {
            System.exit(0);
        }
    }

    public void displayAsAdmin() {
        sentinel = true;
        while (sentinel) {
            System.out.println("""
                           1 - Edit a student in database
                           2 - Remove a student in database
                           3 - add a student in database
                           4 - to go back""");
            int userChoice = SchoolSystem.choice(1, 5);
            switch (userChoice) {
                case 1 -> {
                    editStudent();
                    sentinel = false;
                }
                case 2 -> {
                    removeStudent();
                    sentinel = false;
                }
                case 3 -> {
                    addStudent();
                    sentinel = false;
                }
                case 4 -> {
                    system.menu();
                    sentinel = false;
                }
            }
        }
    }

    public void addStudent() {
        studentID();
        studentData.info.putIfAbsent(this.idString, new ArrayList<>());
        System.out.println("Adding student with ID: " + this.idString);

        name();
        studentData.info.get(this.idString).add(this.name);

        dob();
        studentData.info.get(this.idString).add(this.dob);

        contactDetails();
        studentData.info.get(this.idString).add(this.contact);

        gender();
        studentData.info.get(this.idString).add(this.gender);

        GPA();
        studentData.info.get(this.idString).add(this.getGPA);

        courseInformation();
        studentData.info.get(this.idString).add(this.course);

        System.out.println("Student added successfully!");
        studentData.saveToFile("Studentdatabase.txt");

        System.out.println("Press 1 to quit\nPress 2 to go back");
        int choice = SchoolSystem.choice(1, 2);

        switch (choice) {
            case 1 -> {
                System.exit(0);
            }
            case 2 -> {
                displayAsAdmin();
            }
        }
    }

    public void editStudent() {
        System.out.print("Enter name of student you want to change information or type B to return to the admin menu: ");
        String searchName = s.nextLine().trim();

        if (searchName.equalsIgnoreCase("B")) {
            displayAsAdmin();
            return;
        }

        List<String> matchedKeys = new ArrayList<>();
        for (String key : studentData.info.keySet()) {
            ArrayList<String> details = studentData.info.get(key);
            if (!details.isEmpty() && details.get(0).equalsIgnoreCase(searchName)) {
                matchedKeys.add(key);
            }
        }

        if (matchedKeys.isEmpty()) {
            System.out.println("No student found in database with the name " + searchName);
            editStudent();
            return;
        }

        String selectedKey;
        if (matchedKeys.size() == 1) {
            selectedKey = matchedKeys.get(0);
        } else {
            System.out.println("Multiple students foudn with that name:");
            for (String key : matchedKeys) {
                ArrayList<String> details = studentData.info.get(key);
                System.out.println("ID: " + key + " - " + details);
            }

            System.out.print("Enter ID of student you want to change information: ");
            selectedKey = s.nextLine().trim();
            if (!matchedKeys.contains(selectedKey)) {
                System.out.println("Invalid ID inputted");
                return;
            }
        }

        ArrayList<String> details = studentData.info.get(selectedKey);
        System.out.println("Current details for ID " + selectedKey + ":");
        System.out.println("Name: " + details.get(0));
        System.out.println("DOB: " + details.get(1));
        System.out.println("Contact: " + details.get(2));
        System.out.println("Gender: " + details.get(3));
        System.out.println("GPA: " + details.get(4));
        System.out.println("Course: " + details.get(5));

        System.out.println("Enter the new values (leave blank to keep current values):");

        System.out.print("New name: ");
        String newName = s.nextLine().trim();
        if (!newName.isEmpty()) {
            details.set(0, newName);
        }

        System.out.print("New DOB (MM/DD/YYYY): ");
        String newDob = s.nextLine().trim();
        if (!newDob.isEmpty()) {
            if (newDob.matches("\\d{2}/\\d{2}/\\d{4}")) {
                details.set(1, newDob);
            } else {
                System.out.println("Invalid DOB format. Keeping current.");
            }
        }

        System.out.print("New Contact: ");
        String newContact = s.nextLine().trim();
        if (!newContact.isEmpty()) {
            details.set(2, newContact);
        }

        System.out.print("New Gender: ");
        String newGender = s.nextLine().trim();
        if (!newGender.isEmpty()) {
            details.set(3, newGender);
        }

        System.out.print("New GPA: "); //Not final, will need collaboration with the teachers section
        String newGpa = s.nextLine().trim();
        if (!newGpa.isEmpty()) {
            try {
                double gpa = Double.parseDouble(newGpa);
                if (gpa >= 0.0 && gpa <= 4.0) {
                    details.set(4, String.valueOf(gpa));
                } else {
                    System.out.println("Invalid GPA. Keeping current.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA input. Keeping current.");
            }
        }

        System.out.print("New Course: ");
        String newCourse = s.nextLine().trim();
        if (!newCourse.isEmpty()) {
            if (newCourse.equalsIgnoreCase("NA") || newCourse.equalsIgnoreCase("")) {
                details.set(5, "Undecided");
            } else {
                details.set(5, newCourse);
            }
        }

        System.out.println("Student details updated successfully!");
        studentData.saveToFile("Studentdatabase.txt");
        System.out.println("Updated details: " + details);

        System.out.println("Press 1 to quit\nPress 2 to go back");
        int choice = SchoolSystem.choice(1, 2);

        switch (choice) {
            case 1 -> {
                System.exit(0);
            }
            case 2 -> {
                displayAsAdmin();
            }
        }
    }

    public void removeStudent() {
        if (studentData.info.isEmpty()) {
            System.out.println("No students in database to remove");
            displayAsAdmin();
            return;
        }

        System.out.print("Enter name of student you want to remove or type B to exit to the Admin Menu: ");
        String searchName = s.nextLine().trim();

        if (searchName.equalsIgnoreCase("B")) {
            displayAsAdmin();
            return;
        }

        List<String> matchedKeys = new ArrayList<>();
        for (String key : studentData.info.keySet()) {
            ArrayList<String> details = studentData.info.get(key);
            if (!details.isEmpty() && details.get(0).equalsIgnoreCase(searchName)) {
                matchedKeys.add(key);
            }
        }

        if (matchedKeys.isEmpty()) {
            System.out.println("No student found in database with the name " + searchName);
            return;
        }

        String selectedKey;
        if (matchedKeys.size() == 1) {
            selectedKey = matchedKeys.get(0);
        } else {
            System.out.println("Multiple students foudn with that name:");
            for (String key : matchedKeys) {
                ArrayList<String> details = studentData.info.get(key);
                System.out.println("ID: " + key + " - " + details);
            }

            System.out.print("Enter ID of student you want to remove from database: ");
            selectedKey = s.nextLine().trim();
            if (!matchedKeys.contains(selectedKey)) {
                System.out.println("Invalid ID inputted");
                return;
            }
        }

        ArrayList<String> details = studentData.info.get(selectedKey);
        System.out.println("Student to remove ID: " + selectedKey + " - Details: " + details);
        sentinel = true;
        while (sentinel) {
            System.out.print("Confirm removal?(Y/N): ");
            String choice = s.nextLine().trim();
            if (choice.equalsIgnoreCase("y")) {
                studentData.info.remove(selectedKey);
                System.out.println("Student removed successfully");
                studentData.saveToFile("Studentdatabase.txt");
                sentinel = false;
            } else if (choice.equalsIgnoreCase("n")) {
                System.out.println("Removal Canvelled");
                sentinel = false;
            } else {
                System.out.println("Please input Y or N");
            }
        }

        System.out.println("Press 1 to quit\nPress 2 to go back");
        int choice = SchoolSystem.choice(1, 2);

        switch (choice) {
            case 1 -> {
                System.exit(0);
            }
            case 2 -> {
                displayAsAdmin();
            }
        }
    }

}
