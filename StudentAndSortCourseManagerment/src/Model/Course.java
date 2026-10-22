package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Course {
    private String courseId;
    private String studentId;
    private String name;
    private int duration;
    private LocalDate startDate;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Course() {
    }

    public Course(String courseId, String studentId, String name, int duration, LocalDate startDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.name = name;
        this.duration = duration;
        this.startDate = startDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String toScreen() {
        return String.format("%-10s | %-30s | %2d weeks | %s",
                courseId, name, duration,
                startDate != null ? startDate.format(DATE_FORMAT) : "");
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %d, %s",
                courseId, studentId, name, duration,
                startDate != null ? startDate.format(DATE_FORMAT) : "");
    }
}
