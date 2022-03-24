package ore.scyllacore.repositories;

import ore.scyllacore.domain.MutantData;
import ore.scyllacore.repository.MutantDataRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ScyllaCQLSessionTest {

    @Autowired
    @Qualifier("mutantDataSessionRepository")
    private MutantDataRepository repository;
    @Autowired
    @Qualifier("mutantDataPreparedSessionRepository")
    private MutantDataRepository preparedRepository;

    private final MutantData mutantData = MutantData.builder()
            .firstName("Rob")
            .lastName("Roy")
            .address("1995 Courage St")
            .pictureLocation("https://www.kinopoisk.ru/film/3592")
            .build();

    @Test
    public void select() {
        repository.insert(mutantData);

        assertNotNull(repository.selectOne("Rob", "Roy"));
    }

    @Test
    public void delete() {
        repository.insert(mutantData);
        repository.delete("Rob", "Roy");

        assertNull(repository.selectOne("Rob", "Roy"));
    }

    @Test
    public void insertTtl() throws InterruptedException {
        repository.insert(mutantData, 1);
        assertNotNull(repository.selectOne("Rob", "Roy"));
        TimeUnit.SECONDS.sleep(2);
        assertNull(repository.selectOne("Rob", "Roy"));
    }


    @Test
    public void updateTtl() throws InterruptedException {
        repository.insert(mutantData, 2);
        TimeUnit.SECONDS.sleep(1);
        assertNotNull(repository.selectOne("Rob", "Roy"));

        repository.insert(mutantData, 5);
        TimeUnit.SECONDS.sleep(2);
        assertNotNull(repository.selectOne("Rob", "Roy"));
    }

    @Test
    public void preparedInsert() {
        repository.delete("Rob", "Roy");
        preparedRepository.insert(mutantData);

        assertNotNull(repository.selectOne("Rob", "Roy"));
    }

}
