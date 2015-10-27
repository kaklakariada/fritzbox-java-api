package com.github.fritzbox.model.homeautomation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "device")
public class Device {

    @XmlAttribute(name = "identifier")
    private String identifier;
    @XmlAttribute(name = "id")
    private String id;
    @XmlAttribute(name = "functionbitmask")
    private int functionBitmask;
    @XmlAttribute(name = "fwversion")
    private String firmwareVersion;
    @XmlAttribute(name = "manufacturer")
    private String manufacturer;

    @XmlAttribute(name = "productname")
    private String productName;

    @XmlElement(name = "present")
    private boolean present;
    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "switch")
    private SwitchState switchState;
    @XmlElement(name = "powermeter")
    private PowerMeter powerMeter;
    @XmlElement(name = "temperature")
    private Temperature temperature;

    public String getIdentifier() {
        return identifier;
    }

    public String getId() {
        return id;
    }

    public int getFunctionBitmask() {
        return functionBitmask;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isPresent() {
        return present;
    }

    public String getName() {
        return name;
    }

    public SwitchState getSwitchState() {
        return switchState;
    }

    public PowerMeter getPowerMeter() {
        return powerMeter;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "Device [identifier=" + identifier + ", id=" + id + ", functionBitmask=" + functionBitmask
                + ", firmwareVersion=" + firmwareVersion + ", manufacturer=" + manufacturer + ", productName="
                + productName + ", present=" + present + ", name=" + name + ", switchState=" + switchState
                + ", powerMeter=" + powerMeter + ", temperature=" + temperature + "]";
    }
}
