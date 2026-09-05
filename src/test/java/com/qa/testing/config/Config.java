package com.qa.testing.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lee la configuracion del framework. Prioridad: variables de entorno
 * primero, luego config.properties como fallback. No falla si el
 * archivo config.properties no existe, siempre que las env vars
 * necesarias esten presentes (compatibilidad con CI/CD).
 */
public final class Config {

    private static final String CONFIG_FILE_NAME = "config.properties";

    private static final String BASE_URL_KEY = "base.url";
    private static final String DEFAULT_TIMEOUT_SECONDS_KEY = "default.timeout.seconds";

    private static final String BASE_URL;
    private static final int DEFAULT_TIMEOUT_SECONDS;

    static {
        Properties fileProperties = loadPropertiesFile();

        BASE_URL = resolveValue(BASE_URL_KEY, fileProperties);
        DEFAULT_TIMEOUT_SECONDS = Integer.parseInt(
                resolveValue(DEFAULT_TIMEOUT_SECONDS_KEY, fileProperties)
        );
    }

    private Config() {
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static int getDefaultTimeoutSeconds() {
        return DEFAULT_TIMEOUT_SECONDS;
    }

    /**
     * Intenta cargar config.properties desde el classpath. Si no existe,
     * retorna un Properties vacio en vez de fallar, ya que las env vars
     * pueden cubrir todos los valores requeridos (ej. en CI/CD).
     */
    private static Properties loadPropertiesFile() {
        Properties properties = new Properties();

        try (InputStream input = Config.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE_NAME)) {

            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error reading " + CONFIG_FILE_NAME, e);
        }

        return properties;
    }

    /**
     * Resuelve un valor de configuracion dando prioridad a la variable
     * de entorno sobre la propiedad del archivo. Lanza excepcion si
     * ninguna de las dos fuentes provee el valor.
     */
    private static String resolveValue(String key, Properties fileProperties) {
        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String fileValue = fileProperties.getProperty(key);

        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue;
        }

        throw new IllegalStateException(
                "Missing required config value for key: " + key +
                        " (checked env var " + envKey + " and " + CONFIG_FILE_NAME + ")");
    }
}