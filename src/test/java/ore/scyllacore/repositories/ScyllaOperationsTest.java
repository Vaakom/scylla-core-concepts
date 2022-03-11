package ore.scyllacore.repositories;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import ore.scyllacore.domain.MutantData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class ScyllaOperationsTest {

    private CqlSession session;
    private CassandraOperations template;

    @Autowired
    public ScyllaOperationsTest(CqlSession session) {
        this.session = session;
        this.template = new CassandraTemplate(session);
    }

    @Test
    public void sessionInsert() {
        session.execute("INSERT INTO mutant_data (first_name,last_name,address,picture_location) " +
                "VALUES ('Mike','Tyson','4321 Main St', 'http://www.facebook.com/mtyson')").one();
    }

    @Test
    public void sessionSelect() {
        sessionInsert();

        Row row = session.execute("select first_name,last_name,address,picture_location from mutant_data " +
                "where first_name='Mike' AND last_name='Tyson'").one();

        System.out.println(MutantDataMapper.rowToData(row));
    }

    @Test
    public void templateInsert() {
        MutantData mutantData = template.insert(new MutantData("Red", "Wings", "Moscow", "trtyakovskaya gallery 1"));
        System.out.println("Insert result " + mutantData);
    }

    @Test
    public void templateSelect() {
        List<MutantData> mutantDataList = template.select("select * from mutant_data", MutantData.class);
        System.out.println(mutantDataList);
    }

    class MutantDataMapper {
        public static MutantData rowToData(Row row) {
            return row == null ? null : MutantData.builder()
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .address(row.getString("address"))
                    .pictureLocation(row.getString("picture_location"))
                    .build();
        }
    }
}