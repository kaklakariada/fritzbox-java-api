/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
 * <br>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <br>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <br>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class allows deserializing {@link String}s to a requested type.
 */
public class Deserializer {
    private static final Logger LOG = LoggerFactory.getLogger(Deserializer.class);
    public static final String XML_VERSION_1_0_ENCODING_UTF_8 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    private final Serializer xmlSerializer;

    public Deserializer() {
        this(new Persister(new Format(XML_VERSION_1_0_ENCODING_UTF_8)));
    }

    Deserializer(Serializer xmlSerializer) {
        this.xmlSerializer = xmlSerializer;
    }

    public <T> T parse(InputStream data, Class<T> resultType) {
        try {
            final T resultObject;
            if (resultType == String.class
                    || resultType == Boolean.class
                    || resultType == Integer.class)
            {
                LOG.trace("Parsing simple type: {}", resultType.getSimpleName());
                resultObject = parseSimpleType(resultType, data);
                LOG.trace("parsed simple type: {} {}", resultType.getSimpleName(), resultObject);
            } else {
                LOG.trace("Parsing type {}", resultType.getSimpleName());
                resultObject = xmlSerializer.read(resultType, data);
            }
            LOG.trace("Parsed response: {}", resultObject);
            return resultObject;
        } catch (final Exception e) {
            throw new DeserializerException("Error parsing response body: " + e.getMessage());
        }
    }

    private <T> T parseSimpleType(Class<T> resultType, InputStream data) throws IOException {
        final String string = getStringFromStream(data);
        if (resultType == String.class) {
            return resultType.cast(string);
        } else if (resultType == Boolean.class) {
            return resultType.cast("1".equals(string));
        } else if (resultType == Integer.class) {
            if (string.isEmpty() || "inval".equals(string) || "-".equals(string)) {
                return null;
            }
            return resultType.cast(Integer.parseInt(string));
        }
        throw new IOException("Type '" + resultType + "' is not supported: " + string);
    }

    public String getStringFromStream(InputStream data) {
        final Scanner scanner = new Scanner(data, StandardCharsets.UTF_8);
        final String string = scanner.next();
        scanner.close();
        return string;
    }

}
