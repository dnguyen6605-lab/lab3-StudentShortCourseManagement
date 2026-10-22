package Controller;

import Model.Student;
import Services.DeleteStudentResult;
import Services.StudentService;
import java.util.ArrayList;
import utils.Inputter;

public class StudentController {
    private StudentService studentService;
    private Inputter inputter;
    private static final String STUDENTS_FILE = "Students.txt";

    public StudentController(StudentService studentService, Inputter inputter) {
        this.studentService = studentService;
        this.inputter = inputter;
    }

    // Feature 1: List all students
    public void listAllStudents() {
        ArrayList<Student> stuList = studentService.getAllStudents();
        if (stuList.isEmpty()) {
            System.out.println("Student list is empty.");
            return;
        }
        System.out.println(String.format("%-10s | %-25s | %-25s | %5s",
                "ID", "Full Name", "Major", "GPA"));
        System.out.println("-----------|---------------------------|---------------------------|-------");
        for (Student student : stuList) {
            System.out.println(student.toScreen());
        }
    }

    // Feature 2: Add a new student
    public void addStudent() {
        do {
            // Enter unique student ID
            String id = readUniqueStudentId();
            // Enter full name (at least 2 words)
            String name = inputter.getAString("Enter student full name: ",
                    "Name must have at least 2 words! Try again: ", Inputter.NAME_PATTERN);
            // Enter major
            String major = inputter.getAString("Enter student major: ",
                    "Major cannot be empty! Try again: ");
            // Enter GPA
            double gpa = inputter.getADouble("Enter GPA (0.0 - 4.0): ",
                    "GPA must be between 0.0 and 4.0! Try again: ", 0.0, 4.0);

            Student newStudent = new Student(id, name, major, gpa);
            studentService.addStudent(newStudent);
            System.out.println("Student added successfully!");
        } while (inputter.getYesNo("Do you want to continue adding?"));
    }

    // Feature 3: Search for a student by ID
    public void searchStudent() {
        String id = inputter.getAString("Enter Student ID to search: ",
                "Invalid format! Try again: ", Inputter.stuIdRegex).toUpperCase();
        Student student = studentService.searchStudent(id);
        if (student == null) {
            System.out.println("Student ID does not exist!");
        } else {
            System.out.println(String.format("%-10s | %-25s | %-25s | %5s",
                    "ID", "Full Name", "Major", "GPA"));
            System.out.println("-----------|---------------------------|---------------------------|-------");
            System.out.println(student.toScreen());
        }
    }

    // Feature 4: Update a student's GPA by ID
    public void updateStudentGpa() {
        String id = inputter.getAString("Enter Student ID to update: ",
                "Invalid format! Try again: ", Inputter.stuIdRegex).toUpperCase();
        Student student = studentService.searchStudent(id);
        if (student == null) {
            System.out.println("Student ID does not exist!");
            return;
        }
        System.out.println("Current info: " + student.toScreen());
        double newGpa = inputter.getADouble("Enter new GPA (0.0 - 4.0): ",
                "GPA must be between 0.0 and 4.0! Try again: ", 0.0, 4.0);
        studentService.updateStudentGpa(id, newGpa);
        System.out.println("GPA updated successfully!");
    }

    // Feature 5: List all students by major
    public void listStudentsByMajor() {
        String major = inputter.getAString("Enter major to filter: ",
                "Major cannot be empty! Try again: ");
        ArrayList<Student> result = studentService.getStudentsByMajor(major);
        if (result.isEmpty()) {
            System.out.println("No students found in this major.");
            return;
        }
        System.out.println(String.format("%-10s | %-25s | %-25s | %5s",
                "ID", "Full Name", "Major", "GPA"));
        System.out.println("-----------|---------------------------|---------------------------|-------");
        for (Student student : result) {
            System.out.println(student.toScreen());
        }
    }

    // Feature 9: Remove a student by ID
    public void removeStudent() {
        String id = inputter.getAString("Enter Student ID to remove: ",
                "Invalid format! Try again: ", Inputter.stuIdRegex).toUpperCase();
        DeleteStudentResult result = studentService.removeStudent(id);
        switch (result) {
            case NOT_FOUND:
                System.out.println("Student ID does not exist!");
                break;
            case HAS_COURSES:
                System.out.println("Cannot delete: Student is assigned to Courses.");
                break;
            case SUCCESS:
                System.out.println("Student deleted successfully!");
                break;
        }
    }

    // Feature 10: Sort students by GPA (ascending)
    public void sortStudentsByGpa() {
        ArrayList<Student> sorted = studentService.getStudentsSortedByGpaAsc();
        if (sorted.isEmpty()) {
            System.out.println("Student list is empty.");
            return;
        }
        System.out.println(String.format("%-10s | %-25s | %-25s | %5s",
                "ID", "Full Name", "Major", "GPA"));
        System.out.println("-----------|---------------------------|---------------------------|-------");
        for (Student student : sorted) {
            System.out.println(student.toScreen());
        }
    }

    public boolean loadData(String url) {
        return studentService.loadData(url);
    }

    public boolean saveData(String url) {
        return studentService.saveData(url);
    }

    public boolean hasUnsavedChanges() {
        return studentService.isChanged();
    }

    public void markSaved() {
        studentService.setSaved();
    }

    private String readUniqueStudentId() {
        while (true) {
            String id = inputter.getAString("Enter Student ID (e.g. STU0001): ",
                    "Invalid format! Try again: ", Inputter.stuIdRegex).toUpperCase();
            if (!studentService.checkExist(id)) {
                return id;
            }
            System.out.println("Student ID already exists! Please enter a different ID.");
        }
    }
}
