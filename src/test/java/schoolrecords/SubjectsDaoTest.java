package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubjectsDaoTest {

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
    }

    @Test
    void testSaveSubject() {
        long id = subjectsDao.saveSubject("Matematika");
        Subject actual = subjectsDao.findSubjectById(id);

        assertEquals(1L, actual.getId());
        assertEquals("Matematika", actual.getSubjectName());
    }

    @Test
    void testListSubjects() {
        subjectsDao.saveSubject("Matematika");
        subjectsDao.saveSubject("Fizika");
        subjectsDao.saveSubject("Irodalom");
        List<String> subjectNames = subjectsDao.listSubjects().stream().map(Subject::getSubjectName).toList();

        assertEquals(Arrays.asList("Fizika", "Irodalom", "Matematika"), subjectNames);
    }
}