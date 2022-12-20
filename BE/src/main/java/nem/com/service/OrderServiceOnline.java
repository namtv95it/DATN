package nem.com.service;

import nem.com.domain.request.OrderDTO;
import nem.com.domain.response.OrderDTOResponse;
import nem.com.entity.Orders;

import java.util.List;

public interface OrderServiceOnline {

    Orders save(Orders order);

    Orders checkout(OrderDTO request);

    List<Orders> getAllOrdersByStatus( Short status , Integer id ) ;

    List<OrderDTOResponse> getAllOrders( Integer id ) ;

    void updateStatusOrder( Integer status , Long id ) ;
}
