package com.github.kaklakariada.fritzbox.mapping;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.github.kaklakariada.fritzbox.model.homeautomation.DeviceList;

public class DeserializerTest {

    @Test
    public void parseDeviceList() throws IOException {
        final String fileContent = Files.readAllLines(Paths.get("src/test/resources/deviceListPayload.xml")).stream()
                .collect(joining("\n"));
        new Deserializer().parse(fileContent, DeviceList.class);
    }
}
