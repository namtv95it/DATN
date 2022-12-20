package nem.com.controller;

import nem.com.dto.request.OrderDetailDTO;
import nem.com.entity.OrderDetails;
import nem.com.service.OrderDetailOnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/order-detail-online")
public class OrderDetailOnlineController {

    private final OrderDetailOnlineService orderDetailOnlineService;

    public OrderDetailOnlineController(OrderDetailOnlineService orderDetailOnlineService) {
        this.orderDetailOnlineService = orderDetailOnlineService;
    }

    @PostMapping
    public ResponseEntity<OrderDetails> saveOrderDetailExchange(@RequestBody OrderDetails request) {
        return new ResponseEntity<>(this.orderDetailOnlineService.saveOrderDetailExchange(request), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderDetails> updateOrderDetailExchange(@RequestBody OrderDetails request) {
        return new ResponseEntity<>(this.orderDetailOnlineService.updateOrderDetailExchange(request), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDetails>> GetOrderDetailByOrder(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.orderDetailOnlineService.findAllOrderDetail(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsInExchange(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.orderDetailOnlineService.getOrderDetailsInExchange(id));
    }

    @GetMapping("getByCusAndOrder")
    public ResponseEntity<List<OrderDetails>> getOrderDetailByCustomeAndOrder(@RequestParam("idCus") Integer idCus, @RequestParam("idOrder") Long idOrder) {
        return ResponseEntity.ok(this.orderDetailOnlineService.findAllOrderDetailByCustomeAndOrder(idOrder, idCus));
    }

}
