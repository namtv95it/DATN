package nem.com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "province_id", nullable = true)
    private Integer provinceId;

    @Basic
    @Column(name = "province_name", nullable = true, length = 50)
    private String provinceName;

    @Basic
    @Column(name = "district_id", nullable = true)
    private Integer districtId;

    @Basic
    @Column(name = "district_name", nullable = true, length = 50)
    private String districtName;

    @Basic
    @Column(name = "ward_id", nullable = true, length = 50)
    private String wardId;

    @Basic
    @Column(name = "ward_name", nullable = true, length = 50)
    private String wardName;

    @Basic
    @Column(name = "fullname", nullable = true, length = 255)
    private String fullname;

    @Basic
    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    @Basic
    @Column(name = "other", nullable = true, length = 255)
    private String other;

    @Basic
    @Column(name = "status", nullable = true)
    private Byte status;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customers customer;

}

