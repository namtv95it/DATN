package nem.com.security.userprincipal.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nem.com.entity.Customers;
import nem.com.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
public class CustomerPrinciple implements UserDetails {
    private Integer id;
    private String email;

    @JsonIgnore
    private String password;

    private String fullname;
    private Timestamp birthDate;
    private String phone;
    private String photo;
    private Short status;
    private Roles role;

    public CustomerPrinciple(Integer id, String email, String fullname, String password, String fullname1, Timestamp birthDate, String phone, String photo, Short status, Roles role) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.phone = phone;
        this.photo = photo;
        this.status = status;
        this.role = role;
    }

    public static CustomerPrinciple build(Customers customer) {
        return new CustomerPrinciple(
                customer.getId(),
                customer.getEmail(),
                customer.getFullname(),
                customer.getPassword(),
                customer.getFullname(),
                customer.getBirthDate(),
                customer.getPhone(),
                customer.getPhoto(),
                customer.getStatus(),
                customer.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
