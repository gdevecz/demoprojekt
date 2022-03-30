package schoolrecords.dbservice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class SubjectsDao {


    private JdbcTemplate jdbcTemplate;

    public SubjectsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<SubjectRecord> listAllSubjectRecords() {
        List<SubjectRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from subjects",
                (rs, rowNum) -> new SubjectRecord(
                        rs.getLong("id"),
                        rs.getString("subject_name"),
                        rs.getLong("tutor_id")));
        if (result.isEmpty()) {
            throw new IllegalStateException("No student in the class, average calculation aborted!");
        }
        return result;
    }

    public List<SubjectRecord> listSubjectRecordsByTutorId(long tutorId) {
        List<SubjectRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from subjects where tutor_id = ?",
                (rs, rowNum) -> new SubjectRecord(
                        rs.getLong("id"),
                        rs.getString("subject_name"),
                        rs.getLong("tutor_id")),
                tutorId);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("No subject exists.");
        }
        return result;
    }

    public SubjectRecord findSubjectRecordById(long id) {
        List<SubjectRecord> result = jdbcTemplate.query(//language=sql
                "select * from subjects where id = ?",
                (rs, rowNum) -> new SubjectRecord(
                        id,
                        rs.getString("subject_name"),
                        rs.getLong("tutor_id")),
                id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Cannot find subject by id: " + id);
        }
        return result.get(0);
    }

    public SubjectRecord findSubjectRecordByName(String subjectName) {
        List<SubjectRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from subjects where subject_name = ?",
                (rs, rowNum) -> new SubjectRecord(
                        rs.getLong("id"),
                        rs.getString("subject_name"),
                        rs.getLong("tutor_id")),
                subjectName);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Cannot find subject by name: " + subjectName);
        }
        return result.get(0);
    }

    public long saveSubject(String subjectName, long tutorId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    //language=sql
                    "insert into subjects(subject_name, tutor_id) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subjectName);
            ps.setLong(2, tutorId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
