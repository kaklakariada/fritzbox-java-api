package com.github.kaklakariada.fritzbox.fb6690;

import com.github.kaklakariada.fritzbox.mapping.Deserializer;
import com.github.kaklakariada.fritzbox.model.SessionInfo;
import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FB6690UseCase {

    public static final Logger LOGGER = LoggerFactory.getLogger(FB6690UseCase.class);

    public static void main(String[] args) throws FileNotFoundException {
        testSession();
        testDeviceList();
    }

    private static void testDeviceList() throws FileNotFoundException {
        final Path path = Paths.get("src/test/resources/FritzBox6690/deviceList.xml");
        final File file = path.toFile();
        final InputStream data = new FileInputStream(file);
        Deserializer deserializer = new Deserializer();
        final DeviceList result = deserializer.parse(data, DeviceList.class);
        LOGGER.info("DeviceList {}", result);
    }

    private static void testSession() throws FileNotFoundException {
        final Path path = Paths.get("src/test/resources/FritzBox6690/session.xml");
        final File file = path.toFile();
        final InputStream data = new FileInputStream(file);
        Deserializer deserializer = new Deserializer();
        final SessionInfo result = deserializer.parse(data, SessionInfo.class);
        LOGGER.info("SessionInfo {}", result);
    }
}
