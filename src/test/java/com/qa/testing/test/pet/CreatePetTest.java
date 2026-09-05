package com.qa.testing.test.pet;

import com.qa.testing.api.PetApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.pet.Pet;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePetTest {

    private final PetApi petApi = new PetApi();
    private Long createdPetId;

    @Test
    public void createPet_thenPetIsPersisted() {
        Pet newPet = Pet.random();

        Response createResponse = petApi.createPet(newPet);

        assertThat("Create pet status code",
                createResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Pet createdPet = createResponse.as(Pet.class);
        createdPetId = createdPet.getId();

        assertThat("Created pet id", createdPetId, notNullValue());

        Response getResponse = petApi.getPetById(createdPetId);

        assertThat("Get pet status code after creation",
                getResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Pet persistedPet = getResponse.as(Pet.class);

        assertThat("Persisted pet", persistedPet, notNullValue());
        assertThat("Persisted pet name",
                persistedPet.getName(), equalTo(newPet.getName()));
        assertThat("Persisted pet status",
                persistedPet.getStatus(), equalTo(newPet.getStatus()));
        assertThat("Persisted pet category name",
                persistedPet.getCategory().getName(), equalTo(newPet.getCategory().getName()));
    }

    @AfterMethod
    public void cleanUp() {
        if (createdPetId != null) {
            petApi.deletePet(createdPetId);
        }
    }
}