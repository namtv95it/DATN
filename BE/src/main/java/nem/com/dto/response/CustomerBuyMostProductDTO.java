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
@NamedNativeQuery(name = "CustomerBuyMostProductDTO" ,
query = " select sum(o.total) as total , c.fullname as name , sum(od.quantity) as cc , c.birth_date as bd \n" +
        " from orders o join customers c on c.id = o.customer_id \n" +
        "               join order_details od on od.order_id = o.id \n"+
        " where (o.status = 3 or o.status = 6) and (:startDate is null or o.create_date >= :startDate)  " +
        " and ( :endDate is null or o.create_date <= :endDate) and c.id != 0\n " +
        " group by c.fullname , c.birth_date \n" +
        " order by total desc " +
        "limit 0 , 7 ;\n" , resultSetMapping = "custome_product_dto")
@SqlResultSetMapping(
        name = "custome_product_dto" ,
        classes = @ConstructorResult(
                targetClass = CustomerBuyMostProductDTO.class ,
                columns = {
                        @ColumnResult(name = "total", type = Double.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "cc", type = Integer.class),
                        @ColumnResult(name = "bd", type = Date.class)
                }
        )
)

public class CustomerBuyMostProductDTO {

    @Id
    private Double total ;
    private String name ;
    private Integer quantity ;
    private Date birdDay ;
}
