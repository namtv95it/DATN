package nem.com.repository;

import nem.com.entity.ProductsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ProductsDetailsRepository extends JpaRepository<ProductsDetails, Integer>,ProductDetailsCustomRepository {
    @Query("SELECT pd FROM ProductsDetails pd WHERE pd.product.id = :id")
    public List<ProductsDetails> getByIdProduct(@Param("id") Integer id);

    @Query("SELECT pd FROM ProductsDetails pd WHERE pd.barCode = :barcode ")
    public ProductsDetails getByBarCode(@Param("barcode") String barcode);

    @Query("select p from ProductsDetails p where  p.product.id = :id")
    List<ProductsDetails> findProductsDetailsByProductId(@Param("id") Integer productId);

    @Query("SELECT p FROM ProductsDetails p WHERE p.product.id = :productId AND p.color.id = :colorId AND p.size.id = :sizeId")
    List<ProductsDetails> findProductDetailByProductSizeColor(Integer productId, Integer colorId, Integer sizeId);

    @Query("select p from ProductsDetails p where p.product.id = :productId and p.size.id = :sizeId and p.color.id = :colorId")
    ProductsDetails findProductDetailBySizeAndColor(@Param("productId") Integer productId, @Param("sizeId") Integer sizeId, @Param("colorId") Integer colorId);

    @Transactional
    @Modifying
    @Query("update ProductsDetails p set p.quantity = :quantity where p.id = :id ")
    void updateSoLuong( @Param("quantity") Integer quantity , @Param("id") Integer id ) ;

}
