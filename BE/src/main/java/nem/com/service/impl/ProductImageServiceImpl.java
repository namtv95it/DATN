package nem.com.service.impl;

import nem.com.entity.ProductImages;
import nem.com.entity.Products;
import nem.com.repository.ProductImagesRepository;
import nem.com.repository.ProductsRepository;
import nem.com.service.ProductImageService;
import nem.com.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImagesRepository productImagesRepository;

    public ProductImageServiceImpl(ProductImagesRepository productImagesRepository) {
        this.productImagesRepository = productImagesRepository;
    }

    @Override
    public ProductImages getOne(Integer id) {
        return this.productImagesRepository.findById(id).get();
    }

    @Override
    public List<ProductImages> getAll() {
        return this.productImagesRepository.findAll();
    }

    @Override
    public ProductImages save(ProductImages productImages) {
        return this.productImagesRepository.save(productImages);
    }

    @Override
    public List<ProductImages> getProductsImagesById(Integer id) {
        return this.productImagesRepository.getProductsImagesById(id);
    }

    @Override
    public void delete(Integer id) {
        ProductImages productImages = getOne(id);
        this.productImagesRepository.delete(productImages);
    }

    @Override
    public List<ProductImages> findByProId(Integer id) {
        return this.productImagesRepository.getProductsImagesById( id ) ;
    }
}
