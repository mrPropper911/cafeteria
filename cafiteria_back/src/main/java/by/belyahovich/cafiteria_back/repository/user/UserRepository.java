package by.belyahovich.cafiteria_back.repository.user;

import by.belyahovich.cafiteria_back.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
