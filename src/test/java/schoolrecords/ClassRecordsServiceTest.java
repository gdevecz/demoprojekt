package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassRecordsServiceTest {

    ClassRecordsService classrecordsService;
    StudentService studentService;
    SubjectsDao subjectsDao;

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
        StudentDao studentDao = new StudentDao(dataSource);
        StudentMarkDao studentMarkDao = new StudentMarkDao(dataSource, subjectsDao);
        studentService = new StudentService(studentDao, studentMarkDao);
        classrecordsService = new ClassRecordsService(studentService);

        subjectsDao.saveSubject("Matematika");
        subjectsDao.saveSubject("Fizika");
        subjectsDao.saveSubject("Irodalom");
    }

    @Test
    void testAddStudent() {
        classrecordsService.addStudent(new Student("John Doe"));

        assertEquals("John Doe", classrecordsService.listAllStudents().get(0).getStudentName());
    }

    @Test
    void testFindStudentByName() {
        classrecordsService.addStudent(new Student("Jack Doe"));
        classrecordsService.addStudent(new Student("John Doe"));
        classrecordsService.addStudent(new Student("Jack Doe"));
        List<Student> students = classrecordsService.findStudentByName("Jack Doe");
        List<Student> singleStudent = classrecordsService.findStudentByName("John Doe");

        assertEquals(2, students.size());
        assertEquals("Jack Doe", students.get(1).getStudentName());
        assertEquals("John Doe", singleStudent.get(0).getStudentName());
        assertEquals(2, singleStudent.get(0).getId());
    }

    @Test
    void testRemoveStudent() {
        classrecordsService.addStudent(new Student("John Doe"));
        classrecordsService.addStudent(new Student("Jack Doe"));
        classrecordsService.addStudent(new Student("Jane Doe"));
        Student student = classrecordsService.findStudentByName("Jack Doe").get(0);

       classrecordsService.removeStudent(student);

        assertEquals(Arrays.asList("Jane Doe", "John Doe"),
                classrecordsService.listAllStudents().stream().map(Student::getStudentName).toList());
    }

    @Test
    void testClassAverage() {
        long id = classrecordsService.addStudent(new Student("John Doe"));
        classrecordsService.addStudent(new Student("Jack Doe"));
        long anotherId = classrecordsService.addStudent(new Student("Jane Doe"));
        Student student = classrecordsService.findStudentByName("John Doe").get(0);
        Student anotherStudent = classrecordsService.findStudentByName("Jane Doe").get(0);
        Subject subject = subjectsDao.findSubjectById(1);
        Mark mark = new Mark(MarkType.A, subject);

        studentService.grading(student, mark);
        System.out.println(mark.getMarkType());
        double avg = classrecordsService.calculateClassAverage();
    }
}