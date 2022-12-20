package nem.com.controller;

import nem.com.entity.ProductDiscount;
import nem.com.service.impl.ProductDiscountServiceIplm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/discount_product")
public class ProductDiscountController {

    private final ProductDiscountServiceIplm productDiscountServiceIplm ;

    public ProductDiscountController( ProductDiscountServiceIplm productDiscountServiceIplm){
        this.productDiscountServiceIplm = productDiscountServiceIplm ;
    }

    @GetMapping("")
    public ResponseEntity<Void> save(@RequestParam("idDis") Integer idDis , @RequestParam("idPro") Integer[] idPro){
        this.productDiscountServiceIplm.save( idDis , idPro) ;
        return  ResponseEntity.ok().build();
    }

    @GetMapping("getAll")
    public ResponseEntity<List<ProductDiscount>> findAll( @RequestParam("id") Integer id ){
        return ResponseEntity.ok(this.productDiscountServiceIplm.findAll(id));
    }
}
