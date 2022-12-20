package nem.com.repository;

import nem.com.domain.response.RatingAvgDTO;
import nem.com.domain.response.RatingProductDTO;
import nem.com.entity.Ratings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    @Query(value = "select * from ratings r where  r.order_id in ( select o.id from orders o where o.customer_id = :id and o.status = :status )" ,nativeQuery = true)
    List<Ratings> getAllRatingByIdCustome(@Param("id") Integer id , @Param("status") Short status ) ;

    @Query(value = "select * from ratings r where  r.status = 0" , nativeQuery = true)
    List<Ratings> getAllRatingsWithStatus( ) ;

    @Query("select new RatingAvgDTO(r.product.id , floor(avg(r.rating) ) ) from Ratings r group by r.product.id")
    List<RatingAvgDTO> getAvgRating() ;

    @Query("select new RatingProductDTO( r.id , r.content , r.rating , r.orders.customer.fullname , r.createDate ) from Ratings r where r.product.id = :idPro and r.status = 1")
    Page<RatingProductDTO> getRatingProduct(@Param("idPro") Integer idPro , Pageable pageable ) ;


    @Transactional
    @Modifying
    @Query(value = "update ratings set status = 1 where id in (:idRating)" , nativeQuery = true)
    void UpdateStatusRating( @Param("idRating") Long[] id ) ;

    @Transactional
    @Modifying
    @Query(value = "delete from ratings where id in (:idRating) ;" , nativeQuery = true)
    void deleteRating( @Param("idRating") Long[] idRating ) ;
}
