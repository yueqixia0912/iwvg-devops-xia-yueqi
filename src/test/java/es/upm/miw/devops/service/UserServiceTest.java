package es.upm.miw.devops.service;

import es.upm.miw.devops.data.UsersDatabase;
import es.upm.miw.devops.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

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



}
