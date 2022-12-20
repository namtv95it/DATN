package nem.com.controller;

import lombok.RequiredArgsConstructor;
import nem.com.entity.OrderDetails;
import nem.com.entity.Orders;
import nem.com.repository.OrderDetailsRepository;
import nem.com.service.OrderDetailService;
import org.hibernate.criterion.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/orderDetail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;
    private final OrderDetailsRepository orderDetailsRepository;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderDetails>> getByOrderId(@PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(this.orderDetailService.findByOrderId(orderId), HttpStatus.OK);
    }
    @PutMapping("")
    public ResponseEntity<OrderDetails> update(@RequestBody OrderDetails orderDetails){
        return new ResponseEntity<>(this.orderDetailsRepository.save(orderDetails),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") OrderDetails orderDetails){
        this.orderDetailsRepository.delete(orderDetails);
    }
}
