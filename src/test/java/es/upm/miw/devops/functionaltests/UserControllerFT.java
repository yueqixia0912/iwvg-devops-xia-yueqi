package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class UserControllerFT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadUser() {
        webTestClient.get().uri("/user/1").exchange().expectStatus().isOk().expectBody(User.class).
                value(user -> {
                    assertThat(user).isNotNull();
                    assertThat(user.getId()).isEqualTo(1);
                    assertThat(user.getFirstName()).isEqualTo("Yueqi");
                    assertThat(user.getFamilyName()).isEqualTo("Xia");
                });
    }

    @Test
    void testReadUserNotFound() {
        webTestClient.get().uri("/user/999").exchange().expectStatus().isNotFound();
    }

}
