package nem.com.repository;

import nem.com.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    @Query("select od from OrderDetails od where od.order.id = :orderId")
    List<OrderDetails> findByOrderId(Long orderId);

    @Modifying
    @Query("delete from OrderDetails od where od.order.id = :id")
    void deleteByOrderId(Long id);

    @Query("select od from OrderDetails od where od.order.customer.id = :id")
    List<OrderDetails> getOrderDetailsById(@Param("id") Integer id ) ;

    @Query("select od from OrderDetails od where od.order.id = :idOrder and od.order.customer.id = :idCus")
    List<OrderDetails> getOrderDetailsByByIdOrder( @Param("idOrder") Long idOrder , @Param("idCus") Integer idCus ) ;

    @Query("select od from OrderDetails  od where od.order.id = :id")
    List<OrderDetails> getOrderDetailsByOrder( @Param("id") Long id );

    @Query("select od from OrderDetails  od where od.order.id = :id and od.exchanges.id is not null")
    List<OrderDetails> getOrderDetailsInExchange( @Param("id") Long id );

    @Query("select count(od) from OrderDetails od where od.order.id = :id and od.status <> 1")
    Long countExchangeInOrderDetail(@Param("id") Long id);
}
