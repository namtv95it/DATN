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
public class Contacts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Basic
    @Column(name = "phone", nullable = true, length = 255)
    private String phone;

    @Basic
    @Column(name = "ward_code", nullable = true, length = 255)
    private String ward_code;

    @Basic
    @Column(name = "district_code", nullable = true, length = 255)
    private String district_code;

    @Basic
    @Column(name = "city_code", nullable = true, length = 255)
    private String city_code;

    @Basic
    @Column(name = "ward_name", nullable = true, length = 255)
    private String ward_name;

    @Basic
    @Column(name = "district_name", nullable = true, length = 255)
    private String district_name;

    @Basic
    @Column(name = "city_name", nullable = true, length = 255)
    private String city_name;

    @Basic
    @Column(name = "other", nullable = true, length = 255)
    private String other;

    @Basic
    @Column(name = "status", nullable = true)
    private Short status;
}
