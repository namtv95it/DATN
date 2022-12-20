package nem.com.repository;

import nem.com.entity.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface DiscountsRepository extends JpaRepository<Discounts , Integer> {

    @Transactional
    @Modifying
    @Query("update Products p set p.discount = :discount where p.id in (:id)")
    void updateDiscountProduct(@Param("discount") Integer discount , @Param("id") Integer[] id );

    @Query("select d from Discounts d where d.status = 1 ")
    List<Discounts> findDiscountsByStatus() ;

    @Query("select d from Discounts d where (:name is null or d.discountName like concat('%' , :name , '%')) and" +
            " (:startDate is null or d.startDate >= :startDate ) and (:endDate is null or d.startDate <= :endDate) and" +
            " (:status is null or d.status = :status)")
    List<Discounts> findAll(@Param("name") String name , @Param("startDate")Date startDate ,
                            @Param("endDate") Date endDate , @Param("status") Integer status ) ;
}
