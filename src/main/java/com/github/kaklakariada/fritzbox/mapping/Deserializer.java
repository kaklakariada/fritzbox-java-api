/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2015 Christoph Pirkl <christoph at users.sourceforge.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.mapping;

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
            throw new DeserializerException("Error parsing response body '" + data + "'", e);
        }
    }
}
