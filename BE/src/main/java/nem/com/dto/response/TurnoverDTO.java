package nem.com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedNativeQuery(
        name = "TurnoverDTO" ,
        query = "select sum(o.total) as TT , date_format( o.create_date,:type) as dd , sum(od.quantity)  as SL \n" +
                "from orders o join order_details od on o.id = od.order_id \n" +
                "where (o.status = 3 or o.status = 6)and o.create_date >= :startDate and o.create_date<= :endDate  \n" +
                "group by dd " +
                "order by dd asc;\n" ,
        resultSetMapping = "turnover_dto"
)
@SqlResultSetMapping(
        name = "turnover_dto",
        classes = @ConstructorResult(
                targetClass = TurnoverDTO.class ,
                columns = {
                        @ColumnResult(name = "TT" , type = Double.class ) ,
                        @ColumnResult(name = "dd" , type = String.class ) ,
                        @ColumnResult(name = "SL" , type = Integer.class )
                }
        )
)
public class TurnoverDTO {
    @Id
    private Double total ;
    private String name ;
    private Integer quantity ;
}
