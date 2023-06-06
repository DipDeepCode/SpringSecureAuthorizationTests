package ru.ddc.hellosecurity;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.when;

@TestPropertySource(
        properties = {
                "spring.security.user.password=pass",
                "spring.security.user.name=user",
                "spring.security.enable-csrf=true",
                "spring.security.sessions=if_required"
        }
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;
        RestAssured.filters(new ResponseLoggingFilter());
        RestAssured.filters(new RequestLoggingFilter());
    }

    @Test
    public void apiCallWithoutAuthenticationMustFail() {
        when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}