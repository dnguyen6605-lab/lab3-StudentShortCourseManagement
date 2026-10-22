package Services;

import Model.Course;
import Repository.CourseRepository;
import Repository.StudentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseService {
    private CourseRepository courseRepo;
    private StudentRepository studentRepo;
    private boolean isChanged = false;

    public CourseService(CourseRepository courseRepo, StudentRepository studentRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setSaved() {
        isChanged = false;
    }

    public boolean addCourse(Course course) {
        boolean result = courseRepo.add(course);
        if (result) {
            isChanged = true;
        }
        return result;
    }

    public ArrayList<Course> getAllCourses() {
        return courseRepo.getAll();
    }

    public boolean checkExist(String courseId) {
        return courseRepo.getAll().stream()
                .anyMatch(c -> c.getCourseId().equalsIgnoreCase(courseId));
    }

    public boolean hasCoursesOfStudent(String studentId) {
        return courseRepo.getAll().stream()
                .anyMatch(c -> c.getStudentId().equalsIgnoreCase(studentId));
    }

    public ArrayList<Course> getCoursesByStudentId(String studentId) {
        return courseRepo.getAll().stream()
                .filter(c -> c.getStudentId().equalsIgnoreCase(studentId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Map<String, List<Course>> getCoursesGroupedByStudent() {
        return courseRepo.getAll().stream()
                .collect(Collectors.groupingBy(Course::getStudentId));
    }

    public int calculateTotalDuration(String studentId) {
        return courseRepo.getAll().stream()
                .filter(c -> c.getStudentId().equalsIgnoreCase(studentId))
                .mapToInt(Course::getDuration)
                .sum();
    }

    public boolean loadData(String url) {
        return courseRepo.loadFromFile(url);
    }

    public boolean saveData(String url) {
        return courseRepo.saveToFile(url);
    }
}
