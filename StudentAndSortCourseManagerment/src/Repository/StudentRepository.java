package Repository;

import Model.Student;
import utils.FileHelper;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StudentRepository {
    private ArrayList<Student> stuList = new ArrayList<>();
    private FileHelper<Student> fhStudent = new FileHelper<Student>(stuList) {
        @Override
        public Student handleLine(String line) {
            try {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.countTokens() < 4) return null;
                String stuId = st.nextToken().trim();
                String name = st.nextToken().trim();
                String major = st.nextToken().trim();
                double gpa = Double.parseDouble(st.nextToken().trim());
                return new Student(stuId, name, major, gpa);
            } catch (Exception e) {
                return null;
            }
        }
    };

    public StudentRepository() {
    }

    public boolean add(Student student) {
        return stuList.add(student);
    }

    public boolean updateGpa(String studentId, double newGpa) {
        for (Student student : stuList) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                student.setGpa(newGpa);
                return true;
            }
        }
        return false;
    }

    public boolean remove(String studentId) {
        return stuList.removeIf(s -> s.getStudentId().equalsIgnoreCase(studentId));
    }

    public ArrayList<Student> getAll() {
        return stuList;
    }

    public boolean loadFromFile(String url) {
        return fhStudent.loadFromFile(url);
    }

    public boolean saveToFile(String url) {
        return fhStudent.saveToFile(url);
    }
}
