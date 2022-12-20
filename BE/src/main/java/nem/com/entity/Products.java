package nem.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Products {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    private Double price;

    @Column(name = "discount" , nullable = true)
    private Integer discount ;

    @Basic
    @Column(name = "weight", nullable = true, precision = 0)
    private Double weight;

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Basic
    @Column(name = "thumnail", nullable = true, length = 255)
    private String thumnail;

    @Basic
    @Column(name = "create_date", nullable = true)
    private Date createDate;

    @Basic
    @Column(name = "update_date", nullable = true)
    private Date updateDate;

    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductImages> listProductImages;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Categories category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductsDetails> listProductDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Ratings> listRatings;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductDiscount> listProductDiscounts;
}
