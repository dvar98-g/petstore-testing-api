package com.qa.testing.test.user;

import com.qa.testing.api.UserApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.user.User;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest {

    private final UserApi userApi = new UserApi();
    private String createdUsername;

    @Test
    public void createUser_thenUserIsPersisted() {
        User newUser = User.random();

        Response createResponse = userApi.createUser(newUser);
        createdUsername = newUser.getUsername();

        assertThat("Create user status code",
                createResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Response getResponse = userApi.getUserByUsername(newUser.getUsername());

        assertThat("Get user status code after creation",
                getResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        User persistedUser = getResponse.as(User.class);

        assertThat("Persisted user", persistedUser, notNullValue());
        assertThat("Persisted username",
                persistedUser.getUsername(), equalTo(newUser.getUsername()));
        assertThat("Persisted email",
                persistedUser.getEmail(), equalTo(newUser.getEmail()));
        assertThat("Persisted first name",
                persistedUser.getFirstName(), equalTo(newUser.getFirstName()));
        assertThat("Persisted last name",
                persistedUser.getLastName(), equalTo(newUser.getLastName()));
    }

    @AfterMethod
    public void cleanUp() {
        if (createdUsername != null) {
            userApi.deleteUser(createdUsername);
        }
    }
}