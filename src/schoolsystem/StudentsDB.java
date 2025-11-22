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

    Map<String, ArrayList<String>> info = new HashMap<>();
    static Scanner s = new Scanner(System.in);
    public static SchoolSystem system = new SchoolSystem();
    Random r = new Random();
    private boolean sentinel;
    private String idString;
    private String name;
    private String dob;
    private String contact;
    private String gender;
    private String getGPA = "0.0";
    private String course;

    @Override
    public void studentID() {

        int idNum = Math.abs(r.nextInt(100000));
        String id = String.valueOf(idNum);
        this.idString = id;

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
        //Will need cooperation on Subjects to implement a method for calculating GPA
    }

    @Override
    public void courseInformation() {
        System.out.println("Enter Course or 'NA' if Undicided: ");
        String courseString = s.nextLine().trim();
        if (courseString.equalsIgnoreCase("NA")) {
            this.course = "Undecided";
        } else {
            this.course = courseString;
        }
    }

    public void displayAsUser() {
        System.out.println("Students in database:");
        if (info.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (String key : info.keySet()) {
                System.out.println("ID: " + key + " - Details: " + info.get(key));
            }
        }
        System.out.println("1 - Back\n2 - Exit");
        int choice = SchoolSystem.choice(1, 2);
        if (choice == 1) {
            system.menu();
        }
        else if (choice == 2) {
            System.exit(0);
        }
    }

    public void displayAsAdmin() {
        System.out.println("1 - Edit a student in database\n2 - Remove a student in database\n3 - add a student in database\n4 - to go back");
        sentinel = true;
        while (sentinel) {
            int userChoice = SchoolSystem.choice(1, 4);
            switch (userChoice) {
                case 1 -> {
                    editStudent();
                }
                case 2 -> {
                    removeStudent();
                }
                case 3 ->
                    addStudent();
                case 4 -> {
                    system.menu();
                    sentinel = false;
                }
            }
        }
    }

    public void addStudent() {
        studentID();
        info.putIfAbsent(this.idString, new ArrayList<>());
        System.out.println("Adding student with ID: " + this.idString);

        name();
        info.get(this.idString).add(this.name);

        dob();
        info.get(this.idString).add(this.dob);

        contactDetails();
        info.get(this.idString).add(this.contact);

        gender();
        info.get(this.idString).add(this.gender);

        GPA();
        info.get(this.idString).add(this.getGPA);

        courseInformation();
        info.get(this.idString).add(this.course);

        System.out.println("Student added successfully!");
        System.out.println("Current students:");
        for (String key : info.keySet()) {
            System.out.println("ID: " + key + " - " + info.get(key));
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

    public void editStudent() {
        System.out.print("Enter name of student you want to change information: ");
        String searchName = s.nextLine().trim();

        List<String> matchedKeys = new ArrayList<>();
        for (String key : info.keySet()) {
            ArrayList<String> details = info.get(key);
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
                ArrayList<String> details = info.get(key);
                System.out.println("ID: " + key + " - " + details);
            }

            System.out.print("Enter ID of student you want to change information: ");
            selectedKey = s.nextLine().trim();
            if (!matchedKeys.contains(selectedKey)) {
                System.out.println("Invalid ID inputted");
                return;
            }
        }

        ArrayList<String> details = info.get(selectedKey);
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
            if (newCourse.equalsIgnoreCase("NA")) {
                details.set(5, "Undecided");
            } else {
                details.set(5, newCourse);
            }
        }

        System.out.println("Student details updated successfully!");
        System.out.println("Updated details: " + details);
    }

    public void removeStudent() {
        if (info.isEmpty()) {
            System.out.println("No students in database to remove");
            return;
        }

        System.out.print("Enter name of student you want to change information: ");
        String searchName = s.nextLine().trim();

        List<String> matchedKeys = new ArrayList<>();
        for (String key : info.keySet()) {
            ArrayList<String> details = info.get(key);
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
                ArrayList<String> details = info.get(key);
                System.out.println("ID: " + key + " - " + details);
            }

            System.out.print("Enter ID of student you want to remove from database: ");
            selectedKey = s.nextLine().trim();
            if (!matchedKeys.contains(selectedKey)) {
                System.out.println("Invalid ID inputted");
                return;
            }
        }

        ArrayList<String> details = info.get(selectedKey);
        System.out.println("Student to remove ID: " + selectedKey + " - Details: " + details);
        sentinel = true;
        while (sentinel) {
            System.out.print("Confirm removal?(Y/N): ");
            String choice = s.nextLine().trim();
            if (choice.equalsIgnoreCase("y")) {
                info.remove(selectedKey);
                System.out.println("Student removed successfully");
                sentinel = false;
            } else if (choice.equalsIgnoreCase("n")) {
                System.out.println("Removal Canvelled");
                sentinel = false;
            }
            else {
                System.out.println("Please input Y or N");
            }
        }
    }

}
