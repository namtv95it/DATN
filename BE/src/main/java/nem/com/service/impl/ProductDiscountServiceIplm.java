package nem.com.service.impl;

import nem.com.entity.Discounts;
import nem.com.entity.ProductDiscount;
import nem.com.entity.Products;
import nem.com.repository.DiscountsRepository;
import nem.com.repository.ProductDiscountRepository;
import nem.com.repository.ProductsRepository;
import nem.com.service.ProductDiscountService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductDiscountServiceIplm implements ProductDiscountService {

    private final ProductDiscountRepository productDiscountRepository ;
    private final ProductsRepository productsRepository ;
    private final DiscountsRepository discountsRepository ;

    public ProductDiscountServiceIplm( ProductDiscountRepository repository ,
                                       ProductsRepository productsRepository ,
                                       DiscountsRepository discountsRepository ){
         this.productDiscountRepository = repository ;
         this.productsRepository = productsRepository ;
         this.discountsRepository = discountsRepository ;
    }

    @Override
    public void save(Integer idDis, Integer[] idPro) {
        Discounts d = this.discountsRepository.findById(idDis).get() ;
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(idDis);
        for( ProductDiscount pd : listPd ){
            if( checkProductInDiscount( idPro , pd ) == false){
                this.productDiscountRepository.deleteProductDis( pd.getProduct().getId() , idDis ) ;
                Products products = pd.getProduct();
                products.setDiscount(0);
                this.productsRepository.save(products);
            }
        }
        for( Integer x : idPro ){
            ProductDiscount pd = this.productDiscountRepository.findById( x , idDis);
            if( pd == null ){
                Products p = this.productsRepository.findById(x).get();
                ProductDiscount pdd = new ProductDiscount( );
                pdd.setDiscount(d);
                pdd.setProduct(p);
                this.productDiscountRepository.save(pdd) ;
            }
        }
        if( d.getStatus() == 2 ){
            updateDiscountProductStart(d);
        }
    }

    private boolean checkProductInDiscount( Integer[] listPro , ProductDiscount idPro ){
        for( Integer p : listPro ){
            if( p == idPro.getProduct().getId() ){
                return true ;
            }
        }
        return false ;
    }

    @Override
    public List<ProductDiscount> findAll( Integer id) {
        return this.productDiscountRepository.findAllPd(id);
    }

    public void updateDiscountProductStart(Discounts discounts ) {
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
        List<Integer> listPro = new ArrayList<>();

        for( ProductDiscount x: listPd ){
            if( x.getProduct().getDiscount() < discounts.getDiscount() ){
                listPro.add(x.getProduct().getId() );
            }
        }

        Integer[] arr = listPro.toArray(Integer[]::new) ;
        discounts.setStatus(2);
        this.discountsRepository.save( discounts) ;
        this.discountsRepository.updateDiscountProduct( discounts.getDiscount() , arr );
    }

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
}
