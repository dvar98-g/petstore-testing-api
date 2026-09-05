package com.qa.testing.models.pet;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {

    private Long id;
    private String name;

    public Tag() {
    }

    private Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Tag random() {
        return new Tag(null, "Tag_" + System.nanoTime());
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
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}