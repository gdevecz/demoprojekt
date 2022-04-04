package schoolrecords.repositories;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolrecords.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentsDaoTest {

    StudentsDao studentsDao;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        studentsDao = new StudentsDao(dataSource);
    }

    @Test
    void testSaveNewStudentAndLoadStudentsWithoutMark() {
        long id = studentsDao.saveNewStudent("John Doe");

        List<Student> result = studentsDao.loadStudentsWithoutMarks();

        assertEquals(true, result.stream().map(Student::getName).toList().contains("John Doe"));
    }
}