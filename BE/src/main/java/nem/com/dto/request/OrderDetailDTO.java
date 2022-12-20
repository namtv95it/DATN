package nem.com.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nem.com.entity.Exchanges;
import nem.com.entity.OrderDetails;

@Getter
@Setter
public class OrderDetailDTO {

    private OrderDetails orderDetails;

    private Exchanges exchanges;

}
