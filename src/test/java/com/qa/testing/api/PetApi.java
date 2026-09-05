package com.qa.testing.api;

import com.qa.testing.constants.Constants;
import com.qa.testing.models.pet.Pet;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Ejecuta los requests HTTP contra el recurso /pet de la Petstore API.
 * No realiza ninguna aserción: unicamente dispara el request y retorna
 * la Response cruda para que el test decida que validar.
 */
public class PetApi extends BaseApi {

    public Response createPet(Pet pet) {
        return given()
                .spec(baseRequestSpec())
                .body(pet)
                .when()
                .post(Constants.Endpoints.PET);
    }

    public Response getPetById(Long petId) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.PET_ID_PATH_PARAM, petId)
                .when()
                .get(Constants.Endpoints.PET_BY_ID);
    }

    public Response updatePet(Pet updatedPet) {
        return given()
                .spec(baseRequestSpec())
                .body(updatedPet)
                .when()
                .put(Constants.Endpoints.PET);
    }

    public Response deletePet(Long petId) {
        return given()
                .spec(baseRequestSpec())
                .pathParam(Constants.Params.PET_ID_PATH_PARAM, petId)
                .when()
                .delete(Constants.Endpoints.PET_BY_ID);
    }

    public Response findByStatus(String status) {
        return given()
                .spec(baseRequestSpec())
                .queryParam(Constants.Params.STATUS_QUERY_PARAM, status)
                .when()
                .get(Constants.Endpoints.PET_FIND_BY_STATUS);
    }
}