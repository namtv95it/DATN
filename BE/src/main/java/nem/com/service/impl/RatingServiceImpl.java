package nem.com.service.impl;
import nem.com.domain.response.RatingAvgDTO;
import nem.com.domain.response.RatingProductDTO;
import nem.com.entity.Ratings;
import nem.com.repository.RatingImagesRepository;
import nem.com.repository.RatingsRepository;
import nem.com.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingsRepository ratingsRepository ;
    private final RatingImagesRepository ratingImagesRepository ;

    public RatingServiceImpl( RatingsRepository ratingsRepository , RatingImagesRepository ratingImagesRepository ){
        this.ratingsRepository = ratingsRepository ;
        this.ratingImagesRepository =ratingImagesRepository ;
    }

    @Override
    public List<Ratings> getAllRatingByCustom(Integer id , Short status ) {
        return this.ratingsRepository.getAllRatingByIdCustome( id , status );
    }

    @Override
    public Ratings createRating(Ratings ratings) {
        return this.ratingsRepository.save(ratings);
    }

    @Override
    public List<RatingAvgDTO> getAvgRating() {
        return this.ratingsRepository.getAvgRating();
    }

    @Override
    public Page<RatingProductDTO> getRatingPro(Integer id , Pageable pageable ) {
        return this.ratingsRepository.getRatingProduct(id , pageable );
    }

    @Override
    public List<Ratings> getAllRatingWithStatus() {
        return this.ratingsRepository.getAllRatingsWithStatus() ;
    }

    @Override
    public void UpdateStatusOrder(Long[] id) {
        this.ratingsRepository.UpdateStatusRating(id);
    }

    @Override
    public void deleteRating(Long[] id) {
        this.ratingImagesRepository.deleteRatingImg(id);
        for( Long r: id ){
            Ratings ratings = this.ratingsRepository.findById(r).get();
            ratings.setContent("");
            ratings.setStatus((short) 1);
            this.ratingsRepository.save(ratings);
        }
    }
}
