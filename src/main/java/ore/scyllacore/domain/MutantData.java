package ore.scyllacore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@AllArgsConstructor
@ToString
@Builder
@Table(value ="mutant_data")
public class MutantData {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, value = "first_name")
    private final String firstName;
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, value = "last_name")
    private final String lastName;
    @Column
    private final String address;
    @Column(value = "picture_location")
    private final String pictureLocation;

}
