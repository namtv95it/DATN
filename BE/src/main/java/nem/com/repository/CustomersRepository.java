package nem.com.repository;

import nem.com.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customers, Integer> {

    Optional<Customers> findCustomersByPhone(String phone);

    Optional<Customers> findCustomersByEmail(String email);

    Optional<Customers> findByResetPasswordToken(String resetPasswordToken);

    @Modifying
    @Transactional
    @Query("update Customers c set c.status = 1 where c.id in (:id)")
    void updateAllStatusTrue(List<Integer> id);

    @Modifying
    @Transactional
    @Query("update Customers c set c.status = 0 where c.id in (:id)")
    void updateAllStatusFalse(List<Integer> id);

    @Query("SELECT c FROM Customers  c where c.id > 0")
    List<Customers> fillAllCus();

    @Query("SELECT c FROM Customers  c where c.id > 0 and c.status = 1")
    List<Customers> fillAllByStatus();
}