package nem.com.controller;

import nem.com.entity.Employees;
import nem.com.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService ;

    public EmployeeController( EmployeeService employeeService ) {
        this.employeeService = employeeService ;
    }

    @GetMapping("")
    public ResponseEntity<List<Employees>>  findAll() {
        return new ResponseEntity<>(employeeService.findAll() , HttpStatus.OK )  ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> findById( @PathVariable("id") Integer id  ){
        return new ResponseEntity<>( employeeService.findById(id) , HttpStatus.OK ) ;
    }

    @PostMapping("")
    public ResponseEntity<Employees> create(@RequestBody Employees employees ) {
        return new ResponseEntity<>( employeeService.create(employees) , HttpStatus.CREATED );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employees> update( @PathVariable("id") Integer id , @RequestBody Employees employees ) {
        if(  !employeeService.exitById( id)){
            return ResponseEntity.notFound().build() ;
        }
        return new ResponseEntity<>( employeeService.update(employees) , HttpStatus.OK );
    }

    @PutMapping("status/{id}")
    public ResponseEntity<Employees> updateStatus( @PathVariable("id") Integer id , @RequestBody Employees employees ) {

        return new ResponseEntity<>( employeeService.updateStatus(employees) , HttpStatus.OK );
    }
}
