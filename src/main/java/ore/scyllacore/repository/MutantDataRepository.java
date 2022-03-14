package ore.scyllacore.repository;

import ore.scyllacore.domain.MutantData;

import java.util.List;

public interface MutantDataRepository {

    public void insert(MutantData mutantData);

    public MutantData selectOne(String firstName, String lastName);

    public void delete(String firstName, String lastName);
}
