package nem.com.service.impl;

import nem.com.dto.request.SearchDiscountDTO;
import nem.com.entity.Discounts;
import nem.com.entity.ProductDiscount;
import nem.com.entity.Products;
import nem.com.repository.DiscountsRepository;
import nem.com.repository.ProductDiscountRepository;
import nem.com.repository.ProductsRepository;
import nem.com.scheduled.ProcessToPromotion;
import nem.com.service.DiscountService;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiscountServiceIplm implements DiscountService  {

    private final DiscountsRepository repository ;
    private final ProductDiscountRepository discountRepository ;
    private final ProductsRepository productsRepository ;

    private final DiscountsRepository discountsRepository ;
    private final ProductDiscountRepository productDiscountRepository ;
    public DiscountServiceIplm( DiscountsRepository repository ,
                                ProductDiscountRepository discountRepository,
                                ProductsRepository productsRepository ,
                                DiscountsRepository discountsRepository ,
                                ProductDiscountRepository productDiscountRepository ){
        this.repository = repository ;
        this.productsRepository = productsRepository ;
        this.discountRepository = discountRepository ;
        this.discountsRepository = discountsRepository ;
        this.productDiscountRepository = productDiscountRepository ;
    }

    @Override
    public List<Discounts> findAll(SearchDiscountDTO request) {
        return this.repository.findAll( request.getName() , request.getStartDate() , request.getEndDate() , request.getStatus()) ;
    }

    @Override
    public Discounts create(Discounts discounts) {
        System.out.println("kakaka" + discounts.getDiscount() );
        return this.repository.save(discounts);
    }

    @Override
    public Discounts update(Discounts discounts) {
        Discounts discounts1 = this.repository.save(discounts) ;
        System.out.println( "siêu nhân" + discounts1.getDiscount());
        System.out.println(discounts1.getStatus() );
        if( discounts1.getStatus() == 2){
            this.updateDiscountProductStart(discounts1);
        }
        return discounts1;
    }

    public void updateDiscountProductStart(Discounts discounts ) {
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
        List<ProductDiscount> listPbb = this.productDiscountRepository.findProductDiscountStatus2() ;
        List<Integer> listPro = new ArrayList<>();

            for( ProductDiscount x: listPd ){
                System.out.println( "Id:" + x.getProduct().getId());
                System.out.println( "size" + listPbb.size() );
                if( listPbb.size() != 0 ){
                    for( ProductDiscount y: listPbb ){
                        if( x.getProduct().getId() == y.getProduct().getId() ){
                            if( x.getDiscount().getDiscount() >= y.getDiscount().getDiscount() ){
                                listPro.add(x.getProduct().getId() );
                                break;
                            }
                        }
                    }
                }else{
                    listPro.add(x.getProduct().getId() );
                }
            }

        Integer[] arr = listPro.toArray(Integer[]::new) ;
        for( Integer ss: arr ){
            System.out.println(ss);
        }
        System.out.println(discounts.getDiscount());
        this.discountsRepository.updateDiscountProduct( discounts.getDiscount() , arr );
    }

}
