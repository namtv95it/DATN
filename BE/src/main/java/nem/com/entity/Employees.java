package nem.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employees {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    private String password;

    @Basic
    @Column(name = "fullname", nullable = true, length = 255)
    private String fullname;

    @Basic
    @Column(name = "birth_date", nullable = true)
    private Timestamp birthDate;

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    private String address;

    @Basic
    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @Basic
    @Column(name = "sigin_date", nullable = true)
    private Timestamp siginDate;

    @Basic
    @Column(name = "photo", nullable = true, length = 255)
    private String photo;

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    private String note;

    @Basic
    @Column(name = "status", nullable = true)
    private Short status;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Orders> listOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<Ratings> listRatings;
}
