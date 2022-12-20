package nem.com.controller;

import nem.com.dto.request.SearchDiscountDTO;
import nem.com.entity.Discounts;
import nem.com.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService ;

    public DiscountController( DiscountService discountService ) {
        this.discountService = discountService ;
    }

    @PostMapping("findAll")
    public ResponseEntity<List<Discounts>> findAll(@RequestBody SearchDiscountDTO request) {
        return ResponseEntity.ok( this.discountService.findAll(request) );
    }

    @PostMapping("create")
    public ResponseEntity<Discounts> create(@RequestBody Discounts discounts ){
        return ResponseEntity.ok( this.discountService.create(discounts) ) ;
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Discounts> update( @RequestBody Discounts discounts ){
        return ResponseEntity.ok( this.discountService.update(discounts) );
    }

//    @GetMapping("updateDiscount")
//    public ResponseEntity<Void> updateDiscountInPro( @RequestParam("id") Integer id ){
//        this.discountService.updateDiscountProduct(id);
//        return ResponseEntity.ok().build() ;
//    }
}
