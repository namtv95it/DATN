package nem.com.repository;

import nem.com.entity.RatingImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RatingImagesRepository extends JpaRepository<RatingImages, Integer> {

    @Query("select r from RatingImages  r where r.rating.id in (:id)")
    List<RatingImages> getRatingImgByIdRating(@Param("id") Long[] id ) ;

    @Transactional
    @Modifying
    @Query(value = "delete from rating_images where rating_id in (:idRating)" , nativeQuery = true)
    void deleteRatingImg( @Param("idRating") Long[] id ) ;
}