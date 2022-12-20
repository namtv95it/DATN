package nem.com.service.impl;

import nem.com.domain.request.ProductDetailsDTO;
import nem.com.domain.request.SellingDTO;
import nem.com.domain.request.ServiceResult;
import nem.com.entity.*;
import nem.com.exception.LimitQuantityException;
import nem.com.exception.LitmitQuantitySellingException;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.OrderDetailsRepository;
import nem.com.repository.OrdersRepository;
import nem.com.repository.ProductsDetailsRepository;
import nem.com.service.SellingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
public class SellingServiceImpl implements SellingService {

    private final OrdersRepository ordersRepository;

    private final OrderDetailsRepository orderDetailsRepository;

    private  final ProductsDetailsRepository productsDetailsRepository;

    public SellingServiceImpl(OrdersRepository ordersRepository,
                              OrderDetailsRepository orderDetailsRepository,
                              ProductsDetailsRepository productsDetailsRepository
                              ){
        this.orderDetailsRepository = orderDetailsRepository;
        this.ordersRepository = ordersRepository;
        this.productsDetailsRepository = productsDetailsRepository;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ServiceResult<?> selling(SellingDTO sellingDTO) {
        ServiceResult<SellingDTO> serviceResult = new ServiceResult<>();
        try {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Orders orders = new Orders();
        Customers customers = new Customers();
        Employees employees = new Employees();
        employees.setId(sellingDTO.getEmployee()); // Gán tạm dữ liệu employee;
        customers.setId(sellingDTO.getCustomer()!=null?sellingDTO.getCustomer():0);
        orders.setCustomer(customers);
        orders.setNote(sellingDTO.getNote());
        orders.setTotal(sellingDTO.getTotalPrice());
        orders.setCreateDate(date);
        if(sellingDTO.getCheckSelling() == 0){
            orders.setStatus(6);
        }else{
            orders.setStatus(0);
            orders.setFreight(sellingDTO.getFreight());
            orders.setShipAddress(sellingDTO.getShipAddress());
            orders.setShipName(sellingDTO.getShipName());
            orders.setShipPhone(sellingDTO.getShipPhone());
        }
        orders.setEmployee(employees);
        orders.setDiscount(sellingDTO.getDiscount()*sellingDTO.getTotalPrice()/100);
        orders = ordersRepository.save(orders);
        for (ProductDetailsDTO productDetailsDTO: sellingDTO.getOrderDetail()){
            ProductsDetails productsDetails = productsDetailsRepository.findById(productDetailsDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Id not found " + productDetailsDTO.getId()));
                if (productsDetails.getQuantity() < productDetailsDTO.getQuantity()){
                    throw new LimitQuantityException("Số lượng không đủ. Vui lòng cập nhật lại số lượng trong giỏ hàng !");
                };
            OrderDetails orderDetails = new OrderDetails();
            ProductsDetails productsDetails1 = new ProductsDetails();
            productsDetails1.setId(productDetailsDTO.getId());
//            orderDetails.getOrder().setId(1L);
            orderDetails.setOrder(orders);
            orderDetails.setStatus(1);
            orderDetails.setQuantity(productDetailsDTO.getQuantity());
            orderDetails.setProductsDetail(productsDetails1);
            orderDetails.setRatingStatus((short)1);
            orderDetails.setStatus(1);
            orderDetails.setUnitprice(productsDetails.getProduct().getPrice());
            orderDetailsRepository.save(orderDetails);
            productsDetails.setQuantity(productsDetails.getQuantity()-productDetailsDTO.getQuantity());
            productDetailsDTO.setNameColor(productsDetails.getColor().getName());
            productDetailsDTO.setNameSize(productsDetails.getSize().getCode());
        }
        sellingDTO.setId(orders.getId());
        serviceResult.setData(sellingDTO);
        serviceResult.setStatus(HttpStatus.OK);
        }catch (RuntimeException e){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            e.printStackTrace();
            throw e;
        }
        return serviceResult;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductDetailsDTO> resetQuantityInventory(List<Long> lstId) {
        List<ProductDetailsDTO> lstQuantity = productsDetailsRepository.getProductDetailsByListID(lstId);
        return lstQuantity;
    }
}
