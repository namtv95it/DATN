package nem.com.service;

import nem.com.entity.ProductDiscount;

import java.util.List;

public interface ProductDiscountService {

    void save( Integer idDis , Integer[] idPro) ;

    List<ProductDiscount> findAll( Integer id ) ;
}
