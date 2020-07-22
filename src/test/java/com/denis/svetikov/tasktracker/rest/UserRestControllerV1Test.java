package com.denis.svetikov.tasktracker.rest;

import org.junit.Test;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserRestControllerV1Test extends BaseRestTest{


    @Test
    public void checkUsersEndpointStatusWithoutAuthToken() {
        prepareGetWithNoAuth(USER_ALL).statusCode(SC_FORBIDDEN);
    }

    @Test
    public void checkGetAllUsersEndpointStatus() {
        prepareGet(USER_ALL).statusCode(SC_OK);
    }

    @Test
    public void checkGetUserByIdEndpointStatus() {
        prepareGet(USER_BY_ID).statusCode(SC_OK);
    }

    @Test
    public void checkSchemaValidity() {
        prepareGet(USER_ALL)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/users_schema.json"));
    }

    @Test
    public void checkResponseTimeAll() {
        prepareGet(USER_ALL)
                .time(lessThan(2000L));
    }

    @Test
    public void checkPutMethod() {
        preparePut(USER_ALL, DUMMY_TEST_JSON)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void checkPostMethod() {
        preparePost(USER_ALL, DUMMY_TEST_JSON)
                .then()
                .statusCode(SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void checkDeleteMethod() {
        prepareDelete(USER_ALL)
                .statusCode(SC_BAD_REQUEST);
    }


}
