package schoolrecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudyResultByNameTest {

    StudyResultByName studyResultByName;

    @BeforeEach
    void init() {
        studyResultByName = new StudyResultByName("Pintér Elemér", 2.45);

    }
    @Test
    void testCreateStudyResultByName() {
        assertEquals("Pintér Elemér", studyResultByName.getStudentName());
        assertEquals(2.45, studyResultByName.getStudyAverage());
    }

    @Test
    void testToString() {
        assertEquals("Pintér Elemér tanulmányi átlaga 2.45", studyResultByName.toString());
    }
}