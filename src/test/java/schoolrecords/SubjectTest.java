package schoolrecords;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {


    @Test
    void testCreateSubject() {
        Subject subject = new Subject(1l, "matematika");

        assertEquals(1L, subject.getId());
        assertEquals("matematika", subject.getName());
    }

    @Test
    void testToString() {
        Subject subject = new Subject(2L, "fizika");

        assertEquals("fizika", subject.toString());
    }
}