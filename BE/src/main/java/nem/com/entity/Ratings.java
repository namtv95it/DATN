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
public class Ratings {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "rating", nullable = true)
    private Short rating;

    @Basic
    @Column(name = "content", nullable = true, length = 255)
    private String content;

    @Basic
    @Column(name = "status", nullable = true)
    private Short status;

    @Basic
    @Column(name = "create_date" , nullable = true )
    private Date createDate ;

    @JsonIgnore
    @OneToMany(mappedBy = "rating")
    private List<RatingImages> listRatingImages;

    @ManyToOne
    @JoinColumn(name = "order_id" )
    private Orders orders ;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Employees employee;
}
