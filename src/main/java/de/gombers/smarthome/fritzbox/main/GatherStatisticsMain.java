package de.gombers.smarthome.fritzbox.main;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kaklakariada.fritzbox.EnergyStatisticsService;
import com.github.kaklakariada.fritzbox.FritzBoxException;
import com.github.kaklakariada.fritzbox.HomeAutomation;
import com.github.kaklakariada.fritzbox.TestDriver;
import com.github.kaklakariada.fritzbox.EnergyStatisticsService.EnergyStatsTimeRange;
import com.github.kaklakariada.fritzbox.model.homeautomation.Device;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import com.github.kaklakariada.fritzbox.model.homeautomation.PowerMeter;

public class GatherStatisticsMain {
    private static final Logger LOG = LoggerFactory.getLogger(GatherStatisticsMain.class);

    public void process() {

    }

    public static void main(String[] args) throws InterruptedException {
        final Properties config = readConfig(Paths.get("application.properties"));
        final String url = config.getProperty("fritzbox.url");
        final String username = config.getProperty("fritzbox.username", null);
        final String password = config.getProperty("fritzbox.password");

        LOG.info("Logging in to '{}' with username '{}'", url, username);
        final HomeAutomation homeAutomation = HomeAutomation.connect(url, username, password);

        final DeviceList devices = homeAutomation.getDeviceListInfos();
        LOG.info("Found {} devices", devices.getDevices().size());
        for (final Device device : devices.getDevices()) {
            LOG.info("\t{}", device);
        }
        
        final List<String> ids = homeAutomation.getSwitchList();
        LOG.info("Found {} device ids: {}", ids.size(), ids);
        
        final String ain = devices.getDevices().get(7).getIdentifier();
//        testEnergyStats(homeAutomation, devices.getDevices().get(2).getId());
        testHomeAutomation(homeAutomation, ain);
    }
    
    private static void testEnergyStats(HomeAutomation homeAutomation, String deviceId) {
        final EnergyStatisticsService service = homeAutomation.getEnergyStatistics();
        for (final EnergyStatsTimeRange timeRange : EnergyStatsTimeRange.values()) {
            final String energyStatistics = service.getEnergyStatistics(deviceId, timeRange);
            LOG.debug("Statistics {}: {}", timeRange, energyStatistics);
        }
    }
    
    private static void testHomeAutomation(final HomeAutomation homeAutomation, final String ain)
            throws InterruptedException {
//        homeAutomation.switchPowerState(ain, false);
//        homeAutomation.togglePowerState(ain);
//        LOG.info("Switch {} has present state '{}'", ain, homeAutomation.getSwitchPresent(ain));
//        LOG.info("Switch {} has state '{}'", ain, homeAutomation.getSwitchState(ain));
//        LOG.info("Switch {} uses {}W", ain, homeAutomation.getSwitchPowerWatt(ain));
//        LOG.info("Switch {} has used {}Wh", ain, homeAutomation.getSwitchEnergyWattHour(ain));
//        LOG.info("Switch {} has name '{}'", ain, homeAutomation.getSwitchName(ain));
//        LOG.info("Switch {} has temperature {}°C", ain, homeAutomation.getTemperature(ain));
        LOG.info("Switch {} statistics '{}'", ain, homeAutomation.getBasicStatistics(ain));

        while (true) {
            final List<Device> devices = homeAutomation.getDeviceListInfos().getDevices();
            if (devices.isEmpty()) {
                LOG.warn("No devices found");
                return;
            }
            devices.forEach(GatherStatisticsMain::logDeviceDetails);
            Thread.sleep(1000);
        }
    }
    
    private static void logDeviceDetails(final Device device) {
        final PowerMeter powerMeter = device.getPowerMeter();
        if (device.getSwitchState() == null) {
            return;
        }
        final Object switchState = device.getSwitchState().isOn() ? "on" : "off";
        LOG.debug("State: {}, temp: {}°C, voltage: {}V, power: {}W, energy: {}Wh",
                switchState, device.getTemperature().getCelsius(), powerMeter.getVoltageVolt(),
                powerMeter.getPowerWatt(), powerMeter.getEnergyWattHours());
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
