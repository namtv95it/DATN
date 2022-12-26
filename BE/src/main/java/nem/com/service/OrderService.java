package nem.com.service;

import nem.com.domain.request.UpdateOrderRequest;
import nem.com.dto.request.SearchDTO;
import nem.com.dto.response.BuyMostProductDTO;
import nem.com.dto.response.CustomerBuyMostProductDTO;
import nem.com.dto.response.OverviewStatisticalDTO;
import nem.com.dto.response.TurnoverDTO;
import nem.com.domain.dto.SearchOrderDTO;
import nem.com.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<Orders> getAll();

    Page<Orders> findOrderExchange(Integer page, Integer size);
    Page<Orders> findByStatusOrderByCreateDateDesc(Integer status, Integer page, Integer size);

    Page<Orders> searchOrderByStatus(SearchOrderDTO searchOrderDTO, Integer page, Integer size);

    Page<Orders> searchOrdersExchange(SearchOrderDTO searchOrderDTO, Integer page, Integer size);

    Page<Orders> searchAllOrder(SearchOrderDTO searchOrderDTO, Integer page, Integer size);
    List<Orders> findByStatus(Integer status);
    Page<Orders> getAllOrderSort(Integer page, Integer size);
    Orders verifyOrCancel(Orders orders, Integer f);

    List<CustomerBuyMostProductDTO> CustomerBuyMostProduct( SearchDTO request) ;

    void updateOrder(UpdateOrderRequest updateOrderRequest);
    OverviewStatisticalDTO getOverview() ;

     List<BuyMostProductDTO> BuyMostProductDTO(SearchDTO request ) ;

     List<TurnoverDTO> turnoverDTO( SearchDTO request );
     
    List<Orders> getOrderGhn();
}
