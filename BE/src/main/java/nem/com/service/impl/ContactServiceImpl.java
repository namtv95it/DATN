package nem.com.service.impl;

import lombok.AllArgsConstructor;
import nem.com.entity.Contacts;
import nem.com.repository.ContactsRepository;
import nem.com.service.ContactService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    ContactsRepository contactsRepository;

    @Override
    public Contacts getDefaultContact() {
        return this.contactsRepository.getDefaultContact();
    }
}
