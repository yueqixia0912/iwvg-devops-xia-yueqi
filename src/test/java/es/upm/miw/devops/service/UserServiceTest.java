package es.upm.miw.devops.service;

import es.upm.miw.devops.data.UsersDatabase;
import es.upm.miw.devops.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp(){
        userService = new UserService(new UsersDatabase());
    }

    @Test
    void testReadExistingUser(){
        User user = userService.read(1);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("Yueqi");
        assertThat(user.getFamilyName()).isEqualTo("Xia");
    }

    @Test
    void testReadNotExistingUser(){
        assertThatThrownBy(()->userService.read(999)).isInstanceOf(ResponseStatusException.class).
                hasMessageContaining("User not found: 999");
    }

    @Test
    void testFindByCity() {
        List<User> users = userService.find("Madrid", null, null);

        assertThat(users).extracting(User::getId).containsExactly(1L, 2L, 4L, 10L, 11L);
    }

    @Test
    void testFindByProvince() {
        List<User> users = userService.find(null, "Madrid", null);

        assertThat(users).extracting(User::getId).containsExactly(1L, 2L, 4L, 11L);
    }

    @Test
    void testFindBillableUsers() {
        List<User> users = userService.find(null, null, true);

        assertThat(users).extracting(User::getId).containsExactly(1L, 2L, 3L);
    }

    @Test
    void testFindNonBillableUsers() {
        List<User> users = userService.find(null, null, false);

        assertThat(users).extracting(User::getId).containsExactly(4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L);
    }

    @Test
    void testFindByCityAndProvince() {
        List<User> users = userService.find("Madrid", "Madrid", null);

        assertThat(users).extracting(User::getId).containsExactly(1L, 2L, 4L, 11L);
    }

    @Test
    void testFindByCityProvinceAndBillable() {
        List<User> users = userService.find("Madrid", "Madrid", true);

        assertThat(users).extracting(User::getId).containsExactly(1L, 2L);
    }

    @Test
    void testFindNonBillableUsersByCity() {
        List<User> users = userService.find("Valencia", null, false);

        assertThat(users).extracting(User::getId).containsExactly(7L, 8L, 12L);
    }

    @Test
    void testFindWithoutFilters() {
        List<User> users = userService.find(null, null, null);

        assertThat(users).extracting(User::getId).
                containsExactly(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L);
    }

    @Test
    void testDeleteExistingUser() {
        userService.delete(12);

        assertThatThrownBy(() -> userService.read(12)).isInstanceOf(ResponseStatusException.class).
                hasMessageContaining("User not found: 12");
    }

    @Test
    void testDeleteNotExistingUser() {
        assertThatThrownBy(() -> userService.delete(999)).isInstanceOf(ResponseStatusException.class).
                hasMessageContaining("User not found: 999");
    }
}
