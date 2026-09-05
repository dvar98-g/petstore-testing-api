package com.qa.testing.api;

import com.qa.testing.constants.Constants;
import com.qa.testing.models.order.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Ejecuta los requests HTTP contra el recurso /store de la Petstore API.
 * No realiza ninguna aserción: unicamente dispara el request y retorna
 * la Response cruda para que el test decida que validar.
 */
public class StoreApi extends BaseApi {

    public Response createOrder(Order order) {
        return given()
                .spec(baseRequestSpec())
                .body(order)
                .when()
                .post(Constants.Endpoints.STORE_ORDER);
    }

    public Response getOrderById(Long orderId) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.ORDER_ID_PATH_PARAM, orderId)
                .when()
                .get(Constants.Endpoints.STORE_ORDER_BY_ID);
    }

    public Response deleteOrder(Long orderId) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.ORDER_ID_PATH_PARAM, orderId)
                .when()
                .delete(Constants.Endpoints.STORE_ORDER_BY_ID);
    }

    public Response getInventory() {
        return given()
                .spec(baseRequestSpec())
                .when()
                .get(Constants.Endpoints.STORE_INVENTORY);
    }
}