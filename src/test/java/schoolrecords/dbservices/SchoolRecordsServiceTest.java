package schoolrecords.dbservices;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolrecords.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SchoolRecordsServiceTest {

    SchoolRecordsService srs;
    Flyway flyway;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        SubjectsDao subjectsDao = new SubjectsDao(dataSource);
        TutorsDao tutorsDao = new TutorsDao(dataSource);
        StudentsDao studentsDao = new StudentsDao(dataSource);
        MarksDao marksDao = new MarksDao(dataSource);

        srs = new SchoolRecordsService(studentsDao, marksDao, subjectsDao, tutorsDao);

        ClassRecords classRecords = new ClassRecords("Fourth Grade A", new Random(5), srs);
        Tutor tutor = new Tutor("Nagy Csilla",
                Arrays.asList(new Subject("földrajz"),
                        new Subject("matematika"),
                        new Subject("biológia"),
                        new Subject("zene"),
                        new Subject("fizika"),
                        new Subject("kémia")));
        long tutorId = tutorsDao.saveTutor(tutor.getTutorName());
        tutor.getTaughtSubjects().forEach(subject -> subjectsDao.saveSubject(subject.getSubjectName(), tutorId));
        Student firstStudent = new Student("Kovács Rita");
        Student secondStudent = new Student("Nagy Béla");
        Student thirdStudent = new Student("Varga Márton");
        firstStudent.grading(new Mark(MarkType.A, new Subject("földrajz"), tutor));
        firstStudent.grading(new Mark(MarkType.C, new Subject("matematika"), tutor));
        firstStudent.grading(new Mark(MarkType.D, new Subject("földrajz"), tutor));
        secondStudent.grading(new Mark(MarkType.A, new Subject("biológia"), tutor));
        secondStudent.grading(new Mark(MarkType.C, new Subject("matematika"), tutor));
        secondStudent.grading(new Mark(MarkType.D, new Subject("zene"), tutor));
        thirdStudent.grading(new Mark(MarkType.A, new Subject("fizika"), tutor));
        thirdStudent.grading(new Mark(MarkType.C, new Subject("kémia"), tutor));
        thirdStudent.grading(new Mark(MarkType.D, new Subject("földrajz"), tutor));
        classRecords.addStudent(firstStudent);
        classRecords.addStudent(secondStudent);
        classRecords.addStudent(thirdStudent);
    }

    @Test
    void saveStudentTestAndfindStudentByNameTest() {
        srs.saveStudent(new Student("Kovács Géza"));

        Student student = srs.findStudentByName("Kovács Géza");

        assertEquals(new Student("Kovács Géza"), student);
    }

    @Test
    void deleteStudentTest() {
        srs.deleteStudent(new Student("Nagy Béla"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> srs.findStudentByName("Nagy Béla"));

        assertEquals("Student by this name cannot be found! Nagy Béla", exception.getMessage());
    }

    @Test
    void listAllStudentNamesTest() {

        assertEquals(Arrays.asList("Kovács Rita", "Nagy Béla", "Varga Márton"), srs.listAllStudentNames());
    }

    @Test
    void findStudentByNameTest() {
        Tutor tutor = new Tutor("Nagy Csilla",
                Arrays.asList(new Subject("földrajz"),
                        new Subject("matematika"),
                        new Subject("biológia"),
                        new Subject("zene"),
                        new Subject("fizika"),
                        new Subject("kémia")));
        srs.saveStudent(new Student("Horváth Elemér",
                Arrays.asList(new Mark(MarkType.D, new Subject("Matematika"), tutor))));

        assertEquals("Horváth Elemér", srs.findStudentByName("Horváth Elemér").getName());
        assertEquals("matematika", srs.findStudentByName("Horváth Elemér").getMarks().get(0).getSubject().getSubjectName());
        assertEquals(MarkType.D, srs.findStudentByName("Horváth Elemér").getMarks().get(0).getMarkType());
    }

    @Test
    void calculateClassAverageTest() {

        assertEquals(3.33, srs.calculateClassAverage());
    }

    @Test
    void calculateClassAverageBySubjectTest() {

        assertEquals(3.0, srs.calculateClassAverageBySubjectName("matematika"));
        assertEquals(5.0, srs.calculateClassAverageBySubjectName("fizika"));
    }

    @Test
    void findTutorByNameTest() {
        List<Subject> subjects = Arrays.asList(new Subject("földrajz"),
                new Subject("matematika"),
                new Subject("biológia"),
                new Subject("zene"),
                new Subject("fizika"),
                new Subject("kémia"));

        assertEquals(subjects, srs.findTutorByName("Nagy Csilla").getTaughtSubjects());
    }

    @Test
    void listAllStudentsTest() {

        assertEquals(Arrays.asList("Kovács Rita", "Nagy Béla", "Varga Márton"),
                srs.listAllStudents().stream().map(Student::getName).toList());
    }

    @Test
    void gradingStudentTest() {
        srs.gradingStudent(MarkType.D, "Nagy Béla","matematika");

        assertEquals(4, srs.findStudentByName("Nagy Béla").getMarks().size());
        assertEquals("matematika", srs.findStudentByName("Nagy Béla").getMarks().get(3).getSubject().getSubjectName());
        assertEquals(MarkType.D, srs.findStudentByName("Nagy Béla").getMarks().get(3).getMarkType());
    }

    @Test
    void listStudyResultsTest() {
        List<StudyResultByName> results = srs.listStudyResults();

        assertEquals("Nagy Béla tanulmányi átlaga 3.33", results.get(1).toString());
    }

    @Test
    void findStudyResultByNameTest() {
        StudyResultByName result = srs.findStudyResultByName("Varga Márton");

        assertEquals("Varga Márton tanulmányi átlaga 3.33", result.toString());
    }

    @Test
    void studentAverageBySubjectTest() {
        double average = srs.studentAverageBySubject("Nagy Béla", "zene");

        assertEquals(2.0,average,0.01 );
    }
}