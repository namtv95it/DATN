package nem.com.service.impl;

import lombok.AllArgsConstructor;
import nem.com.domain.dto.ExchangeDTO;
import nem.com.domain.dto.SearchOrderDTO;
import nem.com.domain.request.UpdateOrderRequest;
import nem.com.dto.request.SearchDTO;
import nem.com.dto.response.BuyMostProductDTO;
import nem.com.dto.response.CustomerBuyMostProductDTO;
import nem.com.dto.response.OverviewStatisticalDTO;
import nem.com.dto.response.TurnoverDTO;
import nem.com.entity.*;
import nem.com.repository.*;
import nem.com.service.ExchangeService;
import nem.com.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangesRepository exchangesRepository;

    private final ExchangeImagesRepository exchangeImagesRepository;

    @Override
    public ExchangeDTO findByOrderId(Long orderId) {
        Exchanges exchanges = this.exchangesRepository.findByOrderId(orderId);

        List<ExchangeImages> exchangeImagesList = this.exchangeImagesRepository.findByExchange_Id(exchanges.getId());

        return new ExchangeDTO(exchanges,exchangeImagesList);
    }
}
