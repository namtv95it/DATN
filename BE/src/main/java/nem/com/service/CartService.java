package nem.com.service;

import nem.com.entity.Carts;

import java.util.List;

public interface CartService {

    Carts addToCart(Carts request);

    Carts updateCart(Carts request);

    void deleteCart(Integer cartId);

    void deleteAllByCustomerId(Integer customerId);

    List<Carts> findAllByCustomerId(Integer customerId);


}
