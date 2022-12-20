package nem.com.repository;

import nem.com.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactsRepository extends JpaRepository<Contacts, Short> {
    @Query("SELECT c FROM Contacts c WHERE c.status = 1")
    Contacts getDefaultContact();
}
