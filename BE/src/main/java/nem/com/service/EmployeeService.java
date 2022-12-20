package nem.com.service;

import nem.com.entity.Employees;

import java.util.List;

public interface EmployeeService {
    List<Employees> findAll() ;

    Employees create(Employees employees ) ;

    Employees update( Employees employees ) ;

    boolean exitById( Integer id ) ;

    Employees updateStatus( Employees employees ) ;

    Employees findById( Integer id ) ;

    Employees findEmployeeByEmail(String email);

}
