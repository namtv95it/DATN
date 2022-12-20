package nem.com.controller;

import nem.com.entity.ExchangeImages;
import nem.com.entity.Exchanges;
import nem.com.repository.ExchangeImagesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/exchange-image")
public class ExchangeImageController {

    private final ExchangeImagesRepository exchangeImagesRepository;

    public ExchangeImageController(ExchangeImagesRepository exchangesRepository) {
        this.exchangeImagesRepository = exchangesRepository;
    }


    @PostMapping
    public ResponseEntity<ExchangeImages> save(@RequestBody ExchangeImages request) {
        return new ResponseEntity<>(this.exchangeImagesRepository.save(request), HttpStatus.OK);
    }
}
