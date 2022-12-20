package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nem.com.entity.OrderDetails;
import nem.com.entity.Orders;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private List<OrderDetails> orderDetailsList;
    private Orders orders;
    private int totalPage;
    private Long totalElements;
}
