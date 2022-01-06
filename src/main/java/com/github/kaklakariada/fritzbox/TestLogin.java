package com.github.kaklakariada.fritzbox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
