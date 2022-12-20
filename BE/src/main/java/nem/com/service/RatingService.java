package nem.com.service;

import nem.com.domain.response.RatingAvgDTO;
import nem.com.domain.response.RatingProductDTO;
import nem.com.entity.Ratings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RatingService {

    List<Ratings> getAllRatingByCustom( Integer id , Short status ) ;

    Ratings createRating( Ratings ratings ) ;

    List<RatingAvgDTO> getAvgRating() ;

    Page<RatingProductDTO> getRatingPro(Integer id , Pageable pageable) ;

    List<Ratings> getAllRatingWithStatus() ;

    void UpdateStatusOrder( Long[] id ) ;

    void deleteRating( Long[] id ) ;
}
