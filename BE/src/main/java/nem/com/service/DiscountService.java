package nem.com.service;

import nem.com.dto.request.SearchDiscountDTO;
import nem.com.entity.Discounts;

import java.util.List;

public interface DiscountService {

    List<Discounts> findAll(SearchDiscountDTO request ) ;

    Discounts create( Discounts discounts ) ;

    Discounts update( Discounts discounts ) ;

}
