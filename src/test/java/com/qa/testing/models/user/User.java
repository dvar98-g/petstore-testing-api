package com.qa.testing.models.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

/**
 * Representa la entidad User del recurso /user de la Petstore API.
 * Incluye un factory estatico para generar instancias validas sin
 * repetir logica de instanciacion en cada test.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Integer userStatus;

    // Constructor vacio requerido por Jackson para deserializar
    public User() {
    }

    private User(Long id, String username, String firstName, String lastName,
                 String email, String password, String phone, Integer userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    /**
     * Genera un usuario valido con datos unicos (username y email),
     * evitando colisiones entre ejecuciones de tests.
     */
    public static User random() {
        String uniqueSuffix = UUID.randomUUID().toString().substring(0, 8);

        return new User(
                null,
                "user_" + uniqueSuffix,
                "FirstName_" + uniqueSuffix,
                "LastName_" + uniqueSuffix,
                "user_" + uniqueSuffix + "@testmail.com",
                "Pass_" + uniqueSuffix,
                "555-0100",
                1
        );
    }

    /**
     * Permite construir un usuario a partir de otro, sobreescribiendo
     * unicamente el username. Util para tests de actualizacion (PUT).
     */
    public static User withUpdatedUsername(User source, String newUsername) {
        return new User(
                source.getId(),
                newUsername,
                source.getFirstName(),
                source.getLastName(),
                source.getEmail(),
                source.getPassword(),
                source.getPhone(),
                source.getUserStatus()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}