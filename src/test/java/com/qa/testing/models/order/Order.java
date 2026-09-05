package com.qa.testing.models.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qa.testing.constants.Constants;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Representa la entidad Order del recurso /store/order de la Petstore
 * API. Incluye un factory estatico para generar instancias validas sin
 * repetir logica de instanciacion en cada test.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    public Order() {
    }

    private Order(Long id, Long petId, Integer quantity, String shipDate,
                  String status, Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    /**
     * Genera una orden valida para la mascota indicada, con cantidad
     * fija, fecha de envio actual en formato ISO-8601, status "placed"
     * y complete en false.
     */
    public static Order randomForPet(Long petId) {
        String isoShipDate = DateTimeFormatter.ISO_INSTANT.format(Instant.now());

        return new Order(
                null,
                petId,
                1,
                isoShipDate,
                Constants.OrderStatus.PLACED,
                false
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate='" + shipDate + '\'' +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }
}