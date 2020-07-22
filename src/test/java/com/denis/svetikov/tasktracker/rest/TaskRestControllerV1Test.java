package com.denis.svetikov.tasktracker.rest;

import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.lessThan;

public class TaskRestControllerV1Test extends BaseRestTest {


    @Test
    public void checkTasksEndpointStatusWithoutAuthToken() {
        prepareGetWithNoAuth(TASK_ALL).statusCode(SC_FORBIDDEN);
    }

    @Test
    public void checkGetAllTasksEndpointStatus() {
        prepareGet(TASK_ALL).statusCode(SC_OK);
    }

    @Test
    public void checkGetTaskByIdEndpointStatus() {
        prepareGet(TASK_ALL).statusCode(SC_OK);
    }

    @Test
    public void checkGetTasksByUserIdEndpointStatus() {
        prepareGet(TASK_ALL).statusCode(SC_OK);
    }

    @Test
    public void checkSchemaValidity() {
        prepareGet(TASK_ALL)
                .assertThat()
                .body(matchesJsonSchemaInClasspath(TASK_JSON_SCHEMA));
    }

    @Test
    public void checkResponseTimeAll() {
        prepareGet(TASK_ALL)
                .time(lessThan(2000L));
    }

    @Test
    public void checkPutMethod() {
        preparePut(TASK_ALL, DUMMY_TEST_JSON)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void checkPostMethod() {
        preparePost(TASK_ALL, DUMMY_TEST_JSON)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void checkDeleteMethod() {
        prepareDelete(TASK_ALL)
                .statusCode(SC_METHOD_NOT_ALLOWED);
    }

}
