package nem.com.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nem.com.entity.Carts;
import nem.com.entity.Orders;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Orders order;
    private List<Carts> listCarts;
}
