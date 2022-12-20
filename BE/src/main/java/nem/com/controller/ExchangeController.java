package nem.com.controller;

import nem.com.repository.ExchangesRepository;
import lombok.RequiredArgsConstructor;
import nem.com.domain.dto.ExchangeDTO;
import nem.com.entity.Exchanges;
import nem.com.service.ExchangeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final ExchangesRepository exchangesRepository;

    @PostMapping
    public ResponseEntity<Exchanges> save(@RequestBody Exchanges request) {
        return new ResponseEntity<>(this.exchangesRepository.save(request), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ExchangeDTO> findByOrderId(@PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(this.exchangeService.findByOrderId(orderId), HttpStatus.OK);
    }

    @PutMapping("/update/{status}")
    public ResponseEntity<Exchanges> updateStatus(
            @PathVariable("status") short status, @RequestBody Exchanges exchanges
            ){
        Exchanges exchanges1 = this.exchangesRepository.findById(exchanges.getId()).get();
        exchanges1.setNote(exchanges.getNote());
        exchanges1.setStatus(status);
        exchanges1.setTotal(exchanges.getTotal());
        exchanges1.setNote(exchanges.getNote());
        return new ResponseEntity<>(this.exchangesRepository.save(exchanges1),HttpStatus.OK);
    }
}
