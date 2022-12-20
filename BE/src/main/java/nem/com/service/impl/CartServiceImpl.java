package nem.com.service.impl;

import lombok.AllArgsConstructor;
import nem.com.entity.Carts;
import nem.com.exception.LimitQuantityException;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.CartsRepository;
import nem.com.repository.CustomersRepository;
import nem.com.repository.ProductsDetailsRepository;
import nem.com.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartsRepository cartsRepository;
    private final CustomersRepository customersRepository;
    private final ProductsDetailsRepository productsDetailsRepository;

    @Override
    public Carts addToCart(Carts request) {

        this.customersRepository.findById(request.getCustomer().getId()).orElseThrow(() -> {
            throw new ResourceNotFoundException("Id customer not found " + request.getCustomer().getId());
        });

        this.productsDetailsRepository.findById(request.getProductsDetail().getId()).orElseThrow(() -> {
            throw new ResourceNotFoundException("Id product detail not found  " + request.getProductsDetail().getId());
        });

        //Check so luong trong kho

        Carts cartExist = this.cartsRepository.findCartsByProductDetailAndCustomer(
                request.getProductsDetail().getId(),
                request.getCustomer().getId());

        if (cartExist == null) {
            cartExist = new Carts();
            cartExist.setQuantity(request.getQuantity());
            cartExist.setCustomer(request.getCustomer());
            cartExist.setProductsDetail(request.getProductsDetail());
        } else {
            cartExist.setQuantity(request.getQuantity() + cartExist.getQuantity());
        }

        return cartsRepository.save(cartExist);
    }

    @Override
    public Carts updateCart(Carts request) {

//        if (request.getQuantity() > request.getProductsDetail().getQuantity()) {
//            throw new LimitQuantityException("Số lượng không đủ. Vui lòng cập nhật lại số lượng trong giỏ hàng !");
//        }

        Carts cartExist = this.cartsRepository.findCartsByProductDetailAndCustomer(
                request.getProductsDetail().getId(),
                request.getCustomer().getId());
        cartExist.setQuantity(request.getQuantity());
        return cartsRepository.save(cartExist);
    }

    @Override
    public void deleteCart(Integer cartId) {
        this.cartsRepository.deleteById(cartId);
    }

    @Override
    public void deleteAllByCustomerId(Integer customerId) {
        this.customersRepository.findById(customerId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Id customer not found " + customerId);
        });
        this.cartsRepository.deleteAllByCustomerId(customerId);
    }

    @Override
    public List<Carts> findAllByCustomerId(Integer customerId) {
        this.customersRepository.findById(customerId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Id customer not found " + customerId);
        });
        return this.cartsRepository.findCartsByCustomerId(customerId);
    }
}
