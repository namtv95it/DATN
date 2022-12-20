package nem.com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedNativeQuery(
        name = "overview_statical" ,
        query = "select  (select count(orders.id) from orders ) as totalOrder ,\n" +
                " (select sum(o.total) from orders o where o.status = 3 and date_format( CURDATE() , '%M %Y') = date_format( o.create_date ,  '%M %Y') ) as totalOn ," +
                "(select sum(orders.total) from orders where status = 3 or status = 6 ) as totalPrice , \n" +
                " (select sum(o.total) from orders o where status = 6 and date_format( CURDATE() , '%M %Y') = date_format( o.create_date ,  '%M %Y')  ) as totalOff from dual ;" ,
        resultSetMapping = "view_statical_dto"
)
@SqlResultSetMapping(
        name = "view_statical_dto" ,
        classes = @ConstructorResult(
                targetClass = OverviewStatisticalDTO.class ,
                columns = {
                        @ColumnResult( name = "totalOrder" , type = Long.class ) ,
                        @ColumnResult( name = "totalOn" , type = Double.class),
                        @ColumnResult( name = "totalPrice" , type = Double.class ) ,
                        @ColumnResult( name = "totalOff" , type = Double.class )
                }
        )
)
public class OverviewStatisticalDTO {
    @Id
    private Long totalOrder ;
    private Double totalOn ;
    private Double totalPrice ;
    private Double totalOff ;
}
