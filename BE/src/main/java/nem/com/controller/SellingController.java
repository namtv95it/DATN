package nem.com.controller;
import nem.com.domain.request.ProductDetailsDTO;
import nem.com.domain.request.SellingDTO;
import nem.com.domain.request.ServiceResult;
import nem.com.service.SellingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/selling")
public class SellingController {
    private final SellingService sellingService;

    public SellingController(SellingService sellingService){
        this.sellingService =sellingService;

    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody SellingDTO sellingDTO){
        ServiceResult<?> result = sellingService.selling(sellingDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/resetQuantityInventory")
    public ResponseEntity<?> resetQuantityInventory(@RequestBody List<Long> lstId){
        List<ProductDetailsDTO> lstQuantityProduct = sellingService.resetQuantityInventory(lstId);
        return ResponseEntity.ok(lstQuantityProduct);
    }


}
