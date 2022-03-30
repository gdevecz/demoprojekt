package schoolrecords;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolrecords.dbservices.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class ClassRecordsTest {

    private ClassRecords classRecords;
    private SchoolRecordsService srs;
    private Tutor tutor;
    private Flyway flyway;


    @BeforeEach
    public void setUp() {
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

        classRecords = new ClassRecords("Fourth Grade A", new Random(5), srs);
        tutor = new Tutor("Nagy Csilla",
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
    public void testCreate() {
        assertEquals("Fourth Grade A", classRecords.getClassName());
    }

    @Test
    public void testAddStudentAlreadyExists() {
        assertFalse(classRecords.addStudent(new Student("Nagy Béla")));
    }

    @Test
    public void testAddStudent() {
        assertTrue(classRecords.addStudent(new Student("Nagy Klára")));
    }

    @Test
    public void testRemoveStudent() {
        assertTrue(classRecords.removeStudent(new Student("Nagy Béla")));
    }

    @Test
    public void testRemoveStudentDoesNotExists() {
        assertFalse(classRecords.removeStudent(new Student("Nagy Klára")));
    }

    @Test
    public void emptyStudentListShouldThrowException() throws ArithmeticException {
        flyway.clean();
        flyway.migrate();


        Exception ex = assertThrows(IllegalStateException.class, () -> new ClassRecords("First Grade", new Random(),srs).calculateClassAverage());
        assertEquals("No student in the class!", ex.getMessage());

    }

    @Test
    public void noMarksShouldThrowException() throws ArithmeticException {
        flyway.clean();
        flyway.migrate();

        ClassRecords classRecords = new ClassRecords("First Grade", new Random(),srs);
        classRecords.addStudent(new Student("Nagy Béla"));


        Exception ex = assertThrows(ArithmeticException.class, classRecords::calculateClassAverage);
        assertEquals("No marks present, average calculation aborted!", ex.getMessage());
    }

    @Test
    public void testCalculateClassAverage() {
        assertEquals(3.33, classRecords.calculateClassAverage());
    }

    @Test
    public void testCalculateClassAverageBySubject() {
        //Given
        Subject geography = new Subject("földrajz");
        //Then
        assertEquals(2.75, classRecords.calculateClassAverageBySubject(geography));
    }

    @Test
    public void emptyStudentNameShouldThrowException() throws IllegalArgumentException {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> classRecords.findStudentByName(""));
        assertEquals("Student name must not be empty!", ex.getMessage());
    }

    @Test
    public void emptyListShouldThrowException() throws IllegalStateException {
        flyway.clean();
        flyway.migrate();

        Exception ex = assertThrows(IllegalStateException.class, () -> new ClassRecords("First Grade", new Random(),srs).findStudentByName("Kovács Rita"));
        assertEquals("No student in the class!", ex.getMessage());
    }

    @Test
    public void nonExistingStudentShouldThrowException() throws IllegalArgumentException {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> classRecords.findStudentByName("Kiss Rita"));
        assertEquals("Student by this name cannot be found! Kiss Rita", ex.getMessage());
    }

    @Test
    public void testFindStudentByName() {
        assertEquals("Kovács Rita", classRecords.findStudentByName("Kovács Rita").getName());
    }

    @Test
    public void emptyListException() throws IllegalStateException {
        flyway.clean();
        flyway.migrate();

        Exception ex = assertThrows(IllegalStateException.class, () -> new ClassRecords("Fourth Grade", new Random(),srs).repetition());
        assertEquals("No student in the class!", ex.getMessage());
    }

    @Test
    public void testRepetition() {
        assertEquals("Varga Márton", classRecords.repetition().getName());
    }

    @Test
    public void testListStudyResults() {
        //Given
        List<StudyResultByName> list = classRecords.listStudyResults();
        //Then
        assertEquals("Kovács Rita", list.get(0).getStudentName());
        assertEquals(3.33, list.get(0).getStudyAverage());
        assertEquals(3, list.size());
    }

    @Test
    public void testListStudentNames() {
        assertEquals("Kovács Rita, Nagy Béla, Varga Márton", classRecords.listStudentNames());
    }
}
