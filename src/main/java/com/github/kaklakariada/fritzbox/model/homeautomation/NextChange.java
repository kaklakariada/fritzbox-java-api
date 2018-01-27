package com.github.kaklakariada.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "nextchange")
public class NextChange {

    @Element(name = "endperiod", required = false)
    private int endperiod;

    @Element(name = "tchange", required = false)
    private int tchange;

    public int getEndperiod() {
        return endperiod;
    }

    public void setEndperiod(int endperiod) {
        this.endperiod = endperiod;
    }

    public int getTchange() {
        return tchange;
    }

    public void setTchange(int tchange) {
        this.tchange = tchange;
    }

    @Override
    public String toString() {
        return "NextChange [endperiod=" + endperiod + ", tchange=" + tchange + "]";
    }
}