package com.qa.testing.constants;

/**
 * Agrupa constantes usadas en el modulo de testing de la Petstore API.
 * Evita hardcodear strings/numeros magicos en tests y clases de api
 * (ver seccion "Extract string literals and magic numbers" del
 * documento de buenas practicas).
 */
public final class Constants {

    private Constants() {
    }

    /**
     * Paths relativos de los endpoints del recurso User.
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
}