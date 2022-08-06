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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample method for doing login
 * 
 * @author Junker Martin
 *
 */
public class TestLogin {

    private static final Logger LOG = LoggerFactory.getLogger(TestLogin.class);

    public static HomeAutomation login() {
        final Properties config = TestLogin.readConfig(Paths.get("application.properties"));
        final String url = config.getProperty("fritzbox.url");
        final String username = config.getProperty("fritzbox.username", null);
        final String password = config.getProperty("fritzbox.password");

        LOG.info("Logging in to '{}' with username '{}'", url, username);
        final HomeAutomation homeAutomation = HomeAutomation.connect(url, username, password);

        return homeAutomation;
    }

    private static Properties readConfig(Path path) {
        final Properties config = new Properties();
        final Path absolutePath = path.toAbsolutePath();
        LOG.debug("Reading config from file {}", absolutePath);
        try (InputStream in = Files.newInputStream(absolutePath)) {
            config.load(in);
        } catch (final IOException e) {
            throw new FritzBoxException("Error loading configuration from " + absolutePath, e);
        }
        return config;
    }
}
