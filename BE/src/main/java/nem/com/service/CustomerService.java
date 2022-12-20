package nem.com.service;

import nem.com.entity.Customers;

import java.util.List;
import java.util.Optional;                                                                                                          

public interface CustomerService {

    Customers get(Integer id);

    List<Customers> getAll();

    Customers save(Customers customers);

    Customers update(Customers customers);

    void delete(Integer id);

    void updateAllStatusTrue(List<Integer> listId);

    void updateAllStatusFalse(List<Integer> listId);

    Customers findByResetPasswordToken(String token);

    Customers findCustomerByEmail(String email);

    void  updateResetPassword(String email, String token);

    boolean updatePassword(Integer id, String newPassword);

}
