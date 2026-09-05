package com.qa.testing.test.user;

import com.qa.testing.api.UserApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.user.User;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    private final UserApi userApi = new UserApi();
    private User existingUser;

    @BeforeMethod
    public void createUserForLogin() {
        existingUser = User.random();
        userApi.createUser(existingUser);
    }

    @Test
    public void login_withValidCredentials_returnsOk() {
        Response loginResponse = userApi.login(
                existingUser.getUsername(), existingUser.getPassword());

        assertThat("Login status code",
                loginResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        assertThat("Login response message",
                loginResponse.jsonPath().getString("message"),
                containsString("logged in user session"));
    }

    @AfterMethod
    public void cleanUp() {
        userApi.deleteUser(existingUser.getUsername());
    }
}