package nem.com.service.impl;

import nem.com.entity.Employees;
import nem.com.exception.UniqueFieldException;
import nem.com.repository.EmployeesRepository;
import nem.com.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeesRepository employeesRepository ;
    @Autowired
    private PasswordEncoder passwordEncoder ;

    public EmployeeServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public List<Employees> findAll() {
        return employeesRepository.findAll() ;
    }

    @Override
    public Employees create(Employees employees) {
        Employees employeesByEmail = (Employees) employeesRepository.findByEmail( employees.getEmail() );
        Employees employeesByPhoneNumber = (Employees) employeesRepository.findByPhoneNumber( employees.getPhone() );

        if( employeesByEmail != null ){
            throw  new UniqueFieldException("Nhân viên có email " + employees.getEmail() + " đã tồn tại !");
        }

        if( employeesByPhoneNumber != null  ){
            throw new UniqueFieldException("Nhân viên có số điện thoại "+ employees.getPhone() + " đã tồn tại !" );
        }

        employees.setPassword(passwordEncoder.encode(employees.getPassword()) );
        employees.setSiginDate( new Timestamp( System.currentTimeMillis() ) );
        return employeesRepository.save(employees) ;
    }

    @Override
    public Employees update(Employees employees) {
        return employeesRepository.save(employees) ;
    }

    @Override
    public boolean exitById(Integer id) {
        if( employeesRepository.existsById( id) ){
            return true ;
        }

        return false;
    }

    @Override
    public Employees updateStatus(Employees employees) {
        if( employees.getStatus() == 1 ){
            employees.setStatus((short) 2);
        }else {
            employees.setStatus((short) 1);
        }
        return employeesRepository.save(employees);
    }

    @Override
    public Employees findById(Integer id) {
        return employeesRepository.findById( id ).get();
    }

    public Employees findEmployeeByEmail(String email) {
        return this.employeesRepository.findByEmail(email);
    }

}
