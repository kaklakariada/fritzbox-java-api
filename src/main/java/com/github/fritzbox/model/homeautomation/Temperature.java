package com.github.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "temperature")
public class Temperature {

    @Element(name = "celsius")
    private int deciCelsius;

    @Element(name = "offset")
    private int offsetDeciCelsius;

    public float getCelsius() {
        return (deciCelsius + offsetDeciCelsius) / 10F;
    }

    @Override
    public String toString() {
        return "Temperatur [celsius=" + getCelsius() + "]";
    }
}
