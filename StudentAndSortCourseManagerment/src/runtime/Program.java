package runtime;

import java.util.Scanner;
import utils.Inputter;
import Repository.CourseRepository;
import Repository.StudentRepository;
import Services.CourseService;
import Services.StudentService;
import Controller.CourseController;
import Controller.StudentController;
import Controller.MainView;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inputter inputter = new Inputter(sc);

        // Repository
        StudentRepository studentRepo = new StudentRepository();
        CourseRepository courseRepo = new CourseRepository();

        // Service
        StudentService studentService = new StudentService(studentRepo, courseRepo);
        CourseService courseService = new CourseService(courseRepo, studentRepo);

        // Controller
        StudentController studentController = new StudentController(studentService, inputter);
        CourseController courseController = new CourseController(courseService, studentService, inputter);

        // MainView
        MainView mainView = new MainView(studentController, courseController, inputter);

        // Run the application
        mainView.run();
    }
}