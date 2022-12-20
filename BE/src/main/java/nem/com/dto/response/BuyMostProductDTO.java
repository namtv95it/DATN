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
        name = "BuyMostProductDTO",
        query = "select sum(od.quantity) as TT , p.name as name  from orders o join order_details od on o.id = od.order_id\n" +
                "                       join products_details pd on pd.id = od.product_detail_id \n" +
                "                       join products p on p.id = pd.product_id \n" +
                "where (o.status = 3 or o.status = 6) and (:startDate is null or o.create_date >= :startDate) " +
                "and ( :endDate is null or o.create_date <= :endDate) \n" +
                "group by p.name  \n" +
                "order by TT desc\n" +
                "limit 0 , 7 ;\n" ,
        resultSetMapping = "buy_most_product_dto"
)
@SqlResultSetMapping(
        name = "buy_most_product_dto" ,
        classes = @ConstructorResult(
                targetClass = BuyMostProductDTO.class ,
                columns = {
                        @ColumnResult( name = "TT" , type = Long.class ) ,
                        @ColumnResult( name = "name" , type = String.class ),
                }
        )
)

public class BuyMostProductDTO {

    @Id
    private Long total ;
    private String name ;
}
