package nem.com.repository;

import nem.com.domain.response.ColorDTO;
import nem.com.entity.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ColorsRepository extends JpaRepository<Colors, Integer> {
    @Query("select c from Colors c where c.id in (select p.color.id from  ProductsDetails p where p.product.id = :productId and p.quantity > 0)")
    List<Colors> findAllColorInProductDetails(@Param("productId") Integer productId);

    @Query("select new ColorDTO ( c.id , c.code , c.name , sum(p.quantity) )" +
            "from Colors c left join ProductsDetails p on c.id = p.color.id group by c.id , c.code , c.name ")
    List<ColorDTO> getColors() ;
}
