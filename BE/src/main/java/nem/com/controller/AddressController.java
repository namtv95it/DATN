package nem.com.controller;

import nem.com.entity.Address;
import nem.com.service.AddressService;
import nem.com.service.impl.AddressServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("")
    public ResponseEntity<List<Address>> getAll() {
        return new ResponseEntity<>(this.addressService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<List<Address>> findAddressByCustomerId(@PathVariable("customerId") Integer customerId) {
        return new ResponseEntity<>(this.addressService.findAddressByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("address/{id}")
    public ResponseEntity<Address> getOne(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.addressService.getOne(id), HttpStatus.OK);
    }

    @GetMapping("status/{customerId}")
    public ResponseEntity<Address> findAddressByStatus(@PathVariable("customerId") Integer customerId) {
        return new ResponseEntity<>(this.addressService.findAddressByStatus(customerId), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Address> update(@RequestBody Address address) {
        return new ResponseEntity<>(this.addressService.save(address), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Address> create(@RequestBody Address address) {
        return new ResponseEntity<>(this.addressService.save(address), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        this.addressService.delete(id);
    }
}
