package nem.com.service;

import nem.com.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAll();

    Address getOne(Integer id);

    Address findAddressByStatus(Integer customerId);

    Address save(Address address);

    Address update(Integer addressId, Address address);

    void delete(Integer id);

    List<Address> findAddressByCustomerId(Integer customerId);
}
