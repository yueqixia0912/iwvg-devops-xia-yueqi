package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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

    @Test
    void testFindByCity() {
        webTestClient.get().uri("/user?city=Madrid").exchange().expectStatus().isOk().
                expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(1L, 2L, 4L, 10L, 11L));
    }

    @Test
    void testFindByProvince() {
        webTestClient.get().uri("/user?province=Madrid").exchange().expectStatus().isOk().
                expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(1L, 2L, 4L, 11L));
    }

    @Test
    void testFindBillableUsers() {
        webTestClient.get().uri("/user?billable=true").exchange().expectStatus().isOk().
                expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(1L, 2L, 3L));
    }

    @Test
    void testFindNonBillableUsers() {
        webTestClient.get().uri("/user?billable=false").exchange().expectStatus().isOk().
                expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L));
    }

    @Test
    void testFindByCityAndProvince() {
        webTestClient.get().uri("/user?city=Madrid&province=Madrid").exchange().expectStatus().isOk().
                expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(1L, 2L, 4L, 11L));
    }

    @Test
    void testFindByCityProvinceAndBillable() {
        webTestClient.get().uri("/user?city=Madrid&province=Madrid&billable=true").exchange().
                expectStatus().isOk().expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(1L, 2L));
    }

    @Test
    void testFindNonBillableUsersByCity() {
        webTestClient.get().uri("/user?city=Valencia&billable=false").exchange().
                expectStatus().isOk().expectBodyList(User.class).value(users -> assertThat(users).
                        extracting(User::getId).containsExactly(7L, 8L, 12L));
    }

    @Test
    void testFindWithoutFilters() {
        webTestClient.get().uri("/user").exchange().expectStatus().isOk().expectBodyList(User.class).
                value(users -> assertThat(users).extracting(User::getId).
                        containsExactly(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L));
    }

    @DirtiesContext
    @Test
    void testDeleteUser() {
        webTestClient.delete().uri("/user/12").exchange().expectStatus().isNoContent();
    }

    @Test
    void testDeleteUserNotFound() {
        webTestClient.delete().uri("/user/999").exchange().expectStatus().isNotFound();
    }

}
