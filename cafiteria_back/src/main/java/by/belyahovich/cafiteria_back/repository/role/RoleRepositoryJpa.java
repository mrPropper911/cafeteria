package by.belyahovich.cafiteria_back.repository.role;

import by.belyahovich.cafiteria_back.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryJpa extends JpaRepository<Role, Long> {

//    @Modifying
    @Query("SELECT r FROM Role r WHERE r.users.id = ?1")
    Role findRoleByUserId(long id);

}
