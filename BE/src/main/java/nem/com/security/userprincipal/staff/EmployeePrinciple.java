package nem.com.security.userprincipal.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import nem.com.entity.Employees;
import nem.com.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
public class EmployeePrinciple implements UserDetails {
    private Integer id;
    private String email;

    @JsonIgnore
    private String password;

    private String fullname;
    private Timestamp birthDate;
    private String address;
    private String phone;
    private String photo;
    private String note;
    private Short status;
    private Roles role;

    public EmployeePrinciple(Integer id, String email, String password, String fullname, Timestamp birthDate, String address, String phone, String photo, String note, Short status, Roles role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.photo = photo;
        this.note = note;
        this.status = status;
        this.role = role;
    }

    public static EmployeePrinciple build(Employees employee) {
        return new EmployeePrinciple(
                employee.getId(),
                employee.getEmail(),
                employee.getPassword(),
                employee.getFullname(),
                employee.getBirthDate(),
                employee.getAddress(),
                employee.getPhone(),
                employee.getPhoto(),
                employee.getNote(),
                employee.getStatus(),
                employee.getRole()
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
