package com.qa.testing.models.pet;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    private Long id;
    private String name;

    public Category() {
    }

    private Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category random() {
        return new Category(null, "Category_" + System.nanoTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}