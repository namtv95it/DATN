package nem.com.service.impl;

import nem.com.dto.request.SearchProByCateDTO;
import nem.com.entity.Products;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.ProductsRepository;
import nem.com.service.ProductDetailService;
import nem.com.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;

    private final ProductDetailService productDetailService;

    public ProductServiceImpl(ProductsRepository productsRepository, ProductDetailService productDetailService) {
        this.productsRepository = productsRepository;
        this.productDetailService = productDetailService;
    }

    @Override
    public Products getOne(Integer id) {
        return this.productsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng có mã: " + id));
    }

    @Override
    public List<Products> getAll() {
        return this.productsRepository.findAll();
    }

    @Override
    public Page<Products> getAllByAllPropertites(Integer[] size, Short[] category, Integer[] color, Double min, Double max, Pageable pageable) {
        return this.productsRepository.findProByAllProperty( size , category , color , min , max , pageable );
    }

    @Override
    public List<Products> findTop10Pro() {
        return this.productsRepository.findTop10Product();
    }

    @Override
    public List<Products> findProductNeverRating(Long id) {
        return this.productsRepository.findProductNeverRating( id) ;
    }

    @Override
    public List<Products> getProductsSimilar(Short id) {
        return this.productsRepository.getProductByCategory(id);
    }

    @Override
    public List<Products> getAllNewPro() {
        return this.productsRepository.findTop10NewProduct() ;
    }

    @Override
    public Products save(Products products) {
        System.out.println(products.getCreateDate());
        return this.productsRepository.save(products);
    }

    @Override
    public Products update(Products products) {
        return this.productsRepository.save(products);
    }

    @Override
    public void delete(Integer id) {
        Products products = getOne(id);
        products.setStatus(0);
        this.productsRepository.save(products);
    }
    @Override
    public List<Products> findByCate(Short id){
        return productsRepository.findByCate(id);
    }

    @Override
    public List<Products> findProductByCate(SearchProByCateDTO request) {
        return this.productsRepository.findProByCate( request.getCategory() , request.getStartPrice() , request.getEndPrice() );
    }
}
