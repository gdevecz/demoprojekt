package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaughtSubjectsDaoIT {

    TaughtSubjectsDao taughtSubjectsDao;
    TeachersDao teachersDao;
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

        taughtSubjectsDao = new TaughtSubjectsDao(dataSource);
        teachersDao = new TeachersDao(dataSource);
        subjectsDao = new SubjectsDao(dataSource);
    }


    @Test
    void testSaveTaughtSubject() {
        long teacher_id = teachersDao.saveTeacher("Joh Doe");
        Teacher teacher = teachersDao.findTeacherById(teacher_id);
        long subject_id = subjectsDao.saveSubject("Matematika");
        Subject subject = subjectsDao.findSubjectById(subject_id);

        assertEquals(1L, taughtSubjectsDao.listsAllTaughtSubjects().get(0).getTeacherId());
        assertEquals(1L, taughtSubjectsDao.listsAllTaughtSubjects().get(0).getSubjectId());
    }
}