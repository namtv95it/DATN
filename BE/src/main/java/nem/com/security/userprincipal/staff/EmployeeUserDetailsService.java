package nem.com.security.userprincipal.staff;

import lombok.AllArgsConstructor;
import nem.com.entity.Employees;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.EmployeesRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeesRepository employeesRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employees employee = this.employeesRepository.findByEmail(email);

        if (employee == null) {
            throw new ResourceNotFoundException("Email not found " + email);
        }

        return EmployeePrinciple.build(employee);
    }
}
