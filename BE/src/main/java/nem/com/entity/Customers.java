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
public class Customers {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "email", nullable = true, length = 255)
    private String email;

//    @JsonIgnore
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
    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @Basic
    @Column(name = "sigin_date", nullable = true)
    private Timestamp siginDate;

    @Basic
    @Column(name = "photo", nullable = true, length = 255)
    private String photo;

    @Basic
    @Column(name = "status", nullable = true)
    private Short status;

    @Basic
    @Column(name = "reset_password_token", nullable = true)
    private String resetPasswordToken;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Address> listAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Orders> listOrders;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Roles role;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Carts> listCarts;
}
