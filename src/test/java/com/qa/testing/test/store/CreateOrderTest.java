package com.qa.testing.test.store;

import com.qa.testing.api.PetApi;
import com.qa.testing.api.StoreApi;
import com.qa.testing.constants.Constants;
import com.qa.testing.models.order.Order;
import com.qa.testing.models.pet.Pet;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


/**
 * Verifica la funcionalidad de creacion de una orden para una mascota
 * (requisito 5 de la historia de usuario). El test es independiente:
 * crea su propia mascota como precondicion.
 */

public class CreateOrderTest {

    private final PetApi petApi = new PetApi();
    private final StoreApi storeApi = new StoreApi();

    private Long createdPetId;
    private Long createdOrderId;


    /**
     * Crea una mascota antes de cada test, ya que una orden valida
     * necesita un petId existente.
     */
    @BeforeMethod
    public void createPetForOrder() {
        Pet pet = Pet.random();

        Response createPetResponse = petApi.createPet(pet);
        createdPetId = createPetResponse.as(Pet.class).getId();
    }

    /**
     * Crea una orden para la mascota de la precondicion y verifica, via
     * un GET posterior, que los datos persistidos coinciden con los
     * enviados en la creacion.
     */
    @Test
    public void createOrder_thenOrderIsPersisted() {
        Order newOrder = Order.randomForPet(createdPetId);

        Response createOrderResponse = storeApi.createOrder(newOrder);

        assertThat("Create order status code",
                createOrderResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Order createdOrder = createOrderResponse.as(Order.class);
        createdOrderId = createdOrder.getId();

        assertThat("Created order id", createdOrderId, notNullValue());

        Response getOrderResponse = storeApi.getOrderById(createdOrderId);

        assertThat("Get order status code after creation",
                getOrderResponse.statusCode(), equalTo(Constants.StatusCode.OK));

        Order persistedOrder = getOrderResponse.as(Order.class);

        assertThat("Persisted order", persistedOrder, notNullValue());
        assertThat("Persisted order pet id",
                persistedOrder.getPetId(), equalTo(createdPetId));
        assertThat("Persisted order quantity",
                persistedOrder.getQuantity(), equalTo(newOrder.getQuantity()));
        assertThat("Persisted order status",
                persistedOrder.getStatus(), equalTo(newOrder.getStatus()));
    }

    @AfterMethod
    public void cleanUp() {
        if (createdOrderId != null) {
            storeApi.deleteOrder(createdOrderId);
        }
        if (createdPetId != null) {
            petApi.deletePet(createdPetId);
        }
    }
}