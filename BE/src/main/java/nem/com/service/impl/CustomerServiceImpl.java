package nem.com.service.impl;

import nem.com.entity.Customers;
import nem.com.entity.Roles;
import nem.com.exception.ResourceNotFoundException;
import nem.com.exception.UniqueFieldException;
import nem.com.repository.CustomersRepository;
import nem.com.repository.RolesRepository;
import nem.com.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomersRepository customersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomersRepository customersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.customersRepository = customersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customers get(Integer id) {
        return customersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng có mã: " + id));
    }

    @Override
    public List<Customers> getAll() {
        return this.customersRepository.fillAllCus();
    }

    @Override
    public List<Customers> getAllByStatus() {
        return this.customersRepository.fillAllByStatus();
    }

    @Override
    public Customers save(Customers customers) {

        customersRepository.findCustomersByEmail(customers.getEmail()).ifPresent(e -> {
            throw new UniqueFieldException("Khách hàng có số email " + customers.getEmail() + " đã tồn tại !");
        });
        customersRepository.findCustomersByPhone(customers.getPhone()).ifPresent(e -> {
            throw new UniqueFieldException("Khách hàng có số điện thoại " + customers.getPhone() + " đã tồn tại !");
        });

        String passwordEncoded = this.passwordEncoder.encode(customers.getPassword());
        Roles role = rolesRepository.findRolesByName("ROLE_CUSTOMER");
        customers.setRole(role);
        customers.setPassword(passwordEncoded);
        customers.setSiginDate(new Timestamp(System.currentTimeMillis()));
        return customersRepository.save(customers);
    }

    @Override
    public Customers update(Customers customers) {
        Customers customer = this.customersRepository.findById(customers.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Id not found " + customers.getId()));

        Roles role = rolesRepository.findRolesByName("ROLE_CUSTOMER");
        customers.setRole(role);
        customers.setSiginDate(customer.getSiginDate());

        return customersRepository.save(customers);
    }

    @Override
    public void delete(Integer id) {
        Customers customers = get(id);
        customers.setStatus((short) 0);
        customersRepository.save(customers);
    }

    @Override
    public void updateAllStatusTrue(List<Integer> listId) {
        listId.forEach(id ->
                customersRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng có mã: " + id))
        );
        customersRepository.updateAllStatusTrue(listId);
    }

    @Override
    public void updateAllStatusFalse(List<Integer> listId) {
        listId.forEach(id ->
                customersRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng có mã: " + id))
        );
        customersRepository.updateAllStatusFalse(listId);
    }

    @Override
    public Customers findByResetPasswordToken(String token) {
        return this.customersRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Customer by token not found"));
    }

    @Override
    public Customers findCustomerByEmail(String email) {
        return this.customersRepository.findCustomersByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException("Email không tồn tại !");
        });
    }

    @Override
    public void updateResetPassword(String email, String token) {
        Customers customer = customersRepository.findCustomersByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException("Customer not found with email " + email);
        });

        customer.setResetPasswordToken(token);
        this.customersRepository.save(customer);
    }

    @Override
    public boolean updatePassword(Integer id, String newPassword) {
        Customers customer = customersRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("Id not found with id " + id);
        });
        try {
            customer.setPassword(newPassword);
            customer.setResetPasswordToken(null);
            this.customersRepository.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
