package nem.com.repository;

import nem.com.entity.Exchanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangesRepository extends JpaRepository<Exchanges, Integer> {
    @Query("select distinct e from Exchanges e " +
            "join OrderDetails od on e.id = od.exchanges.id " +
            "join Orders o on o.id = od.order.id " +
            "where o.id = :id")
    Exchanges findByOrderId(Long id);
}
