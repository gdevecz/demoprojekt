package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    @Test
    void testCreateSubjectWithName() {
        Subject result = new Subject("Földrajz");

        assertNull(result.getId());
        assertEquals("Földrajz", result.getSubjectName());
    }

    @Test
    void testCreateSubjectWithIdAndName() {
        Subject result = new Subject(1L,"Földrajz");

        assertEquals(1L, result.getId());
        assertEquals("Földrajz", result.getSubjectName());

    }

}