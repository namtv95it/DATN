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
public class Categories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Basic
    @Column(name = "image", nullable = true, length = 255)
    private String image;

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
    @OneToMany(mappedBy = "category")
    private List<Products> listProducts;

}
