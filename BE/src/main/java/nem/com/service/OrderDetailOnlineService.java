package nem.com.service;

import nem.com.dto.request.OrderDetailDTO;
import nem.com.entity.OrderDetails;

import java.util.List;

public interface OrderDetailOnlineService {

    OrderDetails save(OrderDetails request);

    List<OrderDetails>  findAllOrderDetail( Integer id );

    List<OrderDetails> findAllOrderDetailByCustomeAndOrder( Long idOrder ,  Integer idCustome ) ;

    List<OrderDetails> findOrderDetailByOrder( Long id );

    OrderDetails saveOrderDetailExchange(OrderDetails request);

    OrderDetails updateOrderDetailExchange(OrderDetails request);

    List<OrderDetails> getOrderDetailsInExchange(Long id);
}
