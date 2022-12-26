package nem.com.service.impl;

import lombok.AllArgsConstructor;
import nem.com.domain.request.UpdateOrderRequest;
import nem.com.dto.request.SearchDTO;
import nem.com.dto.response.BuyMostProductDTO;
import nem.com.dto.response.CustomerBuyMostProductDTO;
import nem.com.dto.response.OverviewStatisticalDTO;
import nem.com.dto.response.TurnoverDTO;
import nem.com.domain.dto.SearchOrderDTO;
import nem.com.entity.Discounts;
import nem.com.entity.Orders;
import nem.com.entity.ProductDiscount;
import nem.com.repository.DiscountsRepository;
import nem.com.repository.OrdersRepository;
import nem.com.repository.ProductDiscountRepository;
import nem.com.entity.OrderDetails;
import nem.com.entity.ProductsDetails;
import nem.com.repository.OrderDetailsRepository;
import nem.com.repository.ProductsDetailsRepository;
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
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final DiscountsRepository discountsRepository ;
    private final ProductDiscountRepository productDiscountRepository ;

    OrderDetailsRepository orderDetailsRepository;
    ProductsDetailsRepository productsDetailsRepository;

    @Override
    public List<Orders> getAll() {

        return this.ordersRepository.findAll();
    }

    @Override
    public Page<Orders> findOrderExchange(Integer page, Integer size) {
        return this.ordersRepository.findOrderExchange(PageRequest.of(page,size));
    }

    @Override
    public Page<Orders> findByStatusOrderByCreateDateDesc(Integer status, Integer page, Integer size) {
        return this.ordersRepository.findByStatusOrderByCreateDateDesc(status, PageRequest.of(page,size));
    }

    @Override
    public Page<Orders> searchOrdersExchange(SearchOrderDTO searchOrderDTO, Integer page, Integer size) {
        return this.ordersRepository.searchOrdersExchange(searchOrderDTO.getFullName(),
                searchOrderDTO.getId(),searchOrderDTO.getOrderCode(),
                searchOrderDTO.getStatus(),PageRequest.of(page,size));
    }

    @Override
    public Page<Orders> searchOrderByStatus(SearchOrderDTO searchOrderDTO, Integer page, Integer size) {
        return this.ordersRepository.searchOrderByStatus(searchOrderDTO.getFullName(),
                searchOrderDTO.getId(),searchOrderDTO.getOrderCode(),
                searchOrderDTO.getStatus(),PageRequest.of(page,size));
    }

    @Override
    public Page<Orders> searchAllOrder(SearchOrderDTO searchOrderDTO, Integer page, Integer size) {
        return this.ordersRepository.searchAllOrder(searchOrderDTO.getFullName(),
                searchOrderDTO.getId(),searchOrderDTO.getOrderCode(),PageRequest.of(page,size));
    }

    @Override
    public List<Orders> findByStatus(Integer status) {
        return this.ordersRepository.findByStatus(status);
    }

    @Override
    public Page<Orders> getAllOrderSort(Integer page, Integer size) {
        return this.ordersRepository.getAllOrderSort(PageRequest.of(page,size));
    }

    @Override
    public Orders verifyOrCancel(Orders orders, Integer f) {
            orders.setStatus(f);
        this.ordersRepository.save(orders);
        return orders;
    }

    @Override
    public List<CustomerBuyMostProductDTO> CustomerBuyMostProduct( SearchDTO request) {
        return this.ordersRepository.CustomerBuyMostProduct( request.getStartDate() , request.getEndDate() ) ;
    }

//    public void updateDiscountProductStart(Discounts discounts ) {
//        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
//        List<Integer> listPro = new ArrayList<>();
//
//        for( ProductDiscount x: listPd ){
//            if( x.getProduct().getDiscount() < discounts.getDiscount() ){
//                listPro.add(x.getProduct().getId() );
//            }
//        }
//
//        Integer[] arr = listPro.toArray(Integer[]::new) ;
//        discounts.setStatus(2);
//        this.discountsRepository.save( discounts) ;
//        this.discountsRepository.updateDiscountProduct( discounts.getDiscount() , arr );
//    }
//
//    public void updateDiscountProductEnd(Discounts discounts ) {
//        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
//        List<Integer> listPro = new ArrayList<>();
//
//        for( ProductDiscount x: listPd ){
//            listPro.add(x.getProduct().getId() );
//        }
//
//        discounts.setStatus(3);
//        this.discountsRepository.save(discounts) ;
//        this.discountsRepository.updateDiscountProduct( 0 , listPro.toArray(Integer[]::new)  );
//    }

    @Override
    public void updateOrder(UpdateOrderRequest updateOrderRequest) {
        Orders orders = this.ordersRepository.findById(updateOrderRequest.getId()).get();
        for (int i = 0; i < orders.getListOrderDetails().size(); i++) {
            ProductsDetails productsDetails = orders.getListOrderDetails().get(i).getProductsDetail();
            productsDetails.setQuantity(productsDetails.getQuantity()+orders.getListOrderDetails().get(i).getQuantity());
            this.productsDetailsRepository.save(productsDetails);
            this.orderDetailsRepository.delete(orders.getListOrderDetails().get(i));
        }
        for (int i = 0; i < updateOrderRequest.getListOrderDetail().size(); i++) {
            ProductsDetails productsDetails =
                    this.productsDetailsRepository.findById(updateOrderRequest.getListOrderDetail().get(i).getProductsDetail().getId()).get();
            productsDetails.setQuantity(productsDetails.getQuantity()-updateOrderRequest.getListOrderDetail().get(i).getQuantity());
            this.productsDetailsRepository.save(productsDetails);
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setUnitprice(updateOrderRequest.getListOrderDetail().get(i).getUnitprice());
            orderDetails.setQuantity(updateOrderRequest.getListOrderDetail().get(i).getQuantity());
            orderDetails.setUpdatedDate(new Date());
            orderDetails.setStatus(1);
            orderDetails.setProductsDetail(productsDetails);
            orderDetails.setOrder(orders);
            this.orderDetailsRepository.save(orderDetails);
        }
    }

    @Override
    public OverviewStatisticalDTO getOverview() {
        return this.ordersRepository.getOverview() ;
    }

    @Override
    public List<BuyMostProductDTO> BuyMostProductDTO(SearchDTO request) {
        return this.ordersRepository.buyMostProductDTO( request.getStartDate() , request.getEndDate()) ;
    }

    @Override
    public List<TurnoverDTO> turnoverDTO(SearchDTO request) {
        String type ;
        if( request.getType() == 1 ){
            type = "%Y" ;
        }else if( request.getType() == 2 ){
            type = "%m-%Y" ;
        }else {
            type = "%d-%m-%Y" ;
        }
        System.out.println(request.getEndDate());
        System.out.println(request.getStartDate());
        System.out.println(this.ordersRepository.turnoverDTO( request.getStartDate() , request.getEndDate() , type ).size() );
        return this.ordersRepository.turnoverDTO( request.getStartDate() , request.getEndDate() , type );
    }

    public List<Orders> getOrderGhn() {
        return this.ordersRepository.getOrderGhn();
    }
}
