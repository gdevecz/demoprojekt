package schoolrecords;

import schoolrecords.dbservices.SchoolRecordsService;

import java.util.List;
import java.util.Random;

public class ClassRecords {

    private String className;

    private Random random;

    private SchoolRecordsService srs;

    public ClassRecords(String className, Random random, SchoolRecordsService srs) {
        this.className = className;
        this.random = random;
        this.srs = srs;
    }

    public boolean addStudent(Student student) {
        return srs.saveStudent(student);
    }

    public boolean removeStudent(Student student) {
        return srs.deleteStudent(student);
    }

    public double calculateClassAverage() {
        return srs.calculateClassAverage();
    }

    public double calculateClassAverageBySubject(Subject subject) {
        return srs.calculateClassAverageBySubjectName(subject.getSubjectName());
    }

    public Student findStudentByName(String studentName) {
        Validator.isStudentNameValid(studentName);
        Validator.isStudentsExist(srs.listAllStudents());
        return srs.findStudentByName(studentName);
    }

    public Student repetition() {
        List<String> students = srs.listAllStudentNames();
        return srs.findStudentByName(students.get(random.nextInt(students.size())));
    }

    public List<StudyResultByName> listStudyResults() {
        return srs.listStudyResults();
    }

    public StudyResultByName studentStudyResult(String name) {
        return srs.findStudyResultByName(name);
    }

    public String listStudentNames() {
        return String.join(", ", srs.listAllStudentNames());
    }

    public String getClassName() {
        return className;
    }

    public Random getRandom() {
        return random;
    }

    public SchoolRecordsService getSrs() {
        return srs;
    }
}


