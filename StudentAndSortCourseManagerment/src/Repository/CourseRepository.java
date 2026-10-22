package Repository;

import Model.Course;
import utils.FileHelper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CourseRepository {
    private ArrayList<Course> courseList = new ArrayList<>();
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private FileHelper<Course> fhCourse = new FileHelper<Course>(courseList) {
        @Override
        public Course handleLine(String line) {
            try {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.countTokens() < 5) return null;
                String courseId = st.nextToken().trim();
                String stuId = st.nextToken().trim();
                String name = st.nextToken().trim();
                int duration = Integer.parseInt(st.nextToken().trim());
                LocalDate date = LocalDate.parse(st.nextToken().trim(), DATE_FORMAT);
                return new Course(courseId, stuId, name, duration, date);
            } catch (Exception e) {
                return null;
            }
        }
    };

    public CourseRepository() {
    }

    public boolean add(Course course) {
        return courseList.add(course);
    }

    public ArrayList<Course> getAll() {
        return courseList;
    }

    public boolean loadFromFile(String url) {
        return fhCourse.loadFromFile(url);
    }

    public boolean saveToFile(String url) {
        return fhCourse.saveToFile(url);
    }
}
