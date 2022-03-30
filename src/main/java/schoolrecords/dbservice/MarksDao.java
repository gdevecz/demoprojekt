package schoolrecords.dbservice;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class MarksDao {

    private JdbcTemplate jdbcTemplate;

    public MarksDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<MarkRecord> findMarkRecordsByStudentId(long studentId) {
        return jdbcTemplate.query(
                //language=sql
                "select * from marks where student_id = ? order by id",
                (rs, rowNum) -> new MarkRecord(
                        studentId,
                        rs.getLong("subject_id"),
                        rs.getString("mark_type")),
                studentId);
    }

    public List<MarkRecord> listAllMarkRecords() {
        List<MarkRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from marks order by id",
                (rs, rowNum) -> new MarkRecord(
                        rs.getLong("student_id"),
                        rs.getLong("subject_id"),
                        rs.getString("mark_type")));
        if (result.isEmpty()) {
            throw new IllegalArgumentException("No grade in class.");
        }
        return result;
    }

    public void saveMark(long studentId, long subjectId, String markType){
        jdbcTemplate.update(
                //language=sql
                "insert into marks(student_id, subject_id, mark_type) values(?, ?, ?)",
                studentId, subjectId, markType);
    }
}
