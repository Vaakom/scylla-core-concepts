package ore.scyllacore.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import ore.scyllacore.domain.MutantData;
import ore.scyllacore.mapper.MutantDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MutantDataQueryBuilderRepository implements MutantDataRepository {

    private CqlSession session;

    private Map<String, PreparedStatement> statementMap = new HashMap<>();

    @Autowired
    public MutantDataQueryBuilderRepository(CqlSession session) {
        this.session = session;

        PreparedStatement selectOne = session.prepare(QueryBuilder.selectFrom("mutant_data").all()
                .whereColumn("first_name").isEqualTo(QueryBuilder.bindMarker())
                .whereColumn("last_name").isEqualTo(QueryBuilder.bindMarker())
                .build());

        statementMap.put("selectOne", selectOne);
    }

    @Override
    public void insert(MutantData mutantData) {

    }

    @Override
    public void insert(MutantData mutantData, int ttl) {

    }

    @Override
    public MutantData selectOne(String firstName, String lastName) {
        Row row = session.execute(statementMap.get("selectOne").bind()
                .setString(0, firstName)
                .setString(1, lastName)).one();

        return MutantDataMapper.rowToData(row);
    }

    @Override
    public void delete(String firstName, String lastName) {

    }
}
