package com.qa.testing.test.user;

import com.qa.testing.api.UserApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.user.User;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


/**
 * Verifica la funcionalidad de logout (requisito 6 de la historia de
 * usuario). El test es independiente: crea su propio usuario y hace su
 * propio login como precondicion.
 */

public class LogoutTest {

    private final UserApi userApi = new UserApi();
    private User existingUser;


    /**
     * Crea un usuario y realiza login antes de cada test, para probar
     * el logout en el contexto de una sesion activa.
     */
    @BeforeMethod
    public void createAndLoginUser() {
        existingUser = User.random();
        userApi.createUser(existingUser);
        userApi.login(existingUser.getUsername(), existingUser.getPassword());
    }

    /**
     * Realiza logout y verifica el status code de la respuesta.
     */
    @Test
    public void logout_afterLogin_returnsOk() {
        Response logoutResponse = userApi.logout();

        assertThat("Logout status code",
                logoutResponse.statusCode(), equalTo(Constants.StatusCode.OK));
    }

    @AfterMethod
    public void cleanUp() {
        userApi.deleteUser(existingUser.getUsername());
    }
}