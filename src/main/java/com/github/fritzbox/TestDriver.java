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

import com.github.fritzbox.http.CustomHostnameVerifierHttpRequestFactory;
import com.github.fritzbox.http.NullHostnameVerifier;
import com.github.fritzbox.http.TrustSelfSignedCertificates;
import com.github.fritzbox.model.homeautomation.Device;
import com.github.fritzbox.model.homeautomation.DeviceList;
import com.github.fritzbox.model.homeautomation.PowerMeter;

public class TestDriver {
    private final static Logger LOG = LoggerFactory.getLogger(TestDriver.class);

    public static void main(String[] args) throws InterruptedException {
        final Properties config = readConfig(Paths.get("application.properties"));
        TrustSelfSignedCertificates.trustSelfSignedSSL();
        final String hostname = config.getProperty("fritzbox.hostname");
        final String username = config.getProperty("fritzbox.username", null);
        final String password = config.getProperty("fritzbox.password");
        final FritzBoxSession session = new FritzBoxSession(hostname,
                new RestTemplate(new CustomHostnameVerifierHttpRequestFactory(new NullHostnameVerifier())));
        session.login(username, password);
        final HomeAutomation homeAutomation = new HomeAutomation(session);
        final DeviceList devices = homeAutomation.getDeviceListInfos();
        LOG.info("Found {} devices", devices.getDevices().size());
        devices.getDevices().stream().forEach(d -> LOG.info("\t{}", d));

        if (devices.getDevices().isEmpty()) {
            session.logout();
            return;
        }
        while (true) {
            final Device device = homeAutomation.getDeviceListInfos().getDevices().get(0);
            final PowerMeter powerMeter = device.getPowerMeter();
            LOG.debug("State: {}, temp: {}°C, power: {}W, energy: {}Wh",
                    device.getSwitchState().isState() ? "on" : "off", device.getTemperature().getCelsius(),
                    powerMeter.getPowerWatt(), powerMeter.getEnergyWattHours());
            Thread.sleep(1000);
        }
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
