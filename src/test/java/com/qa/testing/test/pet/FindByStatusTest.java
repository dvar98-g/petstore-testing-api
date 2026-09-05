package com.qa.testing.test.pet;

import com.qa.testing.api.PetApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.pet.Pet;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;

public class FindByStatusTest {

    private final PetApi petApi = new PetApi();
    private Long createdPetId;
    private Pet createdPet;

    @BeforeMethod
    public void createAvailablePet() {
        createdPet = Pet.random();

        Response createResponse = petApi.createPet(createdPet);
        createdPetId = createResponse.as(Pet.class).getId();
    }

    @Test
    public void findByStatus_available_includesCreatedPet() {
        Response findResponse = petApi.findByStatus(Constants.PetStatus.AVAILABLE);

        assertThat("Find by status status code",
                findResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Pet[] foundPets = findResponse.as(Pet[].class);

        assertThat("All returned pets have status available",
                Arrays.asList(foundPets),
                everyItem(hasStatus(Constants.PetStatus.AVAILABLE)));

        assertThat("Created pet is present in the results",
                Arrays.stream(foundPets).map(Pet::getId).toList(),
                hasItem(createdPetId));
    }

    private static org.hamcrest.Matcher<Pet> hasStatus(String expectedStatus) {
        return new org.hamcrest.CustomMatcher<>("pet with status " + expectedStatus) {
            @Override
            public boolean matches(Object actual) {
                return actual instanceof Pet pet
                        && expectedStatus.equals(pet.getStatus());
            }
        };
    }

    @AfterMethod
    public void cleanUp() {
        if (createdPetId != null) {
            petApi.deletePet(createdPetId);
        }
    }
}