package schoolsystem;

import java.util.*;

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
    private String subject = "None";
    private String schedule = "Not Assigned";

    @Override
    public void teacherID() {
        int idNum = Math.abs(r.nextInt());
        String id = String.valueOf(idNum);
        this.idString = id;
        info.put(id, new ArrayList<>());
    }

    @Override
    public void name() {
        System.out.print("Enter Teacher Name: ");
        this.name = s.nextLine();
    }

    @Override
    public void dob() {
        System.out.print("Enter Date of Birth (MMDDYYYY): ");
        sentinel = true;
        while (sentinel) {
            try {
                String date = s.nextLine().trim();
                Integer.valueOf(date);
                this.dob = date;
                sentinel = false;
            } catch (NumberFormatException e) {
                System.out.print("Invalid format. Use numbers only (MMDDYYYY): ");
            }
        }
    }

    @Override
    public void contactDetails() {
        sentinel = true;
        System.out.print("Enter Contact Number: ");
        while (sentinel) {
            String c = s.nextLine();
            if (c.equals("")) {
                System.out.print("Contact Required. Enter Contact Number: ");
            } else {
                this.contact = c;
                sentinel = false;
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
        this.subject = s.nextLine();
    }

    @Override
    public void timeSchedule() {
        System.out.print("Enter Teacher Time Schedule: ");
        this.schedule = s.nextLine();
    }

    public void displayAsUser() {
        System.out.println("\nTeachers in Database:");
        if (info.isEmpty()) {
            System.out.println("No teachers available.");
            return;
        }

        info.forEach((id, list) -> {
            System.out.println("\nTeacher ID: " + id);
            System.out.println("Name: " + list.get(0));
            System.out.println("DOB: " + list.get(1));
            System.out.println("Contact: " + list.get(2));
            System.out.println("Gender: " + list.get(3));
            System.out.println("Subject: " + list.get(4));
            System.out.println("Schedule: " + list.get(5));
        });
    }

    public void displayAsAdmin() {
        System.out.println("\n1 - Edit Teacher in Database");
        System.out.println("2 - Remove Teacher");
        System.out.println("3 - Add Teacher");
        System.out.println("4 - Go Back");

        sentinel = true;
        while (sentinel) {
            int userChoice = SchoolSystem.choice(1, 4);
            switch (userChoice) {
                case 1 ->
                    editTeacher();
                case 2 ->
                    removeTeacher();
                case 3 ->
                    addTeacher();
                case 4 ->
                    system.menu();
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
                info.get(idNum).add(this.name);

                dob();
                info.get(idNum).add(this.dob);

                contactDetails();
                info.get(idNum).add(this.contact);

                gender();
                info.get(idNum).add(this.gender);

                assignedSubject();
                info.get(idNum).add(this.subject);

                timeSchedule();
                info.get(idNum).add(this.schedule);

                System.out.println("Teacher Added Successfully.");
                break;

            } else if (userChoice == 2) {
                displayAsAdmin();
                sentinel = false;
            }
        }
    }

    private void removeTeacher() {
        System.out.print("Enter Teacher ID to remove: ");
        String rem = s.nextLine();

        if (info.containsKey(rem)) {
            info.remove(rem);
            System.out.println("Teacher removed.");
        } else {
            System.out.println("Teacher ID not found.");
        }
    }

    private void editTeacher() {
        System.out.print("Enter Teacher ID to edit: ");
        String id = s.nextLine();

        if (!info.containsKey(id)) {
            System.out.println("Teacher ID not found.");
            return;
        }
        ArrayList<String> t = info.get(id);

        System.out.println("1 - Change Name");
        System.out.println("2 - Change Contact");
        System.out.println("3 - Change Gender");
        System.out.println("4 - Change Subject");
        System.out.println("5 - Change Schedule");
        System.out.println("6 - Back");

        int c = SchoolSystem.choice(1, 6);

        switch (c) {
            case 1 -> {
                name();
                t.set(0, name);
            }
            case 2 -> {
                contactDetails();
                t.set(2, contact);
            }
            case 3 -> {
                gender();
                t.set(3, gender);
            }
            case 4 -> {
                assignedSubject();
                t.set(4, subject);
            }
            case 5 -> {
                timeSchedule();
                t.set(5, schedule);
            }
        }

        System.out.println("Teacher Updated.");
    }
}
