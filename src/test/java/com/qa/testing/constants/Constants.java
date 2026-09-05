package com.qa.testing.constants;

/**
 * Agrupa constantes usadas en el modulo de testing de la Petstore API.
 * Evita hardcodear strings y numeros magicos en tests y clases de api.
 */
public final class Constants {

    private Constants() {
    }

    /**
     * Paths relativos de los endpoints de los recursos User y Pet.
     * El host/base URL se maneja aparte, en Config.
     */
    public static final class Endpoints {

        private Endpoints() {
        }

        public static final String USER = "/user";
        public static final String USER_BY_USERNAME = "/user/{username}";
        public static final String USER_CREATE_WITH_ARRAY = "/user/createWithArray";
        public static final String USER_CREATE_WITH_LIST = "/user/createWithList";
        public static final String USER_LOGIN = "/user/login";
        public static final String USER_LOGOUT = "/user/logout";

        public static final String PET = "/pet";
        public static final String PET_BY_ID = "/pet/{petId}";
        public static final String PET_FIND_BY_STATUS = "/pet/findByStatus";
    }

    /**
     * Nombres de path/query params, para no tipear los strings
     * directamente en las clases de api.
     */
    public static final class Params {

        private Params() {
        }

        public static final String USERNAME_PATH_PARAM = "username";
        public static final String LOGIN_USERNAME_QUERY_PARAM = "username";
        public static final String LOGIN_PASSWORD_QUERY_PARAM = "password";

        public static final String PET_ID_PATH_PARAM = "petId";
        public static final String STATUS_QUERY_PARAM = "status";
    }

    /**
     * Status codes HTTP usados en las validaciones de los tests.
     * Se agregan a medida que se necesitan, no todos de una.
     */
    public static final class StatusCode {

        private StatusCode() {
        }

        public static final int OK = 200;
        public static final int NOT_FOUND = 404;
        public static final int BAD_REQUEST = 400;
    }

    /**
     * Valores validos del campo status del recurso Pet.
     */
    public static final class PetStatus {

        private PetStatus() {
        }

        public static final String AVAILABLE = "available";
        public static final String PENDING = "pending";
        public static final String SOLD = "sold";
    }
}