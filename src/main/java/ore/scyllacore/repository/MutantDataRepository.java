package ore.scyllacore.repository;

import ore.scyllacore.domain.MutantData;

import java.util.List;

public interface MutantDataRepository {

    void insert(MutantData mutantData);

    void insert(MutantData mutantData, int ttl);

    MutantData selectOne(String firstName, String lastName);

    void delete(String firstName, String lastName);
}
