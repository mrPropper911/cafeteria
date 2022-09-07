package by.belyahovich.cafiteria_back.repository.user;

import by.belyahovich.cafiteria_back.domain.Order;
import by.belyahovich.cafiteria_back.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    @Modifying
    @Query("SELECT o FROM Order o WHERE o.user.id = ?1")
    List<Order> getAllOrdersByUserId(long id);

    //Find user by phone because phone unique
    User findUserByPhone (long userName);
}
