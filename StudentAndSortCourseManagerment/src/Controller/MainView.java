package Controller;

import utils.Inputter;
import utils.Menu;

public class MainView {
    private StudentController studentController;
    private CourseController courseController;
    private Inputter inputter;
    private Menu<String> mainMenu;
    private static final String STUDENTS_FILE = "Students.txt";
    private static final String COURSES_FILE = "Courses.txt";

    public MainView(StudentController studentController, CourseController courseController, Inputter inputter) {
        this.studentController = studentController;
        this.courseController = courseController;
        this.inputter = inputter;

        // Build main menu
        mainMenu = new Menu<>("Student And Short Course Management System", inputter);
        mainMenu.addNewOption("List all students");
        mainMenu.addNewOption("Add a new student");
        mainMenu.addNewOption("Search for a student by ID");
        mainMenu.addNewOption("Update a student's GPA by ID");
        mainMenu.addNewOption("List all students by major");
        mainMenu.addNewOption("Add a new course");
        mainMenu.addNewOption("List all courses by student (grouped)");
        mainMenu.addNewOption("Calculate total study duration by student ID");
        mainMenu.addNewOption("Remove a student by ID");
        mainMenu.addNewOption("Sort students by GPA");
        mainMenu.addNewOption("Save data to files");
        mainMenu.addNewOption("Quit program");
    }

    public void run() {
        // Load data from files
        studentController.loadData(STUDENTS_FILE);
        courseController.loadData(COURSES_FILE);

        // Main loop
        boolean running = true;
        while (running) {
            running = viewMenu();
        }
    }

    private boolean viewMenu() {
        mainMenu.show();
        int choice = mainMenu.getChoice();

        switch (choice) {
            case 1:
                studentController.listAllStudents();
                break;
            case 2:
                studentController.addStudent();
                break;
            case 3:
                studentController.searchStudent();
                break;
            case 4:
                studentController.updateStudentGpa();
                break;
            case 5:
                studentController.listStudentsByMajor();
                break;
            case 6:
                courseController.addCourse();
                break;
            case 7:
                courseController.listCoursesGroupedByStudent();
                break;
            case 8:
                courseController.calculateStudyDuration();
                break;
            case 9:
                studentController.removeStudent();
                break;
            case 10:
                studentController.sortStudentsByGpa();
                break;
            case 11:
                saveAllFiles();
                break;
            case 12:
                return handleQuit();
        }
        return true;
    }

    private boolean saveAllFiles() {
        boolean ok1 = studentController.saveData(STUDENTS_FILE);
        boolean ok2 = courseController.saveData(COURSES_FILE);
        if (ok1 && ok2) {
            studentController.markSaved();
            courseController.markSaved();
            System.out.println("All data saved to files successfully!");
            return true;
        } else {
            System.out.println("Error saving data to files!");
            return false;
        }
    }

    private boolean handleQuit() {
        if (hasUnsavedChanges()) {
            if (inputter.getYesNo("Do you want to save the changes before exiting?")) {
                saveAllFiles();
            }
        }
        System.out.println("Goodbye!");
        return false; // false = stop the loop
    }

    private boolean hasUnsavedChanges() {
        return studentController.hasUnsavedChanges() || courseController.hasUnsavedChanges();
    }
}
