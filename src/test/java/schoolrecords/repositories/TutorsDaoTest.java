package schoolrecords.repositories;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schoolrecords.Tutor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TutorsDaoTest {

    TutorsDao tutorsDao;

    @BeforeEach
    void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        tutorsDao = new TutorsDao(dataSource);
    }

    @Test
    void testLoadTutors() {
        List<Tutor> expected = Arrays.asList(
                new Tutor(2L, "Kovács Áron"),
                new Tutor(4L, "Kovács Éva"),
                new Tutor(5L, "Kovács Ildikó"),
                new Tutor(1L, "Kovács István"),
                new Tutor(3L, "Kovács Krisztina")
        );

        List<Tutor> actual = tutorsDao.loadTutorsWithoutSubjects();

        assertEquals(expected.stream().map(Tutor::getName).toList(), actual.stream().map(Tutor::getName).toList());
    }

}