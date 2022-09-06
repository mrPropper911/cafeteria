package by.belyahovich.cafiteria_back.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "location")
    private String location;

    @Column(name = "phone")
    private int phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(targetEntity = Order.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private List<Order> listOrders;

    public void addOrder(Order order){
        if(isNull(listOrders)){
            order = new Order();
        }
        listOrders.add(order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (phone != user.phone) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(surname, user.surname)) return false;
        if (!Objects.equals(location, user.location)) return false;
        if (!email.equals(user.email)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + phone;
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", location='" + location + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
