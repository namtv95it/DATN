package nem.com.service.impl;

import nem.com.entity.RatingImages;
import nem.com.repository.RatingImagesRepository;
import nem.com.repository.RatingsRepository;
import nem.com.service.RatingImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingImageServiceImpl implements RatingImageService {
    private final RatingImagesRepository repository ;

    public RatingImageServiceImpl( RatingImagesRepository repository ){
        this.repository = repository ;
    }

    @Override
    public RatingImages createRatingImage(RatingImages ratingImages) {
        return this.repository.save(ratingImages);
    }

    @Override
    public List<RatingImages> getAll() {
        return this.repository.findAll();
    }

    @Override
    public List<RatingImages> getRatingImgByIdRating(Long[] id) {
        return this.repository.getRatingImgByIdRating(id);
    }

    @Override
    public void deleteRatingImage(Long[] id) {
        this.repository.deleteRatingImg(id); ;
    }
}
