package schoolrecords;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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