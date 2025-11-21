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
    private String getGPA;
    private String course;

    @Override
    public void studentID() {

        int idNum = r.nextInt();
        String id = String.valueOf(idNum);
        this.idString = id;

    }

    @Override
    public void name() {
        System.out.print("Enter Name: ");
        String name = s.nextLine();
        this.name = name;
    }

    @Override
    public void dob() {
        System.out.print("Enter Date of Birth (MM/DD/YYYY): ");
        String date = s.nextLine();
        this.dob = date;
    }

    @Override
    public void contactDetails() {
        sentinel = true;
        System.out.print("Enter Contact Number: ");
        while (sentinel) {
            String contact = s.nextLine();
            if (contact.equals("")) {
                System.out.print("Number is Needed, Please Enter Contact Number: ");
            } else {
                this.contact = contact;
            }
        }
    }

    @Override
    public void gender() {
        System.out.print("Enter Gender(Optional, Leave Blank): ");
        String gender = s.nextLine().strip();
        if (gender.equals("")) {
            this.gender = "Not given";
        } else {
            this.gender = gender;
        }
    }

    @Override
    public void GPA() {
        //Will need cooperation on Subjects to implement a method for calculating GPA
    }

    @Override
    public void courseInformation() {
        System.out.println("Enter Course or NA if Undicided: ");
        String course = s.nextLine();
        if (course.equalsIgnoreCase("NA")) {
            this.course = "Undecided";
        } else {
            this.course = course;
        }
    }

    public void displayAsUser() {
        System.out.println("Students in Database:");

    }

    public void displayAsAdmin() {
        System.out.println("1 - Edit a student in database\n2 - Remove a student in database\n3 - add a student in database\n4 - to go back");
        sentinel = true;
        while (sentinel) {
            int userChoice = system.choice(1, 4);
            switch (userChoice) {
                case 1 -> {
                }
                case 2 -> {
                }
                case 3 ->
                    addUser();
                case 4 ->
                    system.menu();
            }
        }
    }

    public void addUser() {
        sentinel = true;
        studentID();
        int id = Integer.parseInt(this.idString);
        while (sentinel) {
            System.out.println("Add a student? \n1 - Yes\n2 - No");
            int userChoice = system.choice(1, 2);
            if (userChoice == 1) {
                String idNum = this.idString;
                id = r.nextInt();
                sentinel = true;
                while (sentinel) {
                    System.out.println("Enter The following Information for ID no. " + idNum);

                }
            } else if (userChoice == 2) {
                displayAsAdmin();
                sentinel = false;
            }
        }
    }

}
