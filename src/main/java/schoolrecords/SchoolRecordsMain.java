package schoolrecords;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import schoolrecords.consoUi.SchoolRecordsUI;
import schoolrecords.dbservices.*;

public class SchoolRecordsMain {

    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/school?useUnicode=true");
        dataSource.setUser("operator");
        dataSource.setPassword("operator");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

//        if (args.length > 0 && "test".equals(args[1])) {
//            Flyway f = Flyway.configure().locations("/src/resources/data").dataSource(dataSource).load();
//            f.migrate();
//        }
        SubjectsDao subjectsDao = new SubjectsDao(dataSource);
        TutorsDao tutorsDao = new TutorsDao(dataSource);
        StudentsDao studentsDao = new StudentsDao(dataSource);
        MarksDao marksDao = new MarksDao(dataSource);


        SchoolRecordsController src = new SchoolRecordsController(
                new SchoolRecordsUI(), new SchoolRecordsService(
                studentsDao, marksDao, subjectsDao, tutorsDao)
        );

        src.menu("Tesztosztály");
    }
}
