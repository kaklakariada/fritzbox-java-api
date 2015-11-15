package com.github.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "powermeter")
public class PowerMeter {

    @Element(name = "power", required = false)
    private int powerMilliWatt;
    @Element(name = "energy", required = false)
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
