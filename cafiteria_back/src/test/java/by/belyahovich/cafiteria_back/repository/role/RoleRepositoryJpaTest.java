package by.belyahovich.cafiteria_back.repository.role;

import by.belyahovich.cafiteria_back.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Role repository Integration Tests")
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryJpaTest {

    @Autowired
    private RoleRepositoryJpa roleRepositoryJpa;

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addRoleForUser.sql"})
    @Test
    public void findAll_WithTwoCount_shouldPropperlyFindAllRole(){
        //give
        int COUNT_OF_ROLE = 2;

        //when
        List<Role> foundRole = roleRepositoryJpa.findAll();

        //then
        assertAll(
                () -> assertThat(foundRole).isNotEmpty(),
                () -> assertThat(foundRole).hasSize(COUNT_OF_ROLE)
        );
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addRoleForUser.sql"})
    @Test
    public void findById_WithExistingRole_shouldPropperlyFindRole(){
        //give
        Role role = new Role();
        role.setName("ROLE_OFICE");
        Role expectedRole = roleRepositoryJpa.save(role);

        //when
        Optional<Role> actualRole = roleRepositoryJpa.findById(expectedRole.getId());

        //then
        actualRole.ifPresent(roleCheck -> assertThat(roleCheck).isEqualTo(expectedRole));
    }

    @Sql(scripts = {"/sql/clearDatabases.sql", "/sql/addRoleForUser.sql"})
    @Test
    public void findRoleByUserId_WithExistingUserID_shouldReturnRoleOfUser(){
        long USER_ID_WITH_ROLE_ADMIN = 2;
        Role expectedRole = new Role();
        expectedRole.setName("ROLE_ADMIN");
        Role actualRoleByUserId = roleRepositoryJpa.findRoleByUserId(USER_ID_WITH_ROLE_ADMIN);

        assertThat(actualRoleByUserId).isNotNull();
        assertThat(actualRoleByUserId.getName()).isEqualTo(expectedRole.getName());
    }

}