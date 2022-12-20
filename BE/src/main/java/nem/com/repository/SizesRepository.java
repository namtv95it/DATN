package nem.com.repository;

import nem.com.domain.response.SizeDTO;
import nem.com.entity.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizesRepository extends JpaRepository<Sizes, Integer> {
    @Query("select  s from Sizes s where s.id in (select p.size.id from  ProductsDetails p where p.product.id = :productId and p.quantity > 0) order by s.id asc ")
    List<Sizes> findAllSizeInProductDetails(@Param("productId") Integer productId);

    @Query("select new SizeDTO(s.id , s.code , sum(p.quantity)) from Sizes s left join ProductsDetails p on s.id = p.size.id group by s.id , s.code ")
    List<SizeDTO> getSizes() ;
}
