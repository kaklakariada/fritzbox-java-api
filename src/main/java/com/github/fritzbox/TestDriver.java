package com.github.fritzbox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.github.fritzbox.http.TrustSelfSignedCertificates;

public class TestDriver {
    private final static Logger LOG = LoggerFactory.getLogger(TestDriver.class);

    public static void main(String[] args) {
        final Properties config = readConfig(Paths.get("application.properties"));
        TrustSelfSignedCertificates.trustSelfSignedSSL();
        final String hostname = config.getProperty("fritzbox.hostname");
        final String username = config.getProperty("fritzbox.username", null);
        final String password = config.getProperty("fritzbox.password");
        final FritzBoxSession session = new FritzBoxSession(hostname, new RestTemplate());
        session.login(username, password);
    }

    private static Properties readConfig(Path file) {
        final Properties config = new Properties();
        LOG.debug("Reading config from file {}", file);
        try (InputStream in = Files.newInputStream(file)) {
            config.load(in);
        } catch (final IOException e) {
            throw new RuntimeException("Error loading configuration from " + file, e);
        }
        return config;
    }
}
