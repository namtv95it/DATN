package nem.com.service.impl;

import lombok.AllArgsConstructor;
import nem.com.entity.OrderDetails;
import nem.com.repository.OrderDetailsRepository;
import nem.com.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> findByOrderId(Long id) {
        return this.orderDetailsRepository.findByOrderId(id);
    }
}
