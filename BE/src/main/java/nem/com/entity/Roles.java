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
public class Roles {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Short id;

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Employees> listEmployees;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<Customers> listCustomers;
}
