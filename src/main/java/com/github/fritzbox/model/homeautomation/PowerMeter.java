package com.github.fritzbox.model.homeautomation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "powermeter")
public class PowerMeter {

    @XmlElement(name = "power")
    private int powerMilliWatt;
    @XmlElement(name = "energy")
    private int energyWattHours;

    public float getPowerWatt() {
        return powerMilliWatt / 1000F;
    }

    public int getEnergyWattHours() {
        return energyWattHours;
    }

    @Override
    public String toString() {
        return "PowerMeter [energyWattHours=" + energyWattHours + ", powerWatt=" + getPowerWatt() + "]";
    }
}
