package schoolrecords;

import java.util.List;

public class StudentService {

    StudentDao studentDao;
    StudentMarkDao studentMarkDao;

    public StudentService(StudentDao studentDao, StudentMarkDao studentMarkDao) {
        this.studentDao = studentDao;
        this.studentMarkDao = studentMarkDao;
    }

    public long saveStudent(Student student) {
        long id = studentDao.saveStudent(student.getStudentName());
        for (Mark mark : student.getMarks()) {
            studentMarkDao.saveStudentMark(student.getId(), mark.getSubject().getId(), mark.toString());
        }
        return id;
    }

    public void removeStudent(Student student) {
        studentDao.removeStudent(student.getId());
    }

    public List<Student> loadStudent(String name) {
        List<Student> students = studentDao.findStudentByName(name);
        loadStudentsMarks(students);
        return students;
    }

    public List<Student> listAllStudents() {
        List<Student> students = studentDao.loadAllStudents();
        loadStudentsMarks(students);
        return students;
    }

    private void loadStudentsMarks(List<Student> students) {
        for (Student student : students) {
            List<Mark> marks = studentMarkDao.findMarksByStudentId(student.getId());
            marks.forEach(m -> student.grading(m));
        }
    }

    public void grading(Student student, Mark mark) {
        studentMarkDao.saveStudentMark(student.getId(), mark.getSubject().getId(), mark.toString());
    }

    public double calculateAverage(Student student) {
        List<Mark> marks = studentMarkDao.findMarksByStudentId(student.getId());
        return getMarksAverage(marks);
    }

    public double calculateSubjectAverage(Student student, Subject subject) {
        List<Mark> marks = studentMarkDao.findMarksByStudentIdAndSubjectId(student.getId(), student.getId());
        return getMarksAverage(marks);
    }

    private double getMarksAverage(List<Mark> marks) {
        return marks.stream()
                .mapToInt(m -> m.getMarkType().getGrade())
                .average()
                .orElseThrow(() -> new IllegalStateException("No grades."));
    }
}
