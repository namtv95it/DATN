package nem.com.repository;

import nem.com.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("select a from Address a where a.customer.id = :customerId")
    List<Address> findAddressByCustomerId(@Param("customerId") Integer customerId);

    @Query("select a from Address a where a.customer.id = :customerId and a.status = 1")
    Address findAddressByStatus(@Param("customerId") Integer customerId);

}