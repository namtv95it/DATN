package nem.com.controller;

import nem.com.entity.ProductImages;
import nem.com.service.ProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/productImage")
public class ProductImageController {
    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }
    @GetMapping("")
    public ResponseEntity<List<ProductImages>> getAll(){
        return new ResponseEntity<>(this.productImageService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductImages> getOne(@PathVariable("id") Integer id){
        return new ResponseEntity<>(this.productImageService.getOne(id),HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<ProductImages>> getProductImagesById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(this.productImageService.getProductsImagesById(id),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ProductImages> save(@RequestBody ProductImages productImages){
        return new ResponseEntity<>(this.productImageService.save(productImages),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id){
        this.productImageService.delete(id);
    }

    @GetMapping("list-proId/{id}")
    public ResponseEntity<List<ProductImages>> findByProId( @PathVariable("id") Integer id ){
        return ResponseEntity.ok( this.productImageService.findByProId(id) ) ;
    }

}
