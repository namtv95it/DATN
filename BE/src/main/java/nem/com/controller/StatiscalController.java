package nem.com.controller;

import nem.com.dto.request.SearchDTO;
import nem.com.dto.response.BuyMostProductDTO;
import nem.com.dto.response.CustomerBuyMostProductDTO;
import nem.com.dto.response.OverviewStatisticalDTO;
import nem.com.dto.response.TurnoverDTO;
import nem.com.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/statiscal")
public class StatiscalController {

    private final OrderService orderService ;

    public StatiscalController( OrderService orderService ){
        this.orderService = orderService ;
    }

    @PostMapping("CustomerBuyMostProduct")
    public ResponseEntity<List<CustomerBuyMostProductDTO>> CustomerBuyMostProduct( @RequestBody SearchDTO request){
        return ResponseEntity.ok( this.orderService.CustomerBuyMostProduct( request )  ) ;
    }

    @GetMapping("overview")
    public ResponseEntity<OverviewStatisticalDTO> getOverview(){
        return ResponseEntity.ok( this.orderService.getOverview() )  ;
    }

    @PostMapping("turnover")
    public ResponseEntity<List<TurnoverDTO>> turnover( @RequestBody SearchDTO request ){
        return ResponseEntity.ok( this.orderService.turnoverDTO( request)  );
    }

    @PostMapping("buyMostPro")
    public ResponseEntity<List<BuyMostProductDTO>> buyMostProduct( @RequestBody SearchDTO request ){
        return ResponseEntity.ok( this.orderService.BuyMostProductDTO( request) ) ;
    }

}
