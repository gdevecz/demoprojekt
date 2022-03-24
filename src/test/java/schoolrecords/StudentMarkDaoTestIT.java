package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentMarkDaoTestIT {

    StudentMarkDao studentMarkDao;
    SubjectsDao subjectsDao;
    StudentDao studentDao;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        subjectsDao = new SubjectsDao(dataSource);
        studentMarkDao = new StudentMarkDao(dataSource, subjectsDao);
        studentDao = new StudentDao(dataSource);
    }

    @Test
    void testSaveStudentsMarks() {
        long studentId = studentDao.saveStudent("John Doe");
        long subjectId = subjectsDao.saveSubject("Matematika");
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.B.toString());

        assertEquals(MarkType.B, MarkType.valueOf(studentMarkDao.listAllMarks().get(0).getMark()));
    }

    @Test
    void testGetStudentsMarks(){
        long studentId = studentDao.saveStudent("John Doe");
        long anotherStudentId=studentDao.saveStudent("Jane Doe");
        long subjectId = subjectsDao.saveSubject("Matematika");
        long anotherSubjectId = subjectsDao.saveSubject("Fizika");
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.B.toString());
        studentMarkDao.saveStudentMark(anotherStudentId, subjectId, MarkType.C.toString());
        studentMarkDao.saveStudentMark(studentId, anotherSubjectId, MarkType.A.toString());
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.D.toString());

        List<StudentMark> marks = studentMarkDao.listAllMarks();

        assertEquals(Arrays.asList(MarkType.B,MarkType.C,MarkType.A,MarkType.D), marks.stream().map(s->MarkType.valueOf(s.getMark())).toList());
    }

    @Test
    void testFindMarksByStudentId(){
        long studentId = studentDao.saveStudent("John Doe");
        long anotherStudentId=studentDao.saveStudent("Jane Doe");
        long subjectId = subjectsDao.saveSubject("Matematika");
        long anotherSubjectId = subjectsDao.saveSubject("Fizika");
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.B.toString());
        studentMarkDao.saveStudentMark(anotherStudentId, subjectId, MarkType.C.toString());
        studentMarkDao.saveStudentMark(studentId, anotherSubjectId, MarkType.A.toString());
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.D.toString());

        List<Mark> marks = studentMarkDao.findMarksByStudentId(studentId);

        assertEquals(Arrays.asList(MarkType.B,MarkType.A,MarkType.D), marks.stream().map(Mark::getMarkType).toList());
    }


    @Test
    void testFindMarksBySubjectId(){
        long studentId = studentDao.saveStudent("John Doe");
        long anotherStudentId=studentDao.saveStudent("Jane Doe");
        long subjectId = subjectsDao.saveSubject("Matematika");
        long anotherSubjectId = subjectsDao.saveSubject("Fizika");
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.B.toString());
        studentMarkDao.saveStudentMark(anotherStudentId, subjectId, MarkType.C.toString());
        studentMarkDao.saveStudentMark(studentId, anotherSubjectId, MarkType.A.toString());
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.D.toString());

        List<Mark> marks = studentMarkDao.findMarksBySubjectId(studentId);

        assertEquals(Arrays.asList(MarkType.B,MarkType.C,MarkType.D), marks.stream().map(Mark::getMarkType).toList());
    }


    @Test
    void testFindMarksByStudentIdAndSubjectId(){
        long studentId = studentDao.saveStudent("John Doe");
        long anotherStudentId=studentDao.saveStudent("Jane Doe");
        long subjectId = subjectsDao.saveSubject("Matematika");
        long anotherSubjectId = subjectsDao.saveSubject("Fizika");
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.B.toString());
        studentMarkDao.saveStudentMark(anotherStudentId, subjectId, MarkType.C.toString());
        studentMarkDao.saveStudentMark(studentId, anotherSubjectId, MarkType.A.toString());
        studentMarkDao.saveStudentMark(studentId, subjectId, MarkType.D.toString());

        List<Mark> marks = studentMarkDao.findMarksByStudentIdAndSubjectId(studentId, subjectId);

        assertEquals(Arrays.asList(MarkType.B,MarkType.D), marks.stream().map(Mark::getMarkType).toList());
    }
}