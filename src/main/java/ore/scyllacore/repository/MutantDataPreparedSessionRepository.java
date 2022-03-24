package ore.scyllacore.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import ore.scyllacore.domain.MutantData;
import ore.scyllacore.mapper.MutantDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MutantDataPreparedSessionRepository implements MutantDataRepository {

    private CqlSession session;
    private Map<String, PreparedStatement> preparedMap;

    @Autowired
    public MutantDataPreparedSessionRepository(CqlSession session) {
        this.session = session;

        preparedMap = new HashMap<>();
        preparedMap.put("insert", session.prepare("INSERT INTO mutant_data (first_name, last_name, address, picture_location) VALUES (?, ?, ?, ?)"));
        preparedMap.put("select", session.prepare("select first_name,last_name,address,picture_location " +
                "from mutant_data " +
                "where first_name= ? AND last_name= ?"));
    }

    @Override
    public void insert(MutantData mutantData) {
        session.execute(preparedMap.get("insert").bind(
                mutantData.getFirstName(),
                mutantData.getLastName(),
                mutantData.getAddress(),
                mutantData.getPictureLocation())
        );
    }

    @Override
    public void insert(MutantData mutantData, int ttl) {

    }

    @Override
    public MutantData selectOne(String firstName, String lastName) {
        Row row = session.execute(preparedMap.get("select").bind(firstName, lastName)).one();

        return MutantDataMapper.rowToData(row);
    }

    @Override
    public void delete(String firstName, String lastName) {

    }
}
