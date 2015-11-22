package com.github.kaklakariada.fritzbox.model.homeautomation;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Root(name = "devicelist")
public class DeviceList {

    private final static Logger LOG = LoggerFactory.getLogger(DeviceList.class);

    @Attribute(name = "version")
    private String apiVersion;

    @ElementList(name = "device", type = Device.class, inline = true)
    private List<Device> devices;

    public String getApiVersion() {
        return apiVersion;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Device getDeviceByIdentifier(String identifier) {
        for (final Device device : devices) {
            if (device.getIdentifier().equals(identifier)) {
                return device;
            }
        }
        LOG.warn("No device found for identifier '{}'", identifier);
        return null;
    }

    @Override
    public String toString() {
        return "DeviceList [apiVersion=" + apiVersion + ", devices=" + devices + "]";
    }
}
