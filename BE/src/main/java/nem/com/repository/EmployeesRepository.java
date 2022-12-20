package nem.com.repository;

import nem.com.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    @Query( "select em from Employees em where em.email like :email ")
    Employees findByEmail(@Param("email") String email ) ;

    @Query( "select em from Employees em where em.phone like :phone ")
    Employees findByPhoneNumber(@Param("phone") String phone ) ;
}