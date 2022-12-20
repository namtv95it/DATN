package nem.com.repository;

import nem.com.dto.response.BuyMostProductDTO;
import nem.com.dto.response.CustomerBuyMostProductDTO;
import nem.com.dto.response.OverviewStatisticalDTO;
import nem.com.dto.response.TurnoverDTO;
import nem.com.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("select distinct o from Orders o " +
            "join OrderDetails od on o.id = od.order.id " +
            "join Exchanges e on e.id = od.exchanges.id ")
    List<Orders> findAllOrderExchange();

    @Query("select distinct o from Orders o " +
            "join OrderDetails od on o.id = od.order.id " +
            "join Exchanges e on e.id = od.exchanges.id " +
            "order by e.status asc ")
    Page<Orders> findOrderExchange(Pageable pageable);

    @Query("select o from Orders o where  o.status = :status and o.customer.id = :id order by o.createDate desc ")
    List<Orders> getAllOrderByStatus(@Param("status") Short status  , @Param("id") Integer id ) ;

    @Query("select o from Orders o where o.customer.id = :id order by o.createDate desc")
    List<Orders> getAllOrders( @Param("id") Integer id );

    @Query("select o from Orders o order by o.createDate desc")
    Page<Orders> getAllOrderSort(Pageable pageable);

    Page<Orders> findByStatusOrderByCreateDateDesc(Integer status, Pageable pageable);

    @Query("select o from Orders o " +
            "where (:fullname is null or :fullname like '' or o.customer.fullname like concat('%',:fullname,'%')) " +
            "and (:id is null or o.id = :id) " +
            "and (:orderCode is null or :orderCode like '' or o.orderCode like concat('%',:orderCode,'%')) " +
            "and o.status = :status")
    Page<Orders> searchOrderByStatus(String fullname, Long id, String orderCode, Integer status, Pageable pageable);

    @Query("select o from Orders o " +
            "where (:fullName is null or :fullName like '' or o.customer.fullname like concat('%',:fullName,'%')) " +
            "and (:id is null or o.id = :id) " +
            "and (:orderCode is null or :orderCode like '' or o.orderCode like concat('%',:orderCode,'%')) ")
    Page<Orders> searchAllOrder(String fullName, Long id, String orderCode, Pageable pageable);
    List<Orders> findByStatus(Integer status);

    @Query("Update Orders o set o.status = :status where o.id = :id ")
    @Modifying
    @Transactional
    void updateStatusOrder( @Param("status") Integer status , @Param("id") Long id ) ;

//  Thống kê khách mua hàng nhiều nhất

    @Query(name = "CustomerBuyMostProductDTO" , nativeQuery = true)
    List<CustomerBuyMostProductDTO> CustomerBuyMostProduct( @Param("startDate") Date startDate , @Param("endDate") Date endDate ) ;

    @Query(name = "overview_statical" , nativeQuery = true )
    OverviewStatisticalDTO getOverview() ;

    @Query(name = "BuyMostProductDTO" , nativeQuery = true )
    List<BuyMostProductDTO> buyMostProductDTO(@Param("startDate") Date startDate , @Param("endDate") Date endDate ) ;

    @Query(name = "TurnoverDTO" , nativeQuery = true )
    List<TurnoverDTO> turnoverDTO(@Param("startDate") Date startDate , @Param("endDate") Date endDate , @Param("type") String type  ) ;
    
    @Query("SELECT o from Orders o where o.orderCode is not null and o.orderCode <> '' and o.status not in (0,1)")
    List<Orders> getOrderGhn();

    @Query("SELECT o from Orders o where o.orderCode is not null and o.orderCode <> '' and o.status = 2")
    List<Orders> getOrderGhnByStatus();
}
