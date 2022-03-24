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

public class TeachersDao {

    private JdbcTemplate jdbcTemplate;

    public TeachersDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long saveTeacher(String teacherName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                                    PreparedStatement ps = con.prepareStatement(
                                            //language=sql
                                            "insert into teachers(teacher_name) values(?)",
                                            Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, teacherName);
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    //Todo
    //Ha nincs találat
    public Teacher findTeacherById(long id) {
        return jdbcTemplate.query(
                //language=sql
                "select * from teachers where id = ?",
                (rs, rowNum) -> new Teacher(
                        rs.getLong("id"),
                        rs.getString("teacher_name")
                ),
                id).get(0);
    }

    public List<Subject> listTeachers() {
        return jdbcTemplate.query(
                //language=sql
                "select * from teachers order by teacher_name",
                (rs, rowNum) -> new Subject(
                        rs.getLong("id"),
                        rs.getString("teacher_name")
                )
        );
    }
}
