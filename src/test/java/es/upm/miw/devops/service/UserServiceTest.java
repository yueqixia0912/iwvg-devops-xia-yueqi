package es.upm.miw.devops.service;

import es.upm.miw.devops.data.UsersDatabase;
import es.upm.miw.devops.model.Role;
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

    @Test
    void testUserIsInactiveInitially() {
        User user = userService.read(2);

        assertThat(user.isActive()).isFalse();
    }

    @Test
    void testActivateUser() {
        userService.updateActive(1);

        User user = userService.read(1);

        assertThat(user.isActive()).isTrue();
    }

    @Test
    void testActivateUserNotFound() {
        assertThatThrownBy(() -> userService.updateActive(999))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found: 999");
    }

    @Test
    void testUpdateUser() {
        User user = new User(
                1,
                "Yueqi Updated",
                "Xia Updated",
                "yueqi.updated@gmail.com",
                "12345678A",
                "   ",
                "Madrid",
                "Madrid",
                "28001",
                true
        );

        userService.update(1, user);

        User updated = userService.read(1);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getFirstName()).isEqualTo("Yueqi Updated");
        assertThat(updated.getFamilyName()).isEqualTo("Xia Updated");
        assertThat(updated.getEmail()).isEqualTo("yueqi.updated@gmail.com");
        assertThat(updated.getAddress()).isEqualTo("   ");
        assertThat(updated.getCity()).isEqualTo("Madrid");
        assertThat(updated.getProvince()).isEqualTo("Madrid");
        assertThat(updated.getPostalCode()).isEqualTo("28001");
        assertThat(updated.isActive()).isTrue();

        assertThat(updated.isBillable()).isFalse();
    }

    @Test
    void testUpdateUserNotFound() {
        User user = new User(
                999,
                "Test",
                "User",
                "test@test.com",
                "99999999Z",
                "Test Address",
                "Madrid",
                "Madrid",
                "28000",
                false
        );

        assertThatThrownBy(() -> userService.update(999, user)).isInstanceOf(ResponseStatusException.class).
                hasMessageContaining("User not found: 999");
    }

    @Test
    void testUpdateUserForcesId() {
        User user = new User(
                50,
                "Yueqi Updated",
                "Xia Updated",
                "yueqi.updated@gmail.com",
                "12345678A",
                "Calle Nueva 10",
                "Madrid",
                "Madrid",
                "28001",
                true
        );

        userService.update(1, user);

        User updated = userService.read(1);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getFirstName()).isEqualTo("Yueqi Updated");
        assertThat(updated.getFamilyName()).isEqualTo("Xia Updated");
        assertThat(updated.isActive()).isTrue();
    }

    @Test
    void testUpdateActive() {
        List<User> users = List.of(new User(1, null, null, null,
                        null, null, null, null, null, true),
                new User(2, null, null, null, null,
                        null, null, null, null, true),
                new User(3, null, null, null, null,
                        null, null, null, null, false)
        );

        userService.updateActive(users);

        assertThat(userService.read(1).isActive()).isTrue();
        assertThat(userService.read(2).isActive()).isTrue();
        assertThat(userService.read(3).isActive()).isFalse();
    }

    @Test
    void testUpdateActiveToFalse() {
        userService.updateActive(List.of(new User(2, null, null,
                null, null, null, null, null, null, true)));

        assertThat(userService.read(2).isActive()).isTrue();

        userService.updateActive(List.of(new User(2, null, null,
                null, null, null, null, null, null, false)));

        assertThat(userService.read(2).isActive()).isFalse();
    }

    @Test
    void testUpdateActiveUserNotFound() {
        userService.updateActive(List.of(new User(999, null, null,
                null, null, null, null, null, null, true)));

        assertThatThrownBy(() -> userService.read(999)).isInstanceOf(ResponseStatusException.class).
                hasMessageContaining("User not found: 999");
    }

    @Test
    void testUpdateActiveDoesNotChangeOtherAttributes() {
        User original = userService.read(1);

        userService.updateActive(List.of(new User(1, null, null, null,
                null, null, null, null, null, true)));

        User updated = userService.read(1);

        assertThat(updated.getId()).isEqualTo(original.getId());
        assertThat(updated.getFirstName()).isEqualTo(original.getFirstName());
        assertThat(updated.getFamilyName()).isEqualTo(original.getFamilyName());
        assertThat(updated.getEmail()).isEqualTo(original.getEmail());
        assertThat(updated.getIdentity()).isEqualTo(original.getIdentity());
        assertThat(updated.getAddress()).isEqualTo(original.getAddress());
        assertThat(updated.getCity()).isEqualTo(original.getCity());
        assertThat(updated.getProvince()).isEqualTo(original.getProvince());
        assertThat(updated.getPostalCode()).isEqualTo(original.getPostalCode());
        assertThat(updated.isActive()).isTrue();
    }

    @Test
    void testAdminCannotBeDeactivated() {
        assertThat(userService.read(1).getRole()).isEqualTo(Role.ADMIN);
        assertThat(userService.read(1).isActive()).isTrue();

        userService.updateActive(List.of(new User(1, null, null, null, null,
                        null, null, null, null, false, Role.ADMIN)));

        assertThat(userService.read(1).isActive()).isTrue();
    }

    @Test
    void testAdminCanBeActivated() {
        userService.updateActive(List.of(new User(1, null, null, null, null,
                        null, null, null, null, true, Role.ADMIN)));

        assertThat(userService.read(1).isActive()).isTrue();
    }
}
