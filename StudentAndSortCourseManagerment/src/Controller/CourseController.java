package Controller;

import Model.Course;
import Model.Student;
import Services.CourseService;
import Services.StudentService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Inputter;
import utils.Menu;

public class CourseController {
    private CourseService courseService;
    private StudentService studentService;
    private Inputter inputter;
    private Menu<Student> studentMenu;
    private static final String COURSES_FILE = "Courses.txt";

    public CourseController(CourseService courseService, StudentService studentService, Inputter inputter) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.inputter = inputter;
        this.studentMenu = new Menu<>("Select a Student", inputter);
    }

    // Feature 6: Add a new course
    public void addCourse() {
        ArrayList<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("Cannot add course: No students in system!");
            return;
        }

        // Enter unique course ID
        String courseId = readUniqueCourseId();

        // Select student from list
        System.out.println("--- Select a student ---");
        Student selectedStudent = studentMenu.ref_getChoice(students);
        String selectedStudentId = selectedStudent.getStudentId();

        // Enter course name
        String courseName = inputter.getAString("Enter course name: ",
                "Course name cannot be empty! Try again: ");

        // Enter duration (weeks >= 1)
        int duration = inputter.getAnInteger("Enter duration (weeks, >= 1): ",
                "Duration must be >= 1! Try again: ", 1, Integer.MAX_VALUE);

        // Enter start date (must be in the future)
        LocalDate startDate = inputter.getDate("Enter start date (dd/MM/yyyy, must be after today): ",
                "Invalid date or not in the future! Try again: ");

        Course newCourse = new Course(courseId, selectedStudentId, courseName, duration, startDate);
        courseService.addCourse(newCourse);
        System.out.println("Course added successfully!");
    }

    // Feature 7: List all courses grouped by student
    public void listCoursesGroupedByStudent() {
        ArrayList<Student> students = studentService.getAllStudents();
        Map<String, List<Course>> grouped = courseService.getCoursesGroupedByStudent();

        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        for (Student student : students) {
            System.out.println(student.toScreen());
            List<Course> courses = grouped.get(student.getStudentId());
            if (courses == null || courses.isEmpty()) {
                System.out.println("  (No courses registered)");
            } else {
                for (Course course : courses) {
                    System.out.println("  + " + course.toScreen());
                }
            }
        }
    }

    // Feature 8: Calculate total study duration by student ID
    public void calculateStudyDuration() {
        String studentId = inputter.getAString("Enter Student ID: ",
                "Invalid format! Try again: ", Inputter.stuIdRegex).toUpperCase();
        if (!studentService.checkExist(studentId)) {
            System.out.println("Student ID does not exist!");
            return;
        }
        int totalDuration = courseService.calculateTotalDuration(studentId);
        System.out.println("Total study duration: " + totalDuration + " weeks");
    }

    // Delegated methods for MainView
    public boolean loadData(String url) {
        return courseService.loadData(url);
    }

    public boolean saveData(String url) {
        return courseService.saveData(url);
    }

    public boolean hasUnsavedChanges() {
        return courseService.isChanged();
    }

    public void markSaved() {
        courseService.setSaved();
    }

    // Helper: read a unique course ID
    private String readUniqueCourseId() {
        while (true) {
            String courseId = inputter.getAString("Enter Course ID (e.g. CSE201): ",
                    "Invalid format! Try again: ", Inputter.courseIdRegex).toUpperCase();
            if (!courseService.checkExist(courseId)) {
                return courseId;
            }
            System.out.println("Course ID already exists! Please enter a different ID.");
        }
    }
}
