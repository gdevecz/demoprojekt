package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    StudentService studentService;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        SubjectsDao subjectsDao = new SubjectsDao(dataSource);
        StudentDao studentDao = new StudentDao(dataSource);
        StudentMarkDao studentMarkDao = new StudentMarkDao(dataSource, subjectsDao);
        studentService = new StudentService(studentDao, studentMarkDao);

        subjectsDao.saveSubject("Matematika");
    }

    @Test
    void testSaveStudent() {
        long id = studentService.saveStudent(new Student("John Doe"));

        assertEquals(Arrays.asList("John Doe"),
                studentService.loadStudent("John Doe").stream().map(Student::getStudentName).toList());
    }


    @Test
    void testGrading() {
        long id = studentService.saveStudent(new Student("John Doe"));
        Student student = studentService.loadStudent("John Doe").get(0);
        Subject subject = new Subject("Matematika");
        Mark mark = new Mark(MarkType.A, subject);
        studentService.grading(student, mark);
        List<Mark> marks = studentService.loadStudent("John Doe").get(0).getMarks();
        marks.forEach(System.out::println);
        System.out.println();
        assertEquals(MarkType.A, studentService.loadStudent("John Doe").get(0).getMarks().get(0));
    }

    @Test
    void listAllStudents() {
        long id = studentService.saveStudent(new Student("John Doe"));
        List<Student> students = studentService.listAllStudents();
        assertEquals(Arrays.asList("John Doe"), students.stream().map(Student::getStudentName).toList());

    }
}