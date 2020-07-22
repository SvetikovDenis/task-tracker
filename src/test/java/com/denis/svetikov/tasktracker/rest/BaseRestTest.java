package com.denis.svetikov.tasktracker.rest;

import com.denis.svetikov.tasktracker.TasktrackerApplication;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = TasktrackerApplication.class,
        webEnvironment = RANDOM_PORT)
public class BaseRestTest {


    static final String AUTH_LOGIN = "/api/v1/auth/login";
    static final String AUTH_REGISTER = "/api/v1/auth/register";

    static final String USER_ALL = "/api/v1/users";
    static final String USER_BY_ID = "/api/v1/users/1";
    static final String TASK_ALL = "/api/v1/tasks";
    static final String TASK_BY_ID = "/api/v1/tasks/1";
    static final String TASK_BY_USER_ID = "/api/v1/tasks/user/1";

    static final String TASK_JSON_SCHEMA = "schemas/tasks_schema.json";
    static final String USER_JSON_SCHEMA = "schemas/users_schema.json";

    static final String DUMMY_TEST_JSON = "{ \"Test\": \"Test\" }";
    static final long ENDPOINT_RESPONSE_TIME = 200L;

    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "Admin74!";
    private static final String TOKEN_PREFIX = "Bearer_";
    private static String JWT_TOKEN = "";

    @LocalServerPort
    private int port;
    private String HOST_ROOT = "http://localhost:8080";


    @BeforeClass
    public static void setUp() {
        String token =
                given().
                        body("{" +
                                "\"username\" : " + "\"" + USER_NAME + "\",\n" +
                                "\"password\" : " + "\"" + USER_PASSWORD + "\"\n" +
                                "}").
                        when().
                        contentType(ContentType.JSON).
                        post(AUTH_LOGIN).
                        getBody().
                        jsonPath().
                        get("token");

        System.out.println(token);
        JWT_TOKEN = TOKEN_PREFIX + token;
    }

    ValidatableResponse prepareGet(String path) {
        return prepareGetDeleteWhen()
                .get(path)
                .then();
    }

    ValidatableResponse prepareGetWithNoAuth(String path) {
        return prepareGetDeleteWithNoAuthWhen()
                .get(path)
                .then();
    }

    ValidatableResponse prepareDelete(String path) {
        return prepareGetDeleteWhen()
                .delete(HOST_ROOT + path)
                .then();
    }

    Response preparePut(String path, String body) {
        return preparePostPutWhen(body)
                .put(HOST_ROOT + path);
    }

    Response preparePost(String path, String body) {
        return preparePostPutWhen(body)
                .post(HOST_ROOT + path);
    }

    private RequestSpecification preparePostPutWhen(String body) {
        return given()
                .port(port)
                .contentType(ContentType.JSON)
                .header("Authorization", JWT_TOKEN)
                .body(body)
                .when();
    }

    private RequestSpecification prepareGetDeleteWhen() {
        return given()
                .port(port)
                .header("Authorization", JWT_TOKEN)
                .when();
    }

    private RequestSpecification prepareGetDeleteWithNoAuthWhen() {
        return given()
                .port(port)
                .when();
    }


}
