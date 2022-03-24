package schoolrecords;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TaughtSubjectsDao {

    JdbcTemplate jdbcTemplate;

    public TaughtSubjectsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveTaughtSubject(Teacher teacher, Subject subject) {
        jdbcTemplate.update(
                //language=sql
                "insert into taught_subjects(teacher_id, subject_id) values(?,?)",
                teacher.getId(), subject.getId());
    }

    public List<TauhtSubject> listsAllTaughtSubjects() {
        return jdbcTemplate.query(
                //language=sql
                "select * from taught_subjects",
                (rs, rowNum) -> new TauhtSubject(
                        rs.getLong("id"),
                        rs.getLong("teacher_id"),
                        rs.getLong("subject_id")));
    }

    public List<Long> findSubjectsIdByTeacher(Teacher teacher) {
        return jdbcTemplate.query(
                //language=sql
                "select subject_id from taught_subjects where teacher_id = ?",
                (rs, rowNum) -> rs.getLong("subject_id"),
                teacher.getId());
    }

    public List<Long> findTeachersIdBySubject(Subject subject) {
        return jdbcTemplate.query(
                //language=sql
                "select teacher_id from taught_subjects where subject_id = ?",
                (rs, rowNum) -> rs.getLong("teacher_id"),
                subject.getId());
    }
}
