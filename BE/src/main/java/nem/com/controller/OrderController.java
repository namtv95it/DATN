package nem.com.controller;

import lombok.RequiredArgsConstructor;
import nem.com.domain.dto.SearchOrderDTO;
import nem.com.domain.request.UpdateOrderRequest;
import nem.com.domain.response.OrderResponseDTO;
import nem.com.entity.OrderDetails;
import nem.com.entity.Orders;
import nem.com.repository.OrderDetailsRepository;
import nem.com.repository.OrdersRepository;
import nem.com.repository.ProductsDetailsRepository;
import nem.com.service.OrderDetailService;
import nem.com.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderDetailService orderDetailService;
    private final OrdersRepository ordersRepository;
    private final ProductsDetailsRepository productsDetailsRepository;

    @GetMapping("/allExchange")
    public ResponseEntity<List<Orders>> getAllExchange(){
        return new ResponseEntity<>(this.ordersRepository.findAllOrderExchange(),HttpStatus.OK);
    }

    @GetMapping("/exchange")
    public ResponseEntity<List<OrderResponseDTO>> getExchange(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size
    ){
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        Page<Orders> ordersList = this.orderService.findOrderExchange(page,size);
        for (Orders orders: ordersList
        ) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrders(orders);
            List<OrderDetails> orderDetailsList = this.orderDetailService.findByOrderId(orders.getId());
            orderResponseDTO.setOrderDetailsList(orderDetailsList);
            orderResponseDTO.setTotalPage(ordersList.getTotalPages());
            orderResponseDTO.setTotalElements(ordersList.getTotalElements());
            orderResponseDTOList.add(orderResponseDTO);
        }
        return new ResponseEntity<>(orderResponseDTOList,HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Orders>> getData(){
        return new ResponseEntity<>(this.ordersRepository.findAll(),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDTO>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        Page<Orders> ordersList = this.orderService.getAllOrderSort(page,size);
        for (Orders orders: ordersList
             ) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrders(orders);
            List<OrderDetails> orderDetailsList = this.orderDetailService.findByOrderId(orders.getId());
            orderResponseDTO.setOrderDetailsList(orderDetailsList);
            orderResponseDTO.setTotalPage(ordersList.getTotalPages());
            orderResponseDTO.setTotalElements(ordersList.getTotalElements());
            orderResponseDTOList.add(orderResponseDTO);
        }
        return new ResponseEntity<>(orderResponseDTOList,HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<OrderResponseDTO>> searchOrder(
            @RequestBody SearchOrderDTO searchOrderDTO,
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size
    ){
        if (searchOrderDTO.getStatus() != -1){
            List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
            Page<Orders> ordersList = this.orderService.searchOrderByStatus(searchOrderDTO,page,size);
            for (Orders orders: ordersList
            ) {
                OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
                orderResponseDTO.setOrders(orders);
                List<OrderDetails> orderDetailsList = this.orderDetailService.findByOrderId(orders.getId());
                orderResponseDTO.setOrderDetailsList(orderDetailsList);
                orderResponseDTO.setTotalPage(ordersList.getTotalPages());
                orderResponseDTO.setTotalElements(ordersList.getTotalElements());
                orderResponseDTOList.add(orderResponseDTO);
            }
            return new ResponseEntity<>(orderResponseDTOList,HttpStatus.OK);
        }else {
            List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
            Page<Orders> ordersList = this.orderService.searchAllOrder(searchOrderDTO,page,size);
            for (Orders orders: ordersList
            ) {
                OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
                orderResponseDTO.setOrders(orders);
                List<OrderDetails> orderDetailsList = this.orderDetailService.findByOrderId(orders.getId());
                orderResponseDTO.setOrderDetailsList(orderDetailsList);
                orderResponseDTO.setTotalPage(ordersList.getTotalPages());
                orderResponseDTO.setTotalElements(ordersList.getTotalElements());
                orderResponseDTOList.add(orderResponseDTO);
            }
            return new ResponseEntity<>(orderResponseDTOList,HttpStatus.OK);
        }

    }

    @GetMapping("/status/{stt}")
    public ResponseEntity<List<Orders>> findAllByStatus(@PathVariable("stt") Integer stautus){
        return new ResponseEntity<>(this.orderService.findByStatus(stautus), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(this.ordersRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/data/{status}")
    public ResponseEntity<List<OrderResponseDTO>> findByStatus(
            @PathVariable("status") Integer status,
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size
    ){
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<>();
        Page<Orders> ordersList = this.orderService.findByStatusOrderByCreateDateDesc(status, page, size);
        for (Orders orders: ordersList
        ) {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
            orderResponseDTO.setOrders(orders);
            List<OrderDetails> orderDetailsList = this.orderDetailService.findByOrderId(orders.getId());
            orderResponseDTO.setOrderDetailsList(orderDetailsList);
            orderResponseDTO.setTotalPage(ordersList.getTotalPages());
            orderResponseDTOList.add(orderResponseDTO);
        }
        return new ResponseEntity<>(orderResponseDTOList,HttpStatus.OK);
    }

    @PutMapping("")
    public void update(@RequestBody UpdateOrderRequest updateOrderRequest){
        this.orderService.updateOrder(updateOrderRequest);
//        this.orderDetailsRepository.deleteByOrderId(updateOrderRequest.getId());
//        for (int i = 0; i < updateOrderRequest.getListOrderDetail().size(); i++) {
//            this.orderDetailsRepository.save(updateOrderRequest.getListOrderDetail().get(i));
//        }
//        return new ResponseEntity<>(this.ordersRepository.findById(updateOrderRequest.getId()).get(),HttpStatus.OK);
    }
    @PutMapping("/update-order")
    public ResponseEntity<Orders> updateOrder(@RequestBody Orders orders){
        orders.setUpdatedDate(new Date());
        return new ResponseEntity<>(this.ordersRepository.save(orders),HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{status}")
    public ResponseEntity<Orders> updateStatus(@PathVariable("status") Integer status, @RequestBody Orders orders){
        orders.setStatus(status);
        orders.setUpdatedDate(new Date());
        return new ResponseEntity<>(this.orderService.verifyOrCancel(orders,status),HttpStatus.OK);
    }
    
    @GetMapping("/ghn")
    public ResponseEntity<List<Orders>> getOrderGhn(){
        return new ResponseEntity<>(this.orderService.getOrderGhn(),HttpStatus.OK);
    }
}
