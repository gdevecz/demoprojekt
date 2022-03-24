package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {

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

        studentDao = new StudentDao(dataSource);
    }
    @Test
    void testSaveStudent() {
        long id = studentDao.saveStudent("John Doe");
        Student actual = studentDao.findStudentById(id);

        assertEquals(1L, actual.getId());
        assertEquals("John Doe", actual.getStudentName());
    }

    @Test
    void testListSubjects() {
        studentDao.saveStudent("John Doe");
        studentDao.saveStudent("Jane Doe");
        studentDao.saveStudent("Jack Doe");
        List<String> studentNames = studentDao.loadAllStudents().stream().map(Student::getStudentName).toList();

        assertEquals(Arrays.asList("Jack Doe", "Jane Doe", "John Doe"), studentNames);
    }

    @Test
    void testDelete() {
        Long id = studentDao.saveStudent("John Doe");
        studentDao.removeStudent(id);
    }
}