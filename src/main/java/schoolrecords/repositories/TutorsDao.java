package schoolrecords.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import schoolrecords.Tutor;

import javax.sql.DataSource;
import java.util.List;

public class TutorsDao {

    private JdbcTemplate jdbcTemplate;

    public TutorsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Tutor> loadTutorsWithoutSubjects() {
        return jdbcTemplate.query(
                //language=sql
                "select * from tutors order by tutor_name",
                (rs, rowNum) -> new Tutor(
                        rs.getLong("id"),
                        rs.getString("tutor_name")));
    }
}

