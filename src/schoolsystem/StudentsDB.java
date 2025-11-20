package schoolsystem;

import java.util.*;

interface PDI {

    public void studentID();

    public void name(String name);

    public void dob(int date, String month, int year);

    public void contactDetails(String contactNum);

    public void gender(String gender);

}

interface APD {

    public void GPA(double gpa);

}

interface ASF {

    public void courseInformation(String course);

}

public class StudentsDB implements PDI, APD, ASF {

    @Override
    public void studentID() {

        Random r = new Random();

        int idNum = r.nextInt();
        String id = String.valueOf(idNum);
        System.out.println(id);

    }

    @Override
    public void name(String name) {
        System.out.println(name);
    }

    @Override
    public void dob(int day, String month, int year) {
        int monthNum = 0;
        if (month.equalsIgnoreCase("January")) {
            monthNum = 1;
        } else if (month.equalsIgnoreCase("Febuary")) {
            monthNum = 2;
        } else if (month.equalsIgnoreCase("March")) {
            monthNum = 3;
        } else if (month.equalsIgnoreCase("April")) {
            monthNum = 4;
        } else if (month.equalsIgnoreCase("May")) {
            monthNum = 5;
        } else if (month.equalsIgnoreCase("June")) {
            monthNum = 6;
        } else if (month.equalsIgnoreCase("July")) {
            monthNum = 7;
        } else if (month.equalsIgnoreCase("August")) {
            monthNum = 8;
        } else if (month.equalsIgnoreCase("September")) {
            monthNum = 9;
        } else if (month.equalsIgnoreCase("October")) {
            monthNum = 10;
        } else if (month.equalsIgnoreCase("November")) {
            monthNum = 11;
        } else if (month.equalsIgnoreCase("December")) {
            monthNum = 12;
        } else {
            System.out.println("Unknown Month");
        }
        System.out.println(monthNum + "/" + day + "/" + year);
    }
    
    public void dob(int day, int month, int year) {
        System.out.println(month + "/" + day + "/" + year);
    }

    @Override
    public void contactDetails(String contactNum) {
        System.out.println(contactNum);
    }

    @Override
    public void gender(String gender) {
        System.out.println(gender);
    }

    @Override
    public void GPA(double gpa) {
        System.out.println(gpa);
    }

    @Override
    public void courseInformation(String course) {
        System.out.println(course);
    }

}
