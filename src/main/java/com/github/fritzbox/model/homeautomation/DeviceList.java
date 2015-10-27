package com.github.fritzbox.model.homeautomation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "devicelist")
public class DeviceList {

    @XmlAttribute(name = "version")
    private String version;

    @XmlElement(name = "device", type = Device.class)
    private final List<Device> devices = new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public List<Device> getDevices() {
        return devices;
    }

    @Override
    public String toString() {
        return "DeviceList [version=" + version + ", devices=" + devices + "]";
    }
}
