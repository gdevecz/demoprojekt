package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {

    private String name;

    private List<Mark> marks;

    public Student(String name) {
        Validator.isStudentNameValid(name);
        this.name = name;
        marks = new ArrayList<>();
    }

    public Student(String name, List<Mark> marks) {
        this.name = name;
        this.marks = marks;
    }

    public void grading(Mark mark) {
        Validator.isMarkValid(mark);
        marks.add(mark);
    }

    public double calculateAverage() {
        return getTwoDigitPlaceNumber(marks.stream()
                .mapToInt(mark-> mark.getMarkType().getGrade())
                .average()
                .orElse(0.0));
    }

    public double calculateSubjectAverage(Subject subject) {
        return getTwoDigitPlaceNumber(marks.stream()
                .filter(mark -> subject.equals(mark.getSubject()))
                .mapToInt(mark-> mark.getMarkType().getGrade())
                .average()
                .orElse(0.0));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" marks:");
        marks.forEach(mark -> sb.append(" ").append(mark.getSubject().getSubjectName()).append(": ").append(mark));
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public List<Mark> getMarks() {
        return List.copyOf(marks);
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private double getTwoDigitPlaceNumber(double number) {
        return (Math.round(number * 100)) / 100.0;
    }
}
