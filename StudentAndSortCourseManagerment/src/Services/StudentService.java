package Services;

import Model.Student;
import Repository.StudentRepository;
import Repository.CourseRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class StudentService {
    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private boolean isChanged = false;

    public StudentService(StudentRepository studentRepo, CourseRepository courseRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setSaved() {
        isChanged = false;
    }

    public boolean addStudent(Student student) {
        boolean result = studentRepo.add(student);
        if (result) {
            isChanged = true;
        }
        return result;
    }

    public boolean updateStudentGpa(String studentId, double newGpa) {
        boolean result = studentRepo.updateGpa(studentId, newGpa);
        if (result) {
            isChanged = true;
        }
        return result;
    }

    public Student searchStudent(String id) {
        return studentRepo.getAll().stream()
                .filter(s -> s.getStudentId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Student> getAllStudents() {
        return studentRepo.getAll();
    }

    public ArrayList<Student> getStudentsByMajor(String major) {
        return studentRepo.getAll().stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Student> getStudentsSortedByGpaAsc() {
        return studentRepo.getAll().stream()
                .sorted(Comparator.comparingDouble(Student::getGpa))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public DeleteStudentResult removeStudent(String studentId) {
        // Step 1: Check if student exists
        if (!checkExist(studentId)) {
            return DeleteStudentResult.NOT_FOUND;
        }
        // Step 2: Check if student has courses
        boolean hasCourses = courseRepo.getAll().stream()
                .anyMatch(c -> c.getStudentId().equalsIgnoreCase(studentId));
        if (hasCourses) {
            return DeleteStudentResult.HAS_COURSES;
        }
        // Step 3: Remove student
        studentRepo.remove(studentId);
        isChanged = true;
        return DeleteStudentResult.SUCCESS;
    }

    public boolean checkExist(String studentId) {
        return studentRepo.getAll().stream()
                .anyMatch(s -> s.getStudentId().equalsIgnoreCase(studentId));
    }

    public boolean loadData(String url) {
        return studentRepo.loadFromFile(url);
    }

    public boolean saveData(String url) {
        return studentRepo.saveToFile(url);
    }
}
