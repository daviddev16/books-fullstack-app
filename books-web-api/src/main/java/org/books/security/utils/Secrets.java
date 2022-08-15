package org.books.security.utils;

import javax.management.InstanceAlreadyExistsException;
import java.io.IOException;
import java.util.Properties;

public final class Secrets {

    private static Secrets secretsInstance;
    private final Properties properties;

    private Secrets(String resource) throws IOException {
        properties = new Properties();
        properties.load(Secrets.class.getResourceAsStream("/" + resource));
        secretsInstance = this;
    }

    public static void init(final String resourceName) throws InstanceAlreadyExistsException {
        if(secretsInstance == null) {
            try {
                new Secrets(resourceName);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new InstanceAlreadyExistsException("Secrets instance already exists.");
    }

    public static String getTokenSecret() {
        return getInstance().getProperties().getProperty("api.token.secret");
    }

    public static int getTokenExpiresAt() {
        return Integer.parseInt(getInstance().getProperties().getProperty("api.token.expiresAt"));
    }

    public static Secrets getInstance() {
        return secretsInstance;
    }

    public Properties getProperties() {
        return properties;
    }
}
