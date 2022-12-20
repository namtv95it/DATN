package nem.com.repository;

import nem.com.entity.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount , Integer> {

    @Query("select pd from ProductDiscount pd where pd.product.id = :idPro and pd.discount.id = :idDis")
    ProductDiscount findById(@Param("idPro") Integer idPro , @Param("idDis") Integer idDis) ;

    @Query("select pd from ProductDiscount  pd where pd.discount.id = :id")
    List<ProductDiscount> findAllPd( @Param("id") Integer id) ;

    @Modifying
    @Transactional
    @Query("delete from ProductDiscount pd where pd.product.id = :proId and pd.discount.id = :disId")
    void deleteProductDis( @Param("proId") Integer proId , @Param("disId") Integer disId )  ;

}
