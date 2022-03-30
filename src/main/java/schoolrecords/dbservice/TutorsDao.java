package schoolrecords.dbservice;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class TutorsDao {
    private JdbcTemplate jdbcTemplate;

    public TutorsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<TutorRecord> listAllTutorRecords() {
        List<TutorRecord> result = jdbcTemplate.query(//language=sql
                "select * from tutors order by tutor_name",
                (rs, rowNum) -> new TutorRecord(
                        rs.getLong("id"),
                        rs.getString("tutor_name")));
        if (result.isEmpty()) {
            throw new IllegalStateException("No tutor exist.");
        }
        return result;
    }

    public TutorRecord findTutorRecordByName(String name) {
        List<TutorRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from tutors where tutor_name = ?",
                (rs, rowNum) -> new TutorRecord(
                        rs.getLong("id"),
                        rs.getString("tutor_name")),
                name);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Cannot find tutor by name: " + name);
        }
        return result.get(0);
    }

    public TutorRecord findTutorRecordById(long id) {
        List<TutorRecord> result = jdbcTemplate.query(
                //language=sql
                "select * from tutors where id = ?",
                (rs, rowNum) -> new TutorRecord(
                        rs.getLong("id"),
                        rs.getString("tutor_name")),
                id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Cannot find tutor by id: " + id);
        }
        return result.get(0);
    }

    public long saveTutor(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    //language=sql
                    "insert into tutors(tutor_name) values(?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
