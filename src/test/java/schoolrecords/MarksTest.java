package schoolrecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MarksTest {

    Mark mathMark;
    Mark physicsMark;
    Subject math;
    Subject physics;

    @BeforeEach
    void init() {
        math = new Subject(1L, "matematika");
        physics = new Subject(2L, "fizika");
        Tutor tutor = new Tutor(1L, "Kovács István", Arrays.asList(math, physics));
        mathMark = new Mark(MarkType.D, math, tutor);
        physicsMark = new Mark(MarkType.B, physics);

    }

    @Test
    void testCreateMarkWithTutor() {
        assertEquals(MarkType.D, mathMark.getMarkType());
        assertEquals("matematika", mathMark.getSubject().getName());
        assertEquals("Kovács István", mathMark.getTutor().getName());
    }

    @Test
    void testCreateMarkWithoutTutor() {
        assertEquals(MarkType.B, physicsMark.getMarkType());
        assertEquals("fizika", physicsMark.getSubject().getName());
        assertNull(physicsMark.getTutor());
    }

    @Test
    void testSetTutor() {
        Mark marks = new Mark(MarkType.C, physics);
        Tutor tutor = new Tutor(1L, "Kovács István", Arrays.asList(math, physics));

        marks.setTutor(tutor);
        assertEquals("Kovács István", marks.getTutor().getName());
    }

    @Test
    void testToString() {
        assertEquals("matematika: elégséges(2)", mathMark.toString());
    }
}