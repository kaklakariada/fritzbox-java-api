/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2017 Christoph Pirkl <christoph at users.sourceforge.net>
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
package com.github.kaklakariada.fritzbox;

import java.io.*;
import java.nio.file.*;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);
    private static final Path DEFAULT_CONFIG = Paths.get("application.properties");
    private final Properties properties;

    private Config(final Properties properties) {
        this.properties = properties;
    }

    public static Config read() {
        final Path file = DEFAULT_CONFIG.normalize();
        return new Config(loadProperties(file));
    }

    private static Properties loadProperties(final Path configFile) {
        if (!Files.exists(configFile)) {
            throw new IllegalStateException("Config file not found at '" + configFile + "'");
        }
        LOG.info("Reading config file from {}", configFile);
        try (InputStream stream = Files.newInputStream(configFile)) {
            final Properties props = new Properties();
            props.load(stream);
            return props;
        } catch (final IOException e) {
            throw new UncheckedIOException("Error reading config file " + configFile, e);
        }
    }

    public String getUrl() {
        return getMandatoryValue("fritzbox.url");
    }

    public String getUsername() {
        return getMandatoryValue("fritzbox.username");
    }

    public String getPassword() {
        return getMandatoryValue("fritzbox.password");
    }

    private String getMandatoryValue(final String param) {
        return getOptionalValue(param)
                .orElseThrow(
                        () -> new IllegalStateException("Property '" + param + "' not found in config file"));
    }

    private Optional<String> getOptionalValue(final String param) {
        return Optional.ofNullable(this.properties.getProperty(param));
    }
}
