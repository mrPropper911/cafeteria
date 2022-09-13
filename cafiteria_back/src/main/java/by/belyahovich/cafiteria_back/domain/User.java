package by.belyahovich.cafiteria_back.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Size
    @Column(name = "username")
    private String username;

    @Column(name = "surname")
    private String surname;

    @Column(name = "location")
    private String location;

    @Column(name = "phone")
    private long phone;

    @Column(name = "email")
    private String email;

    @Size(min=5, message = "Не меньше 5 знаков")
    @Column(name = "password")
    private String password;

    @Transient
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> listOrders;

    @Transient
    private String passwordConfirm;

    @Transient
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Role> setRoles;

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
        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(surname, user.surname)) return false;
        if (!Objects.equals(location, user.location)) return false;
        if (!email.equals(user.email)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = (int) (31 * result + phone);
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surname='" + surname + '\'' +
                ", location='" + location + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
//        list.add(new SimpleGrantedAuthority("ROLE_" + setRoles.stream().iterator().hasNext()));
        return getRoles();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Role> getRoles(){
        return setRoles;
    }
}
