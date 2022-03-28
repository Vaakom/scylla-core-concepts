package ore.scyllacore.repositories;

import ore.scyllacore.domain.MutantData;
import ore.scyllacore.repository.MutantDataRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ScyllaQueryBuilderTest {

    @Autowired
    @Qualifier("mutantDataQueryBuilderRepository")
    private MutantDataRepository repository;

    private final MutantData mutantData = MutantData.builder()
            .firstName("Rob")
            .lastName("Roy")
            .address("1995 Courage St")
            .pictureLocation("https://www.kinopoisk.ru/film/3592")
            .build();

    @Test
    public void select(){
        assertNotNull(repository.selectOne("Rob", "Roy"));
    }
}
