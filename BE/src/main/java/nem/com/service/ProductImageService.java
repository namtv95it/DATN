package nem.com.service;

import nem.com.entity.ProductImages;

import java.util.List;

public interface ProductImageService {
    ProductImages getOne(Integer id);
    List<ProductImages> getAll();
    ProductImages save(ProductImages productImages);

    List<ProductImages> getProductsImagesById(Integer id);
    void delete(Integer id);

    List<ProductImages> findByProId( Integer id ) ;
}
