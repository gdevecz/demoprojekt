package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SubjectDaoTest {

    SubjectDao subjectDao;

    @BeforeEach
    void init() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/school?useUnicode=true");
        mysqlDataSource.setUser("operator");
        mysqlDataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(mysqlDataSource).load();
        flyway.clean();
        flyway.migrate();

        subjectDao = new SubjectDao(mysqlDataSource);
    }

    @Test
    void testSaveSubject() {
        subjectDao.saveSubject("Matematika");
        Subject result = subjectDao.listSubjects().get(0);

        assertEquals(1, result.getId());
        assertEquals("Matematika", result.getSubjectName());
    }

    @Test
    void testFindById() {
        subjectDao.saveSubject("Matematika");
        subjectDao.saveSubject("Földrajz");
        subjectDao.saveSubject("Irodalom");
        Subject result = subjectDao.findById(2L);

        assertEquals(2L, result.getId());
        assertEquals("Földrajz", result.getSubjectName());
    }

    @Test
    void testFindByIdError() {
        subjectDao.saveSubject("Matematika");
        subjectDao.saveSubject("Földrajz");
        subjectDao.saveSubject("Irodalom");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> subjectDao.findById(6L));

        assertEquals("Cannot find subject.", exception.getMessage());
    }

    @Test
    void testFindByName() {
        subjectDao.saveSubject("Matematika");
        subjectDao.saveSubject("Földrajz");
        subjectDao.saveSubject("Irodalom");
        Subject result = subjectDao.findByName("Földrajz");

        assertEquals(2L, result.getId());
        assertEquals("Földrajz", result.getSubjectName());
    }

    @Test
    void testFindByNameError() {
        subjectDao.saveSubject("Matematika");
        subjectDao.saveSubject("Földrajz");
        subjectDao.saveSubject("Irodalom");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> subjectDao.findByName("Biológia"));

        assertEquals("Cannot find subject.", exception.getMessage());
    }
}