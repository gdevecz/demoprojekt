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

class MarksDaoTest {

    MarksDao marksDao;
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
        marksDao = new MarksDao(dataSource);

        SchoolRecordsService srs = new SchoolRecordsService(studentsDao, marksDao, subjectsDao, tutorsDao);

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
    void saveMarkAndListAllMarkRecordsTest() {
        marksDao.saveMark(2, 4, "B");

        List<MarkRecord> result = marksDao.listAllMarkRecords();

        assertEquals(Arrays.asList("A", "C", "D", "A", "C", "D", "A", "C", "D", "B"), result.stream().map(MarkRecord::getMarkType).toList());
    }

    @Test
    void findMarkRecordsByStudentIdTest() {
        List<MarkRecord> result = marksDao.findMarkRecordsByStudentId(1);

        assertEquals(Arrays.asList("A","C","D"), result.stream().map(MarkRecord::getMarkType).toList());
    }

    @Test
    void listAllMarkRecordsTest() {
        List<MarkRecord> result = marksDao.listAllMarkRecords();

        assertEquals(Arrays.asList("A", "C", "D", "A", "C", "D", "A","C", "D"), result.stream().map(MarkRecord::getMarkType).toList());
    }

    @Test
    void listAllMarkRecordsTestWithNoMarks() {
            flyway.clean();
            flyway.migrate();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> marksDao.listAllMarkRecords());

        assertEquals("No grade in class.", exception.getMessage());
    }
}