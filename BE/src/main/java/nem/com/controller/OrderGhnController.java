package nem.com.controller;

import lombok.AllArgsConstructor;
import nem.com.domain.response.OrderGhnResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("${api.ghn.order}")
@AllArgsConstructor
public class OrderGhnController {

    @PostMapping("/create")
    public ResponseEntity<OrderGhnResponse> create(){
        return null;
    }
}
