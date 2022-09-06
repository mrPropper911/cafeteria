package by.belyahovich.cafiteria_back.repository.order;

import by.belyahovich.cafiteria_back.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
