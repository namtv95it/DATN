package nem.com.controller;

import lombok.AllArgsConstructor;
import nem.com.domain.response.CategoryDTO;
import nem.com.entity.Categories;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.CategoriesRepository;
import nem.com.service.CategoryService;
import nem.com.service.impl.CategoryServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoriesRepository categoriesRepository;

    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return new ResponseEntity<>(this.categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("findAll")
    public ResponseEntity<List<Categories>> findAll() {
        return new ResponseEntity<>(this.categoriesRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Categories>> findByStatus() {
        return new ResponseEntity<>(this.categoriesRepository.findAllByStatus(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Categories> create(@RequestBody Categories categories) {
        categories.setCreateDate(new Date());
        return new ResponseEntity<>(this.categoryService.save(categories), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Categories> update(@RequestBody Categories categories) {
        Categories cateExist = this.categoriesRepository.findById(categories.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Id not found " + categories.getId()));
        categories.setCreateDate(cateExist.getCreateDate());
        categories.setUpdateDate(new Date());
        return new ResponseEntity<>(this.categoryService.save(categories), HttpStatus.OK);
    }
}
