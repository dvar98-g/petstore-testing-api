package com.qa.testing.test.pet;

import com.qa.testing.api.PetApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.pet.Pet;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Verifica la funcionalidad de consulta de una mascota especifica
 * (requisito 4 de la historia de usuario). El test es independiente:
 * crea su propia mascota como precondicion.
 */
public class GetPetTest {

    private final PetApi petApi = new PetApi();
    private Long createdPetId;
    private Pet createdPet;

    /**
     * Crea una mascota antes de cada test, para tener un id valido
     * sobre el cual consultar.
     */
    @BeforeMethod
    public void createPet() {
        createdPet = Pet.random();

        Response createResponse = petApi.createPet(createdPet);
        createdPetId = createResponse.as(Pet.class).getId();
    }

    /**
     * Obtiene la mascota creada por su id y verifica que los datos
     * devueltos coincidan con los enviados en la creacion.
     */
    @Test
    public void getPet_byExistingId_returnsPet() {
        Response getResponse = petApi.getPetById(createdPetId);

        assertThat("Get pet status code",
                getResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Pet retrievedPet = getResponse.as(Pet.class);

        assertThat("Retrieved pet", retrievedPet, notNullValue());
        assertThat("Retrieved pet id",
                retrievedPet.getId(), equalTo(createdPetId));
        assertThat("Retrieved pet name",
                retrievedPet.getName(), equalTo(createdPet.getName()));
        assertThat("Retrieved pet status",
                retrievedPet.getStatus(), equalTo(createdPet.getStatus()));
        assertThat("Retrieved pet category name",
                retrievedPet.getCategory().getName(), equalTo(createdPet.getCategory().getName()));
    }

    @AfterMethod
    public void cleanUp() {
        if (createdPetId != null) {
            petApi.deletePet(createdPetId);
        }
    }
}