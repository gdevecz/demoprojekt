package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassRecords {

    private String className;

    private ClassRecordsService classrecordsService;

    private Random random;

    public ClassRecords(String className, ClassRecordsService classrecordsService, Random random) {
        this.className = className;
        this.classrecordsService = classrecordsService;
        this.random = random;
    }


    public void addStudent(Student student) {
        classrecordsService.addStudent(student);
    }

    public void removeStudent(Student student) {
        classrecordsService.removeStudent(student);
    }

    public double calculateClassAverage() {
        return classrecordsService.calculateClassAverage();
    }

    public double calculateClassAverageBySubject(Subject subject) {
        return classrecordsService.calculateClassAverageBySubject(subject);
    }

    public List<Student> findStudentByName(String name) {
        return classrecordsService.findStudentByName(name);
    }

    public Student repetition() {
        List<Student> students = classrecordsService.listAllStudents();
        return students.get(random.nextInt(students.size()));
    }

    public List<StudyResultByName> listStudyResults() {
        List<Student> students = classrecordsService.listAllStudents();
        List<StudyResultByName> studyResultByNames = new ArrayList<>();
        students.forEach(student -> studyResultByNames.add(new StudyResultByName(
                student.getStudentName(), student.calculateAverage()
        )));
        return studyResultByNames;
    }

}

