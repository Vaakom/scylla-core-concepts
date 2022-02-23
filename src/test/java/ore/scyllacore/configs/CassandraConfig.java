package ore.scyllacore.configs;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CassandraConfig {

    @Bean
    public Cluster cluster() {
        return Cluster.builder().addContactPoints("scylla-node1", "scylla-node2", "scylla-node3").build();
    }

    @Bean
    public Session session(Cluster cluster) {
        return cluster.connect("catalog");
    }
}
