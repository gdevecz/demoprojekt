package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeachersDaoTest {

    TeachersDao teachersDao;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        teachersDao = new TeachersDao(dataSource);
    }

    @Test
    void testSaveSubject() {
        long id = teachersDao.saveTeacher("John Doe");
        Teacher actual = teachersDao.findTeacherById(id);

        assertEquals(1L, actual.getId());
        assertEquals("John Doe", actual.getTeacherName());
    }

    @Test
    void testListSubjects() {
        teachersDao.saveTeacher("John Doe");
        teachersDao.saveTeacher("Jane Doe");
        teachersDao.saveTeacher("Jack Doe");
        List<String> teacherNames = teachersDao.listTeachers().stream().map(Subject::getSubjectName).toList();

        assertEquals(Arrays.asList("Jack Doe", "Jane Doe", "John Doe"), teacherNames);
    }
}