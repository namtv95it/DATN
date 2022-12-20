package nem.com.controller;

import nem.com.entity.RatingImages;
import nem.com.service.impl.RatingImageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/rating-images")
public class RatingImageController {
    private final RatingImageServiceImpl ratingImageService ;

    public RatingImageController( RatingImageServiceImpl ratingImageService ){
        this.ratingImageService = ratingImageService ;
    }

    @PostMapping("")
    public ResponseEntity<RatingImages> createRatingImage(@RequestBody RatingImages ratingImages ){
        return ResponseEntity.ok( this.ratingImageService.createRatingImage( ratingImages) );
    }

    @GetMapping("")
    public ResponseEntity<List<RatingImages>> getAllRating(){
        return ResponseEntity.ok(this.ratingImageService.getAll() ) ;
    }

    @GetMapping("getRatingImg")
    public ResponseEntity<List<RatingImages>> getRatingImgByIdRating( @RequestParam("rateImg") Long[] id ){
        return ResponseEntity.ok( this.ratingImageService.getRatingImgByIdRating(id) );
    }

}
