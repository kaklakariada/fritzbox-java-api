package com.github.fritzbox.mapping;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class allows deserializing {@link String}s to a requested type.
 */
public class Deserializer {
    private final static Logger LOG = LoggerFactory.getLogger(Deserializer.class);

    private final Serializer xmlSerializer;

    public Deserializer() {
        this(new Persister());
    }

    Deserializer(Serializer xmlSerializer) {
        this.xmlSerializer = xmlSerializer;
    }

    public <T> T parse(String data, Class<T> resultType) {
        if (resultType == String.class) {
            return resultType.cast(data);
        }
        if (resultType == Boolean.class) {
            return resultType.cast("1".equals(data));
        }
        if (resultType == Integer.class) {
            if (data.isEmpty() || "inval".equals(data)) {
                return null;
            }
            return resultType.cast(Integer.parseInt(data));
        }
        try {
            final T object = xmlSerializer.read(resultType, data);
            LOG.trace("Parsed response: {}", object);
            return object;
        } catch (final Exception e) {
            throw new RuntimeException("Error parsing response body '" + data + "'", e);
        }
    }
}
