package nem.com.controller;

import lombok.AllArgsConstructor;
import nem.com.entity.Contacts;
import nem.com.service.ContactService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/contact")
@AllArgsConstructor
public class ContactController {

    ContactService contactService;

    @GetMapping("")
    public ResponseEntity<Contacts> getDefaultContact(){
        return new ResponseEntity<>(this.contactService.getDefaultContact(), HttpStatus.OK);
    }
}
