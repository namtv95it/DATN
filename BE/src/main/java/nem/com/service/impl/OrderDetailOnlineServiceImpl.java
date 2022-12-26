package nem.com.service.impl;

import lombok.RequiredArgsConstructor;
import nem.com.dto.request.OrderDetailDTO;
import nem.com.entity.OrderDetails;
import nem.com.entity.ProductsDetails;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.OrderDetailsRepository;
import nem.com.repository.OrdersRepository;
import nem.com.repository.ProductsDetailsRepository;
import nem.com.service.OrderDetailOnlineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailOnlineServiceImpl implements OrderDetailOnlineService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductsDetailsRepository productsDetailsRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public OrderDetails save(OrderDetails request) {
        return this.orderDetailsRepository.save(request);
    }

    @Override
    public List<OrderDetails> findAllOrderDetail(Integer id) {
        return this.orderDetailsRepository.getOrderDetailsById(id);
    }

    @Override
    public List<OrderDetails> findAllOrderDetailByCustomeAndOrder(Long idOrder, Integer idCustome) {
        return this.orderDetailsRepository.getOrderDetailsByByIdOrder(idOrder, idCustome);
    }

    @Override
    public List<OrderDetails> findOrderDetailByOrder(Long id) {
        return this.orderDetailsRepository.getOrderDetailsByOrder(id);
    }

    @Override
    public OrderDetails saveOrderDetailExchange(OrderDetails request) {
        OrderDetails orderDetailById = this.orderDetailsRepository.findById(request.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Id order detail not found " + request.getId()));
        orderDetailById.setQuantity(orderDetailById.getQuantity() - request.getQuantity());
        this.orderDetailsRepository.save(orderDetailById);
        OrderDetails newOrder = new OrderDetails();
        newOrder.setExchanges(request.getExchanges());
        newOrder.setQuantity(request.getQuantity());
        newOrder.setUnitprice(request.getUnitprice());
        newOrder.setOrder(orderDetailById.getOrder());
        newOrder.setProductsDetail(orderDetailById.getProductsDetail());
        newOrder.setStatus(0);
        return this.orderDetailsRepository.save(newOrder);
    }

    @Override
    public OrderDetails updateOrderDetailExchange(OrderDetails request) {
        OrderDetails orderDetailById = this.orderDetailsRepository.findById(request.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Id order detail not found " + request.getId()));
        orderDetailById.setStatus(0);
        orderDetailById.setExchanges(request.getExchanges());
        return this.orderDetailsRepository.save(orderDetailById);
    }

    public List<OrderDetails> getOrderDetailsInExchange(Long id) {
        return this.orderDetailsRepository.getOrderDetailsInExchange(id);
    }


}
