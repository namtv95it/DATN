package nem.com.domain.response;

import lombok.Getter;
import lombok.Setter;
import nem.com.entity.Customers;
import nem.com.entity.Employees;
import nem.com.entity.OrderDetails;
import nem.com.entity.Ratings;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDTOResponse {
    private Long id;

    private Date createDate;

    private Date shippedDate;

    private Date updatedDate;

    private String updateName;

    private Double freight;

    private String orderCode;

    private String shipName;

    private String shipAddress;

    private String shipPhone;

    private String note;

    private Double total;

    private Integer status;

    private Double discount;

    private List<OrderDetails> listOrderDetails;

    private List<Ratings> listRating;

    private Customers customer;

    private Employees employee;

    private Integer checkExchange;
}
