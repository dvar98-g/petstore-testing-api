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


/**
 * Verifica la funcionalidad de login (requisito 2 de la historia de
 * usuario). El test es independiente: crea su propio usuario como
 * precondicion, sin depender de que CreateUserTest se haya ejecutado
 * antes.
 */
public class LoginTest {

    private final UserApi userApi = new UserApi();
    private User existingUser;

    /**
     * Crea un usuario nuevo antes de cada test, para tener credenciales
     * validas con las cuales loguearse.
     */
    @BeforeMethod
    public void createUserForLogin() {
        existingUser = User.random();
        userApi.createUser(existingUser);
    }

    /**
     * Realiza login con las credenciales del usuario creado en la
     * precondicion y verifica el status code y el mensaje de respuesta.
     */
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