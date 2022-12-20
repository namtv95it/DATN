package nem.com.service;

import nem.com.domain.dto.ExchangeDTO;
import nem.com.domain.dto.SearchOrderDTO;
import nem.com.domain.request.UpdateOrderRequest;
import nem.com.dto.request.SearchDTO;
import nem.com.dto.response.BuyMostProductDTO;
import nem.com.dto.response.CustomerBuyMostProductDTO;
import nem.com.dto.response.OverviewStatisticalDTO;
import nem.com.dto.response.TurnoverDTO;
import nem.com.entity.Orders;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ExchangeService {
    ExchangeDTO findByOrderId(Long orderId);
}
