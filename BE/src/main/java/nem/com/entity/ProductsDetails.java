package nem.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products_details", schema = "nem")
public class ProductsDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @JsonIgnore
    @OneToMany(mappedBy = "productsDetail")
    private List<OrderDetails> listOrderDetails;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Colors color;

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Sizes size;

    @JsonIgnore
    @OneToMany(mappedBy = "productsDetail")
    private List<Carts> listCarts;

    @Basic
    @Column(name = "bar_code", length = 15)
    private String barCode;
}
