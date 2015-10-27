package com.github.fritzbox.model.homeautomation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "temperature")
public class Temperature {

    @XmlElement(name = "celsius")
    private int deciCelsius;

    @XmlElement(name = "offset")
    private int offsetDeciCelsius;

    public float getCelsius() {
        return (deciCelsius + offsetDeciCelsius) / 10F;
    }

    @Override
    public String toString() {
        return "Temperatur [celsius=" + getCelsius() + "]";
    }
}
