package nem.com.controller;

import nem.com.dto.request.SearchProByCateDTO;
import nem.com.entity.Products;
import nem.com.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Products>> getAll(){
        return new ResponseEntity<>(this.productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getOne(@PathVariable("id") Integer id){
        return new ResponseEntity<>(this.productService.getOne(id),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Products> create(@RequestBody Products products){
        products.setCreateDate(new Date());
        products.setStatus(1);
        return new ResponseEntity<>(this.productService.save(products),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Products> update(
            @RequestBody Products products
    ){
        products.setUpdateDate(new Date());
        return new ResponseEntity<>(this.productService.update(products),HttpStatus.OK);
    }

    @GetMapping("/findByCate/{id}")
    public ResponseEntity<List<Products>> findByCate(@PathVariable("id") Short id) {
        return ResponseEntity.ok(this.productService.findByCate(id));
    }
    @GetMapping("findSize")
    public ResponseEntity<Page<Products>> findProBySize(@RequestParam(value = "size"  ) Integer[] sizes  ,
                                                        @RequestParam(value = "category" ) Short[] category ,
                                                        @RequestParam(value = "color") Integer[] color ,
                                                        @RequestParam(value = "max" , defaultValue = "99999999") Double max ,
                                                        @RequestParam(value = "min" , defaultValue = "0") Double min ,
                                                        @RequestParam(value = "pageNo")Optional<Integer> pageNo ,
                                                        @RequestParam(value = "pageSize") Integer pageSize ,
                                                        @RequestParam(value = "sortPrice") Integer sortPrice ){
        Pageable pageable ;
        if( sortPrice == 0 ){
            pageable = PageRequest.of( pageNo.orElse(0) , pageSize ) ;
        }else if( sortPrice == 1){
            pageable = PageRequest.of( pageNo.orElse(0) , pageSize , Sort.by("price").ascending() ) ;
        }else{
            pageable = PageRequest.of( pageNo.orElse(0) , pageSize , Sort.by("price").descending() ) ;
        }

        Page<Products> page = this.productService.getAllByAllPropertites(sizes , category, color , min ,max , pageable );
        return new ResponseEntity<>( page , HttpStatus.OK );
    }

    @GetMapping("getNew")
    public ResponseEntity<List<Products>> findProNew(){
         return new ResponseEntity<>(this.productService.getAllNewPro() , HttpStatus.OK ) ;
    }

    @GetMapping("getTop10Pro")
    public ResponseEntity<List<Products>> findTop10Pro() {
        return new ResponseEntity<>(this.productService.findTop10Pro(),  HttpStatus.OK) ;
    }

    @GetMapping("getProductNeverRating")
    public ResponseEntity<List<Products>> getProductNeverRating( @RequestParam("id") Long id ){
        return new ResponseEntity<>(this.productService.findProductNeverRating(id) , HttpStatus.OK ) ;
    }

    @GetMapping("getProSimilar")
    public ResponseEntity<List<Products>> getProductsSimilar( @RequestParam("idCate") Short id ) {
        return ResponseEntity.ok(this.productService.getProductsSimilar(id) );
    }

    @PostMapping("findProByCate")
    public ResponseEntity<List<Products>> findProByCate(@RequestBody SearchProByCateDTO request){
        return ResponseEntity.ok(this.productService.findProductByCate(request) );
    }
}
