package com.github.fritzbox.model.homeautomation;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "devicelist")
public class DeviceList {

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

    @Override
    public String toString() {
        return "DeviceList [apiVersion=" + apiVersion + ", devices=" + devices + "]";
    }
}
