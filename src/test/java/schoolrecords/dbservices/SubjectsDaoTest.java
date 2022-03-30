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

class SubjectsDaoTest {

    SubjectsDao subjectsDao;
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

        subjectsDao = new SubjectsDao(dataSource);
        TutorsDao tutorsDao = new TutorsDao(dataSource);
        StudentsDao studentsDao = new StudentsDao(dataSource);
        MarksDao marksDao = new MarksDao(dataSource);

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
    void testListAllSubjectRecords() {
        List<SubjectRecord> subjectRecords = Arrays.asList(
                new SubjectRecord(1, "földrajz", 1),
                new SubjectRecord(2, "matematika", 1),
                new SubjectRecord(3, "biológia", 1),
                new SubjectRecord(4, "zene", 1),
                new SubjectRecord(5, "fizika", 1),
                new SubjectRecord(6, "kémia", 1)
        );

        assertEquals(subjectRecords, subjectsDao.listAllSubjectRecords());
    }

    @Test
    void TestListSubjectRecordsByTutorId() {
        List<SubjectRecord> subjectRecords = Arrays.asList(
                new SubjectRecord(1, "földrajz", 1),
                new SubjectRecord(2, "matematika", 1),
                new SubjectRecord(3, "biológia", 1),
                new SubjectRecord(4, "zene", 1),
                new SubjectRecord(5, "fizika", 1),
                new SubjectRecord(6, "kémia", 1)
        );

        assertEquals(subjectRecords, subjectsDao.listSubjectRecordsByTutorId(1));
    }

    @Test
    void testFindSubjectRecordById() {

        assertEquals("biológia", subjectsDao.findSubjectRecordById(3).getName());
    }

    @Test
    void testFindSubjectRecordByName() {

        assertEquals(new SubjectRecord(4, "zene", 1),subjectsDao.findSubjectRecordByName("zene"));
    }

    @Test
    void testSaveSubject() {
        subjectsDao.saveSubject("informatika", 1);

        assertEquals(new SubjectRecord(7,"informatika",1), subjectsDao.findSubjectRecordByName("informatika"));
    }
}