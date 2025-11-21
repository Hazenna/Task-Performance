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
    }

    public void displayAsAdmin() {
        System.out.println("1 - Edit a student in database\n2 - Remove a student in database\n3 - add a student in database\n4 - to go back");
        sentinel = true;
        while (sentinel) {
            int userChoice = SchoolSystem.choice(1, 4);
            switch (userChoice) {
                case 1 -> {
                    System.out.println("Work in Progress");
                }
                case 2 -> {
                    System.out.println("Work in Progress");
                }
                case 3 ->
                    addUser();
                case 4 -> {
                    system.menu();
                    sentinel = false;
                }
            }
        }
    }

    public void addUser() {
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

}
