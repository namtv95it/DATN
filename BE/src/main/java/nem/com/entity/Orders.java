package nem.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "create_date", nullable = true)
    private Date createDate;

    @Basic
    @Column(name = "shipped_date", nullable = true)
    private Date shippedDate;

    @Basic
    @Column(name = "update_date", nullable = true)
    private Date updatedDate;

    @Basic
    @Column(name = "update_name", nullable = true)
    private String updateName;

    @Basic
    @Column(name = "freight", nullable = true, precision = 0)
    private Double freight;

    @Basic
    @Column(name = "order_code", nullable = true, length = 255)
    private String orderCode;

    @Basic
    @Column(name = "ship_name", nullable = true, length = 255)
    private String shipName;

    @Basic
    @Column(name = "ship_address", nullable = true, length = 255)
    private String shipAddress;

    @Basic
    @Column(name = "ship_phone", nullable = true, length = 255)
    private String shipPhone;

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    private String note;

    @Basic
    @Column(name = "total", nullable = true, precision = 0)
    private Double total;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    @Basic
    @Column(name = "discount", nullable = true, precision = 0)
    private Double discount;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> listOrderDetails;

    @JsonIgnore
    @OneToMany( mappedBy = "orders")
    private List<Ratings> listRating ;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employees employee;
}
