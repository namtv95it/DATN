package nem.com.utils;

import lombok.AllArgsConstructor;
import nem.com.entity.Discounts;
import nem.com.entity.ProductDiscount;
import nem.com.repository.DiscountsRepository;
import nem.com.repository.ProductDiscountRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DiscountUtils {

    private final DiscountsRepository discountsRepository ;
    private final ProductDiscountRepository productDiscountRepository ;


    public void updateDiscountProductStart(Discounts discounts ) {
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
        List<Integer> listPro = new ArrayList<>();

        if( listPd.size() != 0 ){
            for( ProductDiscount x: listPd ){
                if( x.getProduct().getDiscount() < discounts.getDiscount() || x.getDiscount() == null ){
                    listPro.add(x.getProduct().getId() );
                }
            }
        }

        Integer[] arr = listPro.toArray(Integer[]::new) ;
        discounts.setStatus(2);
        this.discountsRepository.save( discounts) ;
        this.discountsRepository.updateDiscountProduct( discounts.getDiscount() , arr );
    }

    public void updateDiscountProductEnd(Discounts discounts ) {
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
        List<Integer> listPro = new ArrayList<>();

        if( listPd.size() != 0 ){
            for( ProductDiscount x: listPd ){
                listPro.add(x.getProduct().getId() );
            }
        }

        discounts.setStatus(3);
        this.discountsRepository.save(discounts) ;
        this.discountsRepository.updateDiscountProduct( 0 , listPro.toArray(Integer[]::new)  );
    }
}
