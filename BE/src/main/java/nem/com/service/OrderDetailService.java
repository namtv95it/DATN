package nem.com.service;

import nem.com.entity.OrderDetails;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetails> findByOrderId(Long id);
}
