package schoolrecords;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SubjectsDao {

    private JdbcTemplate jdbcTemplate;

    public SubjectsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long saveSubject(String subjectName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                    PreparedStatement ps = con.prepareStatement(
                                            //language=sql
                                            "insert into subjects(subject_name) values(?)",
                                            Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, subjectName);
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    //Todo
    //Ha nincs találat
    public Subject findSubjectById(long id) {
        return jdbcTemplate.query(
                //language=sql
                "select * from subjects where id = ?",
                (rs, rowNum) -> new Subject(
                        rs.getLong("id"),
                        rs.getString("subject_name")
                ),
                id).get(0);
    }

    public List<Subject> listSubjects() {
        return jdbcTemplate.query(
                //language=sql
                "select * from subjects order by subject_name",
                (rs, rowNum) -> new Subject(
                        rs.getLong("id"),
                        rs.getString("subject_name")
                )
        );
    }
}
