package Model;

public class Student {
    private String studentId;
    private String fullName;
    private String major;
    private double gpa;

    public Student() {
    }

    public Student(String studentId, String fullName, String major, double gpa) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.major = major;
        this.gpa = gpa;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String toScreen() {
        return String.format("%-10s | %-25s | %-25s | %5.2f",
                studentId, fullName, major, gpa);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %.1f",
                studentId, fullName, major, gpa);
    }
}
