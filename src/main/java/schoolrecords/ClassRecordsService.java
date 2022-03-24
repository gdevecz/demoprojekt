package schoolrecords;

import java.util.List;

public class ClassRecordsService {

    private StudentService studentService;

    public ClassRecordsService(StudentService studentService) {
        this.studentService = studentService;
    }

    public long addStudent(Student student) {
        return studentService.saveStudent(student);
    }

    public void removeStudent(Student student) {
        studentService.removeStudent(student);
    }

    public double calculateClassAverage() {
        List<Student> students = studentService.listAllStudents();
        return students.stream()
                .mapToDouble(Student::calculateAverage)
                .average()
                .orElseThrow(() -> new IllegalStateException("No grades."));
    }

    public double calculateClassAverageBySubject(Subject subject) {
        List<Student> students = studentService.listAllStudents();
        return students.stream()
                .mapToDouble(student->student.calculateSubjectAverage(subject))
                .average()
                .orElseThrow(() -> new IllegalStateException("No grades."));
    }

    public List<Student> findStudentByName(String name) {
        return studentService.loadStudent(name);
    }

    public List<Student> listAllStudents() {
        return studentService.listAllStudents();
    }
}
