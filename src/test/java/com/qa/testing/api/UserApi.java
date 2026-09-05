package com.qa.testing.api;

import com.qa.testing.constants.Constants;
import com.qa.testing.models.user.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Ejecuta los requests HTTP contra el recurso /user de la Petstore API.
 * No realiza ninguna aserción: unicamente dispara el request y retorna
 * la Response cruda para que el test decida que validar.
 */
public class UserApi extends BaseApi {

    public Response createUser(User user) {
        return given()
                .spec(baseRequestSpec())
                .body(user)
                .when()
                .post(Constants.Endpoints.USER);
    }

    public Response getUserByUsername(String username) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.USERNAME_PATH_PARAM, username)
                .when()
                .get(Constants.Endpoints.USER_BY_USERNAME);
    }

    public Response updateUser(String username, User updatedUser) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.USERNAME_PATH_PARAM, username)
                .body(updatedUser)
                .when()
                .put(Constants.Endpoints.USER_BY_USERNAME);
    }

    public Response deleteUser(String username) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.USERNAME_PATH_PARAM, username)
                .when()
                .delete(Constants.Endpoints.USER_BY_USERNAME);
    }

    public Response login(String username, String password) {
        return given()
                .spec(baseRequestSpec())
                .queryParam(Constants.Params.LOGIN_USERNAME_QUERY_PARAM, username)
                .queryParam(Constants.Params.LOGIN_PASSWORD_QUERY_PARAM, password)
                .when()
                .get(Constants.Endpoints.USER_LOGIN);
    }

    public Response logout() {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(Constants.Endpoints.USER_LOGOUT);
    }
}