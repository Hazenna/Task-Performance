package schoolsystem;

import java.util.*;

/**
 *
 * @author Erich
 */
class Subject {

    String name;
    LinkedList<Course> courses;

    Subject(String name) {
        this.name = name;
        this.courses = new LinkedList<>();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Course {

    String name;
    LinkedList<Subject> subjects;

    Course(String name) {
        this.name = name;
        this.subjects = new LinkedList<>();
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Subjects {

    public static SchoolSystem system = new SchoolSystem();

    public LinkedList<Course> courses;
    private LinkedList<Subject> subjects;

    public Subjects() {
        courses = new LinkedList<>();
        subjects = new LinkedList<>();

        initializeData();
    }

    private void initializeData() {

        Course it101 = new Course("IT101");
        courses.add(it101);
        addSubjectToCourseInternal(it101, "Programming");
        addSubjectToCourseInternal(it101, "Networking");
        addSubjectToCourseInternal(it101, "Databases");
        addSubjectToCourseInternal(it101, "Cybersecurity");
        addSubjectToCourseInternal(it101, "AI");

        Course cpe102 = new Course("CPE102");
        courses.add(cpe102);
        addSubjectToCourseInternal(cpe102, "Circuits");
        addSubjectToCourseInternal(cpe102, "Microprocessors");
        addSubjectToCourseInternal(cpe102, "Embedded Systems");
        addSubjectToCourseInternal(cpe102, "Digital Logic");
        addSubjectToCourseInternal(cpe102, "Robotics");

        Course hm103 = new Course("HM103");
        courses.add(hm103);
        addSubjectToCourseInternal(hm103, "History");
        addSubjectToCourseInternal(hm103, "Literature");
        addSubjectToCourseInternal(hm103, "Philosophy");
        addSubjectToCourseInternal(hm103, "Ethics");
        addSubjectToCourseInternal(hm103, "Sociology");

        Course tm104 = new Course("TM104");
        courses.add(tm104);
        addSubjectToCourseInternal(tm104, "Project Management");
        addSubjectToCourseInternal(tm104, "Leadership");
        addSubjectToCourseInternal(tm104, "Finance");
        addSubjectToCourseInternal(tm104, "Marketing");
        addSubjectToCourseInternal(tm104, "Operations");
    }

    private void addSubjectToCourseInternal(Course course, String subjectName) {
        Subject subject = findSubject(subjectName);
        if (subject == null) {
            subject = new Subject(subjectName);
            subjects.add(subject);
        }
        if (!course.subjects.contains(subject)) {
            course.subjects.add(subject);
            subject.courses.add(course);
        }
    }

    public void addCourse(String courseName) {
        if (findCourse(courseName) == null) {
            courses.add(new Course(courseName));
            System.out.println("Course added: " + courseName);
        } else {
            System.out.println("Course already exists: " + courseName);
        }
    }

    public void addSubject(String subjectName) {
        if (findSubject(subjectName) == null) {
            subjects.add(new Subject(subjectName));
            System.out.println("Subject added: " + subjectName);
        } else {
            System.out.println("Subject already exists: " + subjectName);
        }
    }

    public void addSubjectToCourse(String courseName, String subjectName) {
        Course course = findCourse(courseName);
        Subject subject = findSubject(subjectName);

        if (course == null) {
            System.out.println("Course not found: " + courseName);
            return;
        }
        if (subject == null) {
            System.out.println("Subject not found: " + subjectName);
            return;
        }

        if (!course.subjects.contains(subject)) {
            course.subjects.add(subject);
            subject.courses.add(course);
            System.out.println("Subject '" + subjectName + "' added to course '" + courseName + "'");
        } else {
            System.out.println("Subject '" + subjectName + "' is already in course '" + courseName + "'");
        }
    }

    public void deleteSubjectFromCourse(String courseName, String subjectName) {
        Course course = findCourse(courseName);
        Subject subject = findSubject(subjectName);

        if (course == null) {
            System.out.println("Course not found: " + courseName);
            return;
        }
        if (subject == null) {
            System.out.println("Subject not found: " + subjectName);
            return;
        }

        if (course.subjects.remove(subject)) {
            subject.courses.remove(course);
            System.out.println("Subject '" + subjectName + "' deleted from course '" + courseName + "'");
        } else {
            System.out.println("Subject '" + subjectName + "' not found in course '" + courseName + "'");
        }
    }

    public void checkSubjectInCourse(String courseName, String subjectName) {
        Course course = findCourse(courseName);
        Subject subject = findSubject(subjectName);

        if (course == null) {
            System.out.println("Course not found: " + courseName);
            return;
        }
        if (subject == null) {
            System.out.println("Subject not found: " + subjectName);
            return;
        }

        if (course.subjects.contains(subject)) {
            System.out.println("Yes, subject '" + subjectName + "' is in course '" + courseName + "'");
        } else {
            System.out.println("No, subject '" + subjectName + "' is not in course '" + courseName + "'");
        }
    }

    public void listAllCourses() {
        System.out.println("Available Courses (" + courses.size() + " total):");
        if (courses.isEmpty()) {
            System.out.println("  No courses available.");
        } else {
            int index = 1;
            for (Course cour : courses) {
                System.out.println("  " + index + ". " + cour.name);
                index++;
            }
        }
    }

    public void listAllSubjects() {
        System.out.println("Available Subjects (" + subjects.size() + " total):");
        if (subjects.isEmpty()) {
            System.out.println("  No subjects available.");
        } else {
            int index = 1;
            for (Subject subj : subjects) {
                System.out.println("  " + index + ". " + subj.name);
                index++;
            }
        }
    }

    public void retrieveSubjectsForCourse(Scanner scanner) {
        listAllCourses();
        if (courses.isEmpty()) {
            return;
        }
        System.out.print("Enter the number of the course to open: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > courses.size()) {
                System.out.println("Invalid choice. Please select a valid number.");
                return;
            }
            Course selectedCourse = courses.get(choice - 1);

            displaySubjectsForCourse(selectedCourse);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a number.");
        }
    }

    private void displaySubjectsForCourse(Course course) {
        System.out.println("Subjects in course '" + course.name + "' (" + course.subjects.size() + " total):");
        if (course.subjects.isEmpty()) {
            System.out.println("  No subjects in this course.");
        } else {
            int index = 1;
            for (Subject subj : course.subjects) {
                System.out.println("  " + index + ". " + subj.name);
                index++;
            }
        }
    }

    public void retrieveCoursesForSubject(Scanner scanner) {
        listAllSubjects();
        if (subjects.isEmpty()) {
            return;
        }

        System.out.print("Enter the number of the subject to view courses: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > subjects.size()) {
                System.out.println("Invalid choice. Please select a valid number.");
                return;
            }
            Subject selectedSubject = subjects.get(choice - 1);
            System.out.println("Courses for subject '" + selectedSubject.name + "' (" + selectedSubject.courses.size() + " total):");
            if (selectedSubject.courses.isEmpty()) {
                System.out.println("  No courses for this subject.");
            } else {
                int index = 1;
                for (Course cour : selectedSubject.courses) {
                    System.out.println("  " + index + ". " + cour.name);
                    index++;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a number.");
        }
    }

    private Course findCourse(String name) {
        for (Course c : courses) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return null;
    }

    private Subject findSubject(String name) {
        for (Subject s : subjects) {
            if (s.name.equals(name)) {
                return s;
            }
        }
        return null;
    }

    public void displayAsUser() {
        boolean loop = true;

        while (loop) {
            System.out.println("\n===== SUBJECTS (USER VIEW) =====");
            System.out.println("1 - List All Courses");
            System.out.println("2 - List All Subjects");
            System.out.println("3 - View Subjects under a Course");
            System.out.println("4 - View Courses of a Subject");
            System.out.println("5 - Back");

            int choice = SchoolSystem.choice(1, 5);

            switch (choice) {
                case 1 ->
                    listAllCourses();
                case 2 ->
                    listAllSubjects();
                case 3 ->
                    retrieveSubjectsForCourse(SchoolSystem.s);
                case 4 ->
                    retrieveCoursesForSubject(SchoolSystem.s);
                case 5 -> {
                    system.choicesMenu("User");
                    loop = false;
                }
            }
        }
    }

    public void displayAsAdmin() {
        boolean loop = true;

        while (loop) {
            System.out.println("\n===== SUBJECTS (ADMIN VIEW) =====");
            System.out.println("1 - Add Course");
            System.out.println("2 - Add Subject");
            System.out.println("3 - Add Subject to Course");
            System.out.println("4 - Delete Subject from Course");
            System.out.println("5 - Check if Subject is in Course");
            System.out.println("6 - View Subjects under a Course");
            System.out.println("7 - View Courses of a Subject");
            System.out.println("8 - List All Courses");
            System.out.println("9 - List All Subjects");
            System.out.println("10 - Back");

            int choice = SchoolSystem.choice(1, 10);

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter course name: ");
                    String name = SchoolSystem.s.nextLine();
                    addCourse(name);
                }
                case 2 -> {
                    System.out.print("Enter subject name: ");
                    String name = SchoolSystem.s.nextLine();
                    addSubject(name);
                }
                case 3 -> {
                    System.out.print("Enter course name: ");
                    String c = SchoolSystem.s.nextLine();
                    System.out.print("Enter subject name: ");
                    String s = SchoolSystem.s.nextLine();
                    addSubjectToCourse(c, s);
                }
                case 4 -> {
                    System.out.print("Enter course name: ");
                    String c = SchoolSystem.s.nextLine();
                    System.out.print("Enter subject name: ");
                    String s = SchoolSystem.s.nextLine();
                    deleteSubjectFromCourse(c, s);
                }
                case 5 -> {
                    System.out.print("Enter course name: ");
                    String c = SchoolSystem.s.nextLine();
                    System.out.print("Enter subject name: ");
                    String s = SchoolSystem.s.nextLine();
                    checkSubjectInCourse(c, s);
                }
                case 6 ->
                    retrieveSubjectsForCourse(SchoolSystem.s);
                case 7 ->
                    retrieveCoursesForSubject(SchoolSystem.s);
                case 8 ->
                    listAllCourses();
                case 9 ->
                    listAllSubjects();
                case 10 -> {
                    system.choicesMenu("Admin");
                    loop = false;
                }
            }
        }
    }
}
