package schoolsystem;

import java.util.*;

/**
 *
 * @author James
 */
interface TPDI {

    void teacherID();

    void name();

    void dob();

    void contactDetails();

    void gender();
}

interface TInfo {

    void assignedSubject();

    void timeSchedule();
}

public class TeachersDB implements TPDI, TInfo {

    static Scanner s = new Scanner(System.in);
    public static SchoolSystem system = new SchoolSystem();
    public DB teachersData = new DB();

    private boolean sentinel;
    private String idString;
    private String name;
    private String dob;
    private String contact;
    private String gender;
    private String subject = "None";
    private String schedule = "Not Assigned";
    private String password;

    @Override
    public void teacherID() {
        this.idString = String.valueOf(teachersData.idCounter++);
    }

    @Override
    public void name() {
        System.out.print("Enter Teacher Name: ");
        this.name = s.nextLine().trim();
    }

    @Override
    public void dob() {
        while (sentinel) {
            System.out.print("Enter Date of Birth (MM/DD/YYYY): ");
            String dateInput = s.nextLine().trim();
            if (dateInput.matches("\\d{2}/\\d{2}/\\d{4}")) {
                String[] parts = dateInput.split("/");
                int month = Integer.parseInt(parts[0]);
                if (month <= 1 || month <= 12) {
                    this.dob = dateInput;
                    sentinel = false;
                } else {
                    System.out.println("Enter the required date format.");
                }
            }
        }
    }

    @Override
    public void contactDetails() {
        sentinel = true;
        System.out.print("Enter Contact Number: ");
        while (sentinel) {
            String c = s.nextLine();
            if (c.isEmpty()) {
                System.out.print("Contact Required. Enter Contact Number: ");
            } if (c.matches("^\\d{11}$")){
                this.contact = c;
                sentinel = false;
            } else {
                System.out.println("Enter contact number in 11 digits");
            }
        }
    }

    @Override
    public void gender() {
        System.out.print("Enter Gender (Optional, leave blank): ");
        String g = s.nextLine().strip();
        this.gender = g.equals("") ? "Not given" : g;
    }

    @Override
    public void assignedSubject() {
        System.out.print("Enter Assigned Subject: ");
        this.subject = s.nextLine().trim();
    }

    @Override
    public void timeSchedule() {
        System.out.print("Enter Teacher Time Schedule: ");
        this.schedule = s.nextLine().trim();
    }

    public void setPassword() {
        System.out.print("Set credentials for this teacher: ");
        this.password = s.nextLine().trim();
        if (this.password.isEmpty()) {
            System.out.println("Credential cannot be empty. setting password to password");
            this.password = "password";
        }
    }

    public void displayAsUser() {
        try {
            teachersData.loadFromFile(SchoolSystem.teacherFile);
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
        }

        System.out.println("\nTeachers in Database:");
        if (teachersData.info.isEmpty()) {
            System.out.println("No teachers available.");
            System.out.println("1 - Back\n2 - Exit");
            int choice = SchoolSystem.choice(1, 2);
            if (choice == 1) {
                system.menu();
            } else if (choice == 2) {
                System.exit(0);
            }
        }

        for (String key : teachersData.info.keySet()) {
            ArrayList<String> details = teachersData.info.get(key);
            System.out.println("ID: " + key + " | Name: " + details.get(0) + " | DOB: "
                    + details.get(1) + " | Contact: " + details.get(2) + " | Gender: " + details.get(3)
                    + " | Subject: " + details.get(4) + " | Schedule: " + details.get(5));
        }

        System.out.println("1 - Back\n2 - Exit");
        int choice = SchoolSystem.choice(1, 2);
        if (choice == 1) {
            system.choicesMenu("User");
        } else if (choice == 2) {
            System.exit(0);
        }
    }

    public void displayAsAdmin() {
        try {
            teachersData.loadFromFile(SchoolSystem.teacherFile);
        } catch (Exception e) {
            System.out.println("Error loading database: " + e.getMessage());
        }
        sentinel = true;
        while (sentinel) {
            System.out.println("\n1 - Edit Teacher in Database");
            System.out.println("2 - Remove Teacher");
            System.out.println("3 - Add Teacher");
            System.out.println("4 - Access Teachers User Menu");
            System.out.println("5 - Go Back");

            int userChoice = SchoolSystem.choice(1, 5);
            switch (userChoice) {
                case 1 -> {
                    editTeacher();
                    sentinel = false;
                }
                case 2 -> {
                    removeTeacher();
                    sentinel = false;
                }
                case 3 -> {
                    addTeacher();
                    sentinel = false;
                }
                case 4 -> {
                    SchoolSystem.teacher.login();
                    sentinel = false;
                }
                case 5 -> {
                    sentinel = false;
                    system.choicesMenu("Admin");
                }
            }
        }
    }

    public void addTeacher() {
        sentinel = true;
        teacherID();
        String idNum = this.idString;

        while (sentinel) {
            System.out.println("Add teacher? \n1 - Yes\n2 - No");
            int userChoice = SchoolSystem.choice(1, 2);

            if (userChoice == 1) {
                System.out.println("Enter the following details for ID: " + idNum);

                name();
                teachersData.info.get(idNum).add(this.name);

                dob();
                teachersData.info.get(idNum).add(this.dob);

                contactDetails();
                teachersData.info.get(idNum).add(this.contact);

                gender();
                teachersData.info.get(idNum).add(this.gender);

                assignedSubject();
                teachersData.info.get(idNum).add(this.subject);

                timeSchedule();
                teachersData.info.get(idNum).add(this.schedule);

                setPassword();
                teachersData.info.get(idNum).add(this.password);

                System.out.println("Teacher Added Successfully.");
                teachersData.saveToFile(SchoolSystem.teacherFile);

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

            } else if (userChoice == 2) {
                displayAsAdmin();
                sentinel = false;
            }
        }
    }

    private void removeTeacher() {
        if (teachersData.info.isEmpty()) {
            System.out.println("No teachers in database to remove");
            displayAsAdmin();
            return;
        }

        System.out.print("Enter name of teacher you want to remove or type B to exit to the Admin Menu: ");
        String searchName = s.nextLine().trim();

        if (searchName.equalsIgnoreCase("B")) {
            displayAsAdmin();
            return;
        }

        List<String> matchedKeys = new ArrayList<>();
        for (String key : teachersData.info.keySet()) {
            ArrayList<String> details = teachersData.info.get(key);
            if (!details.isEmpty() && details.get(0).equalsIgnoreCase(searchName)) {
                matchedKeys.add(key);
            }
        }

        if (matchedKeys.isEmpty()) {
            System.out.println("No teachers found in database with the name " + searchName);
            return;
        }

        String selectedKey;
        if (matchedKeys.size() == 1) {
            selectedKey = matchedKeys.get(0);
        } else {
            System.out.println("Multiple teachers foudn with that name:");
            for (String key : matchedKeys) {
                ArrayList<String> details = teachersData.info.get(key);
                System.out.println("ID: " + key + " - " + details);
            }

            System.out.print("Enter ID of teacher you want to remove from database: ");
            selectedKey = s.nextLine().trim();
            if (!matchedKeys.contains(selectedKey)) {
                System.out.println("Invalid ID inputted");
                return;
            }
        }
        
        if (teachersData.info.containsKey(selectedKey)) {
            ArrayList<String> details = teachersData.info.get(selectedKey);
            System.out.println("teacher to remove ID: " + selectedKey + " - Details: " + details);
            sentinel = true;
            while (sentinel) {
                System.out.print("Confirm removal?(Y/N): ");
                String choice = s.nextLine().trim();
                if (choice.equalsIgnoreCase("y")) {
                    teachersData.info.remove(selectedKey);
                    if (teachersData.idCounter > 1) {
                        teachersData.idCounter--;
                    }
                    try {
                        System.out.println("Teacher removed successfully");
                        teachersData.saveToFile(SchoolSystem.teacherFile);
                    } catch (Exception e) {
                        System.err.println("Error removeing from file: " + e.getMessage());
                    }
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
        } else {
            System.out.println("Teacher ID not found.");
        }
    }

    private void editTeacher() {

        System.out.print("Enter Teacher name to edit or type B to go back: ");
        String teacherName = s.nextLine().trim();
        
        if (teacherName.equalsIgnoreCase("b")) {
            displayAsAdmin();
        }
        
        List<String> matchedKeys = new ArrayList<>();
        for (String key : teachersData.info.keySet()) {
            ArrayList<String> details = teachersData.info.get(key);
            if (!details.isEmpty() && details.get(0).equalsIgnoreCase(teacherName)) {
                matchedKeys.add(key);
            }
        }

        if (matchedKeys.isEmpty()) {
            System.out.println("No teacher found in the database with the name " + teacherName + ".");
            editTeacher();
            return;
        }

        String selectedKey;
        if (matchedKeys.size() == 1) {
            selectedKey = matchedKeys.get(0);
        } else {
            System.out.println("Multiple teachers found with that name:");
            for (String key : matchedKeys) {
                ArrayList<String> details = teachersData.info.get(key);
                System.out.println("ID: " + key + " - " + details);
            }

            System.out.print("Enter ID of teacher you want to change information: ");
            selectedKey = s.nextLine().trim();
            if (!matchedKeys.contains(selectedKey)) {
                System.out.println("Invalid ID inputted");
                return;
            }
        }

        ArrayList<String> t = teachersData.info.get(selectedKey);

        System.out.println("Current details for ID " + selectedKey + ":");
        System.out.println("Name: " + t.get(0));
        System.out.println("DOB: " + t.get(1));
        System.out.println("Contact: " + t.get(2));
        System.out.println("Gender: " + t.get(3));
        System.out.println("GPA: " + t.get(4));
        System.out.println("Course: " + t.get(5));

        System.out.println("1 - Change Name");
        System.out.println("2 - Change DOB");
        System.out.println("3 - Change Contact");
        System.out.println("4 - Change Gender");
        System.out.println("5 - Change Subject");
        System.out.println("6 - Change Schedule");
        System.out.println("7 - Change credential");
        System.out.println("8 - Back");

        int c = SchoolSystem.choice(1, 8);
        switch (c) {
            case 1 -> {
                name();
                t.set(0, name);
            }
            case 2 -> {
                dob();
                t.set(1, dob);
            }
            case 3 -> {
                contactDetails();
                t.set(2, contact);
            }
            case 4 -> {
                gender();
                t.set(3, gender);
            }
            case 5 -> {
                assignedSubject();
                t.set(4, subject);
            }
            case 6 -> {
                timeSchedule();
                t.set(5, schedule);
            }
            case 7 -> {
                setPassword();
                t.set(6, password);
            }
            case 8 -> {
                displayAsAdmin();
                return;
            }
        }

        System.out.println("Teacher Updated.");
        teachersData.saveToFile(SchoolSystem.teacherFile);

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
