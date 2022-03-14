package ore.scyllacore.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import lombok.AllArgsConstructor;
import ore.scyllacore.domain.MutantData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MutantDataSessionRepository implements MutantDataRepository {

    private final CqlSession session;

    @Override
    public void insert(MutantData mutantData) {
        session.execute("INSERT INTO mutant_data (first_name,last_name,address,picture_location) " +
                "VALUES ('Rob','Roy','1995 Courage St', 'https://www.kinopoisk.ru/film/3592/')");
    }

    @Override
    public MutantData selectOne(String firstName, String lastName) {
        Row row = session.execute("select first_name,last_name,address,picture_location from mutant_data " +
                "where first_name= ? AND last_name= ?", firstName, lastName).one();

        return rowToData(row);
    }

    @Override
    public void delete(String firstName, String lastName) {
        session.execute("delete from mutant_data where first_name= ? AND last_name= ?", firstName, lastName);
    }

    private MutantData rowToData(Row row) {
        return row == null ? null : MutantData.builder()
                .firstName(row.getString("first_name"))
                .lastName(row.getString("last_name"))
                .address(row.getString("address"))
                .pictureLocation(row.getString("picture_location"))
                .build();
    }
}
