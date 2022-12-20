package nem.com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unitprice", nullable = true, precision = 0)
    private Double unitprice;

    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Basic
    @Column(name = "update_name", nullable = true)
    private String updateName;

    @Basic
    @Column(name = "update_date", nullable = true)
    private Date updatedDate;

    @Basic
    @Column(name = "rating_status", nullable = true)
    private Short ratingStatus;

    @Basic
    @Column(name = "discount", nullable = true, precision = 0)
    private Double discount;

    @Basic
    @Column(name = "note", nullable = true)
    private String note;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "exchange_id", referencedColumnName = "id")
    private Exchanges exchanges;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id")
    private ProductsDetails productsDetail;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders order;
}
