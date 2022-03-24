package ore.scyllacore.mapper;

import com.datastax.oss.driver.api.core.cql.Row;
import ore.scyllacore.domain.MutantData;
import org.springframework.stereotype.Component;

public class MutantDataMapper {

   public static MutantData rowToData(Row row) {
       return row == null ? null : MutantData.builder()
               .firstName(row.getString("first_name"))
               .lastName(row.getString("last_name"))
               .address(row.getString("address"))
               .pictureLocation(row.getString("picture_location"))
               .build();
   }
}
