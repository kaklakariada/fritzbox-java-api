package com.github.fritzbox.model.homeautomation;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "devicelist")
public class DeviceList {

    @Attribute(name = "version")
    private String apiVersion;

    @Element(name = "device", type = Device.class)
    private final List<Device> devices = new ArrayList<>();

    public String getApiVersion() {
        return apiVersion;
    }

    public List<Device> getDevices() {
        return devices;
    }

    @Override
    public String toString() {
        return "DeviceList [apiVersion=" + apiVersion + ", devices=" + devices + "]";
    }
}
