package nem.com.security.userprincipal.customer;

import lombok.AllArgsConstructor;
import nem.com.entity.Customers;
import nem.com.exception.ResourceNotFoundException;
import nem.com.repository.CustomersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomersRepository customersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customers customer = customersRepository.findCustomersByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found " + email));

        return CustomerPrinciple.build(customer);
    }
}
