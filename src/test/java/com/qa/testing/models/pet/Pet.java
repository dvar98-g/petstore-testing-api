package com.qa.testing.models.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qa.testing.constants.Constants;

import java.util.List;
import java.util.UUID;

/**
 * Representa la entidad Pet del recurso /pet de la Petstore API.
 * Incluye un factory estatico para generar instancias validas sin
 * repetir logica de instanciacion en cada test.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pet {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Pet() {
    }

    private Pet(Long id, Category category, String name, List<String> photoUrls,
                List<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    /**
     * Genera una mascota valida con nombre unico, categoria y tag
     * generados, una photo url de ejemplo, y status "available".
     */
    public static Pet random() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);

        return new Pet(
                null,
                Category.random(),
                "Pet_" + uniqueSuffix,
                List.of("https://example.com/photo_" + uniqueSuffix + ".jpg"),
                List.of(Tag.random()),
                Constants.PetStatus.AVAILABLE
        );
    }

    /**
     * Permite construir una mascota a partir de otra, sobreescribiendo
     * unicamente el status. Util para tests de actualizacion (PUT).
     */
    public static Pet withUpdatedStatus(Pet source, String newStatus) {
        return new Pet(
                source.getId(),
                source.getCategory(),
                source.getName(),
                source.getPhotoUrls(),
                source.getTags(),
                newStatus
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }
}