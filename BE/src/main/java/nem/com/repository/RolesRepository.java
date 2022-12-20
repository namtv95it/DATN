package nem.com.repository;

import nem.com.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Short> {

    Roles findRolesByName(String name);

}