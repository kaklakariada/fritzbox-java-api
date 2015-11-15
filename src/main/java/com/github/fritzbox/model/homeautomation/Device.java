package com.github.fritzbox.model.homeautomation;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "device")
public class Device {

    @Attribute(name = "identifier", required = true)
    private String identifier;
    @Attribute(name = "id")
    private String id;
    @Attribute(name = "functionbitmask")
    private int functionBitmask;
    @Attribute(name = "fwversion")
    private String firmwareVersion;
    @Attribute(name = "manufacturer")
    private String manufacturer;

    @Attribute(name = "productname")
    private String productName;

    @Element(name = "present")
    private boolean present;
    @Element(name = "name")
    private String name;

    @Element(name = "switch")
    private SwitchState switchState;
    @Element(name = "powermeter")
    private PowerMeter powerMeter;
    @Element(name = "temperature")
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
