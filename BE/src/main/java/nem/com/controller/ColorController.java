package nem.com.controller;

import nem.com.domain.response.ColorDTO;
import nem.com.entity.Colors;
import nem.com.service.ColorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @PostMapping("")
    public ResponseEntity<Colors> create(@RequestBody Colors colors) {
        colors.setStatus(1);
        return new ResponseEntity<>(this.colorService.save(colors), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ColorDTO>> getAll() {
        return new ResponseEntity<>(this.colorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colors> getOne(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.colorService.getOne(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colors> remove(@RequestBody Colors colors) {
        colors.setStatus(0);
        return new ResponseEntity<>(this.colorService.save(colors), HttpStatus.OK);
    }

    @GetMapping("productId/{id}")
    public ResponseEntity<List<Colors>> findAllColorInProductDetails(@PathVariable("id") Integer productId) {
        return new ResponseEntity<>(this.colorService.findAllColorInProductDetails(productId), HttpStatus.OK);
    }
}
