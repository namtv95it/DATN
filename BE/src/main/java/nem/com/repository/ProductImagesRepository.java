package nem.com.repository;

import nem.com.entity.ProductImages;
import nem.com.entity.ProductsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {
    @Query("SELECT p FROM ProductImages p WHERE p.product.id = :id")
    List<ProductImages> getProductsImagesById(Integer id);
}

